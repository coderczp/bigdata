package com.czp.utils.bitmap;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Function:用bit位标记数据,为了性能一次申请足够的内存<br>
 * 避免bitset动态扩容,一个byte字节共8bit位,可以标记 8<br>
 * 个数字,所需内存为:MaxNum/8标记一个数的步骤:<br>
 * 1.找到这个数在bit数组里的索引:int index =num>>3(等于num/8)<br>
 * 2.找到这个数在num[index]的bit位: int bitIndex=num&7<br>
 * 对于移位运算,num %8 = num&((1<<3)-1)==num&7<br>
 * 3.将bit为标记为1: num[index]|=1<<num&7 <br>
 * 
 * @date:2016年9月25日/下午3:07:51
 * @Author:coder_czp@126.com
 * @version:1.0
 */
public class ByteBitmap {

	private byte[] map;

	private int len;

	private int max;

	public ByteBitmap(int maxValue) {
		/** 一个byte8个bit */
		len = maxValue / 8;
		len = (maxValue % 8 == 0) ? len : len++;
		map = new byte[len];
		max = maxValue;
	}

	/**
	 * 判断某个数字是否标记为1
	 * 
	 * @param num
	 * @return
	 */
	public boolean get(int num) {
		if (num < 0)
			throw new IllegalArgumentException("num < 0: " + num);

		int arrIndex = num >> 3;
		return (arrIndex < len) && ((map[arrIndex] & (1 << (num & 7))) != 0);
	}

	/***
	 * 设置标志位为1
	 * 
	 * @param num
	 */
	public void set(int num) {
		if (num < 0 || num > max)
			throw new IllegalArgumentException("num must be >=0 add <=" + max);

		int arrIndex = num >> 3;
		int bitIndex = num & 7;
		map[arrIndex] |= (1 << bitIndex);
	}
}
