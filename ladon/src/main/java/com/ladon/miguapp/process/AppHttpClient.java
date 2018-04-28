package com.ladon.miguapp.process;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.ladon.miguapp.utils.AndroidVersion;
import com.ladon.miguapp.utils.DES;
import com.ladon.miguapp.utils.IMEI;
import com.ladon.miguapp.utils.UA;

public class AppHttpClient {
	//wap:0140912
	private static final String CHANNEL = "0140912";
	private static final String SUB_CHANNEL = "yy01";
	private static final String APP_VERSION_NAME = "4.3.1.1";
	private static final String APP_VERSION_CODE = "93";
	private static final String SERVER_HOST_URL = "http://a.mll.migu.cn/rdp2/v5.5/serverHost.do";
	private static final String UPDATE_CHECK_URL = "http://a.mll.migu.cn/rdp2/v5.5/update_check.do";
	private static final String REPORT_URL = "http://a.mll.migu.cn/rdp2/v5.5/push/report.do";
	private AppHttpInvoker invoker = new AppHttpInvoker();
	
	/**
	 * <p>模拟客户端启动</p>
	 * @return void
	 */
	public void requestStartUp(String subChannel) {
		try {
			String imei = IMEI.getIMEI();
		    String imsi = IMEI.getImsi();
		    String ua = UA.randomUA();
		    String androidVersion = AndroidVersion.randomVersion();
	    
		    this.requestServerHost(imei, imsi, ua, androidVersion, subChannel);
			this.requestUpdateCheck(imei, imsi, ua, androidVersion, subChannel);
			this.requestReport(imei, imsi, ua, androidVersion, subChannel);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>发起server_host请求</p>
	 * @param imei
	 * @param imsi
	 * @param ua
	 * @throws Exception 
	 */
	private String requestServerHost(String imei, String imsi, String ua, String androidVersion, String subChannel) throws Exception {
		Map<String, String> headerMap = this.getHttpHeader(null, imei, imsi, ua, androidVersion, subChannel);
	    Map<String, String> paraMap = this.getHttpPara();
	    
	    String result = this.invoker.httpGet(SERVER_HOST_URL, headerMap, paraMap);
	    return result;
	}

	/**
	 * <p>发起update_check请求</p>
	 * @return
	 * @return String
	 * @throws Exception 
	 */
	private String requestUpdateCheck(String imei, String imsi, String ua, String androidVersion, String subChannel) throws Exception {
	    String headerString = getUpdateHeader(imei, imsi, ua, androidVersion);
	    Map<String, String> customHeaderMap = null;
	    String encodeString = DES.encode(DES.S_DES_KEY, headerString);
        if (StringUtils.isNotBlank(encodeString)) {
        	customHeaderMap = new HashMap<String, String>();
            customHeaderMap.put("up", encodeString);
        }
	    
	    Map<String, String> headerMap = this.getHttpHeader(customHeaderMap, imei, imsi, ua, androidVersion, subChannel);
	    Map<String, String> paraMap = this.getHttpPara();
	    
	    String result = this.invoker.httpGet(UPDATE_CHECK_URL, headerMap, paraMap);
	    return result;
	}

	/**
	 * <p>发起report请求</p>
	 * @param imei
	 * @param imsi
	 * @param ua
	 * @throws Exception 
	 */
	private String requestReport(String imei, String imsi, String ua, String androidVersion, String subChannel) throws Exception {
		Map<String, String> headerMap = this.getHttpHeader(null, imei, imsi, ua, androidVersion, subChannel);
	    Map<String, String> paraMap = this.getHttpPara();
	    
	    String result = this.invoker.httpGet(REPORT_URL, headerMap, paraMap);
	    return result;
	}
	
	/**
	 * <p>组装update_check.do请求的头信息</p>
	 * @param imei
	 * @param imsi
	 * @return
	 * @return String
	 */
	private String getUpdateHeader(String imei, String imsi, String ua, String androidVersion){
	    JSONObject jsonObject = new JSONObject();
        jsonObject.put("ad", imei + "-" + imsi + "-" + IMEI.getMac());
        jsonObject.put("db", ua);//"手机品牌"
        jsonObject.put("dm", "device model"); //device model
        jsonObject.put("sversion", androidVersion);//"系统版本号"
        
        jsonObject.put("AVC", APP_VERSION_NAME + "|" + APP_VERSION_CODE);
        jsonObject.put("ALevel", "32");
        jsonObject.put("SO","");

	    return jsonObject.toString();
	}

	/**
	 * <p>组装http请求的默认头信息</p>
	 * @param headerArr
	 * @return
	 * @throws Exception 
	 */
	private Map<String, String> getHttpHeader(Map<String, String> customHeaderMap, String imei, String imsi, 
			String ua, String androidVersion, String subChannel) throws Exception {
		Map<String, String> headerMap = new TreeMap<String, String>();
		
		String mobile = "";
		if (StringUtils.isNotBlank(mobile)) {
			headerMap.put("migu-x-up-calling-line-id", mobile);
		}
		
		String uid = "";
		if (StringUtils.isNotBlank(uid)) {
			headerMap.put("uid", uid);
		}
		
		String passId = "";
		if (StringUtils.isNotBlank(passId)) {
			headerMap.put("passId", passId);
		}
		
		String uSessionID = "";
		if (StringUtils.isNotBlank(uSessionID)) {
			headerMap.put("uSessionID", uSessionID);
		}
		
		String member = "";
		if (StringUtils.isNotBlank(member)) {
			headerMap.put("member", member);
		}
		
		String memberType = "";
		if (StringUtils.isNotBlank(memberType)) {
			headerMap.put("memberType", memberType);
		}
		
		String randomsessionkey = "";
		if (StringUtils.isNotBlank(randomsessionkey)) {
			headerMap.put("randomsessionkey", randomsessionkey);
		}
			
		headerMap.put("mode", "android");
		headerMap.put("imei", DES.encode(DES.S_DES_KEY, imei));
		headerMap.put("imsi", DES.encode(DES.S_DES_KEY, imsi));
		
		String roProductName = ua;
		if (StringUtils.isNotBlank(roProductName)) {
			headerMap.put("ro-product-name", roProductName);
		}
		
		headerMap.put("channel", CHANNEL);
		headerMap.put("subchannel", subChannel);
		
		headerMap.put("sst-user-agent", ua);
		
		String sstNetworkType = "wlan";
		headerMap.put("sst-Network-type", sstNetworkType);
		
		String sstNetworkStandard = "04";
		headerMap.put("sst-Network-standard", sstNetworkStandard);
		
		String locationData = "";
		headerMap.put("location-data", locationData);
		
		String unifiedPayAppid = "22000603";
		headerMap.put("unified_pay_appid", unifiedPayAppid);
		
		String locationInfo = "_";
		headerMap.put("location-info", locationInfo);
		
		StringBuffer sb = new StringBuffer();
		sb.append("Mozilla/5.0 (Linux; U; ");
		sb.append("Android ").append(androidVersion).append("; ");
		sb.append("zh-cn; ");
		sb.append(ua);
		sb.append(" Build) ");
		sb.append("AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
		headerMap.put("User-Agent", sb.toString());
		
		if (customHeaderMap != null) {
			headerMap.putAll(customHeaderMap);
		}
		return headerMap;
	}
	
	/**
	 * <p>组装http参数</p>
	 * @return
	 * @return Map<String,String>
	 */
	private Map<String, String> getHttpPara() {
		Map<String, String> paraMap = new HashMap<String, String>();
	    paraMap.put("ua", "Android_sst");
	    paraMap.put("version", "4.3110");
	    return paraMap;
	}
	
	
	/**
	 * <p>RequestHandler</p>
	 *
	 * <p>模拟请求线程处理器</p>
	 * 
	 * @ClassName RequestHandler
	 * @author duanyong
	 * @version 1.0
	 *
	 */
	static class RequestHandler implements Runnable {
		private int requestCnt;
		private String subChannel;
		
		public RequestHandler(int requestCnt, String subChannel) {
			this.requestCnt = requestCnt;
			this.subChannel = subChannel;
		}
		
		@Override
		public void run() {
			AppHttpClient client = new AppHttpClient();
			Random random = new Random();
			
			System.out.println("********" + requestCnt + "********"); 
			for (int i = 0; i < requestCnt; i++) {
				System.out.println("********" + subChannel + "=" + i + "********"); 
				client.requestStartUp(subChannel);
				try {
					Thread.sleep(random.nextInt(60) * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	
	/**
	 * <p>模拟请求</p>
	 * @param args
	 * @throws InterruptedException
	 * @return void
	 */
	public static void main(String[] args) throws InterruptedException {
		RequestHandler handler = new RequestHandler(1, "");
		new Thread(handler).start();
		
		RequestHandler handlerSub = new RequestHandler(1234, SUB_CHANNEL);
		new Thread(handlerSub).start();
		
	}
}
