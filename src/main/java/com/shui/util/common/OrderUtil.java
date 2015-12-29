package com.shui.util.common;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生成订单号 100/s
 * 
 * @author zgj
 * */
public class OrderUtil {
	public static AtomicInteger race = new AtomicInteger(0);
	private static Date date = new Date();
	private static final int THREADS_COUNT = 99;

	private static int increase() {
		if (race.get() >= THREADS_COUNT) {
			race = new AtomicInteger(0);
		} else {
			return race.incrementAndGet();
		}
		return race.get();
	}

	public static synchronized String buildOrderId() {
		date.setTime(System.currentTimeMillis());
		return String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%2$02d", date,
				increase());
	}

	public static synchronized String buildOrderId(String prefix) {
		date.setTime(System.currentTimeMillis());
		StringBuilder sb = new StringBuilder(prefix);
		sb.append(String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%2$02d", date,
				increase()));
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(OrderUtil.buildOrderId("R"));
	}
}
