package org.cx.game.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.cx.game.widget.Ground;
import org.cx.game.widget.Scene;

public class CommandUtil {

	public static Class getParameterType(String str) {
		if(Util.isInteger(str))
			return Integer.class;
		if(Util.isBoolean(str))
			return Boolean.class;
		
		return String.class;
	}
	
	public static Object getParameterObject(String str) {
		if(Util.isInteger(str))
			return Integer.valueOf(str);
		if(Util.isBoolean(str))
			return Boolean.valueOf(str);
		
		return str;
	}
	
	/**
	 * clzs2[i] 等于 clzs1[i]，或是父类
	 * @param c1 命令参数的class
	 * @param c2 方法参数的class
	 * @return
	 */
	public static Boolean compareParameterClass(Class [] c1, Class[] c2) {
		if(c1.length != c2.length)
			return false;
		
		for(int i=0;i<c1.length;i++){
			if(c1[i].equals(c2[i]) || isSuperClassOrInterface(c2[i], c1[i]))
				continue;
			else
				return false;
		}
		
		return true;
	}
	
	/**
	 * c1 是 c2 的父类或接口则返回true
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static Boolean isSuperClassOrInterface(Class c1, Class c2) {
		List<Class> list = new ArrayList<Class>();
		Class superClass = c2.getSuperclass();
		while(null!=superClass){
			list.add(superClass);
			superClass = superClass.getSuperclass();
		}
		
		for(Class inf : c2.getInterfaces())
			list.add(inf);
		
		return list.contains(c1);
	}
	
	public static String conversionNotifyType(String name) {
		Properties prop = new Properties();
		
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("resource/conversion.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path = prop.getProperty(name);
		return path;
	}
	
	public static void main(String[] args) {
		Class c2 = Ground.class;
		Class c1 = Scene.class;
		
		System.out.println(isSuperClassOrInterface(c2, c1));
	}
}
