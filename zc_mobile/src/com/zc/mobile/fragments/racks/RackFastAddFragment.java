package com.zc.mobile.fragments.racks;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
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
import com.zc.mobile.LoadingDialog;
import com.zc.mobile.R;
import com.zc.mobile.activity.HomeActivity;
import com.zc.mobile.common.Constants;

/**
 * 快度上架
 *
 */
public class RackFastAddFragment extends Fragment implements OnClickListener {
	
	private EditText rack_fast_num;
	private Button btn_rack_fast;
	private Button btn_rack_down;
	private TextView rack_fast_info;
	
	/**
	 * 显示Fragment的界面
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.rack_fast_add, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.rack_fast_num=(EditText) this.getActivity().findViewById(R.id.rack_fast_num);
		this.btn_rack_fast=(Button) this.getActivity().findViewById(R.id.rack_fast_btn);
		this.rack_fast_info=(TextView) this.getActivity().findViewById(R.id.rack_fast_info);
		this.btn_rack_down=(Button) this.getActivity().findViewById(R.id.rack_fast_btn_down);
		this.btn_rack_fast.setOnClickListener(this);
		this.btn_rack_down.setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.rack_fast_btn:
//			new LoadingDialog(this.getActivity()).setMessage("正在加载...").show();
			
			
			try{
				refreshFragment();
			}catch(Exception e){
				AlertDialog.Builder alert=new AlertDialog.Builder(this.getActivity());
				alert.setMessage("输入信息有误，请重新输入!"+e.getMessage());				
				alert.show();
			}
			break;
		case R.id.rack_fast_btn_down:
			submitDownRack();
		default:
			break;
		}
	}

	
	
	/**
	 * 跳转前判断
	 * @param url
	 */
	private void refreshFragment() {
		
		String value=rack_fast_num.getText().toString();
		if(value!=null && value.length()==4){
			value="00"+value;
		}else if(value!=null && value.length()==5){
			value="0"+value;
		}
		String url = Constants.WLPOST_URL + "local/getLocalPlateByFast/" + value;
		JSONObject jsonObject = null;
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						gotoRackLocatorAdd(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						alertInfo("网络异常", error.getMessage());
					}
				});
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		queue.add(request);
	}
	
	private void gotoRackLocatorAdd(JSONObject json){
		
		
		if(json==null || json.isNull("row")){
			AlertDialog.Builder alert=new AlertDialog.Builder(this.getActivity());
			alert.setMessage("板位不存在，请重新输入!");		
			alert.setTitle("操作提示");
			alert.setCancelable(true);
			alert.setPositiveButton("OK", null);
			
			alert.show();
			return;
		}
		JSONObject obj=json.optJSONObject("row");
		String id=obj.optString("plateId");
		
		String boxId=obj.optString("boxOutId");
		String rowId=obj.optString("row");
//		String temp_value=jsonRow.optString("row")+jsonRow.optString("boxOut")+" boxCode "+jsonRow.optString("floor");
		String title=obj.optString("row")+obj.optString("boxOut")+obj.optString("code")+obj.optString("floor");
		String itemId=obj.optString("itemId");
		if(itemId==null || itemId.length()==0){
			//上架界面切换			
			RackLocatorAddFragment rackLocatorAddFragment = new RackLocatorAddFragment();
			rackLocatorAddFragment.setId(id);
			
			rackLocatorAddFragment.setTitle(title);
			rackLocatorAddFragment.setBoxId(boxId);
			rackLocatorAddFragment.setRowId(rowId);
			FragmentTransaction transaction = this.getFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.lay_home, rackLocatorAddFragment);
			transaction.commit();
			
		}else{
			//下架
			String info=obj.optString("shopName")+" | "+obj.optString("itemName")+" | "+obj.optString("itemSku");
			this.rack_fast_info.setText(info);
			this.rack_fast_info.setTag(obj.optString("plateId"));
			this.btn_rack_fast.setVisibility(View.INVISIBLE);
			this.btn_rack_down.setVisibility(View.VISIBLE);
			Toast.makeText(getActivity(), "此货架上面已有商品,请先下架!", 0).show();
		}
	}
	
	

	private void submitDownRack(){
	     final String idx=this.rack_fast_info.getTag().toString();
	     if(idx==null || idx.length()==0){
//	    	 Toast.makeText(getActivity(), "请先选择有货的板位!", 0).show();
	    	 alertInfo("操作提示", "请先选择有货的板位!");
	    	 return;
	     }
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
//										Toast.makeText(getActivity(), "商品下架成功!", 0).show();
										alertInfo("操作提示", "下架成功!");
										clearFragment();
									}
								}
							}, new Response.ErrorListener() {
								@Override
								public void onErrorResponse(VolleyError error) {
									alertInfo("网络异常", error.getMessage());
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
		this.btn_rack_fast.setVisibility(View.VISIBLE);
		this.btn_rack_down.setVisibility(View.INVISIBLE);
		this.rack_fast_info.setText("");
		this.rack_fast_info.setTag("");
		this.rack_fast_num.setFocusable(true);
	}
	
	private void alertInfo(String title,String info){
		AlertDialog.Builder alert=new AlertDialog.Builder(this.getActivity());
		alert.setMessage(""+info);		
		alert.setTitle(title);
		alert.setCancelable(true);
		alert.show();
	}
}
