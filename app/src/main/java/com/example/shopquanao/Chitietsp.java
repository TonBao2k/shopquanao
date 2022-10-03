package com.example.shopquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Map;

public class Chitietsp extends AppCompatActivity {
    TextView tvtenchitietsp, tvgiachitietsp, tvmotachitietsp, tvcong,tvtru, tvtong;
    ImageView imgchitietsp;
    CheckBox cbsize1, cbsize2, cbsize3;
    Button btnmua;
    ArrayList<classSanpham> Sanpham;
    sanphamAdapter adapterSp;
    int soluong = 1;
    SharedPreferences sharedPreferences;
    String check = "M";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsp);
        Init();
        Act();
    }

    private void Act() {
    getData();
    soluong();
    sukiencheck();
    sukienthemgiohang();


    }

    private void sukienthemgiohang() {
        btnmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemHang();
            }
        });
    }

    private void ThemHang() {
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String idkhachhang  = sharedPreferences.getString("taikhoan","");
        Intent intent = getIntent();
        String idsanpham = intent.getStringExtra("thongtinsanpham");
        String soluongsanphamtronggio = tvtong.getText().toString().trim();
        String ur = getString(R.string.API);
        String url = "http://"+ur+"/shopquanao/insertspvaogiohang.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Chitietsp.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Chitietsp.this, "Bạn đã thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idkhachhang", idkhachhang);
                params.put("idsanpham", idsanpham);
                params.put("soluongsanphamtronggio", soluongsanphamtronggio);
                params.put("sizesanpham", check);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void sukiencheck() {
        cbsize1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbsize1.isChecked()){
                    cbsize2.setChecked(false);
                    cbsize3.setChecked(false);
                    check = "M";
                }
            }
        });
        cbsize2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbsize2.isChecked()){
                    cbsize1.setChecked(false);
                    cbsize3.setChecked(false);
                    check = "S";
                }
            }
        });
        cbsize3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbsize3.isChecked()){
                    cbsize2.setChecked(false);
                    cbsize1.setChecked(false);
                    check = "L";
                }
            }
        });

    }

    private void soluong() {
        tvcong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soluong++;
                tvtong.setText(soluong+"");
            }
        });
        tvtru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(soluong==0){
                    tvtong.setText("0");
                }else {
                    soluong--;
                    tvtong.setText(soluong+"");}
            }
        });
    }

    private void getData() {
        String ur = getString(R.string.API);
        String url = "http://"+ur+"/shopquanao/loadsanpham.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject object = array.getJSONObject(0);
                    tvtenchitietsp.setText(object.getString("tensanpham"));
                    tvgiachitietsp.setText(object.getInt("giasanpham")+" Đ");
                    Picasso.get().load(object.getString("hinhanhsanpham"))
                            .into(imgchitietsp);
                    tvmotachitietsp.setText(object.getString("motasanpham"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            Intent intent = getIntent();
            String mama = intent.getStringExtra("thongtinsanpham");
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", mama);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void Init() {
        tvtenchitietsp = findViewById(R.id.tvtenchitietsp);
        tvgiachitietsp = findViewById(R.id.tvgiachitietsp);
        tvmotachitietsp = findViewById(R.id.tvmotachitiet);
        imgchitietsp = findViewById(R.id.imgchitietsp);
        cbsize1 = findViewById(R.id.cbsize1);
        cbsize2 = findViewById(R.id.cbsize2);
        cbsize3 = findViewById(R.id.cbsize3);
        btnmua = findViewById(R.id.btnmua);
        tvcong = findViewById(R.id.tvcong);
        tvtru = findViewById(R.id.tvtru);
        tvtong = findViewById(R.id.tvtong);

    }
}