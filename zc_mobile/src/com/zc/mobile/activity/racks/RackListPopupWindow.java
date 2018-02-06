package com.zc.mobile.activity.racks;

import com.zc.mobile.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * 货架弹出窗口
 * @author admin
 *
 */

public class RackListPopupWindow extends PopupWindow implements OnClickListener{

	private View rootView;
	private LayoutInflater inflater;
	
	private String value;
	
	OnGetData mOnGetData;
	
	public RackListPopupWindow(Activity context,String value) {
		super(context);
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rootView=inflater.inflate(R.layout.rack_view_popupwin, null);
		initWin(rootView);
		this.value=value;
		setContentView(rootView);
//		this.setWidth(LayoutParams.WRAP_CONTENT);
//		this.setHeight(LayoutParams.WRAP_CONTENT);
		
		this.setWidth(300);
		this.setHeight(250);
		
		
		setTouchable(true);
		setOutsideTouchable(true);
		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0xffA5EBF7);
		setBackgroundDrawable(dw);
		update();
		
		getContentView().setFocusableInTouchMode(true);
		getContentView().setFocusable(true);
		setAnimationStyle(R.style.AppBaseTheme);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.btn_add_rack_one:
//			Toast.makeText(view.getContext(), "上架"+this.value, 0).show();
			openRackAddWin(this.value);
			break;
		case R.id.btn_del_rack_one:
//			Toast.makeText(view.getContext(), "下架"+this.value, 0).show();
			break;
			default:
				break;
		}
		
	}
	
	private void initWin(View view){
		Button btn_add=(Button) view.findViewById(R.id.btn_add_rack_one);
		Button btn_del=(Button) view.findViewById(R.id.btn_del_rack_one);
		btn_del.setOnClickListener(this);
		btn_add.setOnClickListener(this);
//		btn_add.setVisibility(View.INVISIBLE);
//		btn_del.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));  
	}
	/**
	 * 下架保存操作
	 */
	private void submitDelRack(){
		
	}
	
	// 数据接口设置,数据源接口传入
	public void setOnData(OnGetData sd) {
		mOnGetData = sd;
	}
	
	/**
	 * 上架页面打开
	 * @param id
	 */
	private void openRackAddWin(String id){
		mOnGetData.onDataCallBack(id);
	}
	
	
	// 数据接口抽象方法
	public interface OnGetData {
		abstract int onSeclectItem();

		abstract void onDataCallBack(String value);
	}
	
}
