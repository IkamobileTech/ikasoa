package com.ikasoa.core.thrift.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.server.TServlet;

/**
 * ThriftServlet
 * <p>
 * 通过Servlet来提供服务.
 * 
 * @author <a href="mailto:larry7696@gmail.com">Larry</a>
 * @version 0.2
 */
public class ThriftServlet extends TServlet {

	private static final long serialVersionUID = 1L;

	private String serverName;

	/**
	 * 构造方法
	 * 
	 * @param server
	 *            ThriftServer对象(这里一般用<i>ServletThriftServerImpl</i>实现)
	 */
	public ThriftServlet(ThriftServer server) {
		super(server.getProcessor(), server.getThriftServerConfiguration().getProtocolFactory());
		this.serverName = server.getServerName();
	}

	/**
	 * 
	 * @author <a href="mailto:larry7696@gmail.com">Larry</a>
	 * @version 0.5.1
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// super.doPost(request, response);
		PrintWriter w = response.getWriter();
		w.write("This is a ikasoa server! (" + this.serverName + ")");
		w.close();
	}

}