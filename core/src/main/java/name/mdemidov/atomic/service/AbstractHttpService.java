package name.mdemidov.atomic.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import name.mdemidov.atomic.param.Param;
import name.mdemidov.atomic.param.Params;
import name.mdemidov.atomic.service.exception.ServiceCallException;
import rp.org.apache.http.client.config.RequestConfig;
import rp.org.apache.http.client.methods.CloseableHttpResponse;
import rp.org.apache.http.client.methods.HttpUriRequest;
import rp.org.apache.http.client.protocol.HttpClientContext;
import rp.org.apache.http.impl.client.CloseableHttpClient;
import rp.org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.stream.Stream;

@Slf4j
@Getter
public class AbstractHttpService {

    protected static final Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        .create();
    private static final int DEFAULT_WAIT_TIMEOUT = Integer.parseInt(Params.get(Param.DEFAULT_WAIT_TIMEOUT));
    private static final RequestConfig CONFIG = RequestConfig.custom()
        .setConnectionRequestTimeout(DEFAULT_WAIT_TIMEOUT * 1000)
        .setConnectTimeout(DEFAULT_WAIT_TIMEOUT * 1000)
        .setSocketTimeout(DEFAULT_WAIT_TIMEOUT * 1000)
        .build();
    private HttpClientContext context;

    public AbstractHttpService(@NonNull HttpClientContext context) {
        this.context = context;
    }

    private static CloseableHttpClient client() {
        return HttpClientBuilder.create().setDefaultRequestConfig(CONFIG).build();
    }

    protected CloseableHttpResponse execute(@NonNull HttpUriRequest request) {
        log.debug("Execute {}", request);
        log.trace("With headers:");
        Stream.of(request.getAllHeaders()).forEach(h -> log.trace("{}={}", h.getName(), h.getValue()));
        if (context.getCookieStore() != null) {
            log.trace("With cookies:");
            context.getCookieStore().getCookies().forEach(c -> log.trace("{}={}", c.getName(), c.getValue()));
        }
        try {
            val response = client().execute(request, context);
            log.debug("Got response: {}", response.getStatusLine());
            return response;
        } catch (IOException e) {
            throw new ServiceCallException("Failed to execute " + request, e);
        }
    }
}
