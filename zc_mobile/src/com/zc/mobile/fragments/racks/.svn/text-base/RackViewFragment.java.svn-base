package com.zc.mobile.fragments.racks;

import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zc.mobile.R;
import com.zc.mobile.activity.racks.RackListPopupWindow;
import com.zc.mobile.adapter.RackViewAdapter;
import com.zc.mobile.common.Constants;

public class RackViewFragment extends Fragment implements OnClickListener {

	private ListView rackViewList;
	private JSONObject arrayList;
	private RackViewAdapter adapter;
	/**
	 * 货架ID
	 */
	private String rowId;
	
	private RackListPopupWindow popupWindow;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.activity_rack_view, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		rackViewList = (ListView) getActivity()
				.findViewById(R.id.rackView_list);
		rackVeiw();
		adapter.setFragmentManager(this.getFragmentManager());
		Button btn_back=(Button) getActivity().findViewById(R.id.rackView_back);
		btn_back.setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View view) {
		FragmentManager manager=this.getFragmentManager();
		RackMainFragment fragment=new RackMainFragment();
		FragmentTransaction transaction= manager.beginTransaction();
		transaction.replace(R.id.lay_home, fragment);
		transaction.commit();
	}
	/**
	 * 视图列表展示
	 */
	private void rackVeiw() {
		adapter=new RackViewAdapter(getActivity());
		String url = Constants.WLPOST_URL + "local/getBoxOutList/"+rowId;	
//		Toast.makeText(getActivity(), url, 0).show();
		getData(url);
		rackViewList.setAdapter(adapter);
	}
	
	
	public void setRowId(String rowId){
		this.rowId=rowId;
	}

	
	private void getData(String url){
		JSONObject jsonObject = null;
		System.err.println(url);
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						arrayList = response.optJSONObject(rowId);
						adapter.setData(arrayList);
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
	
	
/*	
	private void openWindow(String value) {
		popupWindow=new RackListPopupWindow(getActivity(),value);
		// location获得控件的位置
		int[] location = new int[2];

		popupWindow.setOnData(new OnGetData() {
			
			@Override
			public int onSeclectItem() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public void onDataCallBack(String value) {
				// TODO Auto-generated method stub
				openRackAddWin(value);
			}
		});
		
		popupWindow.setAnimationStyle(R.style.AppBaseTheme);
		 WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		 lp.alpha = 1f;
		 getActivity().getWindow().setAttributes(lp);
		// mTestPopwindow2弹出在某控件(button)的下面
		popupWindow.showAtLocation(getView(), Gravity.CENTER,
				0, 0);
	}
	
	private void openRackAddWin(String id){
//		Toast.makeText(getActivity(), "打开第二个窗口", 0).show();
		popupWindow.dismiss();
		RackAddPopupWindow rackAddWin=new RackAddPopupWindow(getActivity(),id);
		
		rackAddWin.showAtLocation(getView(), Gravity.CENTER,
				0, 0);
	}
	
	*/
	
}
