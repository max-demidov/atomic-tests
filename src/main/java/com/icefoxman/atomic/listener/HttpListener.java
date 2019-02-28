package com.icefoxman.atomic.listener;

import static com.icefoxman.atomic.browser.Name.CHROME;
import static org.testng.ITestResult.FAILURE;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.icefoxman.atomic.browser.Browser;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.openqa.selenium.logging.LogEntry;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpListener implements IInvokedMethodListener {

    private static final String REQUEST_TYPE = "Network.requestWillBeSent";
    private static final String RESPONSE_TYPE = "Network.responseReceived";
    private static final List<String> ENTRY_TYPES = Arrays.asList(REQUEST_TYPE, RESPONSE_TYPE);
    private static final int RESPONSE_TIME_LIMIT = 10; // in seconds
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static String parseUrl(@NonNull JsonObject message) {
        try {
            val params = message.getAsJsonObject("params");
            val entry = params.has("response") ? params.getAsJsonObject("response") : params.getAsJsonObject("request");
            return entry.get("url").getAsString();
        } catch (Exception ignored) {
            return "<Unknown URL>";
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // do nothing
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (testResult.getStatus() != FAILURE || Browser.name() != CHROME) {
            return;
        }
        val logs = Browser.getPerformanceLogs();
        try {
            processPerformanceLogs(logs);
        } catch (Exception e) {
            log.warn("Failed to process performance logs", e);
        }
    }

    private void processPerformanceLogs(List<LogEntry> logs) {
        val notRespondedRequests = new HashMap<String, JsonObject>();
        val responseCodes = new HashMap<JsonObject, Integer>();
        val responseTimes = new HashMap<JsonObject, Double>();

        for (val entry : logs) {
            val msg = entry.getMessage();
            val json = GSON.fromJson(msg, JsonObject.class);
            val message = json.getAsJsonObject("message");
            val method = message.get("method").getAsString();

            if (!ENTRY_TYPES.contains(method)) {
                continue;
            }

            val params = message.getAsJsonObject("params");
            val requestId = params.get("requestId").getAsString();

            if (REQUEST_TYPE.equals(method)) {
                notRespondedRequests.put(requestId, message);
                continue;
            }

            notRespondedRequests.remove(requestId);

            val response = params.getAsJsonObject("response");
            val status = response.get("status").getAsInt();
            if (status != 200) {
                responseCodes.put(message, status);
            }

            if (!response.has("timing")) {
                continue; // some log entries don't have 'timing' node
            }

            val timing = response.get("timing").getAsJsonObject();
            val receiveHeadersEnd = timing.get("receiveHeadersEnd").getAsDouble();
            if (receiveHeadersEnd > RESPONSE_TIME_LIMIT * 1_000) {
                responseTimes.put(message, receiveHeadersEnd);
            }
        }

        if (!notRespondedRequests.isEmpty()) {
            log.info("There are requests left without response");
            notRespondedRequests.values().forEach(r -> {
                log.debug("Request without response for {}", parseUrl(r));
                log.trace("Request:\n{}", GSON.toJson(r));
            });
        }

        if (!responseCodes.isEmpty()) {
            log.info("There are responses with status other than 200");
            responseCodes.forEach((k, v) -> {
                log.debug("Response code is {} for {}", v, parseUrl(k));
                log.trace("Response:\n{}", GSON.toJson(k));
            });
        }

        if (!responseTimes.isEmpty()) {
            log.info("There are response times bigger than {} sec", RESPONSE_TIME_LIMIT);
            responseTimes.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())) // desc sort by response time
                .forEach(e -> {
                    log.debug("Response time is {} ms for {}", e.getValue(), parseUrl(e.getKey()));
                    log.trace("Response:\n{}", GSON.toJson(e.getKey()));
                });
        }
    }
}
