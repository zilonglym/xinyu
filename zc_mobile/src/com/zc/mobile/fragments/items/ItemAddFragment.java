package com.zc.mobile.fragments.items;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DownloadManager.Request;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zc.mobile.R;
import com.zc.mobile.adapter.ItemAddUpdateAdapter;
import com.zc.mobile.common.Constants;
import com.zxing.activity.CaptureActivity;

public class ItemAddFragment extends Fragment implements OnClickListener {

	private Spinner spinner;
	
	private List<String> data_list;
	private ArrayAdapter<String> arr_adapter;
	
	private EditText itemName;
	private EditText itemSku;
	private EditText itemBarCode;
	private Button btnScan;
	private Button btnSave;
	private Button btnUpdate;
	private Button btnSearch;
	
	private ItemAddUpdateAdapter itemAdapter;
	private JSONArray itemArray;
	/**
	 * 显示Fragment的界面
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.item_add, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		spinner=(Spinner) this.getActivity().findViewById(R.id.item_add_shop);
		 btnSave=(Button) this.getActivity().findViewById(R.id.item_add_save);
		itemName=(EditText) this.getActivity().findViewById(R.id.item_add_name);
		itemSku=(EditText) this.getActivity().findViewById(R.id.item_add_sku);
		itemBarCode=(EditText) this.getActivity().findViewById(R.id.item_add_barCode);
		btnScan=(Button) this.getActivity().findViewById(R.id.item_add_scan);
		btnUpdate=(Button) this.getActivity().findViewById(R.id.item_add_update);
		btnSearch=(Button) this.getActivity().findViewById(R.id.item_add_search);
		itemAdapter=new ItemAddUpdateAdapter(getActivity());
		
		//数据
        data_list = new ArrayList<String>();
        initShopInfo();
        
        //适配器
        arr_adapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
		btnSave.setOnClickListener(this);
		btnScan.setOnClickListener(this);
//		this.btnSave.setEnabled(true);
		this.btnUpdate.setOnClickListener(this);
		this.btnSearch.setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.item_add_save:
			saveItemAdd();
			break;
		case R.id.item_add_search:
			String url= Constants.WLPOST_URL+ "local/getLocalItemByBarCode/"+itemBarCode.getText().toString();
			searchItem(url);
			break;
		case R.id.item_add_update:
			updateItem();
			break;
		case R.id.item_add_scan:
			scanBarCode(view);
			break;
		}
	}
	
	



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 获得扫描结果
		//处理扫描结果（在界面上显示）
				if (resultCode == -1) {
					Bundle bundle = data.getExtras();
					String scanResult = bundle.getString("result");
					itemBarCode.setText(scanResult);
				}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 扫描条码
	 * @param view
	 */
	private void scanBarCode(View view){
		Intent openCameraIntent = new Intent(this.getActivity(),CaptureActivity.class);
		startActivityForResult(openCameraIntent, 0);
	}
	
