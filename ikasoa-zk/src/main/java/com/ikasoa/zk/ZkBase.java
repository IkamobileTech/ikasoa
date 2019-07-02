package com.ikasoa.zk;

import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.ikasoa.core.loadbalance.ServerInfo;
import com.ikasoa.core.utils.StringUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Zookeeper基础操作类
 * 
 * @author <a href="mailto:larry7696@gmail.com">Larry</a>
 * @version 0.1
 */
@Slf4j
public class ZkBase {

	@Getter
	@Setter
	private ZkClient zkClient;

	@Getter
	@Setter
	private String zkNode;

	private List<String> nodeList;

	public final static String ZK_ROOT_NODE = "/";

	public ZkBase(String zkServerString, String zkNode) {
		if (StringUtil.isEmpty(zkServerString))
			throw new IllegalArgumentException("'zkServerString' is null !");
		else
			zkClient = new ZkClient(zkServerString);

		this.zkNode = StringUtil.isEmpty(zkNode) ? ZK_ROOT_NODE : zkNode;

		zkClient.subscribeDataChanges(this.zkNode, new IZkDataListener() {

			@Override
			public void handleDataChange(String nodePath, Object nodeObj) throws Exception {
				log.debug("handleDataChange (nodePath : {}, nodeObj : {})", nodePath, nodeObj);
			}

			@Override
			public void handleDataDeleted(String nodePath) throws Exception {
				log.warn("handleDataDeleted (nodePath : {})", nodePath);
			}

		});

		zkClient.subscribeStateChanges(new IZkStateListener() {

			@Override
			public void handleNewSession() throws Exception {
				log.debug("handleNewSession");
				nodeList = getChildren();
			}

			@Override
			public void handleSessionEstablishmentError(Throwable t) throws Exception {
				log.error(t.getMessage());
			}

			@Override
			public void handleStateChanged(KeeperState state) throws Exception {
				log.debug("handleStateChanged (state : {})", state);
			}

		});

		zkClient.subscribeChildChanges(this.zkNode, new IZkChildListener() {

			@Override
			public void handleChildChange(String parentPath, List<String> currentChildList) throws Exception {
				log.debug("handleChildChange (parentPath : {}, currentChildList : {})", parentPath, currentChildList);
				nodeList = currentChildList;
			}

		});

	}

	public List<ServerInfo> getServerInfoList() {
		List<ServerInfo> serverInfoList = new ArrayList<>();
		List<String> nList = zkClient.getChildren(zkNode);
		for (String n : nList) {
			ZkServerNode zksn = (ZkServerNode) zkClient
					.readData(new StringBuilder(zkNode).append("/").append(n).toString());
			serverInfoList.add(new ServerInfo(zksn.getServerHost(), zksn.getServerPort()));
		}
		log.debug("ServerInfoList is : {}", serverInfoList);
		return serverInfoList;
	}

	public boolean isExistNode(String serverName, String serverHost, int serverPort) {
		if (nodeList == null || nodeList.isEmpty())
			nodeList = getChildren();
		for (String n : nodeList)
			if (n.contains(new StringBuilder(serverName).append("-").append(serverHost).append("-").append(serverPort)
					.toString()))
				return true;
		return false;
	}

	public List<String> getChildren() {
		return zkClient.getChildren(zkNode);
	}

}
