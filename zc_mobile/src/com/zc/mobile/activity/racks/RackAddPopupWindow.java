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
 * ���ܵ�������
 * 
 * @author admin
 * 
 */

public class RackAddPopupWindow extends PopupWindow implements OnClickListener {

	private View rootView;
	private LayoutInflater inflater;
	
	OnGetData mOnGetData;
	/**
	 * ��λID
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
//			Toast.makeText(view.getContext(), "�ϼ�" + this.rackid, 0).show();
			mOnGetData.onDataCallBack(this.rackid);
			break;
		case R.id.rack_add_scan:
			Toast.makeText(view.getContext(), "ɨ��" + this.rackid, 0).show();
			break;
		default:
			break;
		}
	}

	// ���ݽӿ�����,����Դ�ӿڴ���
	public void setOnData(OnGetData sd) {
		mOnGetData = sd;
	}

	// ���ݽӿڳ��󷽷�
	public interface OnGetData {
		abstract int onSeclectItem();

		abstract void onDataCallBack(String value);
	}

	private TextWatcher txtWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// ��ѯ��Ʒ�¼�д�����λ�ã����������������Բ�����
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
		final String[] items = { "����1", "����2", "����3", "����4" };
		yourChoice = -1;
		AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(RackViewFragment);
		singleChoiceDialog.setTitle("����һ����ѡDialog");
		// �ڶ���������Ĭ��ѡ��˴�����Ϊ0
		singleChoiceDialog.setSingleChoiceItems(items, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						yourChoice = which;
					}
				});
		singleChoiceDialog.setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (yourChoice != -1) {
							Toast.makeText(getContentView(),
									"��ѡ����" + items[yourChoice],
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		singleChoiceDialog.show();
	}
	
*/
}
