package com.zc.mobile.fragments.racks;

import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zc.mobile.R;
import com.zc.mobile.common.Constants;
import com.zxing.activity.CaptureActivity;

/**
 * 快度上架
 *
 */
public class RackFastConfirmFragment extends Fragment implements OnClickListener {
	
	private EditText rack_fast_num;
	private Button btn_rack_fast;
	private Button btn_fast_scan;
	private TextView txt_info;
	
	/**
	 * 显示Fragment的界面
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.rack_fast_confirm, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.rack_fast_num=(EditText) this.getActivity().findViewById(R.id.rack_fast_confirm_num);
		this.btn_rack_fast=(Button) this.getActivity().findViewById(R.id.rack_fast_confirm_btn);
		this.btn_fast_scan=(Button) this.getActivity().findViewById(R.id.rack_fast_confirm_scan);
		this.txt_info=(TextView) this.getActivity().findViewById(R.id.rack_fast_confirm_info);
		this.rack_fast_num.setEnabled(false);
		this.btn_rack_fast.setOnClickListener(this);
		this.btn_fast_scan.setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.rack_fast_confirm_btn:
			submitConfirm();
			break;
		case R.id.rack_fast_confirm_scan:
			Intent openCameraIntent = new Intent(this.getActivity(),CaptureActivity.class);
			startActivityForResult(openCameraIntent, 5);
			break;
		default:
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
					rack_fast_num.setText(scanResult);
					refreshFragment();
				}
//				searchItem();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	/**
	 * 刷新此面板数据
	 * @param url
	 */
	private void refreshFragment() {
		String value=rack_fast_num.getText().toString();
		String url = Constants.WLPOST_URL + "local/getLocalPlate/" + value;
		JSONObject jsonObject = null;
		System.err.println(url);
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						gotoRackLocatorAdd(response);
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
	
	private void gotoRackLocatorAdd(JSONObject json){
		String code=json.optString("code");
		if(code!=null && code.equals("200")){
			JSONObject resultObj=json.optJSONObject("row");
			txt_info.setText(resultObj.optString("shopName")+" | "+resultObj.optString("itemName")+" | "+resultObj.optString("itemSku")+" | {"+resultObj.optString("quantity")+"}");
			txt_info.setTag(resultObj.optString("batchId"));
		}else{
			Toast.makeText(getActivity(), "", 0).show();
		}
	}
	
	
	private void submitConfirm(){
		String value=txt_info.getTag().toString();
		if(value==null || value.length()==0){
			Toast.makeText(getActivity(), "批次ID为空，请重新扫描批次号！", 0).show();
		}else{
			String url = Constants.WLPOST_URL + "local/updateBatch/" + value;
			JSONObject jsonObject = null;
			System.err.println(url);
			JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							Toast.makeText(getActivity(), "确认成功!", 0).show();
							rack_fast_num.setText("");
							txt_info.setText("");
							txt_info.setTag("");
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
	}

}
