package com.czp.utils.ip;

import com.czp.utils.bitmap.ByteBitmap;
import com.czp.utils.bitmap.IntBitmap;

/**
 * Function:IP黑/白名单工具接口实现类
 * <P>
 * ipV4全网40亿IP 大约520M内存{@link IntBitmap}<br>
 * 因为利用volatile实现无锁并发更新,所以需要java5+版本<br>
 * </P>
 * 
 * @date:2016年9月25日/下午3:18:37
 * @Author:coder_czp@126.comb
 * @version:1.0
 */
public class Ipv4BitmapImpl extends AbstractIpList {

	// 正数20亿个
	private volatile ByteBitmap positiveIps = new ByteBitmap(Integer.MAX_VALUE);

	// 负数20亿BitSet需要正数索引,所以将负数 转为正数标记
	private volatile ByteBitmap negativeIps = new ByteBitmap(Integer.MAX_VALUE);

	@Override
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
	public boolean isNumIpInList(int ip) {
		return ip >= 0 ? positiveIps.get((int) ip) : negativeIps.get((int) (ip + Integer.MAX_VALUE + 1));
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
}
