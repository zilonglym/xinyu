package com.xinyu.util;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;


public class CityJson {
	
	private GroupMap<String, String> stateMap = new GroupMap<String, String>();
	
	public void putCity(String state, String city) {
		List<String> citys = stateMap.getList(state);
		if (CollectionUtils.isNotEmpty(citys) && citys.contains(city)) {
			return;
		}
		stateMap.put(state, city);
	}
	
	public String getJson() {
		if (stateMap == null || stateMap.getSize() == 0) return "";
		StringBuffer b = new StringBuffer();
		
		b.append("{" + c("citylist") + ":[\n");
		
		for (Iterator<String> iterator = stateMap.getKeySet().iterator(); iterator.hasNext();) {
			String state =  iterator.next();
			b.append("{" + c("p") + ":").append(c(state)).append(",");
			b.append(c("c") +  ":").append(parseToJson(stateMap.getList(state))).append("}");
			if (iterator.hasNext()) {
				b.append(",");
			}
			b.append("\n");
		}
		b.append("]}");
		return b.toString();
	}
	
	private String parseToJson(List<String> citys) {
		StringBuffer buf = new StringBuffer();
		buf.append("[");
		for (Iterator<String> iterator = citys.iterator(); iterator.hasNext();) {
			String city = (String) iterator.next();
			buf.append("{" + c("n") + ":").append(c(city)).append("}");
			if (iterator.hasNext()) {
				buf.append(",");
			}
		}
		buf.append("]");
		return buf.toString();
	}
	
	private String c(String c) {
		StringBuffer buf =new StringBuffer();
		buf.append("\"").append(c).append("\"");
		return buf.toString();
	}
	
	public static void main(String[] args) {
		CityJson m = new CityJson();
		m.putCity("湖南省", "长沙市");
		m.putCity("湖南省", "湘潭市");
		m.putCity("湖北省", "荆州市");
		m.putCity("湖北省", "武汉市");
		m.putCity("湖南省", "长沙市");
		System.out.println(m.getJson());
	}
}
