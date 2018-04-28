package com.ladon.miguapp.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ladon.common.utils.BusinessException;

/**
 * <p>UserCenterHttpInvoker</p>
 *
 * <p>报文封装、解析、发送中心</p>
 * 
 * @ClassName UserCenterHttpInvoker
 * @author duanyong
 * @version 1.0
 *
 */
public class AppHttpInvoker {
	private static final Logger appLogger = LoggerFactory.getLogger("APP_HTTP_INVOKER");
	private Integer timeout = 15000;

	
	public String httpGet(String serviceUrl, Map<String, String> headerMap, Map<String, String> paraMap) throws BusinessException {
		appLogger.info("httpGet(String) request, serviceUrl={}, header={}, para={}", new Object[]{serviceUrl, headerMap, paraMap});
		GetMethod getMethod = null;
		InputStream inputStream = null;
		try {
			HttpClient httpClient = new HttpClient();
			if (timeout != null) {
				HttpConnectionManager httpConnectionManager = httpClient.getHttpConnectionManager();
				HttpConnectionManagerParams httpConnectionManagerParams = httpConnectionManager.getParams();
				httpConnectionManagerParams.setConnectionTimeout(timeout);
				httpConnectionManagerParams.setSoTimeout(timeout);
			}
			
			StringBuffer paraBuffer = new StringBuffer();
			for (Map.Entry<String, String> entry : paraMap.entrySet()) {
				paraBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			String paraString = paraBuffer.toString();
			String queryString = serviceUrl + "?" + paraString.substring(0, paraString.length() - 1);
			
			getMethod = new GetMethod(queryString);
			
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				getMethod.addRequestHeader(entry.getKey(), entry.getValue());
			}
			
			try {
				int status = httpClient.executeMethod(getMethod);
				if (HttpStatus.SC_OK != status) {
					throw new BusinessException("664021", "HTTP请求返回状态失败, status=" + status);
				}
			} catch (HttpException e) {
				throw new BusinessException("664022", "HTTP请求HTTP异常", e);
			} catch (IOException e) {
				throw new BusinessException("664023", "HTTP请求IO异常", e);
			}
			try {
				inputStream = getMethod.getResponseBodyAsStream();
			} catch (IOException e) {
				throw new BusinessException("664033", "HTTP响应IO异常", e);
			}
			String resultString = null;
			try {
				resultString = readStringFromStream(inputStream);
			} catch (IOException e) {
				throw new BusinessException("664034", "HTTP响应IO读取异常", e);
			}
			appLogger.info("httpGet(String) response, serviceUrl={}, header={}, para={}, resultString={}", new Object[]{serviceUrl, headerMap, paraMap, resultString});
			return resultString;
		} catch (BusinessException e) {
			appLogger.error("httpGet(String) catch BusinessException,  serviceUrl=" + serviceUrl + ", header=" + headerMap + ", para=" + paraMap, e);
			throw e;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
	}

	protected String readStringFromStream(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

}