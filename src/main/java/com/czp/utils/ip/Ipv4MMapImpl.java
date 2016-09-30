package com.czp.utils.ip;

import com.czp.utils.bitmap.MMapBitmap;

/**
 * Function:�ڴ�ӳ��汾
 *
 * @date:2016��9��30��/����10:47:40
 * @Author:coder_czp@126.com
 * @version:1.0
 */
public class Ipv4MMapImpl extends AbstractIpList {

	// ����20�ڸ�
	private MMapBitmap positiveIps = new MMapBitmap(Integer.MAX_VALUE);

	// ����20��BitSet��Ҫ��������,���Խ����� תΪ�������
	private MMapBitmap negativeIps = new MMapBitmap(Integer.MAX_VALUE);

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
		return ip >= 0 ? positiveIps.get(ip) : negativeIps.get(ip + Integer.MAX_VALUE + 1);
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
