package com.czp.utils.test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import org.junit.Test;

import com.czp.utils.ip.IpList;

/**
 * Function:性能测试
 *
 * @date:2016年9月30日/下午11:42:01
 * @Author:coder_czp@126.com
 * @version:1.0
 */
public class IPPerformTest {

	MemoryMXBean mmBean = ManagementFactory.getMemoryMXBean();

	@Test
	public void mmapImplPerformTest() {
		doTest(IpList.Factory.create(IpList.Factory.IPV4_MMAP_IMPL));
	}

	@Test
	public void bitmapImplPerformTest() {
		doTest(IpList.Factory.create(IpList.Factory.IPV4_BITMAP_IMPL));
	}

	@Test
	public void bitsetImplPerformTest() {
		doTest(IpList.Factory.create(IpList.Factory.IPV4_BITSET_IMPL));
	}

	private void doTest(IpList util) {
		util.loadConfig("./white_ip.txt");
		long start = System.currentTimeMillis();
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			util.isNumIpInList(i);
		}
		long end = System.currentTimeMillis();
		double times = (end - start) / 1000.0;
		long mm = (mmBean.getHeapMemoryUsage().getUsed()) / 1024 / 1024;
		System.out.println(String.format("time:%ss memory:%sMB Impl:%s", times,mm,util));
	}
}
