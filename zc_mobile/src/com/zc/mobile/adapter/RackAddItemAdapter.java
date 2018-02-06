package com.zc.mobile.adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * ��ѡ�Ի���
 * @author admin
 *
 */
public class RackAddItemAdapter extends BaseAdapter{
	
	private JSONArray itemArray;
	
	private Context context;
	
	public RackAddItemAdapter(Context context){
		this.context=context;
	}
	
	
	public void setItemArray(JSONArray array){
		this.itemArray=array;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemArray==null?0:itemArray.length();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView tv=new TextView(context);
		tv.setHeight(60);
		JSONObject obj=itemArray.optJSONObject(arg0);
		tv.setText(obj.optString("userName")+" | "+ obj.optString("itemName")+" | "+obj.optString("code")+"|"+obj.optString("sku"));
		tv.setTag(obj.optString("itemId"));
		return tv;
	}

}
