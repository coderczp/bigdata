package com.czp.utils.bitmap;

import java.io.IOException;
import java.io.RandomAccessFile;
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

	private int len;
	private int max;
	private RandomAccessFile rf;
	private MappedByteBuffer map;

	public MMapBitmap(int maxValue) {
		try {
			/** 一个byte8个bit */
			len = maxValue / 8;
			len = (maxValue % 8 == 0) ? len : len + 1;
			rf = new RandomAccessFile("bit.dat", "rw");
			map = rf.getChannel().map(FileChannel.MapMode.PRIVATE, 0, len);
			map.put(len - 1, (byte) 0);
			max = maxValue;
			addShutdwon();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/***
	 * 测试数据是否设置bit位
	 * 
	 * @param num
	 * @return
	 */
	public boolean get(int num) {

		if (num < 0 || num > max)
			return false;

		int arrIndex = num >> 3;
		return (arrIndex < len) && ((map.get(arrIndex) & (1 << (num & 7))) != 0);
	}

	/**
	 * 设置num的bit标记
	 * 
	 * @param num
	 */
	public void set(int num) {
		if (num < 0 || num > max)
			return;
		int arrIndex = num >> 3;
		int bitIndex = num & 7;
		map.put(arrIndex, (byte) (map.get(arrIndex) | (1 << bitIndex)));
	}

	public void release() {
		try {
			rf.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	/***
	 * JVM退出时自动释放内存映射
	 * 
	 * @param rf
	 */
	private void addShutdwon() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				release();
			}
		}));
	}
}
