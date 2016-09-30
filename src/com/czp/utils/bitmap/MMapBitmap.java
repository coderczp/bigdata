package com.czp.utils.bitmap;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Function:基于内存映射的bitmap
 *
 * @date:2016年9月30日/下午6:27:08
 * @Author:jeff@aoliday.cao
 * @version:1.0
 */
public class MMapBitmap {

	public static final long MAX_VALUE = 281474976710656l; // 2 ^48;
	
	/* 每20亿一个段用一个ByteBitmap标记 */
	private ByteBitmap[] mapArr;

	public MMapBitmap(long maxValue) {
		if (maxValue > MAX_VALUE || maxValue < 0)
			throw new IllegalArgumentException("0=< num <= " + MAX_VALUE);

		if (maxValue <= Integer.MAX_VALUE) {
			mapArr = new ByteBitmap[1];
			mapArr[0] = new ByteBitmap((int) maxValue);
		} else {
			int arrSize = (int) (maxValue / Integer.MAX_VALUE);
			if (maxValue % Integer.MAX_VALUE != 0)
				arrSize++;
			mapArr = new ByteBitmap[arrSize];
			int i = 0;
			while (i < arrSize - 1) {
				mapArr[i] = new ByteBitmap(Integer.MAX_VALUE);
			}
			// 最后一个bitmap的大小
			mapArr[i] = new ByteBitmap((int) (maxValue % Integer.MAX_VALUE));
		}
	}

	/***
	 * 测试数据是否设置bit位
	 * 
	 * @param num
	 * @return
	 */
	public boolean get(long num) {

		if (num < 0 || num > MAX_VALUE)
			return false;

		// 1 计算数据的数组位2把所有数据都折算为0~20亿
		int arrIndex = (int) (num >> 32);
		int tmp = (int) (num - (arrIndex << 32));
		return mapArr[arrIndex].get(tmp);
	}

	/**
	 * 设置num的bit标记
	 * 
	 * @param num
	 */
	public void set(long num) {
		if (num < 0 || num > MAX_VALUE)
			return;

		// 1 计算数据的数组位2把所有数据都折算为0~20亿
		int arrIndex = (int) (num >> 32);
		int tmp = (int) (num - (arrIndex << 32));
		mapArr[arrIndex].set(tmp);
	}

	public static void main(String[] args) {
		try {
			MemoryMXBean mmBean = ManagementFactory.getMemoryMXBean();
			RandomAccessFile rf = new RandomAccessFile("res.txt", "rw");
			int max = 100000000;
			MappedByteBuffer buffer = rf.getChannel().map(FileChannel.MapMode.PRIVATE, 0, max);
			long t1 = System.currentTimeMillis();
			for (int i = 0; i < max; i++) {
				byte b = (byte) ((1 << i & 7));
				buffer.put(b);
			}
			for (int i = 0; i < max; i++) {
				buffer.get(i);
			}
			double t = (System.currentTimeMillis() - t1) / 1000.0;
			rf.close();
			System.out.println(t + "-memory used(MB):" + (mmBean.getHeapMemoryUsage().getUsed()) / 1024 / 1024);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
