package com.ikasoa.core.thrift.client.pool;

import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.transport.TTransportException;

import com.ikasoa.core.IkasoaException;
import com.ikasoa.core.thrift.client.socket.ThriftSocket;
import com.ikasoa.core.utils.ServerUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Socket连接池参数对象
 * 
 * @author <a href="mailto:larry7696@gmail.com">Larry</a>
 * @version 0.6
 */
@AllArgsConstructor
@Data
public class ClientSocketPoolParameters {

	private final String host;

	private final int port;

	private final int timeout;

	private final TSSLTransportParameters sslTransportParameters;

	public ThriftSocket buildClientThriftSocket() throws IkasoaException {
		if (!ServerUtil.checkHostAndPort(host, port))
			throw new IllegalArgumentException("The 'host' or 'port' error !");
		try {
			return sslTransportParameters == null ? new ThriftSocket(host, port, timeout)
					: (ThriftSocket) TSSLTransportFactory.getClientSocket(host, port, timeout, sslTransportParameters);
		} catch (TTransportException e) {
			throw new IkasoaException(e);
		}
	}

	public String getKey() {
		return ServerUtil.buildCacheKey(host, port);
	}
}
