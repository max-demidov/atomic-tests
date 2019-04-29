package name.mdemidov.atomic.example.service.some;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import name.mdemidov.atomic.example.env.Env;
import name.mdemidov.atomic.example.service.some.object.Response;
import name.mdemidov.atomic.example.service.some.object.SomeData;
import name.mdemidov.atomic.param.Param;
import name.mdemidov.atomic.param.Params;
import name.mdemidov.atomic.service.AbstractHttpService;
import rp.org.apache.http.client.methods.HttpGet;
import rp.org.apache.http.client.protocol.HttpClientContext;
import rp.org.apache.http.util.EntityUtils;

import java.io.IOException;

@Slf4j
public class SomeService extends AbstractHttpService {

    public static final String BASE_URL = Env.valueOf(Params.get(Param.ENV)).getStartUrl();

    public SomeService() {
        super(new HttpClientContext());
    }

    public SomeData getSomeData() throws IOException {
        val url = BASE_URL + "/some/data.json";
        val request = new HttpGet(url);
        val response = execute(request);
        val entity = response.getEntity();
        val text = EntityUtils.toString(entity);
        log.trace("Response JSON from {}:\n{}", url, text);
        return GSON.fromJson(text, Response.class).getSomeData();
    }
}
