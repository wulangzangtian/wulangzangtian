package wu.lang.wedding.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author 寒
 * @date 2021/12/6
 */
@Slf4j
public class HttpUtils {


    public static final Executor executor = Executors.newFixedThreadPool(8);

    public static String post(String url, Map<String, Object> params, Map<String, String> headers, String body){
        CloseableHttpClient client = null;
        CloseableHttpResponse ret = null;
        try {
            client = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).setConnectionRequestTimeout(3000).setRedirectsEnabled(false).build();
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            if (params != null && params.size() > 0) {
                List<NameValuePair> formParams = new ArrayList<>();
                for (String key : params.keySet()) {
                    Object value = params.get(key);
                    if (value != null) {
                        formParams.add(new BasicNameValuePair(key, String.valueOf(value)));
                    }
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                post.setEntity(entity);
            }
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }
            if(body!=null){
                post.setEntity(new StringEntity(body,Consts.UTF_8));
            }
            ret = client.execute(post);
            HttpEntity response = ret.getEntity();
            return EntityUtils.toString(response, Consts.UTF_8);
        } catch (HttpHostConnectException e) {
            log.error("游戏服未开启 http_api close");
            return null;
        } catch (Exception e) {
            log.error("请求失败,url:" + url + ",params:" + params, e);
            return null;
        } finally {
            if (ret != null) {
                try {
                    ret.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取body内容
     *
     * @param request
     * @return
     */
    public static String getBodyData(HttpServletRequest request) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine()))
                data.append(line);
        } catch (IOException e) {
        } finally {
        }
        return data.toString();
    }

    public static String post(String url, Map<String, Object> params, Map<String, String> headers, String contentTypeStr, int timeout) {
        CloseableHttpClient client = null;
        CloseableHttpResponse ret = null;
        try {
            client = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout)
                    .setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).setRedirectsEnabled(false).build();
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);

            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }
            String jsonStr = JSON.toJSONString(params);
            StringEntity entity = new StringEntity(jsonStr, Consts.UTF_8);
            entity.setContentEncoding("UTF-8");
            entity.setContentType(contentTypeStr);//发送json数据需要设置contentType
            post.setEntity(entity);
            ret = client.execute(post);
            HttpEntity response = ret.getEntity();
            return EntityUtils.toString(response, Consts.UTF_8);
        } catch (Exception e) {
            log.error("请求失败,url:" + url + ",params:" + params, e);
            return null;
        } finally {

            if (ret != null) {
                try {
                    ret.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
