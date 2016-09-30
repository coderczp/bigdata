package com.czp.utils.bitmap;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Function:��bitλ�������,Ϊ������һ�������㹻���ڴ�<br>
 * ����bitset��̬����,һ��byte�ֽڹ�8bitλ,���Ա�� 8<br>
 * ������,�����ڴ�Ϊ:MaxNum/8���һ�����Ĳ���:<br>
 * 1.�ҵ��������bit�����������:int index =num>>3(����num/8)<br>
 * 2.�ҵ��������num[index]��bitλ: int bitIndex=num&7<br>
 * ������λ����,num %8 = num&((1<<3)-1)==num&7<br>
 * 3.��bitΪ���Ϊ1: num[index]|=1<<num&7 <br>
 * 
 * @date:2016��9��25��/����3:07:51
 * @Author:coder_czp@126.com
 * @version:1.0
 */
public class ByteBitmap {

	private byte[] map;

	private int len;

	private int max;

	public ByteBitmap(int maxValue) {
		/** һ��byte8��bit */
		len = maxValue / 8;
		len = (maxValue % 8 == 0) ? len : len++;
		map = new byte[len];
		max = maxValue;
	}

	/**
	 * �ж�ĳ�������Ƿ���Ϊ1
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
	 * ���ñ�־λΪ1
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
