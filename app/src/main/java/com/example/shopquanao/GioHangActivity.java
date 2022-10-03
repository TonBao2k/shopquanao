package com.example.shopquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GioHangActivity extends AppCompatActivity {
    ListView lvgiohang;
    ArrayList<Classgiohang> GioHang;
    AdapterGioHang adapterGioHang;
    ImageView imgback;
    Button btnthanhtoan;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Init();
        Act();
    }

    private void Act() {
        LoadGioHang();
        ClickMua();
        XoaItemgioHang();
        Clickquayve();

    }

    private void Clickquayve() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void XoaItemgioHang() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //cứu pháp dialog
                Dialog dialog =new Dialog(GioHangActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                //anh xạ
                Button btnxco = dialog.findViewById(R.id.btnco);
                Button btnkhong = dialog.findViewById(R.id.btnkhong);

                btnxco.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GioHang.remove(i);
                        adapterGioHang.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                btnkhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //tắt dialog
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    private void ClickMua() {
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String idkhachhang  = sharedPreferences.getString("taikhoan","");
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ur = getString(R.string.API);
                String url = "http://"+ur+"/shopquanao/updateTrangThayGH.php";
                RequestQueue requestQueue = Volley.newRequestQueue(GioHangActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GioHang.remove(GioHang);
                        adapterGioHang.notifyDataSetChanged();
                        Intent intent = new Intent(GioHangActivity.this,trangchu.class);
                        startActivity(intent);
                        Toast.makeText(GioHangActivity.this, "Yêu cầu mua hàng thành công!! Mời bạn tiếp tục mua", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("idkhachhang", idkhachhang);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    private void LoadGioHang() {
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String idkhachhang  = sharedPreferences.getString("taikhoan","");
        String ur = getString(R.string.API);
        String url = "http://"+ur+"/shopquanao/loadgiohang.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=1; i< jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Classgiohang giohang = new Classgiohang(jsonObject.getString("tensanpham"),jsonObject.getInt("giasanpham"),
                                jsonObject.getString("hinhanhsanpham"),jsonObject.getInt("soluongsanphamtronggio"),jsonObject.getString("sizesanpham"));
                        GioHang.add(giohang);
                    }
                        adapterGioHang.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idkhachhang", idkhachhang);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Init() {
        imgback = findViewById(R.id.imggiohangquayve);
        btnthanhtoan = findViewById(R.id.btnthanhtoan);
        lvgiohang = findViewById(R.id.lvgiohang);
        GioHang = new ArrayList<>();
        adapterGioHang = new AdapterGioHang(this, R.layout.dong_giohang, GioHang);
        lvgiohang.setAdapter(adapterGioHang);
    }
}