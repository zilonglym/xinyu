package com.zc.mobile.fragments.racks;

import java.util.Vector;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ericssonlabs.MainActivity;
import com.google.zxing.BarcodeFormat;
import com.zc.mobile.R;
import com.zc.mobile.activity.HomeActivity;
import com.zc.mobile.adapter.RackAddItemAdapter;
import com.zc.mobile.common.Constants;
import com.zxing.activity.CaptureActivity;
import com.zxing.camera.CameraManager;
import com.zxing.decoding.CaptureActivityHandler;
import com.zxing.decoding.InactivityTimer;
import com.zxing.view.ViewfinderView;

/**
 * ����İ�λ�ϼ�
 * 
 * @author admin
 * 
 */
public class RackLocatorAddFragment extends Fragment implements OnClickListener {

	private EditText txt_barCode;
	private EditText txt_num;
	private EditText txt_code;

	private String title;
	private String id; //��λID
	private String boxId;
	private String rowId;

	private RackAddItemAdapter itemAdapter;
	private JSONArray itemArray;
	
	
	public static final String RESULT_TYPE = "result_type";
    public static final String RESULT_STRING = "result_string";
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_FAILED = 2;

    public static final String LAYOUT_ID = "layout_id";


	private String[] itemList;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	
	public void setRowId(String rowId){
		this.rowId=rowId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.rack_locator_add, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// 1.���ð�λ�ı�����Ϣ
		// 2.��������ֵ�ı��¼�,�����ýӿ�ȡ�þ��������
		// 3.ɨ�谴ť��ʵ��
		// 4.�����ʵ��
		Button btnSubmit = (Button) getActivity().findViewById(
				R.id.rack_add_submit);
		Button btnScan = (Button) getActivity()
				.findViewById(R.id.rack_add_scan);
		Button btnSearch = (Button) getActivity().findViewById(
				R.id.rack_add_search);
		Button btnBack = (Button) getActivity()
				.findViewById(R.id.rack_add_back);
		txt_barCode = (EditText) getActivity().findViewById(
				R.id.rack_add_barCode);
		txt_code=(EditText) getActivity().findViewById(R.id.rack_add_code);
		txt_code.setEnabled(false);
		btnSubmit.setOnClickListener(this);
		btnScan.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		TextView rack_add_title = (TextView) getActivity().findViewById(
				R.id.rack_add_title);
		rack_add_title.setText(title);
		txt_num = (EditText) getActivity().findViewById(R.id.rack_add_num);
		itemAdapter = new RackAddItemAdapter(getActivity());
		
		
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.rack_add_scan:
			//��ɨ�����ɨ����������ά��
			Intent openCameraIntent = new Intent(this.getActivity(),CaptureActivity.class);
			startActivityForResult(openCameraIntent, 5);
			break;
		case R.id.rack_add_submit:
			/**
			 * �����ϼ�����
			 */
			try{
				saveRackAddItemInfo();
			}catch(Exception e){
				alertInfo("������ʾ", "������Ϣ��������!");
			}
			break;
		case R.id.rack_add_search:
			String value = txt_barCode.getText().toString().trim();
			if (value == null || value.length() == 0) {
				alertInfo("������ʾ", "������Ҫ��ѯ����Ʒ��Ϣ!");
				txt_barCode.setFocusable(true);
			} else {
				searchItem();
			}
			break;
		case R.id.rack_add_back:
			openBack();
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ���ɨ����
		//����ɨ�������ڽ�������ʾ��
				if (resultCode == -1) {
					Bundle bundle = data.getExtras();
					String scanResult = bundle.getString("result");
					txt_barCode.setText(scanResult);
					txt_code.setText(scanResult);
				}
//				searchItem();
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void openBack() {
		RackLocatorFragment rackLocatorFragment = new RackLocatorFragment();
		rackLocatorFragment.setBoxId(boxId);
		
		if(title!=null && title.length()>0){
			String temp=title.substring(0,5);
			temp=temp+"boxCode";
			temp=temp+title.substring(6);
			rackLocatorFragment.setTitle(temp);
		}
		rackLocatorFragment.setRowId(rowId);
		FragmentTransaction transaction = this.getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.lay_home, rackLocatorFragment);
		transaction.commit();
	}

