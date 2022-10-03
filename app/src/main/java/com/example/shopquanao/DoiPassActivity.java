package com.example.shopquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DoiPassActivity extends AppCompatActivity {
    EditText edttaikhoan, edtmatkhaucu, edtmatkhaumoi, edtxacnhanlaimk;
    Button btnxacnhandoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_pass);
        Init();
        Act();
    }

    private void Act() {
        SuKienXetTkDoiMK();

    }

    private void SuKienXetTkDoiMK() {
        btnxacnhandoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ur = getString(R.string.API);
                String url =  "http://"+ur+"/shopquanao/acctiondangnhap.php";
                RequestQueue requestQueue = Volley.newRequestQueue(DoiPassActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success")){
                            UpdateMatKhau();
                        }else{
                            Toast.makeText(DoiPassActivity.this, "Mật khẩu cũ không đúng !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("taikhoan", edttaikhoan.getText().toString());
                        params.put("matkhau", edtmatkhaucu.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            private void UpdateMatKhau() {
                String ur = getString(R.string.API);
                String url =  "http://"+ur+"/shopquanao/UpdateMK.php";
                RequestQueue requestQueue = Volley.newRequestQueue(DoiPassActivity.this);
                StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(DoiPassActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(DoiPassActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("taikhoan", edttaikhoan.getText().toString());
                        params.put("matkhaumoi", edtmatkhaumoi.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    private void Init() {
        edttaikhoan = findViewById(R.id.edttaikhoanmoi);
        edtmatkhaucu = findViewById(R.id.edtmatkhaumoi);
        edtmatkhaumoi = findViewById(R.id.edthovatenmoi);
        edtxacnhanlaimk = findViewById(R.id.edtsdtmoi);
        btnxacnhandoi = findViewById(R.id.btnxacnhandangky);
    }
}