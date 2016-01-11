package com.shui.util.common;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinagleBeanSetter {
	private static final byte BYTE = 1;
	private static final short SHORT = 2;
	private static final int INT = 3;
	private static final long LONG = 4;
	private static final float FLOAT = 5;
	private static final double DOUBLE = 6;
	private static final char CHAR = 'c';
	private static final boolean BOOLEAN = true;
	private static final String STRING = "str";
	private static final byte[] BYTE_ARRAY = { 1 };
	private static final short[] SHORT_ARRAY = { 2 };
	private static final int[] INT_ARRAY = { 3 };
	private static final long[] LONG_ARRAY = { 4 };
	private static final float[] FLOAT_ARRAY = { 5 };
	private static final double[] DOUBLE_ARRAY = { 6 };
	private static final char[] CHAR_ARRAY = { 'c' };
	private static final boolean[] BOOLEAN_ARRAY = { true };
	private static final String[] STRING_ARRAY = { "str" };
	private static final Map<String, Object> baseMap = new HashMap<String, Object>();
	private static final Map<String, Object> sealingMap = new HashMap<String, Object>();
	private static final Map<String, Object> baseArrayMap = new HashMap<String, Object>();
	private static final Map<String, Object> sealingArrayMap = new HashMap<String, Object>();
	static {
		baseMap.put("byte", BYTE);
		baseMap.put("short", SHORT);
		baseMap.put("int", INT);
		baseMap.put("long", LONG);
		baseMap.put("float", FLOAT);
		baseMap.put("double", DOUBLE);
		baseMap.put("char", CHAR);
		baseMap.put("boolean", BOOLEAN);
		baseMap.put("java.lang.String", STRING);

		sealingMap.put("class java.lang.Byte", BYTE);
		sealingMap.put("class java.lang.Short", SHORT);
		sealingMap.put("class java.lang.Integer", INT);
		sealingMap.put("class java.lang.Long", LONG);
		sealingMap.put("class java.lang.Float", FLOAT);
		sealingMap.put("class java.lang.Double", DOUBLE);
		sealingMap.put("class java.lang.Char", CHAR);
		sealingMap.put("class java.lang.Boolean", BOOLEAN);
		sealingMap.put("class java.lang.String", STRING);

		baseArrayMap.put("byte[]", BYTE_ARRAY);
		baseArrayMap.put("short[]", SHORT_ARRAY);
		baseArrayMap.put("int[]", INT_ARRAY);
		baseArrayMap.put("long[]", LONG_ARRAY);
		baseArrayMap.put("float[]", FLOAT_ARRAY);
		baseArrayMap.put("double[]", DOUBLE_ARRAY);
		baseArrayMap.put("char[]", CHAR_ARRAY);
		baseArrayMap.put("boolean[]", BOOLEAN_ARRAY);
		baseArrayMap.put("class [Ljava.lang.String;", STRING_ARRAY);

		sealingArrayMap.put("java.lang.Byte[]", BYTE_ARRAY);
		sealingArrayMap.put("java.lang.Short[]", SHORT_ARRAY);
		sealingArrayMap.put("java.lang.Integer[]", INT_ARRAY);
		sealingArrayMap.put("java.lang.Long[]", LONG_ARRAY);
		sealingArrayMap.put("java.lang.Float[]", FLOAT_ARRAY);
		sealingArrayMap.put("java.lang.Double[]", DOUBLE_ARRAY);
		sealingArrayMap.put("java.lang.Char[]", CHAR_ARRAY);
		sealingArrayMap.put("java.lang.Boolean[]", BOOLEAN_ARRAY);
	}

	private static Object get(String key) {
		if (sealingMap.containsKey(key)) {
			return sealingMap.get(key);
		}
		if (sealingArrayMap.containsKey(key)) {
			return sealingArrayMap.get(key);
		}
		return getBase(key);
	}

	private static Object getBase(String key) {
		if (baseMap.containsKey(key)) {
			return baseMap.get(key);
		}
		if (baseArrayMap.containsKey(key)) {
			return baseArrayMap.get(key);
		}
		return null;
	}

	public static <Q> Q setter(Class<Q> bean) throws InstantiationException,
			IllegalAccessException {
		if (!bean.isInterface()) {
			if (bean.isArray()) {
				Class<?> clazs = (Class<?>) bean.getComponentType();
				Object clazss = Array.newInstance(clazs, 1);

				String type = clazs.getName();
				if (null != getBase(type)) {
					Array.set(clazss, 0, getBase(type));
				} else {
					Array.set(clazss, 0, setter(clazs));
				}
				return (Q) clazss;
			} else {
				Q q = bean.newInstance();
				Field[] fields = bean.getFields();
				for (Field field : fields) {
					if (!Modifier.isFinal(field.getModifiers())) {
						field.setAccessible(true);
						if(field.getType() == java.util.List.class){
							ParameterizedType pt = (ParameterizedType) field.getGenericType();
							Class clz = (Class) pt.getActualTypeArguments()[0];
							String type = clz.getName();
							List<Object> list = new ArrayList<Object>();
							list.add(get(type));
							field.set(q, list);
						}else{
							String type = field.getGenericType().toString();
							if (null != get(type)) {
								field.set(q, get(type));
							} else {
								field.set(q, setter(field.getType()));
							}
						}
					}
				}
				return q;
			}
		}
		return null;
	}

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
//		Test2 t2 = setter(Test2.class);
//		System.out.println(t2);
	}
}
