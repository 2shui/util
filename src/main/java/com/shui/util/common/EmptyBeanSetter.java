package com.shui.util.common;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyBeanSetter {
	private static final Logger log = LoggerFactory.getLogger(EmptyBeanSetter.class);
	
	private static final byte BYTE = 1;
	private static final short SHORT = 2;
	private static final int INT = 3;
	private static final long LONG = 4;
	private static final float FLOAT = 5;
	private static final double DOUBLE = 6;
	private static final char CHAR = 'c';
	private static final boolean BOOLEAN = true;
	private static final String STRING = "str";
	private static final byte[] BYTE_ARRAY = {1};
	private static final short[] SHORT_ARRAY = {2};
	private static final int[] INT_ARRAY = {3};
	private static final long[] LONG_ARRAY = {4};
	private static final float[] FLOAT_ARRAY = {5};
	private static final double[] DOUBLE_ARRAY = {6};
	private static final char[] CHAR_ARRAY = {'c'};
	private static final boolean[] BOOLEAN_ARRAY = {true};
	private static final String[] STRING_ARRAY = {"str"};
	private static final Map<String, Object> typeMap = new HashMap<String, Object>();
	static {
		typeMap.put("byte", BYTE);
		typeMap.put("short", SHORT);
		typeMap.put("int", INT);
		typeMap.put("long", LONG);
		typeMap.put("float", FLOAT);
		typeMap.put("double", DOUBLE);
		typeMap.put("char", CHAR);
		typeMap.put("boolean", BOOLEAN);
		typeMap.put("class java.lang.String", STRING);
		
		typeMap.put("byte[]", BYTE_ARRAY);
		typeMap.put("short[]", SHORT_ARRAY);
		typeMap.put("int[]", INT_ARRAY);
		typeMap.put("long[]", LONG_ARRAY);
		typeMap.put("float[]", FLOAT_ARRAY);
		typeMap.put("double[]", DOUBLE_ARRAY);
		typeMap.put("char[]", CHAR_ARRAY);
		typeMap.put("boolean[]", BOOLEAN_ARRAY);
		typeMap.put("class java.lang.String[]", STRING_ARRAY);
	}

	private static boolean instanceofBaseArray(String typeName) {
		return typeMap.containsKey(typeName);
	}
	
	public static <Q> Q setter(Class<Q> bean) throws InstantiationException,
			IllegalAccessException {
		if (!bean.isInterface()) {
			
			if (bean.isArray()) {
				if(instanceofBaseArray(bean.getTypeName())){
					return (Q) typeMap.get(bean.getTypeName());
				}else{
					// TODO
				}
			} else {
				Q q = bean.newInstance();
//				Field[] fields = bean.getDeclaredFields();
				Field[] fields = bean.getFields();
				for (Field field : fields) {
					if (!Modifier.isFinal(field.getModifiers())) {
						field.setAccessible(true);
						String type = field.getGenericType().toString();
						if (typeMap.containsKey(type)) {
							field.set(q, typeMap.get(type));
						} else {
							field.set(q, setter(field.getType()));
						}
					}
				}
				return q;
			}

		} else {
			// TODO
			try {
				List<Class<?>> classes = getAllAssignedClass(bean);
				if(classes.size()>0){
					return (Q) setter(classes.get(0));
				}
				
			} catch (ClassNotFoundException e) {
				log.error("{} get Assigned class error {}",bean, e);
				e.printStackTrace();
			}
			
		}
		return null;
	}

	
	private static List<Class<?>> getAllAssignedClass(Class<?> cls) throws ClassNotFoundException{
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for(Class<?> c:getClasses(cls)){
			if(cls.isAssignableFrom(c) && !cls.equals(c)){
				classes.add(c);
			}
		}
		return classes;
	}
	
	private static List<Class<?>> getClasses(Class<?> cls) throws ClassNotFoundException{
		String pk = cls.getPackage().getName();
		String path = pk.replace('.', '/');
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL url = classloader.getResource(path);
		Class ddd = classloader.loadClass("com.biostime.coupon.rpc.utils.EmptyBeanSetter");
		URL url3 = classloader.getSystemResource("/"+path);
		URL url2 = classloader.getResource("/com/biostime");
		return getClasses(new File(url.getFile()), pk);
	}
	
	private static List<Class<?>> getClasses(File dir, String pk) throws ClassNotFoundException{
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if(!dir.exists()){
			return classes;
		}
		for(File f:dir.listFiles()){
			if(f.isDirectory()){
				classes.addAll(getClasses(f, pk+"."+f.getName()));
			}
			String name = f.getName();
			if(name.endsWith(".class")){
				classes.add(Class.forName(pk+"."+name.substring(0,name.length()-6)));
			}
		}
		
		return classes;
	}
	
	
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		// return EmptyBeanSetter.setter(response.class);
//		QueryCouponResponse resp = setter(QueryCouponResponse.class);
//		System.out.println(resp);
	}
}