	/**
	 * 查询商品
	 * 
	 * @param url
	 */
	private void searchItem(String url) {
		JSONObject jsonObject = null;
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						System.err.println("searchItem" + response);
						itemArray = response.optJSONArray("list");
						itemAdapter.setItemArray(itemArray);
						
						showItemListChoiceDialog();
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
	
	
	private void showItemListChoiceDialog() {
		System.err.println("showItemListChoiceDialog");
		AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(
				getActivity());
		AlertDialog alertDialog = singleChoiceDialog.create();

		WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
		lp.width = 600;// 定义宽度
		lp.height = 500;// 定义高度
		alertDialog.getWindow().setAttributes(lp);

		// 第二个参数是默认选项，此处设置为0
		singleChoiceDialog.setSingleChoiceItems(itemAdapter, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which != -1) {
							JSONObject obj = itemArray.optJSONObject(which);
							itemBarCode.setText(obj.optString("barCode"));
							itemName.setText(obj.optString("itemName"));
							itemSku.setText(obj.optString("sku"));
							itemName.setTag(obj.optString("itemId"));
							String shop=obj.optString("userName");
							int count=itemAdapter.getCount();
							System.err.println(count);
							for(int i=0;i<=count;i++){
								System.err.println(i);								
								String value=spinner.getAdapter().getItem(i).toString();
								if(value!=null && value.equals(shop)){
									spinner.setSelection(i, true);
									break;
								}
							}
							dialog.dismiss();
						}
					}
				});
		singleChoiceDialog.show();
	}
	
	
	private void initShopInfo(){
		String url = Constants.WLPOST_URL+ "local/getShops";
		JSONObject jsonObject = null;
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						if(response!=null && response.optString("code").equals("200")){
							JSONArray array=response.optJSONArray("list");
							for(int i=0;array!=null && i<array.length();i++){
								JSONObject obj=array.optJSONObject(i);
								data_list.add(obj.optString("name"));
								arr_adapter.notifyDataSetChanged();
							}
						}
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
	
	private void saveItemAdd(){
//		this.btnSave.setEnabled(true);
		final String txtName=this.itemName.getText().toString();
		final String txtSku=this.itemSku.getText().toString();
		final String txtBarCode=this.itemBarCode.getText().toString();
		final String txtShop=this.spinner.getSelectedItem().toString();
		final String itemId=(String)this.itemName.getTag();
		if(txtName==null || txtName.trim().length()==0){
			showAlert("商品名称不能为空!");
			this.itemName.setFocusable(true);
			return;
		}
		
		if(txtBarCode==null || txtBarCode.trim().length()==0){
			showAlert("商品条码不能为空!");
			this.itemBarCode.setFocusable(true);
			return;
		}
		
		if(itemId!=null && itemId.trim().length()>=0){
			showAlert("此处不能保存，请点击修改按钮！");
			return;
		}
		JSONObject jsonObject = null;
		String url = "";
			url=Constants.WLPOST_URL+ "local/save/item?shopName="+txtShop+"&barCode="+txtBarCode+"&sku="+txtSku+"&itemType=zc&name="+txtName;
			
			Toast.makeText(getActivity(), url, 0).show();
		String u="";
		try {
			u = URLDecoder.decode(url,"UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,u,
				jsonObject, new Response.Listener<JSONObject>() {
				
					@Override
					public void onResponse(JSONObject response) {
						if(response!=null && response.optString("code").equals("200")){
							if(itemId==null || itemId.trim().length()==0){
								Toast.makeText(getActivity(), "商品保存成功!", 0).show();
							}else{
								Toast.makeText(getActivity(), "商品修改成功!", 0).show();
							}
							cleanFrom();						
						}
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
	
	
	private void updateItem() {
//		this.btnSave.setEnabled(true);
		String txtName=this.itemName.getText().toString();
		String txtSku=this.itemSku.getText().toString();
		String txtBarCode=this.itemBarCode.getText().toString();
		String txtShop=this.spinner.getSelectedItem().toString();
		final String itemId=(String)this.itemName.getTag();
		Toast.makeText(getActivity(), txtShop, 0).show();
		if(txtName==null || txtName.trim().length()==0){
//			Toast.makeText(getActivity(), "商品名称不能为空!", 0).show();
			showAlert("商品名称不能为空!");
			this.itemName.setFocusable(true);
			return;
		}
		
		if(txtBarCode==null || txtBarCode.trim().length()==0){
//			Toast.makeText(getActivity(), "商品条码不能为空!", 0).show();
			showAlert("商品条码不能为空!");
			this.itemBarCode.setFocusable(true);
			return;
		}
		if(itemId==null || itemId.trim().length()==0){
			showAlert("此处不能修改，请点击保存按钮！");
			return;
		}
		JSONObject jsonObject = null;
		String url = "";
		url=Constants.WLPOST_URL+ "local/update/item?shopName="+txtShop+"&barCode="+txtBarCode+"&sku="+txtSku+"&itemType=zc&name="+txtName+"&itemId="+itemId;
		
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						if(response!=null && response.optString("code").equals("200")){
							if(itemId==null || itemId.trim().length()==0){
								Toast.makeText(getActivity(), "商品保存成功!", 0).show();
							}else{
								Toast.makeText(getActivity(), "商品修改成功!", 0).show();
							}
							cleanFrom();						
						}
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
	private void cleanFrom(){
		this.itemBarCode.setText("");
		this.itemSku.setText("");
		this.itemName.setText("");
//		this.btnSave.setEnabled(false);
	}
	
	
	private void showAlert(String context){
		AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this.getActivity());
		AlertDialog alertDialog = alertDialogBuilder.create();
//		alertDialog.setTitle("提示");
		alertDialog.setMessage(context);
	    alertDialog.show();//将dialog显示出来
	}
}
