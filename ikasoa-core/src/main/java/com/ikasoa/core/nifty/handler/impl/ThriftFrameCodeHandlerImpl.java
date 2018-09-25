package com.ikasoa.core.nifty.handler.impl;

import org.apache.thrift.protocol.TProtocolFactory;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.ikasoa.core.nifty.handler.ThriftFrameCodeHandler;

public class ThriftFrameCodeHandlerImpl implements ThriftFrameCodeHandler {
	private final FrameDecoder decoder;
	private final OneToOneEncoder encoder;

	public ThriftFrameCodeHandlerImpl(int maxFrameSize, TProtocolFactory inputProtocolFactory) {
		this.decoder = new ThriftFrameDecoder(maxFrameSize, inputProtocolFactory);
		this.encoder = new ThriftFrameEncoder(maxFrameSize);
	}

	@Override
	public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		encoder.handleDownstream(ctx, e);
	}

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		decoder.handleUpstream(ctx, e);
	}
}
