package com.example.shopquanao;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class InsertUserActivity extends AppCompatActivity {
    EditText edttaikhoanmoi, edtmkmoi, edthoten, edtsdt, edtdiachi;
    Button btndangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_user);
        Init();
        Act();
    }

    private void Act() {
        CliclDangKy();

    }

    private void CliclDangKy() {
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ur = getString(R.string.API);
                String url =  "http://" + ur +"/shopquanao/taotaikhoan.php";
                RequestQueue requestQueue = Volley.newRequestQueue(InsertUserActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("ttkerror")){
                            Toast.makeText(InsertUserActivity.this, "Tai khoan da ton tai", Toast.LENGTH_SHORT).show();
                        }else {
                            if(response.equals("success")){
                                Toast.makeText(InsertUserActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("taikhoan", edttaikhoanmoi.getText().toString());
                        params.put("matkhau", edtmkmoi.getText().toString());
                        params.put("tennguoidung", edthoten.getText().toString());
                        params.put("sodienthoai", edtsdt.getText().toString());
                        params.put("diachi", edtdiachi.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    private void Init() {
        edttaikhoanmoi = findViewById(R.id.edttaikhoanmoi);
        edtmkmoi = findViewById(R.id.edtmatkhaumoi);
        edthoten = findViewById(R.id.edthovatenmoi);
        edtsdt = findViewById(R.id.edtsdtmoi);
        edtdiachi = findViewById(R.id.edtdiachi);
        btndangky = findViewById(R.id.btnxacnhandangky);
    }
}