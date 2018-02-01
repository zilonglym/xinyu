package com.xinyu.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 其他类型转化为json字符串
 * */
public class JsonUtil {
		
    /** 
     * 通过反射机制,将枚举值转化为json串 
     * @param enumValues 
     * @return String
     * @throws IllegalAccessException 
     * @throws InvocationTargetException 
     */  
    public static String toJson(Enum<?>[] enumValues) throws IllegalAccessException, InvocationTargetException {  
        StringBuffer buffer=new StringBuffer("[");  
        boolean obj1st=true;  
        for (Object obj : enumValues) {  
            if(obj1st){  
                obj1st=false;  
            }else{  
                buffer.append(",");  
            }  
            buffer.append("{");  
      
            Method[] methods = obj.getClass().getMethods();  
            boolean method1st=true;  
            for (int i = 0; i < methods.length; i++) {  
      
                Method method = methods[i];  
                //获取枚举值的get方法  
                if (method.getName().startsWith("get") && method.getParameterTypes().length == 0 && !method.getName().contains("Class")) {  
                    //处理逗号  
                    if(method1st){  
                        method1st=false;  
                    }else{  
                        buffer.append(",");  
                    }  
                    //将get方法的get去掉,并且首字母小写  
                    String name = method.getName().replace("get","");  
                    buffer.append("\'" + name.substring(0, 1).toLowerCase() + name.substring(1) + "\':\'");  
                    buffer.append(method.invoke(obj)+"\'");  
                }  
            }  
            buffer.append("}");  
        }  
        buffer.append("]");  
        return buffer.toString();  
    }  
}
