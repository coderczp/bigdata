package com.czp.utils.ip;

/**
 * Function:IP�ڰ������ӿ�
 *
 * @date:2016��9��26��/����9:34:34
 * @Author:jeff@aoliday.cao
 * @version:1.0
 */
public interface IpList {

	/**
	 * ����IP�Ƿ�����
	 * 
	 * @param ip
	 * @return
	 */
	boolean isInList(String ip);

	/**
	 * ����IP�Ƿ�����
	 * 
	 * @param ip
	 * @return
	 */
	boolean isNumIpInList(long ip);

	/***
	 * ���������ļ�
	 * 
	 * @param configPath
	 */
	void loadConfig(String configPath);

	enum Impl {
		/***
		 * bitSetʵ�ְ汾 ֧��IPv4
		 */
		IPV4_BITSET_IMPL,
		/***
		 * bitmapʵ�ְ汾֧��IPv4���ܱ�IPV4_BITSET_IMPL����
		 */
		IPV4_BITMAP_IMPL,
		/***
		 * bitSetʵ�ְ汾,֧��ipv6
		 */
		SUPPORT_IPV6_IMPL;

		public static IpList create(Impl type) {
			switch (type) {
			case IPV4_BITSET_IMPL:
				return new Ipv4BitsetImpl();
			case IPV4_BITMAP_IMPL:
				return new Ipv4BitmapImpl();
			case SUPPORT_IPV6_IMPL:
				return new Ipv4v6ListImpl();
			default:
				throw new IllegalArgumentException("unknown type:" + type);
			}
		}
	}

}
