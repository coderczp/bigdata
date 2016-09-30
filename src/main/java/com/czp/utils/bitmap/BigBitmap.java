//package com.czp.utils.bitmap;
//
///**
// * Function:支持long索引的bitmap
// *
// * @date:2016年9月30日/下午3:41:04
// * @Author:jeff@aoliday.cao
// * @version:1.0
// */
//public class BigBitmap {
//
//	public static final long MAX_VALUE =281474976710656l; //2 ^48;
//
//	/* 每20亿一个段用一个ByteBitmap标记 */
//	private ByteBitmap[] mapArr;
//
//	public BigBitmap(long maxValue) {
//		if (maxValue > MAX_VALUE || maxValue < 0)
//			throw new IllegalArgumentException("0=< num <= " + MAX_VALUE);
//
//		if (maxValue <= Integer.MAX_VALUE) {
//			mapArr = new ByteBitmap[1];
//			mapArr[0] = new ByteBitmap((int) maxValue);
//		} else {
//			int arrSize = (int) (maxValue / Integer.MAX_VALUE);
//			if (maxValue % Integer.MAX_VALUE != 0)
//				arrSize++;
//			mapArr = new ByteBitmap[arrSize];
//			int i = 0;
//			while (i < arrSize - 1) {
//				mapArr[i] = new ByteBitmap(Integer.MAX_VALUE);
//			}
//			// 最后一个bitmap的大小
//			mapArr[i] = new ByteBitmap((int) (maxValue % Integer.MAX_VALUE));
//		}
//	}
//
//	/***
//	 * 测试数据是否设置bit位
//	 * 
//	 * @param num
//	 * @return
//	 */
//	public boolean get(long num) {
//
//		if (num < 0 || num > MAX_VALUE)
//			return false;
//
//		// 1 计算数据的数组位2把所有数据都折算为0~20亿
//		int arrIndex = (int) (num >> 32);
//		int tmp = (int) (num - (arrIndex << 32));
//		return mapArr[arrIndex].get(tmp);
//	}
//
//	/**
//	 * 设置num的bit标记
//	 * 
//	 * @param num
//	 */
//	public void set(long num) {
//		if (num < 0 || num > MAX_VALUE)
//			return;
//
//		// 1 计算数据的数组位2把所有数据都折算为0~20亿
//		int arrIndex = (int) (num >> 32);
//		int tmp = (int) (num - (arrIndex << 32));
//		mapArr[arrIndex].set(tmp);
//	}
//}