	/**
	 * ��ѯ��Ʒ
	 * 
	 * @param url
	 */
	private void searchItem() {
		String value = txt_barCode.getText().toString().trim();
		String t="barCode";
		String url= Constants.WLPOST_URL+ "local/getLocalItemByBarCode/"+value;
		if(value!=null && value.length()==14){
			String val=txt_code.getText().toString();
			if(val==null || val.length()==0){
				txt_code.setText(value);
			}
			url= Constants.WLPOST_URL+ "local/getLocalPlate/"+txt_code.getText().toString();
			t="code";
		}
		JSONObject jsonObject = null;
		final String type=t;
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						System.err.println("searchItem" + response);
						if(type!=null && type.equals("barCode")){
							itemArray = response.optJSONArray("list");
							itemAdapter.setItemArray(itemArray);
							showItemListChoiceDialog();
						}else{
							String code=response.optString("code");
							if(code!=null && code.equals("200")){
								JSONObject resultObj=response.optJSONObject("row");
								String plateId=resultObj.optString("plateId");
								if(plateId!=null && plateId.length()>0){
									Toast.makeText(getActivity(), "�����κ����ϼܣ�������ɨ��!", 0).show();
									txt_barCode.setTag("");
									txt_num.setText("");
									txt_barCode.setText("");
									txt_code.setTag("");
								}else{
									txt_barCode.setTag(resultObj.optString("itemId"));
									txt_num.setText(resultObj.optString("quantity"));
									String value=resultObj.optString("shopName")+"|"+resultObj.optString("itemName")+"|"+resultObj.optString("sku");
									txt_barCode.setText(value);
									txt_code.setTag(resultObj.optString("batchId"));
								}
							}else{
								Log.w("zc", response.toString());
								Toast.makeText(getActivity(), " "+response.optString("msg"), 0).show();
							}							
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.w("zc", error.getMessage());
						alertInfo("�����쳣", error.getMessage());
					}
				});
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		queue.add(request);
	}

	/**
	 * �����ϼ���Ϣ
	 */
	private void saveRackAddItemInfo() {
		/**
		 * ��λID����ƷID������
		 */
		if(txt_barCode.getTag()==null){
			alertInfo("������ʾ", "��Ʒ�����ڣ����Ȳ�ѯ��Ʒ!");
			return;
		}
		String itemId = txt_barCode.getTag().toString().trim();
		String num = txt_num.getText().toString().trim();
		String url = Constants.WLPOST_URL + "local/upLocalPlate/" + id + "/"
				+ itemId + "/" + num;
		String value="";
		if(txt_code.getTag()!=null){
			value=txt_code.getTag().toString();
		}
		String operatorId=HomeActivity.loginUserInfo.get("id")+"";
		if(operatorId==null || operatorId.length()==0){
			alertInfo("������ʾ", "�����µ�¼!");
			Intent intent=new Intent(this.getActivity(),com.zc.mobile.activity.MainActivity.class);
			this.startActivity(intent);
			this.getActivity().finish();
			return;
		}
		url=url+"?opertionId="+operatorId+"&batchId="+value;
		JSONObject jsonObject = null;
		System.err.println(url);
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						alertInfo("������ʾ", "�ϼܳɹ�!");
						openBack();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("zc", error.getMessage());
					}
				});
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		queue.add(request);
	}

	private void showItemListChoiceDialog() {
		System.err.println("showItemListChoiceDialog");
		AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(
				getActivity());
		singleChoiceDialog.setTitle("��Ʒѡ��");
		AlertDialog alertDialog = singleChoiceDialog.create();

		WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
		lp.width = 600;// ������
		lp.height = 500;// ����߶�
		alertDialog.getWindow().setAttributes(lp);

		// �ڶ���������Ĭ��ѡ��˴�����Ϊ0
		singleChoiceDialog.setSingleChoiceItems(itemAdapter, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which != -1) {
							JSONObject obj = itemArray.optJSONObject(which);
							txt_barCode.setText(obj.optString("userName")
									+ " | " + obj.optString("itemName") + " | "
									+ obj.optString("barCode"));
							txt_barCode.setTag(obj.optString("itemId"));
							dialog.dismiss();
						}
					}
				});
		singleChoiceDialog.show();
	}
	
	
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }
	
	private void alertInfo(String title,String info){
		AlertDialog.Builder alert=new AlertDialog.Builder(this.getActivity());
		alert.setMessage(""+info);		
		alert.setTitle(title);
		alert.setCancelable(true);
		alert.setPositiveButton("OK", null);
		alert.show();
	}
}
