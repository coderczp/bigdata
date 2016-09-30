package com.czp.utils.bitmap;

/**
 * Function:֧��long������bitmap
 *
 * @date:2016��9��30��/����3:41:04
 * @Author:jeff@aoliday.cao
 * @version:1.0
 */
public class BigBitmap {

	public static final long MAX_VALUE =281474976710656l; //2 ^48;

	/* ÿ20��һ������һ��ByteBitmap��� */
	private ByteBitmap[] mapArr;

	public BigBitmap(long maxValue) {
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
			// ���һ��bitmap�Ĵ�С
			mapArr[i] = new ByteBitmap((int) (maxValue % Integer.MAX_VALUE));
		}
	}

	/***
	 * ���������Ƿ�����bitλ
	 * 
	 * @param num
	 * @return
	 */
	public boolean get(long num) {

		if (num < 0 || num > MAX_VALUE)
			return false;

		// 1 �������ݵ�����λ2���������ݶ�����Ϊ0~20��
		int arrIndex = (int) (num >> 32);
		int tmp = (int) (num - (arrIndex << 32));
		return mapArr[arrIndex].get(tmp);
	}

	/**
	 * ����num��bit���
	 * 
	 * @param num
	 */
	public void set(long num) {
		if (num < 0 || num > MAX_VALUE)
			return;

		// 1 �������ݵ�����λ2���������ݶ�����Ϊ0~20��
		int arrIndex = (int) (num >> 32);
		int tmp = (int) (num - (arrIndex << 32));
		mapArr[arrIndex].set(tmp);
	}
}
