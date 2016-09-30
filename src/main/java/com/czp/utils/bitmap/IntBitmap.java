package com.czp.utils.bitmap;

/**
 * Function:用bit位标记数据,为了性能一次申请足够的内存<br>
 * 避免bitset动态扩容,一个int4字节共32bit位,可以标记 32<br>
 * 个数字,标记一个数的步骤:<br>
 * 1.找到这个数在bit数组里的索引:int index =num>>5(等于num/32)<br>
 * 2.找到这个数在num[index]的bit位: int bitIndex=1<<num<br>
 * 对于移位运算,对于32位 N左移m位==N<<(N mod 32) 3.将bit为标记为1: num[index]|=1<<num<br>
 * 
 * @date:2016年9月25日/下午3:07:51
 * @Author:coder_czp@126.com
 * @version:1.0
 */
public class IntBitmap {

	private int[] map;

	private int len;

	private int max;

	public IntBitmap(int maxValue) {
		/** 一个Int有32位bit */
		len = maxValue / 32;
		len = (maxValue % 32 == 0) ? len : len+1;
		map = new int[len];
		max = maxValue;
	}

	public boolean get(int num) {
		if (num < 0)
			throw new IllegalArgumentException("num < 0: " + num);

		int index = num >> 5;
		return (index < len) && ((map[index] & (1 << num)) != 0);
	}

	public void set(int num) {
		if (num < 0 || num > max)
			throw new IllegalArgumentException("num must be >=0 add <=" + max);

		int index = num >> 5;
		map[index] |= (1 << num);
	}
}
