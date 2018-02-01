package activityhuowu.example.com.zcwarehouse;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import admin.example.adapter.ShopAdapter;
import admin.example.other.http.JsonThread;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Context context =this;
    ListView listView;
    Handler handler;
    ShopAdapter shopadapter;
    private LinearLayout ll;
    Button bt_cx;
    EditText text_shuru;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_shuru = (EditText) findViewById(R.id.type_et);
        listView = (ListView) findViewById(R.id.wz_listview);
        ll = (LinearLayout) findViewById(R.id.model_linear);
        ll.setVisibility(View.GONE);
        bt_cx = (Button) findViewById(R.id.cx_bt);
        bt_cx.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cx_bt) {
            ll.setVisibility(View.VISIBLE);
            String a2 = text_shuru.getText().toString();
            String url = "http://admin.wlpost.com/admin/location/find?page=1&rows=100&txt=" + a2;
            handler = new Handler();
            new JsonThread(context, listView, url, handler).start();
        }

    }
}