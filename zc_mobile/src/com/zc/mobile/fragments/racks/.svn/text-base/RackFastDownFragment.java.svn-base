package com.zc.mobile.fragments.racks;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.zc.mobile.activity.HomeActivity;
import com.zc.mobile.common.Constants;
import com.zxing.activity.CaptureActivity;

/**
 * 快度下架
 *
 */
public class RackFastDownFragment extends Fragment implements OnClickListener {
	
	private EditText rack_fast_num;
	private Button btn_rack_fast;
	private Button rack_fast_btn;
	private TextView txt_value;
	
	/**
	 * 显示Fragment的界面
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.rack_fast_down, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.rack_fast_num=(EditText) this.getActivity().findViewById(R.id.rack_fast_code);
		this.btn_rack_fast=(Button) this.getActivity().findViewById(R.id.rack_fast_down);
		this.txt_value=(TextView) this.getActivity().findViewById(R.id.rack_fast_info);
		this.rack_fast_btn=(Button) this.getActivity().findViewById(R.id.rack_fast_btn);
		this.btn_rack_fast.setOnClickListener(this);
		this.rack_fast_btn.setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.rack_fast_down:
			String id=this.txt_value.getTag().toString();
			if(id==null || id.length()==0){
				Toast.makeText(getActivity(), "请先找到要下架的板位!", 0).show();
			}else{
				submitDownRack(id);
			}
			break;
		case R.id.rack_fast_btn:
			Intent openCameraIntent = new Intent(this.getActivity(),CaptureActivity.class);
			startActivityForResult(openCameraIntent, 5);
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
	 * 查询板位数据
	 * @param url
	 */
	private void refreshFragment() {
		String value=rack_fast_num.getText().toString();
		String url= Constants.WLPOST_URL+ "local/getLocalPlate/"+value;
		JSONObject jsonObject = null;
		System.err.println(url);
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						String code=response.optString("code");
						if(code!=null && code.equals("200")){
							JSONObject resultObj=response.optJSONObject("row");
							String plateId=resultObj.optString("plateId");
							if(plateId!=null && plateId.length()>0){
								String title=resultObj.optString("row")+resultObj.optString("boxOut")+resultObj.optString("code")+resultObj.optString("floor");
								txt_value.setText(title+" || "+resultObj.optString("shopName")+" | "+resultObj.optString("itemName")+" | "+resultObj.optString("itemSku")+" | {"+resultObj.optString("num")+"}");
								txt_value.setTag(resultObj.optString("plateId"));
							}else{
								txt_value.setText("该条码没有上架，无法找到板位信息!");
							}
						}else{
							Log.w("zc", response.toString());
							Toast.makeText(getActivity(), "取数据信息异常,请联系管理员!", 0).show();
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
	
	
	private void submitDownRack(String id){
	     final String idx=id;
		 new AlertDialog.Builder(getActivity()).setTitle("系统提示")//设置对话框标题

		 .setMessage("是否确认下架此板商品")//设置显示的内容

		 .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
			 @Override
			 public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件			

				 String url = Constants.WLPOST_URL + "local/downLocalPlate/" + idx;
				 url=url+"?opertionId="+HomeActivity.loginUserInfo.get("id");
				 JSONObject jsonObject = null;
					System.err.println(url);
					JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
							new Response.Listener<JSONObject>() {
								@Override
								public void onResponse(JSONObject response) {
									String code=response.optString("code");
									if(code!=null && code.equals("200")){
										Toast.makeText(getActivity(), "商品下架成功!", 0).show();
										clearFragment();
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

		 }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮		

			 @Override
			 public void onClick(DialogInterface dialog, int which) {//响应事件
				 dialog.dismiss();
			 }

		 }).show();//在按键响应事件中显示此对话框

	}

	
	private void clearFragment() {
		// TODO Auto-generated method stub
		this.txt_value.setText("");
		this.txt_value.setTag("");
		this.rack_fast_num.setText("");
	}
}
