package com.ecarinfo.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ecarinfo.utils.ProxyIPCollection.ProxyHost;


public class HainanTrafficUtils {
	private static Logger logger = Logger.getLogger(HainanTrafficUtils.class);
	private static Map<String, String> headers = new HashMap<String, String>();
	private static String hainanjj_host = "http://www.hainanjj.gov.cn";
	static {
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		headers.put("Accept-Encoding", "gzip,deflate,sdch");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		headers.put("Cache-Control", "max-age=0");
		headers.put("Connection", "keep-alive");
		headers.put("Host", hainanjj_host);
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");		
	}
	public static void init(String hainanjj) {
		hainanjj_host = hainanjj;
		headers.put("Host", hainanjj_host);
	}
	
	public static List<HainanTrafficInfo> getTrafficInfos(String carNo, String frameNo) throws IOException {	
		
		if (StringUtils.isEmpty(carNo) || StringUtils.isEmpty(frameNo) || frameNo.length()<4 ) {
			return null;
		}
		if (!carNo.startsWith("琼")) {
			return null; //暂时只查海南的车牌
		}
		//使用代理
		ProxyHost ipport = ProxyIPCollection.getProxyIpAddress();
		if (ipport==null) {
			logger.error("------------------没有可用的代理ip了");
			throw new IOException("没有可用的代理ip了");
		}
		
		HttpHost proxy = new HttpHost(ipport.getHost(), ipport.getPort());
		
		logger.info("-------------user proxy:" + ipport);
		
		//创建session
		HttpResponse response = null;
		String html = null;
		String sessionId = null;		
		try {
			response = HttpClientUtils.httpGet(proxy, hainanjj_host + "/info5/search1.html", null, headers);
			sessionId = getSessionIdFromResponse(response);										
		} catch(Exception e) {
			logger.error("====================generate sessionId", e);
			throw new IOException("由于网络原因采集失败");
		} finally {
			HttpClientUtils.closeResponse(response);
			HttpClientUtils.closeHttpClient();
		}	
		
		if (sessionId==null) {
			throw new IOException("由于网络原因采集失败,sessionId is null");
		}
		
		//提交查询请求
		Map<String, String> searchHeaders = getSearchHeader();
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", String.valueOf(0));
		params.put("QueryString", String.format("Hphm|%s|0$Hpzl|02|0$Clsbdh|%s|0", carNo, frameNo.substring(frameNo.length()-4)));	
		searchHeaders.put("Cookie", String.format("ASP.NET_SessionId=%s;", sessionId));
		try {
			response = HttpClientUtils.httpGet(proxy, hainanjj_host + "/myweb/webpost/Data.ashx", params, searchHeaders);
		} catch (Exception e) {
			throw new IOException("由于网络原因采集失败");
		} finally {
			HttpClientUtils.closeResponse(response);
			HttpClientUtils.closeHttpClient();
		}	
		 	
		//获取查询结果	
		try {
			html = HttpClientUtils.httpGetForHtml(proxy, hainanjj_host + "/info5/search1.html", null, searchHeaders);
		} catch (Exception e) {
			logger.error("====================get search result", e);
			throw new IOException("由于网络原因采集失败");
		}
		
		List<HainanTrafficInfo> trafficInfos = parseTrafficInfoFromResultHtml(html);
		return trafficInfos;
	}
	
	private static List<HainanTrafficInfo> parseTrafficInfoFromResultHtml(String html) {
		List<HainanTrafficInfo> infos = new ArrayList<HainanTrafficInfo>();
		Document doc = Jsoup.parse(html);
		
		Elements trs = doc.select(".tab1 tr");
		int trIndex=0;
		for (Element e : trs) {
			if (trIndex>=2 && trIndex%2==0) {
				Elements tds = e.select("td");
				String carNo = tds.get(0).text().trim();
				String address = tds.get(1).text().trim();
				String time = tds.get(2).text().trim();
				String trafficType = tds.get(3).text().trim();
				String carType = tds.get(4).text().trim();
				String executedOrg = tds.get(5).text().trim();
				infos.add(new HainanTrafficInfo(carNo, address, trafficType, time, carType, executedOrg));
			}
			trIndex = trIndex+1;
		}
		return infos;
	}
	
	private static Map<String, String>  getSearchHeader() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "*/*");
		headers.put("Accept-Encoding", "gzip,deflate,sdch");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		headers.put("Cache-Control", "max-age=0");
		headers.put("Connection", "keep-alive");
		headers.put("Host", "hainanjj.gov.cn");
		headers.put("Referer", hainanjj_host + "/info5/search1.html");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return headers;
	}
	
	private static int sessionKeyLen = "ASP.NET_SessionId=".length();
	private static String getSessionIdFromResponse(HttpResponse response) {		
		Header cookieHeader = response.getFirstHeader("set-cookie");
		String setCookie = null;
		if (cookieHeader!=null) {
			setCookie = response.getFirstHeader("set-cookie").getValue();
		} else {
			return null;
		}
		int start = setCookie.indexOf("ASP.NET_SessionId=")+sessionKeyLen;
		int end = setCookie.indexOf(";", start);
		logger.debug(setCookie.substring(start, end));
		return setCookie.substring(start, end);
	}
	
	public static class HainanTrafficInfo {
		private String carNo;
		private String address;
		private String trafficType;
		private String time;
		private String carType;
		private String executedOrg;
		
		public HainanTrafficInfo() {}
		
		public HainanTrafficInfo(String carNo, String address, String trafficType,
				String time, String carType, String executedOrg) {
			super();
			this.carNo = carNo;
			this.address = address;
			this.trafficType = trafficType;
			this.time = time;
			this.carType = carType;
			this.executedOrg = executedOrg;
		}

		public String getCarNo() {
			return carNo;
		}
		public void setCarNo(String carNo) {
			this.carNo = carNo;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getTrafficType() {
			return trafficType;
		}
		public void setTrafficType(String trafficType) {
			this.trafficType = trafficType;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getCarType() {
			return carType;
		}
		public void setCarType(String carType) {
			this.carType = carType;
		}
		public String getExecutedOrg() {
			return executedOrg;
		}
		public void setExecutedOrg(String executedOrg) {
			this.executedOrg = executedOrg;
		}

		@Override
		public String toString() {
			return "HainanTrafficInfo [carNo=" + carNo + ", address=" + address
					+ ", trafficType=" + trafficType + ", time=" + time
					+ ", carType=" + carType + ", executedOrg=" + executedOrg
					+ "]";
		}	
	}
	
	public static void main(String[] args) throws IOException {
		try {			
			try {
				System.out.println("normal info...");
				throw new Exception("fuck1");
			} catch (Exception e) {
				System.out.println("catch exception...");
				throw new Exception("fuck2");
			} finally {
				System.out.println("final info...");
			}	
		} catch (Exception e) {
			System.out.println("catch exception2...");
		}  finally {
			System.out.println("final2 info...");
		}
		
	}
}
