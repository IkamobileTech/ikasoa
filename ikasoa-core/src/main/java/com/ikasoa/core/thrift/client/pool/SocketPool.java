package com.ikasoa.core.thrift.client.pool;

import com.ikasoa.core.thrift.client.socket.ThriftSocket;

/**
 * Socket连接池
 * 
 * @author <a href="mailto:larry7696@gmail.com">Larry</a>
 * @version 0.4.2
 */
public interface SocketPool {

	/**
	 * 连接池默认大小
	 */
	byte defaultSize = 0x10;

	/**
	 * 连接默认超时时间
	 */
	int defaultTime = 0;

	/**
	 * 从连接池中获取一个空闲的ThriftSocket连接
	 * 
	 * @param host
	 *            服务器地址
	 * @param port
	 *            服务器端口
	 * @return ThriftSocket ThriftSocket连接对象
	 */
	ThriftSocket buildThriftSocket(String host, int port);

	/**
	 * 回收ThriftSocket连接
	 * 
	 * @param thriftSocket
	 *            待回收的ThriftSocket连接对象
	 * @param host
	 *            服务器地址
	 * @param port
	 *            服务器端口
	 */
	void releaseThriftSocket(ThriftSocket thriftSocket, String host, int port);

	default void releaseThriftSocket(ThriftSocket thriftSocket) {
		if (thriftSocket != null)
			if (thriftSocket.getSocket() != null && thriftSocket.getSocket().getInetAddress() != null)
				releaseThriftSocket(thriftSocket, thriftSocket.getSocket().getInetAddress().getHostName(),
						thriftSocket.getSocket().getPort());
			else
				thriftSocket.close();
	}

	/**
	 * 回收所有ThriftSocket连接
	 */
	void releaseAllThriftSocket();

}
