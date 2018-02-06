package com.zc.mobile.adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zc.mobile.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderBeforeListAdapter extends BaseAdapter{

	private  JSONArray orderArray;
	
	private Context context;
	
	public OrderBeforeListAdapter(Context context){
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return orderArray!=null?orderArray.length():0;
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
	
	public void setData(JSONArray array){
		this.orderArray=array;
	}

	@Override
	public View getView(int index, View v, ViewGroup arg2) {
		View view = View.inflate(context, R.layout.order_before_item_list,null);
		
		TextView txt_shopName=(TextView) view.findViewById(R.id.order_before_item_shopName);		
		TextView txt_title=(TextView) view.findViewById(R.id.order_before_item_title);
		TextView txt_num=(TextView) view.findViewById(R.id.order_before_item_num);		
		TextView txt_noFinish=(TextView) view.findViewById(R.id.order_before_item_noFinishNum);
		TextView txt_finishNum=(TextView) view.findViewById(R.id.order_before_item_finishNum);
		JSONObject orderJson=null;
		if(index>orderArray.length()){
			index=orderArray.length();
		}
		orderJson=orderArray.optJSONObject(index);
		//{"auditQuantity":0,"itemName":"DDG-30AZ","shopName":"���(����)","finishQuantity":4,"printQuantity":0,"quantity":"5","itemId":"7229","itemCode":"30az"}
		if(orderJson!=null){
			txt_shopName.setText(orderJson.optString("shopName"));
			txt_title.setText(orderJson.optString("itemName"));
			txt_num.setText(orderJson.optString("quantity"));
			int quantity=orderJson.optInt("quantity")-orderJson.optInt("finishQuantity");
			txt_noFinish.setText(""+quantity);
			txt_finishNum.setText(""+orderJson.optInt("finishQuantity"));
			
		}
		return view;
	}

	
}
