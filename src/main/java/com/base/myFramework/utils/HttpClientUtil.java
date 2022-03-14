package com.base.myFramework.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sx
 * @date: 2022/3/14 14:29
 * @description: httpClient工具类
 */
@Slf4j
public class HttpClientUtil {

    /**
     * 编码格式
     */
    private static final String ENCODING = "UTF-8";

    /**
     * 设置连接超时时间，单位毫秒。
     */
    private static final int CONNECT_TIMEOUT = 15000;

    /**
     * 请求获取数据的超时时间(即响应时间)，单位毫秒。
     */
    private static final int SOCKET_TIMEOUT = 60000;

    private static final String JSON_STATUS_CODE = "statusCode";

    public static JSONObject get(String url) {
        return get(url, null, null);
    }

    /**
     * 发送get请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param params  请求参数
     * @return 响应请求返回json
     */
    public static JSONObject get(String url, Map<String, String> headers, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject responseJson = null;
        CloseableHttpResponse httpResponse = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (params != null && !params.isEmpty()) {
                Set<Map.Entry<String, String>> entrySet = params.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.setConfig(getRequestConfig());
            setHeaders(httpGet, headers);
            responseJson = getResponse(httpClient, httpResponse, httpGet);
        } catch (Exception e) {
            log.error("HTTP GET 请求异常信息记录: {}", e.getMessage());
        } finally {
            release(httpResponse, httpClient);
        }
        return responseJson;
    }

    public static JSONObject postJson(String url) {
        return postJson(url, null, null);
    }

    public static JSONObject postJson(String url, JSONObject params) {
        return postJson(url, null, params);
    }

    /**
     * 发送post请求
     */
    public static JSONObject postJson(String url, Map<String, String> headers, JSONObject params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(getRequestConfig());
        setHeaders(httpPost, headers);
        setJsonParams(httpPost, params);
        JSONObject responseJson = null;
        CloseableHttpResponse httpResponse = null;
        try {
            responseJson = getResponse(httpClient, httpResponse, httpPost);
        } catch (Exception t) {
            log.error("HTTP POST 请求异常信息记录: {}", t.getMessage());
        } finally {
            release(httpResponse, httpClient);
        }
        return responseJson;
    }

    /**
     * 表单方式发送 postJson 请求
     */
    public static JSONObject postForm(String url, Map<String, String> headers, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(getRequestConfig());
        setHeaders(httpPost, headers);
        JSONObject responseJson = null;
        CloseableHttpResponse httpResponse = null;
        try {
            setFormParams(httpPost, params);
            responseJson = getResponse(httpClient, httpResponse, httpPost);
        } catch (Exception t) {
            log.error("HTTP POST 请求异常信息记录: {}", t.getMessage());
        } finally {
            release(httpResponse, httpClient);
        }
        return responseJson;
    }

    /**
     * 发送 put 请求
     */
    public static JSONObject put(String url, Map<String, String> headers, JSONObject jsonObject) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(getRequestConfig());
        setHeaders(httpPut, headers);
        setJsonParams(httpPut, jsonObject);
        JSONObject responseJson = null;
        CloseableHttpResponse httpResponse = null;
        try {
            responseJson = getResponse(httpClient, httpResponse, httpPut);
        } catch (Exception t) {
            log.error("HTTP PUT 请求异常信息记录: {}", t.getMessage());
        } finally {
            release(httpResponse, httpClient);
        }
        return responseJson;
    }

    /**
     * 发送 delete 请求
     */
    public static JSONObject delete(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(getRequestConfig());
        JSONObject responseJson = null;
        CloseableHttpResponse httpResponse = null;
        try {
            responseJson = getResponse(httpClient, httpResponse, httpDelete);
        } catch (Exception e) {
            log.error("HTTP DELETE 请求异常信息记录: ", e);
        } finally {
            release(httpResponse, httpClient);
        }
        return responseJson;
    }

    /**
     * 发送 patch 请求
     */
    public static JSONObject patch(String url, Map<String, String> headers, JSONObject jsonObject) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setConfig(getRequestConfig());
        setHeaders(httpPatch, headers);
        setJsonParams(httpPatch, jsonObject);
        JSONObject responseJson = null;
        CloseableHttpResponse httpResponse = null;
        try {
            responseJson = getResponse(httpClient, httpResponse, httpPatch);
        } catch (Exception e) {
            log.error("HTTP PATCH 请求异常信息记录: ", e);
        } finally {
            release(httpResponse, httpClient);
        }
        return responseJson;
    }

    /**
     * 获取默认请求配置
     *
     * @return
     */
    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
    }

    /**
     * 设置请求头
     *
     * @param httpMethod
     * @param headers
     */
    private static void setHeaders(HttpRequestBase httpMethod, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            Set<Map.Entry<String, String>> entrySet = headers.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 设置 json 请求参数
     *
     * @param httpMethod
     * @param paramsJson
     */
    private static void setJsonParams(HttpEntityEnclosingRequestBase httpMethod, JSONObject paramsJson) {
        if (paramsJson != null && !paramsJson.isEmpty()) {
            StringEntity stringEntity = new StringEntity(paramsJson.toString(), ENCODING);
            stringEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            httpMethod.setEntity(stringEntity);
        }
    }

    /**
     * 设置表单请求参数
     *
     * @param httpMethod
     * @param paramsMap
     * @throws UnsupportedEncodingException
     */
    private static void setFormParams(HttpEntityEnclosingRequestBase httpMethod, Map<String, String> paramsMap) throws UnsupportedEncodingException {
        if (paramsMap != null && !paramsMap.isEmpty()) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            Set<Map.Entry<String, String>> entrySet = paramsMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs, ENCODING));
        }
    }

    /**
     * 获取响应结果
     *
     * @param httpClient
     * @param httpResponse
     * @param httpMethod
     * @return
     * @throws IOException
     */
    private static JSONObject getResponse(HttpClient httpClient, HttpResponse httpResponse, HttpRequestBase httpMethod) throws IOException {
        httpResponse = httpClient.execute(httpMethod);
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                String content = EntityUtils.toString(httpEntity, Charset.forName(ENCODING));
                JSONObject responseJson = JSON.parseObject(content);
                log.debug("打印 {} 请求返回内容: {}", httpMethod.getMethod(), responseJson);
                if (responseJson != null) {
                    responseJson.put(JSON_STATUS_CODE, httpResponse.getStatusLine().getStatusCode());
                }
                return responseJson;
            }
        }
        return new JSONObject();
    }

    /**
     * 释放资源
     *
     * @param httpResponse
     * @param httpClient
     */
    private static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) {
        if (httpResponse != null) {
            try {
                httpResponse.close();
            } catch (IOException e) {
                log.error("httpResponse close fail!", e);
            }
        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("httpClient close fail!", e);
            }
        }
    }

}
