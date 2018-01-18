package com.ikasoa.core.thrift.protocol;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TTransport;

import com.ikasoa.core.utils.SimpleDESUtil;
import com.ikasoa.core.utils.StringUtil;

/**
 * 基于DES加密的序列化实现
 * <p>
 * 继承于<code>TCompactProtocol</code>,仅对String类型加密.
 * 
 * @author <a href="mailto:larry7696@gmail.com">Larry</a>
 * @version 0.4.5
 */
public class DESCompactProtocol extends TCompactProtocol {

	private final String key;

	public static class Factory implements TProtocolFactory {

		private static final long serialVersionUID = 1L;

		private final String key_;

		public Factory(String key) {
			if (StringUtil.isNotEmpty(key))
				key_ = key;
			else
				throw new RuntimeException("'key' is null !");
		}

		public TProtocol getProtocol(TTransport trans) {
			return new DESCompactProtocol(trans, key_);
		}
	}

	public DESCompactProtocol(TTransport transport, String key) {
		super(transport);
		if (StringUtil.isEmpty(key))
			throw new RuntimeException("'key' is null !");
		this.key = key.length() < 8 ? formatStr(key, 8) : key;
	}

	@Override
	public void writeString(String str) throws TException {
		if (StringUtil.isEmpty(str))
			super.writeString(str);
		try {
			super.writeString(SimpleDESUtil.encrypt(str, getKey()));
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public String readString() throws TException {
		String str = super.readString();
		if (StringUtil.isEmpty(str))
			return str;
		try {
			return new String(SimpleDESUtil.decrypt(str, getKey()));
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	private String getKey() {
		return key.length() < 8 ? formatStr(key, 8) : key;
	}

	private static String formatStr(String str, int length) {
		int strLen = str.getBytes().length;
		if (strLen == length) {
			return str;
		} else if (strLen < length) {
			int temp = length - strLen;
			String tem = "";
			for (int i = 0; i < temp; i++)
				tem = tem + " ";
			return str + tem;
		} else {
			return str.substring(0, length);
		}
	}
}
