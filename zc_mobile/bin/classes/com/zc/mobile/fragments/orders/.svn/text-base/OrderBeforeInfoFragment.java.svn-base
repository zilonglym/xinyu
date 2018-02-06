package com.zc.mobile.fragments.orders;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zc.mobile.R;
import com.zc.mobile.adapter.OrderBeforeListAdapter;
import com.zc.mobile.common.Constants;

/**
 * 订单推送界面
 * 
 * @author admin
 * 
 */
public class OrderBeforeInfoFragment extends Fragment {

	private List<Map<String, Object>> orderList;

	private ListView list_order;

	private JSONArray orderArray;

	private OrderBeforeListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.order_before_list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		list_order = (ListView) this.getActivity().findViewById(
				R.id.order_before_list);
		refreshListView();
		super.onActivityCreated(savedInstanceState);
	}

	private void refreshListView() {
		try {
			getOrderBeforeListData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		adapter = new OrderBeforeListAdapter(getActivity());
		list_order.setAdapter(adapter);
	}

	private void getOrderBeforeListData() {
		String url = Constants.ADMIN_URL + "mobile/listData";
		//?userGroup=GROUP2
		JSONObject jsonObject = null;
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						orderArray = response.optJSONArray("rows");
						adapter.setData(orderArray);
						System.err.println(orderArray);
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						System.err.println(error.getMessage());
						Toast.makeText(getActivity(), error.getMessage(), 0)
								.show();
					}
				});
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		queue.add(request);
	}

}
