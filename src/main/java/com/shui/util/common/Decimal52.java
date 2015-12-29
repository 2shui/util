package com.shui.util.common;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 26/52进制与10进制互转工具类
 * 
 * @author zgj 2015-12-29 11:09:51
 * 
 * */
public class Decimal52 {
	private static final int LOWER_A = 97;
	private static final int LOWER_Z = 123;
	private static final int UPPER_A = 65;
	private static final int UPPER_Z = 91;
	private List<Character> C_LIST = new LinkedList<Character>();
	private int charSize;

	public Decimal52() {
		this(true, false);
	}

	public Decimal52(boolean upperOnly, boolean lowerOnly) {
		if (upperOnly) {
			IntStream.range(UPPER_A, UPPER_Z).forEach(
					(c) -> C_LIST.add((char) c));
		} else if (lowerOnly) {
			IntStream.range(LOWER_A, LOWER_Z).forEach(
					(c) -> C_LIST.add((char) c));
		} else {
			IntStream.range(UPPER_A, UPPER_Z).forEach(
					(c) -> C_LIST.add((char) c));
			IntStream.range(LOWER_A, LOWER_Z).forEach(
					(c) -> C_LIST.add((char) c));
		}
		charSize = C_LIST.size();
	}

	/**
	 * @version 1.1
	 * @Description 26/52进制转10进制<br/>
	 *              -1非法字符转换
	 * @return
	 * */
	public int getDecimalism(String str) {
		int num = 0;
		char[] cNum = str.toCharArray();
		int length = cNum.length;
		for (char c : cNum) {
			if (C_LIST.indexOf(c) < 0)
				return -1;
			num += C_LIST.indexOf(c) * (Math.pow(charSize, (--length)));
		}
		return num;
	}

	/**
	 * @version 1.0.0
	 * @Description 10进制转26/52进制
	 * @return
	 * */
	public String getDecimal(int num) {
		if (num < 0)
			return null;
		StringBuilder sb = new StringBuilder();
		if (num >= 26) {
			sb.append(this.getDecimal(num / charSize));
		}
		int least = num % charSize;
		sb.append(C_LIST.get(least));

		return sb.toString();
	}

	public static void main(String[] args) {
		Decimal52 de = new Decimal52();
		System.out.println(de.getDecimal(10));
		System.out.println(de.getDecimalism("FA"));
	}
}
