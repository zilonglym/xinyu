package com.zc.mobile.adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zc.mobile.R;
import com.zc.mobile.fragments.racks.RackViewFragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class RackMainAdapter extends BaseAdapter{
	
	private JSONArray array;
	
	private Context context;
	
	private FragmentManager manager;
	
	public void setManager(FragmentManager manager) {
		this.manager = manager;
	}

	public RackMainAdapter(Context context){
		this.context=context;
	}
	
	public void setData(JSONArray array){
		this.array=array;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array==null?0:array.length()/5;
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
	public View getView(int index, View arg1, ViewGroup arg2) {
		View view = View.inflate(context, R.layout.rack_main_item,null);
		TextView txt_view=(TextView) view.findViewById(R.id.rack_main_item);	
		JSONObject object=null;
		if(index>array.length()){
			index=array.length();
		}
		
//		txt_view.setText(object.optString("row"));
		
		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.HORIZONTAL);
		ll.setGravity(Gravity.CENTER);
		int row=array.length()/5;
		for(int i=0;i<5;i++){
			int n=5*index+i;
			object=array.optJSONObject(n);
			Button btn = new Button(context);
			btn.setWidth(200);
//			btn.setGravity(LinearLayout.HORIZONTAL);
			btn.setBackgroundResource(R.drawable.local);
//			btn.setBackgroundColor(Color.GRAY);
			btn.setText(object.optString("row"));
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Button button=(Button) arg0;
					/**
					 * 点击切换至单个货架详情
					 */
					RackViewFragment rackViewFragment=new RackViewFragment();
					rackViewFragment.setRowId(button.getText().toString());
					FragmentTransaction transaction= manager.beginTransaction();
					transaction.replace(R.id.lay_home, rackViewFragment);
					transaction.commit();
				}
			});
			ll.addView(btn);
		}
		return ll;
	}

}
