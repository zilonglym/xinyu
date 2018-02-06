package com.zc.mobile.fragments.racks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.common.StringUtils;
import com.zc.mobile.R;
import com.zc.mobile.adapter.RackAddItemAdapter;
import com.zc.mobile.common.Constants;

/**
 * 库位查询界面
 * @author admin
 *
 */
public class RackFragment extends Fragment implements OnClickListener {
	private ListView list_data;
	private EditText txt_search;
	private int displayMode = 0;// 显示模式，列表或表格
	private Button btn_displayModel;
	private int total;
	private JSONArray itemArray;
	private RackListAdapter adapter;
	
	private RackAddItemAdapter itemAdapter;
	
	/**
	 * 显示Fragment的界面
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.activity_rack_list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		txt_search = (EditText) this.getActivity().findViewById(
				R.id.txt_searchText);

		Button btn_search = (Button) this.getActivity().findViewById(
				R.id.btn_search);
		btn_search.setOnClickListener(this);
		this.btn_displayModel = (Button) this.getActivity().findViewById(
				R.id.btn_displayModel);
		this.btn_displayModel.setOnClickListener(this);
		itemAdapter=new RackAddItemAdapter(getActivity());
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_search:
			this.refreshData(view);
			break;
		case R.id.btn_displayModel:
			if (displayMode == 0) {
				displayMode = 1;
				this.btn_displayModel.setText("视图");
			} else {
				displayMode = 0;
				this.btn_displayModel.setText("列表");
			}
			this.refreshData(view);
		default:
			break;
		}
	}

	private void refreshData(View view) {
		// Toast.makeText(getActivity(), "列表加载开始!",0).show();
		String value=txt_search.getText().toString();
		if(value==null || value.trim().length()==0){
			Toast.makeText(getActivity(), "查询条件不能为空!", 0).show();
		}else{
//			String url = Constants.WLPOST_URL + "mobile/getItemBySearch?searchText="+value;	 
			String url= Constants.WLPOST_URL+ "local/getLocalItemByBarCode/"+value;
			if (displayMode == 1) {
				searchItem(url,view);
			} else {
				searchItem(url,view);
	//			this.refreshListView(view);
			}
		}
	}

	private void refreshListView(View view) {
		String searchText = this.txt_search.getText().toString().trim();
		getDataBySearch(searchText);
		this.list_data = (ListView) this.getActivity().findViewById(
				R.id.list_data);
		adapter=new RackListAdapter();
		list_data.setAdapter(adapter);
	}

	private class RackListAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return total;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View view = View.inflate(getActivity(), R.layout.rack_item_list,
					null);
			TextView txt_hj = (TextView) view.findViewById(R.id.txt_hj);

			TextView txt_itemName = (TextView) view
					.findViewById(R.id.txt_itemName);
			TextView txt_shop = (TextView) view.findViewById(R.id.txt_shop);
			TextView txt_type = (TextView) view.findViewById(R.id.txt_type);

			TextView txt_num = (TextView) view.findViewById(R.id.txt_num);
			TextView txt_date = (TextView) view.findViewById(R.id.txt_date);
			Button btn_operator=(Button) view.findViewById(R.id.txt_operator);
			JSONObject object = null;
			if(arg0>=total){
				arg0=total;
			}
			object = itemArray.optJSONObject(arg0);
			// {"id":"3584","HJ":"039","BW":"3","shop":"天际","num":"69","CS":"3","name":"电炖锅","WZ":"M","lastDate":"2017-08-02 12:00:00","type":"16BW"}
			if (object != null) {
				String temp=object.optString("row")+object.optString("boxOut")+" "+object.optString("boxCode")+" "+object.optString("floor");
				txt_hj.setText(temp); //货架
//				txt_cs.setText(object.optString("floor"));//层数
//				txt_bw.setText(object.optString("boxOut"));//板位
				txt_itemName.setText(object.optString("itemName"));//商品名称
				txt_shop.setText(object.optString("shopName"));//商家
				txt_type.setText(object.optString("itemCode"));//商品编码
				txt_date.setText(object.optString("date"));//时间
				txt_num.setText(object.optString("num"));
				btn_operator.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Toast.makeText(getActivity(), "返回相应货架", 0).show();
					}
				});
				return view;
			} else {
				return null;
			}
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
	}
	
	/**
	 * 查询商品
	 * @param url
	 */
	private void searchItem(String url,View view){
		final View v=view;
		JSONObject jsonObject = null;
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						System.err.println("searchItem"+response);
						itemArray = response.optJSONArray("list");
						itemAdapter.setItemArray(itemArray);
						showItemListChoiceDialog(v);
						
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						System.err.println(error);
					}
				});
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		queue.add(request);
	}
	
	private void showItemListChoiceDialog(View view) {
		final View v=view;
		AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(getActivity());
		singleChoiceDialog.setTitle("商品选择");		
		
	
		// 第二个参数是默认选项，此处设置为0
		singleChoiceDialog.setSingleChoiceItems(itemAdapter, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which != -1) {
							JSONObject obj=itemArray.optJSONObject(which);
							txt_search.setText(obj.optString("code"));
							txt_search.setTag(obj.optString("itemId"));		
							refreshListView(v);
							dialog.dismiss();
						}
					}
				});
		singleChoiceDialog.show();
	}
	

	private void getDataBySearch(String searchText) {
		
//		String url = Constants.ADMIN_URL + "admin/location/find?txt="+ searchText;
		String value=this.txt_search.getTag().toString();
		if(value==null || value.trim().length()==0){
			Toast.makeText(getActivity(), "请先选择要查询的商品!", 0).show();
			return;
		}
		String url=Constants.WLPOST_URL+"local/getLocalPlate/item/"+value;
		JSONObject jsonObject = null;
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
//						total = response.optInt("total");
						itemArray = response.optJSONArray("list");
						total=itemArray!=null ?itemArray.length():0;
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getActivity(), error.getMessage(), 0)
								.show();
					}
				});
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		queue.add(request);
	}
}
