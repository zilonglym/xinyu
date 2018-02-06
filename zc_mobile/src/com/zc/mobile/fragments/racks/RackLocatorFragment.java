package com.zc.mobile.fragments.racks;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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

/**
 * ��λ�������
 * 
 * @author admin
 * 
 */
public class RackLocatorFragment extends Fragment implements OnClickListener {

	private String boxId;
	private String title;
	private String rowId;

	private Button btn_add_one;
	private Button btn_add_two;
	private Button btn_add_three;
	private Button btn_del_one;
	private Button btn_del_two;
	private Button btn_del_three;

	private TextView txt_rack_one;
	private TextView txt_rack_two;
	private TextView txt_rack_three;
	
	private TextView rack_title_one;
	private TextView rack_title_two;
	private TextView rack_title_three;
	
	private TextView rack_locator_title;
	private Map<String,String> titleMap=new HashMap<String, String>();

	public void setRowId(String rowId){
		this.rowId=rowId;
	}
	public void setBoxId(String value) {
		this.boxId = value;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.rack_locator, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// 1.���ÿ�λ����
		// 2.���ÿ�λ����
		// 3.�ж����飬���ð�ť
		btn_add_one = (Button) getActivity()
				.findViewById(R.id.btn_add_rack_one);
		btn_add_two = (Button) getActivity()
				.findViewById(R.id.btn_add_rack_two);
		btn_add_three = (Button) getActivity().findViewById(
				R.id.btn_add_rack_three);
		btn_del_one = (Button) getActivity()
				.findViewById(R.id.btn_del_rack_one);
		btn_del_two = (Button) getActivity()
				.findViewById(R.id.btn_del_rack_two);
		btn_del_three = (Button) getActivity().findViewById(
				R.id.btn_del_rack_three);
		rack_locator_title=(TextView) this.getActivity().findViewById(R.id.rack_locator_title);
		txt_rack_one = (TextView) getActivity().findViewById(R.id.rack_one);
		txt_rack_two = (TextView) getActivity().findViewById(R.id.rack_two);
		txt_rack_three = (TextView) getActivity().findViewById(R.id.rack_three);
		rack_title_one=(TextView) getActivity().findViewById(R.id.rack_title_one);
		rack_title_two=(TextView) getActivity().findViewById(R.id.rack_title_two);
		rack_title_three=(TextView) getActivity().findViewById(R.id.rack_title_three);
		Button btnBack=(Button) getActivity().findViewById(R.id.rack_locator_back);
		btnBack.setOnClickListener(this);
		refreshFragment();
		btn_add_one.setOnClickListener(this);
		btn_add_two.setOnClickListener(this);
		btn_add_three.setOnClickListener(this);
		btn_del_one.setOnClickListener(this);
		btn_del_two.setOnClickListener(this);
		btn_del_three.setOnClickListener(this);
//		rack_locator_title.setText(title!=null?title.replace("boxCode", ""):"");
//		rack_title_one.setText(title);
//		rack_title_two.setText(title);
//		rack_title_three.setText(title);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View btn) {
		Button button = (Button) btn;
		if (btn.getId() == R.id.btn_add_rack_one
				|| btn.getId() == R.id.btn_add_rack_three
				|| btn.getId() == R.id.btn_add_rack_two) {
			// �����ϼܷ���
			String id = button.getTag().toString();
			
//			if(title!=null && title.indexOf("boxCode")>0){
//				String temp=titleMap.get(id);
//				title=title.replace("boxCode", temp);
//			}else{
				if(btn.getId()==R.id.btn_add_rack_one){
					title=this.rack_title_one.getText().toString();
				}
				if(btn.getId()==R.id.btn_add_rack_two){
					title=this.rack_title_two.getText().toString();						
				}
				if(btn.getId()==R.id.btn_add_rack_three){
					title=this.rack_title_three.getText().toString();
				}
//			}
			openRackLocatorAdd(id);
		}else if(btn.getId()==R.id.rack_locator_back){
			openRackView();
		} else {
			// �¼�
			String id = button.getTag().toString();
			downRackLocator(id);
		}
	}
	/**
	 * �����б�,����ID
	 */
	private void openRackView(){
		FragmentManager manager=this.getFragmentManager();
		RackViewFragment rackViewFragment=new RackViewFragment();
		rackViewFragment.setRowId(rowId);
		FragmentTransaction transaction= manager.beginTransaction();
		transaction.replace(R.id.lay_home, rackViewFragment);
		transaction.commit();
	}
	/**
	 * �¼���Ʒ
	 * @param id
	 */
	private void downRackLocator(String id){
	     final String idx=id;
		 new AlertDialog.Builder(getActivity()).setTitle("ϵͳ��ʾ")//���öԻ������

		 .setMessage("�Ƿ�ȷ���¼ܴ˰���Ʒ")//������ʾ������

		 .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {//���ȷ����ť
			 @Override
			 public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�			

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
										Toast.makeText(getActivity(), "��Ʒ�¼ܳɹ�!", 0).show();
										refreshFragment();
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

		 }).setNegativeButton("����",new DialogInterface.OnClickListener() {//��ӷ��ذ�ť		

			 @Override
			 public void onClick(DialogInterface dialog, int which) {//��Ӧ�¼�
				 dialog.dismiss();
			 }

		 }).show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���

	}
	
	/**
	 * �ϼ���Ʒ
	 * @param id
	 */
	private void openRackLocatorAdd(String id) {
		RackLocatorAddFragment rackLocatorAddFragment = new RackLocatorAddFragment();
		rackLocatorAddFragment.setId(id);
		
		rackLocatorAddFragment.setTitle(title);
		rackLocatorAddFragment.setBoxId(boxId);
		rackLocatorAddFragment.setRowId(rowId);
		FragmentTransaction transaction = this.getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.lay_home, rackLocatorAddFragment);
		transaction.commit();
	}
	/**
	 * ˢ�´��������
	 * @param url
	 */
	private void refreshFragment() {
		String url = Constants.WLPOST_URL + "local/getLocalPlateList/" + boxId;
		JSONObject jsonObject = null;
		System.err.println(url);
		JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						setRackLocatorData(response);
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

	/**
	 * ���ð�λ�ľ�������
	 * 
	 * @param object
	 */
	private void setRackLocatorData(JSONObject object) {
		String code = object.optString("code");
		if (code != null && code.equals("200")) {
			JSONArray array = object.optJSONArray("list");
			System.err.println("getLocalPlateList" + array);
			JSONObject jsonBox1 = array.optJSONObject(0);
			JSONObject jsonBox2 = array.optJSONObject(1);
			JSONObject jsonBox3 = array.optJSONObject(2);
			/*
			 
			 {"list":[{"id":1,"itemName":"","num":null,"shopName":"","fastCode":"001A11","state":"0","code":"1","sku":"","itemId":"","date":"2017-12-20 04:04:59","itemCode":""},{"id":2,"itemName":"","num":null,"shopName":"","fastCode":"001A21","state":"0","code":"2","sku":"","itemId":"","date":"2017-10-11 16:48:42","itemCode":""},{"id":3,"itemName":"","num":null,"shopName":"","fastCode":"001A31","state":"0","code":"3","sku":"","itemId":"","date":"2017-10-11 16:49:01","itemCode":""}],"code":"200"}
			 
			 
			 */

			int state1 = jsonBox1.optInt("state");
			System.err.println("stat1"+state1);
			rack_title_one.setText(jsonBox1.optString("fastCode"));
			rack_title_two.setText(jsonBox2.optString("fastCode"));
			rack_title_three.setText(jsonBox3.optString("fastCode"));
			if (state1 == 1) {
				btn_add_one.setVisibility(View.INVISIBLE);
				
				btn_del_one.setVisibility(View.VISIBLE);
				txt_rack_one.setText(jsonBox1.optString("shopName")+"  ||  "+jsonBox1.optString("itemName")+"||"+jsonBox1.optString("sku") + "  { "
						+ jsonBox1.optString("num")+" }");
				
			} else {
				btn_add_one.setVisibility(View.VISIBLE);
				btn_del_one.setVisibility(View.INVISIBLE);
				txt_rack_one.setText("");
			}
			btn_add_one.setTag(jsonBox1.optString("id"));
			btn_del_one.setTag(jsonBox1.optString("id"));
			titleMap.put(jsonBox1.optString("id"), jsonBox1.optString("code"));

			int state2 = jsonBox2.optInt("state");
			if (state2 == 1) {
				btn_add_two.setVisibility(View.INVISIBLE);
				btn_del_two.setVisibility(View.VISIBLE);
				txt_rack_two.setText(jsonBox2.optString("shopName")+"  ||  "+jsonBox2.optString("itemName") +"||"+jsonBox2.optString("sku") + "  { "
						+ jsonBox2.optString("num")+" }");
			} else {
				btn_add_two.setVisibility(View.VISIBLE);
				btn_del_two.setVisibility(View.INVISIBLE);
				txt_rack_two.setText("");
			}

			btn_add_two.setTag(jsonBox2.optString("id"));
			btn_del_two.setTag(jsonBox2.optString("id"));
			titleMap.put(jsonBox2.optString("id"), jsonBox2.optString("code"));

			
			
			int state3 = jsonBox3.optInt("state");
			if (state3 == 1) {
				btn_add_three.setVisibility(View.INVISIBLE);
				btn_del_three.setVisibility(View.VISIBLE);
				txt_rack_three.setText(jsonBox3.optString("shopName")+"  ||  "+jsonBox3.optString("itemName")+"||"+jsonBox3.optString("sku")  + "  { "
						+ jsonBox3.optString("num")+" }");
			} else {
				btn_add_three.setVisibility(View.VISIBLE);
				btn_del_three.setVisibility(View.INVISIBLE);
				txt_rack_three.setText("");
			}
			btn_add_three.setTag(jsonBox3.optString("id"));
			btn_del_three.setTag(jsonBox3.optString("id"));
			titleMap.put(jsonBox3.optString("id"), jsonBox3.optString("code"));
		} else {
			Toast.makeText(getActivity(), "����ȡ���쳣,�����Ի���ϵ����Ա!", 0).show();
		}

	}

}
