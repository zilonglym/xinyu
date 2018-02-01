package com.graby.store.admin.util;

import java.io.Serializable;

public class JsonModel implements Serializable{
private boolean success=false;
private String msg="";
	private Object obj = null;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer("\"{success:'");
		str.append(success).append("',");
		str.append("msg:").append("'").append(msg).append("'}\"");
		return str.toString();
	}
}
