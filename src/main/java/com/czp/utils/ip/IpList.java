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
	boolean isNumIpInList(int ip);

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
		 * 内存映射版本
		 */
		IPV4_MMAP_IMPL,

		/***
		 * HashMap实现版本,最简单的版本,但是内存消耗过高
		 */
		HASH_MAP_IMPL;

		public static IpList create(Impl type) {
			switch (type) {
			case IPV4_BITSET_IMPL:
				return new Ipv4BitsetImpl();
			case IPV4_BITMAP_IMPL:
				return new Ipv4BitmapImpl();
			case HASH_MAP_IMPL:
				return new Ipv4HashmapImpl();
			case IPV4_MMAP_IMPL:
				return new Ipv4MMapImpl();
			default:
				throw new IllegalArgumentException("unknown type:" + type);
			}
		}
	}

}
