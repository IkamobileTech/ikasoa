package com.ikasoa.core.thrift.server.impl;

import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerTransport;

import com.ikasoa.core.thrift.server.ThriftServerConfiguration;

import lombok.NoArgsConstructor;

/**
 * ThreadPool服务器实现
 * 
 * @author <a href="mailto:larry7696@gmail.com">Larry</a>
 * @version 0.1
 */
@NoArgsConstructor
public class ThreadPoolThriftServerImpl extends AbstractThriftServerImpl {

	public ThreadPoolThriftServerImpl(String serverName, int serverPort, ThriftServerConfiguration configuration,
			TProcessor processor) {
		setServerName(serverName);
		setServerPort(serverPort);
		if (configuration == null)
			configuration = new ThriftServerConfiguration();
		setConfiguration(configuration);
		setProcessor(processor);
	}

	/**
	 * 初始化Thrift服务
	 * <p>
	 * 启动Thrift服务之前必须要进行初始化.
	 * 
	 * @param serverTransport
	 *            服务传输类型
	 */
	protected void initServer(TServerTransport serverTransport) {
		ThriftServerConfiguration configuration = getServerConfiguration();
		// 使用TThreadPoolServer方式启动Thrift服务器,对每个连接都会单独建立一个线程.
		TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport)
				.transportFactory(configuration.getTransportFactory())
				.protocolFactory(configuration.getProtocolFactory());
		// 如果不设置ExecutorService,则默认使用ThreadPoolExecutor实现.
		if (configuration.getExecutorService() != null)
			args.executorService(configuration.getExecutorService());
		server = new TThreadPoolServer(
				configuration.getServerArgsAspect().tThreadPoolServerArgsAspect(args).processor(getProcessor()));
		if (configuration.getServerEventHandler() != null)
			server.setServerEventHandler(configuration.getServerEventHandler());
	}

}
