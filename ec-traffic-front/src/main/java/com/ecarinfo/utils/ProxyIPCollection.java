package com.ecarinfo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import com.ecarinfo.persist.simple.DaoTool;

public class ProxyIPCollection {
	private static Logger logger = Logger.getLogger(ProxyIPCollection.class);
	private static List<ProxyHost> ipports = new ArrayList<ProxyHost>();
	private static int index = 0;
	private static Timer timer = new Timer(true);   
	public static void init() {
		loadProxyHostFromdb();
		timer.schedule(new TimerTask() {			
			@Override
			public void run() {
				reset();
			}
		}, 1000*60*5, 1000*60*5);
	}
	
	private static synchronized void  reset() {
		logger.info("--------------reload proxy ips");
		ipports.clear();
		loadProxyHostFromdb();
	}
	
	public static synchronized void remove(ProxyHost host) {
		logger.info("--------------remvoe host:" + host);
		System.err.println("------------remove host:" + host);
		if (ipports.contains(host)) {
			ipports.remove(host);
		}
	}
	
	public static synchronized ProxyHost getProxyIpAddress() {
		if (ipports.size()==0) {
			return null;
		}
		if (index>=ipports.size()) {
			index = 0;
		}
		return ipports.get(index++);		
	}
	
	public static class ProxyHost {
		private String host;  //主键
		private int port;
		private int valid;
		private int lastReplyTime;
			
		public ProxyHost() {
			super();
		}
		public ProxyHost(String host, int port, int valid, int lastReplyTime) {
			super();
			this.host = host;
			this.port = port;
			this.valid = valid;
			this.lastReplyTime = lastReplyTime;
		}
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public int getValid() {
			return valid;
		}
		public void setValid(int valid) {
			this.valid = valid;
		}
		public int getLastReplyTime() {
			return lastReplyTime;
		}
		public void setLastReplyTime(int lastReplyTime) {
			this.lastReplyTime = lastReplyTime;
		}
		@Override
		public String toString() {
			return "ProxyHost [host=" + host + ", port=" + port + ", valid="
					+ valid + ", lastReplyTime=" + lastReplyTime + "]";
		}
		
	}
	
	public static void loadProxyHostFromdb() {
		logger.info("--------------loadProxyHostFromdb");
		List<ProxyHost> hosts = DaoTool.queryForList(ProxyHost.class, "select * from proxy_host where valid=1 and last_reply_time <= 1000");
		ipports = hosts;
		if (hosts==null) {
			ipports = new ArrayList<ProxyHost>();
		}
	}
	public static void main(String[] args) {
		
	}
}
