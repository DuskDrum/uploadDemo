package com.jyt.upload.demo.util.common;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClient431Util {
	
	private static final RequestConfig config;
	
    //连接时间为10秒
    private static final RequestConfig configForNotify;
    
	public static final String DEFAULT_RES_CHARSET = "UTF-8";
	
	public static final String DEFAULT_SEND_CHARSET = "UTF-8";
	
    static {
        config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(60000).build();
    }
	
    static {
    	configForNotify = RequestConfig.custom().setConnectTimeout(10000).
    			setSocketTimeout(10000).setConnectionRequestTimeout(10000).setStaleConnectionCheckEnabled(true).build();
    }
    
    public static String doPost(Map<String, String> params,String url){
        return doPost(params,url,DEFAULT_SEND_CHARSET,DEFAULT_RES_CHARSET);
    }
	

    /**
     * 通知超时时间为10秒
     * @param dataContent
     * @param contentType
     * @param contentCharset
     * @param resCharset
     * @param url
     * @return
     */
    public static String doPostContentForNotify(String dataContent,String contentType,String contentCharset,String resCharset,String url){
    	String result = null;
       	CloseableHttpClient httpClient = getSingleSSLConnection(configForNotify);
       	CloseableHttpResponse response = null;
        if(StringUtil.isBlank(url)){
            throw new RuntimeException("url doesn't exists");
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            
            httpPost.addHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Content-Type", contentType);
            httpPost.addHeader("Connection" , "close");
            HttpEntity reqentity = new StringEntity(dataContent, ContentType.create(contentType, contentCharset) );
            httpPost.setEntity(reqentity);
            
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new Exception("HttpClient,error status code :" + statusCode);
            }
            
            HttpEntity entity = response.getEntity();
            if (entity != null){
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("HttpClient connect fail");
        } finally{
        	if(response!=null) {
                try {
    				response.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        	}
        }
        return result;
    }
    
	 /**
	  * 创建单向ssl的连接
	  * @return
	  * @throws Exception
	  */
	private static CloseableHttpClient getSingleSSLConnection(RequestConfig config) {
		CloseableHttpClient httpClient = null;

		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						@Override
						public boolean isTrusted(
								X509Certificate[] paramArrayOfX509Certificate,
								String paramString) throws CertificateException {
							return true;
						}
					}).build();

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
					.setDefaultRequestConfig(config).build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpClient;

	}
	
		/**
	    * HTTP Post 获取内容
	    * @param params 请求的参数
	    * @param url  请求的url地址 ?之前的地址
	    * @param reqCharset    编码格式
		* @param resCharset    编码格式
	    * @return    页面内容
	    */
	   public static String doPost(Map<String,String> params,String url,String reqCharset,String resCharset){
		   CloseableHttpClient httpClient = getSingleSSLConnection(config);
		   CloseableHttpResponse response = null;
		   if(StringUtil.isBlank(url)){
			   return null;
		   }
		   try {
			   List<NameValuePair> pairs = null;
			   if(params != null && !params.isEmpty()){
				   pairs = new ArrayList<NameValuePair>(params.size());
				   for(Map.Entry<String,String> entry : params.entrySet()){
					   String value = entry.getValue();
					   if(value != null){
						   pairs.add(new BasicNameValuePair(entry.getKey(),value));
					   }
				   }
			   }
			   HttpPost httpPost = new HttpPost(url);
			   httpPost.addHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
			   httpPost.addHeader("Connection" , "close");
			   if(pairs != null && pairs.size() > 0){
				   httpPost.setEntity(new UrlEncodedFormEntity(pairs,reqCharset==null?DEFAULT_SEND_CHARSET:reqCharset));
			   }
			   response = httpClient.execute(httpPost);
			   int statusCode = response.getStatusLine().getStatusCode();
			   if (statusCode != 200) {
				   httpPost.abort();
				   throw new Exception("状态码" + statusCode);
			   }
			   HttpEntity entity = response.getEntity();
			   String result = null;
			   if (entity != null){
				   result = EntityUtils.toString(entity, resCharset==null?DEFAULT_RES_CHARSET:resCharset);
			   }
			   EntityUtils.consume(entity);
			   response.close();
			   return result;
		   }catch (Exception e) {
			   //TODO LOG
		   } finally{
			   if(response!=null)
				   try {
					   response.close();
				   } catch (IOException e) {
				   }
		   }
		   return "";
	   }
	 
	   /**
	    * https
	    * @param params
	    * @param url
	    * @param reqCharset
	    * @param resCharset
	    * @return
	    */
	   public static String httpsPostJSON(Map<String,String> params,String url,String reqCharset,String resCharset){
		   	CloseableHttpClient httpClient = getSingleSSLConnection(config);
		   	CloseableHttpResponse response = null;
		       if(StringUtil.isBlank(url)){
		           return null;
		       }
		       try {
		           String reqStr = JSON.toJSONString(params);
		           
		           HttpPost httpPost = new HttpPost(url);
		           httpPost.addHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
		           httpPost.addHeader("Connection" , "close");
		           
		           StringEntity stringEntiry = new StringEntity(reqStr, reqCharset);
		           httpPost.setEntity(stringEntiry);
		           
		           response = httpClient.execute(httpPost);
		           int statusCode = response.getStatusLine().getStatusCode();
		           if (statusCode != 200) {
		               httpPost.abort();
		           }
		           HttpEntity entity = response.getEntity();
		           String result = null;
		           if (entity != null){
		               result = EntityUtils.toString(entity, resCharset==null?DEFAULT_RES_CHARSET:resCharset);
		           }
		           EntityUtils.consume(entity);
		           response.close();
		           return result;
		       }catch (Exception e) {
		       } finally{
		        	if(response!=null)
		            try {
						response.close();
					} catch (IOException e) {
					}
		        }

		       return "";
		   }
}

