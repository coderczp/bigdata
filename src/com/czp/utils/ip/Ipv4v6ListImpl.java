package com.czp.utils.ip;

import com.czp.utils.bitmap.BigBitmap;

/**
 * Function:IP黑/白名单工具接口实现类 支持全网ip
 * 
 * @date:2016年9月30日/下午2:53:52
 * @Author:jeff@aoliday.cao
 * @version:1.0
 */
public class Ipv4v6ListImpl extends AbstractIpList {

	// IpV6的最大为:@see BigBitmap.MAX_VALUE
	private BigBitmap map = new BigBitmap(BigBitmap.MAX_VALUE);

	@Override
	public boolean isInList(String ip) {
		try {
			if (ip == null || ip.length() < IPV4_MIN_LEN || ip.length() > IPV6_MAX_LEN)
				return false;

			return map.get(stringToLong(ip));
		} catch (Exception e) {
			// Ignore Exception
			return false;
		}
	}

	@Override
	protected void onReadIP(String ip) {
		map.set(stringToLong(ip));
	}

	@Override
	public boolean isNumIpInList(long ip) {
		return map.get(ip);
	}
}
