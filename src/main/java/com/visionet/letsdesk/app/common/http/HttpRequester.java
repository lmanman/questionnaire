package com.visionet.letsdesk.app.common.http;

import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

/**
 * 
 * @author 李晓健
 * @date 2013年10月23日 上午11:49:09
 */
public class HttpRequester {
	private static Logger logger = LoggerFactory.getLogger(HttpRequester.class);
	
	private String defaultContentEncoding;

	public HttpRequester() {
		this.defaultContentEncoding = Charset.defaultCharset().name();
	}

	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	public HttpRespons sendGet(String urlString) throws IOException {
		return this.send(urlString, "GET", null, null);
	}

	/**
	 * 发送GET请求
	 *
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	public HttpRespons sendGet(String urlString, Map<String, String> params,boolean isUTF8)
			throws IOException {
		return this.send(urlString, "GET", params, null);
	}

	/**
	 * 发送GET请求
	 *
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属性
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	public HttpRespons sendGet(String urlString, Map<String, String> params,
			Map<String, String> propertys,boolean isUTF8) throws IOException {
		return this.send(urlString, "GET", params, propertys);
	}

	/**
	 * 发送POST请求
	 *
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	public HttpRespons sendPost(String urlString) throws IOException {
		return this.send(urlString, "POST", null, null);
	}

	/**
	 * 发送POST请求
	 *
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	public HttpRespons sendPost(String urlString, Map<String, String> params,boolean isUTF8)
			throws IOException {
		return this.send(urlString, "POST", params, null);
	}

	/**
	 * 发送POST请求
	 *
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属性
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	public HttpRespons sendPost(String urlString, Map<String, String> params,
			Map<String, String> propertys,boolean isUTF8) throws IOException {
		return this.send(urlString, "POST", params, propertys);
	}

	/**
	 * 发送HTTP请求
	 *
	 * @param urlString
	 * @return 响映对象
	 * @throws java.io.IOException
	 */
	private HttpRespons send(String urlString, String method, Map<String, String> parameters, Map<String, String> propertys)
			throws IOException {

		HttpURLConnection urlConnection = null;
		logger.debug("HttpRequester urlString:"+urlString+"   "+method);
		try {
			if (method.equalsIgnoreCase("GET") && parameters != null) {
				StringBuffer param = new StringBuffer();
				int i = 0;
				for (Map.Entry<String,String> entry : parameters.entrySet()) {
					if (i == 0)
						param.append("?");
					else
						param.append("&");
					param.append(entry.getKey()).append("=").append(entry.getValue());
					i++;
				}
				urlString += param;
			}

			//for HTTP Proxies
			if(CheckProduction()){
				Properties systemProperties = System.getProperties();
				systemProperties.setProperty("proxySet", "true");
				systemProperties.setProperty("proxyHost", PropsUtil.getProperty(PropsKeys.HTTP_PROXYHOST));
				systemProperties.setProperty("proxyPort", PropsUtil.getProperty(PropsKeys.HTTP_PROXYPORT));
			}

			URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setRequestMethod(method);
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setUseCaches(false);

			if (propertys != null)
				for (Map.Entry<String,String> entry : propertys.entrySet()) {
					urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
				}

			if (method.equalsIgnoreCase("POST") && parameters != null) {
				StringBuffer param = new StringBuffer();
				for (Map.Entry<String,String> entry : parameters.entrySet()) {
					param.append("&");
					param.append(entry.getKey()).append("=").append(entry.getValue());
				}
				urlConnection.getOutputStream().write(param.toString().getBytes());
				urlConnection.getOutputStream().flush();
				urlConnection.getOutputStream().close();
			}

			return this.makeContent(urlString, urlConnection);
		} catch (IOException ie) {
			logger.error("HttpRequester send error: ",ie);
			throw ie;
		} finally{
			if (urlConnection != null){
				try {
					urlConnection.disconnect();
				} catch (Exception e2) {
					logger.error(e2.toString());
				}
			}
		}

	}

	/**
	 * 得到响应对象
	 *
	 * @param urlConnection
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	private HttpRespons makeContent(String urlString,HttpURLConnection urlConnection) throws IOException {
		HttpRespons httpResponser = new HttpRespons();
        BufferedReader bufferedReader = null;
		try {
			InputStream in = urlConnection.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(in,defaultContentEncoding));
			httpResponser.contentCollection = new Vector<String>();
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				httpResponser.contentCollection.add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}

			String ecod = urlConnection.getContentEncoding();
			if (ecod == null)
				ecod = defaultContentEncoding;

			httpResponser.urlString = urlString;

			httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
			httpResponser.file = urlConnection.getURL().getFile();
			httpResponser.host = urlConnection.getURL().getHost();
			httpResponser.path = urlConnection.getURL().getPath();
			httpResponser.port = urlConnection.getURL().getPort();
			httpResponser.protocol = urlConnection.getURL().getProtocol();
			httpResponser.query = urlConnection.getURL().getQuery();
			httpResponser.ref = urlConnection.getURL().getRef();
			httpResponser.userInfo = urlConnection.getURL().getUserInfo();
			httpResponser.content = temp.toString();
			httpResponser.contentEncoding = ecod;
			httpResponser.code = urlConnection.getResponseCode();
			httpResponser.message = urlConnection.getResponseMessage();
			httpResponser.contentType = urlConnection.getContentType();
			httpResponser.method = urlConnection.getRequestMethod();
			httpResponser.connectTimeout = urlConnection.getConnectTimeout();
			httpResponser.readTimeout = urlConnection.getReadTimeout();

			return httpResponser;
		} catch (IOException e) {
			throw e;
		} finally {
            if(bufferedReader!=null){
                bufferedReader.close();
            }

		}
	}

	/**
	 * 默认的响应字符集
	 */
	public String getDefaultContentEncoding() {
		return this.defaultContentEncoding;
	}

	/**
	 * 设置默认的响应字符集
	 */
	public void setDefaultContentEncoding(String defaultContentEncoding) {
		this.defaultContentEncoding = defaultContentEncoding;
	}
	
	public static boolean CheckProduction(){
		String domain = PropsUtil.getProperty(PropsKeys.SERVICE_DOMAIN);
		if(domain!=null && domain.indexOf("dxtxq.cn")!=-1){
			return true;
		}
		return false;
	}


    /**
     * 发起https请求并获取结果
     *
     * @author 李晓健
     * @date 2014年7月12日 下午12:17:36
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outputStr
     *            提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     * @return
     */
    public static JSONObject SendRequest(String requestUrl,String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        HttpsURLConnection httpUrlConn = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if (requestMethod.equalsIgnoreCase("GET")) {
                httpUrlConn.connect();
            }
            if(null != outputStr){
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            httpUrlConn.connect();
            inputStream = httpUrlConn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            jsonObject = JSONObject.fromObject(buffer.toString());

        } catch (ConnectException ce) {
            logger.error("Weixin server connection timed out:{}",ce.toString());
        } catch (Exception e) {
            logger.error("https request error:{}", e);
        } finally {
            // 释放资源
            try {
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
                if (null != inputStreamReader) {
                    inputStreamReader.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != httpUrlConn) {
                    httpUrlConn.disconnect();
                }
            } catch (Exception e2) {
                logger.error(e2.toString());
            }
        }
        return jsonObject;
    }

}
