package com.czp.utils.ip;

/**
 * Function:IP黑白名单接口
 *
 * @date:2016年9月26日/上午9:34:34
 * @Author:jeff@aoliday.cao
 * @version:1.0
 */
public interface IpList {

	/**
	 * 测试IP是否允许
	 * 
	 * @param ip
	 * @return
	 */
	boolean isInList(String ip);

	/**
	 * 测试IP是否允许
	 * 
	 * @param ip
	 * @return
	 */
	boolean isNumIpInList(long ip);

	/***
	 * 加载配置文件
	 * 
	 * @param configPath
	 */
	void loadConfig(String configPath);

	enum Impl {
		/***
		 * bitSet实现版本 支持IPv4
		 */
		IPV4_BITSET_IMPL,
		/***
		 * bitmap实现版本支持IPv4性能比IPV4_BITSET_IMPL更好
		 */
		IPV4_BITMAP_IMPL,
		/***
		 * bitSet实现版本,支持ipv6
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
