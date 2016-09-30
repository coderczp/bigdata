package com.czp.utils.ip;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;

/**
 * Function:负责加载配置数据,ip间用逗号分隔
 * 
 * @date:2016年9月30日/下午5:15:15
 * @Author:jeff@aoliday.cao
 * @version:1.0
 */
public abstract class AbstractIpList implements IpList {

	// ipv6长度
	public static final int IPV6_MAX_LEN = "ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff".length();

	// ipv4长度 最多为:223.255.255.255
	public static final int IPV4_MAX_LEN = "223.255.255.255".length();

	// ipv4 长度最少为:0.0.0.0
	public static final int IPV4_MIN_LEN = "0.0.0.0".length();

	/**
	 * ipv6地址转有byte[]
	 */
	public static byte[] ipv6ToBytes(String ipv6) {
		byte[] ret = new byte[17];
		ret[0] = 0;
		int ib = 16;
		boolean comFlag = false;// ipv4混合模式标记
		if (ipv6.startsWith(":"))
			ipv6 = ipv6.substring(1);
		String groups[] = ipv6.split(":");
		for (int i = groups.length - 1; i > -1; i--) {
			if (groups[i].contains(".")) {
				byte[] temp = ipv4ToBytes(groups[i]);
				ret[ib--] = temp[4];
				ret[ib--] = temp[3];
				ret[ib--] = temp[2];
				ret[ib--] = temp[1];
				comFlag = true;
			} else if ("".equals(groups[i])) {
				int zlg = 9 - (groups.length + (comFlag ? 1 : 0));
				while (zlg-- > 0) {
					ret[ib--] = 0;
					ret[ib--] = 0;
				}
			} else {
				int temp = Integer.parseInt(groups[i], 16);
				ret[ib--] = (byte) temp;
				ret[ib--] = (byte) (temp >> 8);
			}
		}
		return ret;
	}

	/**
	 * ipv4 转byte[]
	 */
	protected static byte[] ipv4ToBytes(String ipv4) {
		byte[] arr = new byte[5];
		arr[0] = 0;
		int pos1 = ipv4.indexOf(".");
		int pos2 = ipv4.indexOf(".", pos1 + 1);
		int pos3 = ipv4.indexOf(".", pos2 + 1);
		arr[1] = (byte) Integer.parseInt(ipv4.substring(0, pos1));
		arr[2] = (byte) Integer.parseInt(ipv4.substring(pos1 + 1, pos2));
		arr[3] = (byte) Integer.parseInt(ipv4.substring(pos2 + 1, pos3));
		arr[4] = (byte) Integer.parseInt(ipv4.substring(pos3 + 1));
		return arr;
	}

	/***
	 * 将string类型的IP转化为long
	 * 
	 * @param strIp
	 * @return
	 */
	public static long stringToLong(String ipInString) {
		ipInString = ipInString.replace(" ", "");
		byte[] bytes;
		if (ipInString.contains(":"))
			bytes = ipv6ToBytes(ipInString);
		else
			bytes = ipv4ToBytes(ipInString);
		return new BigInteger(bytes).longValue();
	}

	/***
	 * 将string类型的IP转化为int
	 * 
	 * @param strIp
	 * @return
	 */
	public static int stringToInt(String ipInString) {
		String[] ips = ipInString.split("\\.");
		return (Integer.parseInt(ips[0]) << 24) + (Integer.parseInt(ips[1]) << 16) + (Integer.parseInt(ips[2]) << 8)
				+ Integer.parseInt(ips[3]);

	}

	@Override
	public void loadConfig(String configPath) {
		try {
			String line = null;
			BufferedReader reader = new BufferedReader(new FileReader(configPath));
			while ((line = reader.readLine()) != null) {
				String[] ips = line.split(",");
				for (String string : ips) {
					onReadIP(string);
				}
			}
			reader.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 处理读取到的IP
	 * 
	 * @param ip
	 */
	protected abstract void onReadIP(String ip);

}
