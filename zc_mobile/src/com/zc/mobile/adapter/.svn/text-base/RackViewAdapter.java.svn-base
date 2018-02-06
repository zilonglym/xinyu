package com.zc.mobile.adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.zc.mobile.R;
import com.zc.mobile.fragments.racks.RackLocatorFragment;

public class RackViewAdapter extends BaseAdapter {

	private JSONObject array;

	private Context context;
	
	private FragmentManager manager;

	public RackViewAdapter(Context context) {
		this.context = context;
	}

	public void setData(JSONObject array ) {
		this.array = array;
	}

	public void setFragmentManager(FragmentManager manager){
		this.manager=manager;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array!=null?array.length():5;
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
		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.HORIZONTAL);
	
		if(array==null){
			return ll; 
		}
		JSONArray ary=array.optJSONArray(String.valueOf(index+1));
		if(ary!=null){
			System.err.println(ary);
		}
		for (int i = 0; ary!=null && i <  ary.length(); i++) {
			JSONObject jsonRow=ary.optJSONObject(i);
			if (i == 0) {
				TextView btn = new TextView(context);
				btn.setText("" + (index+1));
				btn.setWidth(10);
				btn.setEnabled(false);
			//	ll.addView(btn);
			}
			Button btn = new Button(context);
			if(ary.length()>8){
				android.view.ViewGroup.LayoutParams btnPara = new LayoutParams(80,150);
				btn.setLayoutParams(btnPara);
			}else{
				android.view.ViewGroup.LayoutParams btnPara = new LayoutParams(130,150);
				btn.setLayoutParams(btnPara);
			}
			
//			btn.setBackgroundResource(R.drawable.image_1);
			
//			btnPara.width=60;
//			btnPara.height=120;
			
			String temp_value=jsonRow.optString("row")+jsonRow.optString("boxOut")+" boxCode "+jsonRow.optString("floor");
			String value=jsonRow.optString("row")+jsonRow.optString("boxOut")+jsonRow.optString("boxCode")+jsonRow.optString("floor");
			btn.setText(value);
			
			btn.setTag(jsonRow.optString("id")+","+temp_value+","+jsonRow.optString("row"));
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Button button=(Button) view;
					openRackLocatorFragment(button.getTag().toString());
				}
			});
			ll.addView(btn);
		}
		return ll;
	}

	
	/**
	 * fragment �л�
	 * @param value
	 */
	private void openRackLocatorFragment(String value){
		
		String[] ary=value.split(",");
		System.err.println("openRackLocatorFragment"+ary[0]+"xx"+ary[1]);
		RackLocatorFragment rackLocatorFragment=new RackLocatorFragment();
		rackLocatorFragment.setBoxId(ary[0]);
		rackLocatorFragment.setTitle(ary[1]);
		rackLocatorFragment.setRowId(ary[2]);
		FragmentTransaction transaction= manager.beginTransaction();
		transaction.replace(R.id.lay_home, rackLocatorFragment);
		transaction.commit();
	}
}
