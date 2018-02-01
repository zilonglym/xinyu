package admin.example.other.http;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

import admin.example.adapter.ShopAdapter;
import admin.example.other.model.Shop_shelves_location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/19.
 */

public class JsonThread extends  Thread{
    Context context;
    ListView listView;
    String url;
    Handler handler;
    List<Shop_shelves_location> ssls;
    ShopAdapter adapter;

    public JsonThread(Context context,ListView listView,String url,Handler handler){
        this.context=context;
        this.listView=listView;
        this.url=url;
        this.handler=handler;
    }

    private List<Shop_shelves_location> getCangku(String data){
        List<Shop_shelves_location> ssls = new ArrayList<Shop_shelves_location>();
        try{
            JSONObject object = new JSONObject(data);
            // JSONArray jsonArray = new JSONArray(data);
            if (object.getInt("total")>=1) {
                JSONArray jsonArray = object.getJSONArray("rows");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject sslobject = (JSONObject)jsonArray.get(i);
                    //  JSONObject sslobject =(JSONObject) jsonArray.get(i);
                    Shop_shelves_location sslt = new Shop_shelves_location();
                    sslt.hj = sslobject.getString("HJ");
                    sslt.wz = sslobject.getString("WZ");
                    sslt.bw = sslobject.getString("BW");
                    sslt.cs = sslobject.getString("CS");
                    sslt.name = sslobject.getString("name");
                    sslt.shop = sslobject.getString("shop");
                    sslt.type = sslobject.getString("type");
                    sslt.num = sslobject.getString("num");
                    sslt.date_cr = sslobject.getString("lastDate");
                    Log.e("tag",sslt.hj);
                    Log.e("tag",sslt.wz);
                    Log.e("tag",sslt.bw);
                    Log.e("tag",sslt.cs);
                    Log.e("tag",sslt.name);
                    Log.e("tag",sslt.shop);
                    Log.e("tag",sslt.type);
                    Log.e("tag",sslt.num);
                    Log.e("tag",sslt.date_cr);
                    ssls.add(sslt);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  ssls;
    }

    @Override
    public void run() {
        StringBuffer result = new StringBuffer();
        try{
            URL Url= new URL(url);
            HttpURLConnection connection = (HttpURLConnection)Url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            InputStream inputStream=connection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                result.append(line);
            }
            System.out.println();
            ssls=getCangku(result.toString());
            inputStream.close();
            bufferedReader.close();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter = new ShopAdapter(context,handler,ssls);
                    listView.setAdapter(adapter);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        super.run();
    }

}
