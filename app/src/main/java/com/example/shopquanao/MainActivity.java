package com.example.shopquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText edttaikhoan, edtmatkhau;
    TextView tvquenmatkhau, tvdangkymoi;
    Button btndangnhap;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        Act();
    }

    private void Act() {
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("taikhoan");
        editor.remove("tentaikhoan");
        editor.remove("ttdn");
    Sukiendangnhap();
    sukiendoimk();
    sukiendangkyUser();

    }

    private void sukiendangkyUser() {
        tvdangkymoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InsertUserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sukiendoimk() {
        tvquenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DoiPassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Sukiendangnhap() {
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ur = getString(R.string.API);
                String url =  "http://"+ur+"/shopquanao/acctiondangnhap.php";
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success")){
                            sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("taikhoan", edttaikhoan.getText().toString());
                            editor.putInt("ttdn", 1);
                            editor.commit();
                            String ur = getString(R.string.API);
                            String url =  "http://"+ur+"/shopquanao/loadthongtinTk.php";
                            getThongTinTK(url);
                            Intent intent = new Intent(MainActivity.this, trangchu.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(MainActivity.this, "Tài khoản và mật không đúng", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error1805", error.toString());
                    }
                }){
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("taikhoan", edttaikhoan.getText().toString());
                        params.put("matkhau", edtmatkhau.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            private void getThongTinTK(String url) {
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("aaaaaaaaaaa","aaaaaaaaaaa");
                            JSONArray array = new JSONArray(response);
                            JSONObject object = array.getJSONObject(0);
                            String tentaikhoan = object.getString("tentaikhoan");
                            String tennguoidung = object.getString("tennguoidung");
                            String sodienthoai = object.getString("sodienthoai");
                            String diachi = object.getString("diachi");

                            sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("tentaikhoan",tentaikhoan );
                            editor.putString("tennguoidung",tennguoidung );
                            editor.putString("sodienthoai",sodienthoai );
                            editor.putString("diachi",diachi );

                            editor.commit();
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
                        Map<String,String> params = new HashMap<>();
                        params.put("tentaikhoan", edttaikhoan.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    private void Init() {
        edttaikhoan = findViewById(R.id.edttaikhoanmoi);
        edtmatkhau = findViewById(R.id.edtmatkhaucandoi);
        btndangnhap = findViewById(R.id.btnxacnhandangky);
        tvquenmatkhau = findViewById(R.id.tvquenmatkhau);
        tvquenmatkhau.setPaintFlags(tvquenmatkhau.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvdangkymoi = findViewById(R.id.tvdangky);
        tvdangkymoi.setPaintFlags(tvquenmatkhau.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }
}