package com.zc.mobile.fragments.racks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zc.mobile.R;
import com.zc.mobile.adapter.RackMainAdapter;
import com.zc.mobile.common.Constants;
import com.zc.mobile.util.VolleyUtil;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 仓库上下架主入口
 * @author admin
 *
 */
public class RackMainFragment extends Fragment implements  OnItemClickListener{

	
	private ListView rackList;
	private RackMainAdapter adapter;
	private JSONArray array;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.rack_main, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rackList=(ListView) this.getActivity().findViewById(R.id.rack_main_list);
		refreshList();
		super.onActivityCreated(savedInstanceState);
	}
	
	private void refreshList(){
		adapter=new RackMainAdapter(getActivity());
		String url = Constants.WLPOST_URL + "local/getRows";		
		getData(url);
		adapter.setManager(this.getFragmentManager());
		rackList.setAdapter(adapter);
//		rackList.setOnItemClickListener(this);
	}
	
	
	private void getData(String url){
		JSONObject jsonObject = null;
		System.err.println(url);
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						array = response.optJSONArray("rows");
						System.err.println(array);
						adapter.setData(array);
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						System.err.println(error.getLocalizedMessage());
						System.err.println(error.getMessage());
					}
				});
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		queue.add(request);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		LinearLayout lineOut=(LinearLayout) view;
		TextView menuText=(TextView) lineOut.getChildAt(0);
		String value=menuText.getText().toString();
		/**
		 * 点击切换至单个货架详情
		 */
		FragmentManager manager=this.getFragmentManager();
		RackViewFragment rackViewFragment=new RackViewFragment();
		rackViewFragment.setRowId(value);
		FragmentTransaction transaction= manager.beginTransaction();
		transaction.replace(R.id.lay_home, rackViewFragment);
		transaction.commit();
	}
}
