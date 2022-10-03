package com.example.shopquanao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterGioHang extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Classgiohang> list;

    public AdapterGioHang(Context context, int layout, List<Classgiohang> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public  class Viewhoder{
        TextView tvtenspgiohang, tvgiaspgiohang, tvsoluongspgiohang, tvsizespgiohang;
        ImageView imghinhtronggio;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewhoder viewhoder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            viewhoder = new AdapterGioHang.Viewhoder();

            viewhoder.tvtenspgiohang = view.findViewById(R.id.tvtenspgiohang);
            viewhoder.imghinhtronggio = view.findViewById(R.id.imghinhtronggio);
            viewhoder.tvgiaspgiohang = view.findViewById(R.id.tvgiaspgiohang);
            viewhoder.tvsoluongspgiohang = view.findViewById(R.id.tvsoluongspgiohang);
            viewhoder.tvsizespgiohang = view.findViewById(R.id.tvsizespgiohang);

            view.setTag(viewhoder);
        }else {
            viewhoder = (AdapterGioHang.Viewhoder) view.getTag();
        }

        Classgiohang giohang = list.get(i);

        viewhoder.tvtenspgiohang.setText("Tên sản phẩm: "+giohang.getTensp());
        viewhoder.tvsoluongspgiohang.setText("Số lượng: "+giohang.getSl());
        viewhoder.tvsizespgiohang.setText("Size: "+giohang.getSize());
        String urlhinh = giohang.getHinh();
        Glide.with(view).load(urlhinh).centerCrop().placeholder(R.drawable.backround1).into(viewhoder.imghinhtronggio);

        viewhoder.tvgiaspgiohang.setText("Giá: "+giohang.getGiasp()+" Đ");
        return view;
    }
}
