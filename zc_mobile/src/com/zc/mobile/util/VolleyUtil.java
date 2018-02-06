package com.zc.mobile.util;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zc.mobile.common.Constants;

public class VolleyUtil {

	private int total;
	private JSONArray array;
	private BaseAdapter adapter;
	private Context context;
	
	public VolleyUtil(Context context,BaseAdapter adapter){
		this.context=context;
		this.adapter=adapter;
	}
	
	public  void getData(String url) {
		JSONObject jsonObject = null;
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						total = response.optInt("total");
						array = response.optJSONArray("rows");
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						
					}
				});
		RequestQueue queue = Volley.newRequestQueue(context);
		queue.add(request);
	}
}
