package com.ikasoa.zk;

import com.ikasoa.core.ServerCheck;

/**
 * 服务可用性验证实现
 * 
 * @author <a href="mailto:larry7696@gmail.com">Larry</a>
 * @version 0.1
 */
public class ZkServerCheck implements ServerCheck {

	private ZkBase zkBase;

	public ZkServerCheck(String zkServerString, String zkNode) {
		zkBase = new ZkBase(zkServerString, zkNode);
	}

	@Override
	public boolean check(String serverHost, int serverPort) {
		return zkBase.isExistNode("", serverHost, serverPort);
	}

}
