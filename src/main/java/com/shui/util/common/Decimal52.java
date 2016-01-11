package com.shui.util.common;

import java.math.BigDecimal;
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
	private static final int ZERO = 48;
	private static final int NINE = 58;
	private List<Character> C_LIST = new LinkedList<Character>();
	private int charSize;

	public Decimal52() {
		this(true, false, true);
	}

	public Decimal52(boolean upperOnly, boolean lowerOnly, boolean hasNum) {
		if (hasNum) {
			IntStream.range(ZERO, NINE).forEach((c) -> C_LIST.add((char) c));
		}
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
	 * @version 1.0.0
	 * @Description 10进制转26/52进制
	 * @return
	 * */
	public String getDecimal(int num) {
		if (num < 0)
			return null;
		StringBuilder sb = new StringBuilder();
		if (num >= charSize) {
			sb.append(this.getDecimal(num / charSize));
		}
		int least = num % charSize;
		sb.append(C_LIST.get(least));

		return sb.toString();
	}

	/**
	 * @version 1.1
	 * @Description N进制转10进制<br/>
	 *              null非法字符转换
	 * @return
	 * */
	public BigDecimal getDecimalism(String str) {
		BigDecimal num = BigDecimal.valueOf(0);
		char[] cNum = str.toCharArray();
		int length = cNum.length;
		for (char c : cNum) {
			if (C_LIST.indexOf(c) < 0)
				return null;
			num = num.add(BigDecimal.valueOf(C_LIST.indexOf(c)).multiply(
					BigDecimal.valueOf(Math.pow(charSize, (--length)))));
		}
		return num;
	}

	/**
	 * @version 1.0.0
	 * @Description 10进制转N进制
	 * @return
	 * */
	public String getDecimal(BigDecimal num) {
		if (num.compareTo(BigDecimal.valueOf(0)) < 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		BigDecimal[] divideAndRemainder = num.divideAndRemainder(BigDecimal
				.valueOf(charSize));
		if (divideAndRemainder[0].intValue() > 0) {
			sb.append(this.getDecimal(divideAndRemainder[0]));
		}
		int least = divideAndRemainder[1].intValue();
		sb.append(C_LIST.get(least));

		return sb.toString();
	}

	public static void main(String[] args) {
		Decimal52 de = new Decimal52();
		System.out.println(de.getDecimal(BigDecimal.valueOf(382938278328l)));// 4VX3KAM0
		System.out.println(de.getDecimalism("ZZZZZZZZ"));// 2821109907455.0
		System.out.println(de.getDecimal(BigDecimal
				.valueOf(382938278328329482l)));// 2WQKAGGVC5CA
		System.out.println(de.getDecimal(BigDecimal
				.valueOf(382938278328329483l)));// 2WQKAGGVC5CB
		System.out.println(de.getDecimalism("FAFAFAFAFAFAFAFAFAFA"));// 5677314282906604921686077693550.0
	}
}
