package com.czp.utils.test;

import static org.junit.Assert.assertTrue;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import org.junit.Before;
import org.junit.Test;

import com.czp.utils.ip.IpList;

/**
 * Function:测试类
 *
 * @date:2016年9月30日/下午5:05:43
 * @Author:jeff@aoliday.cao
 * @version:1.0
 */
public class IPUtilTest {

	MemoryMXBean mmBean = ManagementFactory.getMemoryMXBean();
	IpList util = IpList.Impl.create(IpList.Impl.SUPPORT_IPV6_IMPL);

	@Before
	public void loadCfg() {
		util.loadConfig("./white_ip.txt");
	}

	@Test
	public void testInList() {
		long start = System.currentTimeMillis();
		assertTrue(util.isInList("10.0.12.13"));
		long end = System.currentTimeMillis();
		System.out.println("times(s):" + (end - start) / 1000.0);
		System.out.println("memory used(MB):" + (mmBean.getHeapMemoryUsage().getUsed()) / 1024 / 1024);
	}

	@Test
	public void testNotInList() {
		long start = System.currentTimeMillis();
		assertTrue(util.isInList("192.168.0.189"));
		long end = System.currentTimeMillis();
		System.out.println("times(s):" + (end - start) / 1000.0);
		System.out.println("memory used(MB):" + (mmBean.getHeapMemoryUsage().getUsed()) / 1024 / 1024);
	}

	@Test
	public void testErrIp() {
		long start = System.currentTimeMillis();
		assertTrue(util.isInList("xxxxxxx"));
		long end = System.currentTimeMillis();
		System.out.println("times(s):" + (end - start) / 1000.0);
		System.out.println("memory used(MB):" + (mmBean.getHeapMemoryUsage().getUsed()) / 1024 / 1024);
	}

	@Test
	public void performTest() {
		long maxIp = 2 << 48;
		long start = System.currentTimeMillis();
		for (long i = 0; i < maxIp; i++) {
			util.isNumIpInList(i);
		}
		long end = System.currentTimeMillis();
		System.out.println("times(s):" + (end - start) / 1000.0);
		System.out.println("memory used(MB):" + (mmBean.getHeapMemoryUsage().getUsed()) / 1024 / 1024);
	}
}
