package admin.example.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import activityhuowu.example.com.zcwarehouse.R;
import admin.example.other.model.Shop_shelves_location;

/**
 * Created by admin on 2017/7/24.
 */

public class ShopAdapter extends BaseAdapter{


    List<Shop_shelves_location> ssls;
    Context context;
    LayoutInflater inflater;
    Handler handler;

    public ShopAdapter(Context context, Handler handler, List<Shop_shelves_location> ssls){
        this.handler=handler;
        this.context=context;
        this.ssls=ssls;
        inflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return ssls.size();
    }

    @Override
    public Object getItem(int position) {
        return ssls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Viewholder holder =null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.shopwz_list_item,null);
            holder= new Viewholder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (Viewholder) convertView.getTag();
        }
        System.out.println(ssls.get(position).hj);
        holder.hj.setText(ssls.get(position).hj);
        holder.wz.setText(ssls.get(position).wz);
        holder.bw.setText(ssls.get(position).bw);
        holder.cs.setText(ssls.get(position).cs);
        holder.name.setText(ssls.get(position).name);
        holder.shop.setText(ssls.get(position).shop);
        holder.type.setText(ssls.get(position).type);
        holder.num.setText(ssls.get(position).num);
        holder.date.setText(ssls.get(position).date_cr);

        return convertView;
    }

    class Viewholder{
        public TextView hj;
        public TextView wz;
        public TextView bw;
        public TextView cs;
        public TextView name;
        public TextView shop;
        public TextView type;
        public TextView num;
        public TextView date;

        public Viewholder(View view){
            hj =(TextView) view.findViewById(R.id.tx_hj);
            wz =(TextView) view.findViewById(R.id.tx_wz);
            bw =(TextView) view.findViewById(R.id.tx_bw);
            cs =(TextView) view.findViewById(R.id.tx_cs);
            name =(TextView) view.findViewById(R.id.tx_name);
            shop =(TextView) view.findViewById(R.id.tx_shop);
            type =(TextView) view.findViewById(R.id.tx_type);
            num =(TextView) view.findViewById(R.id.tx_num);
            date =(TextView) view.findViewById(R.id.tx_date_cr);
        }
    }
}
