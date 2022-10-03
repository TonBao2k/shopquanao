package com.example.shopquanao;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class sanphamAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<classSanpham> list;

    public sanphamAdapter(Context context, int layout, List<classSanpham> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public  class Viewhoder{
        TextView tvtensp, tvgiasp, tvmotasp;
        ImageView imghinhanhsp;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Viewhoder holder;
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            holder = new Viewhoder();

            holder.tvtensp = convertView.findViewById(R.id.tvtensp);
            holder.imghinhanhsp = convertView.findViewById(R.id.imghinhanhsp);
            holder.tvgiasp = convertView.findViewById(R.id.tvgiasp);
            holder.tvmotasp = convertView.findViewById(R.id.tvmotasp);

            convertView.setTag(holder);
        }else {
            holder = (Viewhoder) convertView.getTag();
        }
        classSanpham sanpham = list.get(i);

        holder.tvtensp.setText(sanpham.getTensp());
        String urlhinh = sanpham.getHinhanhsp();
        Glide.with(convertView).load(urlhinh).centerCrop().placeholder(R.drawable.backround1).into(holder.imghinhanhsp);

        holder.tvgiasp.setText(sanpham.getGiasp()+" Đ");
        //mota chi co 2 dong
        holder.tvmotasp.setMaxLines(2);
        holder.tvmotasp.setEllipsize(TextUtils.TruncateAt.END);
        holder.tvmotasp.setText(sanpham.getMotasp());
        return convertView;
    }
}
