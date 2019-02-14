package com.kingyon.chengxin.framework.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Http，基于群集服务器的选择器
 *
 */

public class HttpCluster {

	private static Logger logger = LoggerFactory.getLogger(HttpCluster.class);
	/**检查时间间隔（毫秒）*/
	private static int CHECK_TIME = 20 * 1000;

	public class Host {
		String host = "http://192.168.1.5:5080/";
		int connectors = 0;
		/**最后的连接检查时间*/
		long lastCheckTime = 0;
		/**是否可连接*/
		boolean isConnected = false;

		public String getUrl() {
			return host;
		}

		public synchronized void close() {
			this.connectors--;
		}

		/**
		 * 检查群集点，是否可用
		 * @return
		 */
		private synchronized boolean canConnected() {
			long time = System.currentTimeMillis() ;
//			if (!isConnected && time - lastCheckTime < CHECK_TIME)
//				return this.isConnected;
			if (this.isConnected ) {
				if (time - lastCheckTime < CHECK_TIME/5)
					return this.isConnected;
			} else {
				if (time - lastCheckTime < CHECK_TIME)
					return this.isConnected;
			}

			lastCheckTime = time;
			isConnected = false;
			HttpURLConnection conn = null;
			try
			{
				URL url = new URL(this.host );
		        conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestMethod("HEAD" );
		        conn.setConnectTimeout(200);
		        conn.connect();

		        isConnected = true;
			}catch(Exception e){
//				e.printStackTrace();
			}finally {
				if (conn != null) {
					try
					{
						conn.disconnect();
					}catch(Exception e){}
				}
			}
//			System.out.println("--check-time:" + (System.currentTimeMillis() - time) + "; " + this.host + "; isOk=" + this.isConnected);
			return isConnected;
		}
	}

	private ConcurrentHashMap<String, Host> clusters = new ConcurrentHashMap<String, Host>();

	public HttpCluster() {
	}

	public HttpCluster(String[] hosts) {
		addCluster(hosts);
	}
	/**
	 * 添加一个群集主机url（如http://192.168.1.5:1234）
	 * @param host
	 */
	public synchronized void addCluster(String host) {
		if (clusters.containsKey(host))
			return;
		Host config = new Host();
		config.host = host;
		clusters.put(host, config);
		logger.debug("添加Cluster:"  + host);
	}
	/**
	 * 使用数组添加群集主机
	 * @param hosts
	 */
	public void addCluster(String[] hosts) {
		for(String url : hosts) {
			addCluster(url );
		}
	}

	/**
	 * 获得一个连接选择HttpClient对象，注意这个对象为设置url、method等参数，请在使用连接时设置
	 * @key 	根据参数key，在都通畅情况下，优先固定选择同一节点
	 * @return
	 */
	public Host getClusterHost(String key) {
		int size = this.clusters.size();

		int cur_idx = Math.abs((key == null ? "" : key).hashCode()) % size;
		int cl_idx = -1;

		java.util.Enumeration<Host> cs = this.clusters.elements();
		Host min_conn = null, cur;
		while(cs.hasMoreElements() ) {
			cur = cs.nextElement();

			if (!cur.canConnected() )
				continue;
			/**根据参数key，在都通畅情况下，优先固定选择同一节点*/
			cl_idx = Math.abs(cur.host.hashCode()) % size;
			if (cur_idx == cl_idx) {
				min_conn = cur;
				break;
			}

			min_conn = cur;
//			if (cur.connectors < 20){
//				min_conn = cur;
//				break;
//			} else {
//				if (min_conn == null){
//					min_conn = cur;
//				} else if (cur.connectors < min_conn.connectors ){
//					min_conn = cur;
//				}
//			}
		}
//		System.out.println("获得Cluster:"  + min_conn);
		if (min_conn == null)
			return null;

		logger.debug("获得Cluster:key={}, host={}",key , min_conn.host);

//		Http conn = new Http();
//		conn.cluster = this;
//		conn.cluster_host = min_conn.host;
		synchronized(min_conn) {
			min_conn.connectors++;
		}
		return min_conn;
	}

	void closeClusterHost(String host){
		Host cluster = clusters.get(host);
		if (cluster != null) {
			logger.debug("释放Cluster:"  +host);
			cluster.close();
		}
	}

	void setClosed(String host){
		Host cluster = clusters.get(host);
		if (cluster != null) {
			cluster.isConnected = false;
		}
	}
}
