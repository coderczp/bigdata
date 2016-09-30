package com.czp.utils.ip;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Function:最简单的实现
 *
 * @date:2016年9月30日/下午10:42:07
 * @Author:coder_czp@126.com
 * @version:1.0
 */
public class Ipv4HashmapImpl extends AbstractIpList {

	private ConcurrentHashMap<Integer, Boolean> map = new ConcurrentHashMap<Integer, Boolean>();

	@Override
	public boolean isInList(String ip) {
		if (ip == null || ip.length() < IPV4_MIN_LEN || ip.length() > IPV4_MAX_LEN)
			return false;

		if (ip.indexOf('.') < 1)
			return false;

		return map.get(stringToInt(ip));
	}

	@Override
	public boolean isNumIpInList(int ip) {
		return map.containsKey(ip);
	}

	@Override
	protected void onReadIP(String ip) {
		map.put(stringToInt(ip), true);
	}

}
