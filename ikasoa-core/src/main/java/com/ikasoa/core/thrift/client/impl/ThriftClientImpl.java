package com.ikasoa.core.thrift.client.impl;

import com.ikasoa.core.thrift.client.ThriftClientConfiguration;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Thrift客户端实现
 * 
 * @author <a href="mailto:larry7696@gmail.com">Larry</a>
 * @version 0.1
 */
@NoArgsConstructor
@Slf4j
public class ThriftClientImpl extends AbstractThriftClientImpl {

	public ThriftClientImpl(String serverHost, int serverPort, ThriftClientConfiguration configuration) {
		setServerHost(serverHost);
		setServerPort(serverPort);
		if (configuration == null) {
			log.debug("Thrift client configuration is null .");
			setConfiguration(new ThriftClientConfiguration());
		} else
			setConfiguration(configuration);
	}

}
