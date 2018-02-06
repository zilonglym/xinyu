package com.zc.mobile.activity.racks;

import com.zc.mobile.R;
import com.zc.mobile.RackViewActivity;
import com.zc.mobile.activity.racks.RackListPopupWindow.OnGetData;
import com.zc.mobile.fragments.racks.RackViewFragment;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * 货架弹出窗口
 * 
 * @author admin
 * 
 */

public class RackAddPopupWindow extends PopupWindow implements OnClickListener {

	private View rootView;
	private LayoutInflater inflater;
	
	OnGetData mOnGetData;
	/**
	 * 板位ID
	 */
	private String rackid;

	public RackAddPopupWindow(android.content.Context context, String value) {
		super(context);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rootView = inflater.inflate(R.layout.rack_add_popupwin, null);
		EditText barCode = (EditText) rootView
				.findViewById(R.id.rack_add_barCode);
		barCode.addTextChangedListener(txtWatcher);
		this.rackid = value;
		setContentView(rootView);
		
		Button btn_submit=(Button) rootView.findViewById(R.id.rack_add_submit);
		btn_submit.setOnClickListener(this);
		Button btn_scan=(Button) rootView.findViewById(R.id.rack_add_scan);
		btn_scan.setOnClickListener(this);
		this.setWidth(500);
		this.setHeight(450);

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
		switch (view.getId()) {
		case R.id.rack_add_submit:
//			Toast.makeText(view.getContext(), "上架" + this.rackid, 0).show();
			mOnGetData.onDataCallBack(this.rackid);
			break;
		case R.id.rack_add_scan:
			Toast.makeText(view.getContext(), "扫描" + this.rackid, 0).show();
			break;
		default:
			break;
		}
	}

	// 数据接口设置,数据源接口传入
	public void setOnData(OnGetData sd) {
		mOnGetData = sd;
	}

	// 数据接口抽象方法
	public interface OnGetData {
		abstract int onSeclectItem();

		abstract void onDataCallBack(String value);
	}

	private TextWatcher txtWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// 查询商品事件写在这个位置，其它两个方法可以不用理
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			System.err.println("2" + arg0);
		}

		@Override
		public void afterTextChanged(Editable arg0) {
			System.err.println("3" + arg0);
		}
	};
/*
	int yourChoice;
	private void showItemListChoiceDialog() {
		final String[] items = { "我是1", "我是2", "我是3", "我是4" };
		yourChoice = -1;
		AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(RackViewFragment);
		singleChoiceDialog.setTitle("我是一个单选Dialog");
		// 第二个参数是默认选项，此处设置为0
		singleChoiceDialog.setSingleChoiceItems(items, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						yourChoice = which;
					}
				});
		singleChoiceDialog.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (yourChoice != -1) {
							Toast.makeText(getContentView(),
									"你选择了" + items[yourChoice],
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		singleChoiceDialog.show();
	}
	
*/
}
