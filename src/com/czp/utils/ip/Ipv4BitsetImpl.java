package com.czp.utils.ip;

import java.util.BitSet;

/**
 * Function:IP黑/白名单工具接口实现类
 * <P>
 * ipV4全网40亿IP,利用bitmap的特性: 一个数32bit可以标记32个数<br>
 * 最大消耗内存=4000000000/32*4/1024/1024~=477M<br>
 * 缺点是如果一开始就配置很大或者很小的IP将导致内存消耗达到400m+<br>
 * 因为利用volatile实现无锁并发更新,所以需要java5+版本<br>
 * </P>
 * 
 * @date:2016年9月7日/下午9:42:37
 * @Author:coder_czp@126.com
 * @version:2.0
 * @since java 1.5+
 */
public class Ipv4BitsetImpl extends AbstractIpList {

	// 正数20亿个
	private volatile BitSet positiveIps = new BitSet();

	// 负数20亿BitSet需要正数索引,所以讲负数 转为正数标记
	private volatile BitSet negativeIps = new BitSet();

	public boolean isInList(String ip) {
		try {
			if (ip == null || ip.length() < IPV4_MIN_LEN || ip.length() > IPV4_MAX_LEN)
				return false;

			if (ip.indexOf('.') < 1)
				return false;

			return isNumIpInList(stringToInt(ip));
		} catch (Exception e) {
			// Ignore Exception
			return false;
		}
	}

	@Override
	protected void onReadIP(String ip) {
		int intIp = stringToInt(ip);
		if (intIp >= 0) {
			positiveIps.set(intIp);
		} else {
			// Integer.MIN_VALUE+Integer.MAX_VALUE =-1 so +1
			negativeIps.set(intIp + Integer.MAX_VALUE + 1);
		}
	}

	@Override
	public boolean isNumIpInList(long ip) {
		return ip >= 0 ? positiveIps.get((int) ip) : negativeIps.get((int) (ip + Integer.MAX_VALUE + 1));
	}
}
