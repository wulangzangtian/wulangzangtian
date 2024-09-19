package wu.lang.wedding.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class OkHttpUtils {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient okHttpClient;

    static {
        okHttpClient = new OkHttpClient.Builder()
                // 是否开启缓存
                .retryOnConnectionFailure(false)
                .connectionPool(new ConnectionPool(60,5L, TimeUnit.MINUTES))
                .connectTimeout(5L, TimeUnit.SECONDS)
                .readTimeout(5L, TimeUnit.SECONDS)
                .writeTimeout(5L,TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .build();
    }



    public static String post(String url, Map<String, Object> params, Map<String, String> headers, String body) {
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request.Builder reqBuild = new Request.Builder();
        reqBuild.url(url);
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                reqBuild.addHeader(entry.getKey(), entry.getValue());
            }
        }
        reqBuild.post(requestBody);
        Response response = null;
        try {
            response = okHttpClient.newCall(reqBuild.build()).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            log.error("okhttp request 出错啦：" + e.fillInStackTrace().toString());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
}
