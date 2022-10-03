package com.example.shopquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class trangchu extends AppCompatActivity {
    ListView lvsanpham;
    ViewFlipper fliperquangcao;
    ImageView imgtrangchu, imggiohang, imgcanhan;
    ArrayList<classSanpham> Sanpham;
    sanphamAdapter adapterSp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);
        Init();
        Act();
    }

    private void Act() {
        quangcao();
        loadSanpham();
        lickitemlistview();
        ViewGiohang();
        lickthongtinKH();
        XoaItemListview();
    }

    private void XoaItemListview() {
        lvsanpham.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return false;
            }
        });
    }

    private void lickthongtinKH() {
        imgcanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(trangchu.this, ThongTinActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ViewGiohang() {
        imggiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(trangchu.this,GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void lickitemlistview() {
        lvsanpham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(trangchu.this, Chitietsp.class);
                String id = Sanpham.get(position).getId()+"";
                intent.putExtra("thongtinsanpham", id);
                startActivity(intent);
            }
        });
    }

    private void loadSanpham() {
        String ur = getString(R.string.API);
        String url = "http://"+ur+"/shopquanao/getsanpham.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray Array = new JSONArray(response);

                    for (int i=1; i< Array.length();i++)
                    {
                      JSONObject jsonObject = Array.getJSONObject(i);
                      classSanpham sanpham = new classSanpham(jsonObject.getInt("id"),jsonObject.getString("tensanpham"),jsonObject.getInt("giasanpham")
                      ,jsonObject.getString("hinhanhsanpham"),jsonObject.getString("motasanpham"));
                      Sanpham.add(sanpham);

                    }
                    adapterSp.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void quangcao() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://suno.vn/blog/wp-content/uploads/2015/12/sale-off.jpg");
        mangquangcao.add("https://file.hstatic.net/1000312752/file/banner_1_0372383aa56c4783848d60e5ca325a76_grande.jpg");
        mangquangcao.add("https://suno.vn/blog/wp-content/uploads/2015/12/holiday_sale_hp.gif");
        for (int i = 0; i<mangquangcao.size(); i++){
            ImageView imageView = new ImageView(this);
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            fliperquangcao.addView(imageView);
        }
        fliperquangcao.setFlipInterval(3000);
        fliperquangcao.setAutoStart(true);

    }

    private void Init() {
        lvsanpham = findViewById(R.id.lvsanpham);
        fliperquangcao = findViewById(R.id.vFquangcao);
        imgtrangchu = findViewById(R.id.imgtrangchu);
        imgcanhan = findViewById(R.id.imgcanhan);
        imggiohang = findViewById(R.id.imggiohang);
        Sanpham = new ArrayList<>();
        adapterSp = new sanphamAdapter(trangchu.this, R.layout.dong_san_pham, Sanpham);
        lvsanpham.setAdapter(adapterSp);

    }

}