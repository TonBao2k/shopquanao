package com.example.shopquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ThongTinActivity extends AppCompatActivity {
    TextView tvtentk, tvsdt, tvdiachi, tvtenkh, textView12;
    ImageView imgdangxuat, imgquayve;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        Init();
        Act();
    }

    private void Act() {
        GetThongTinKH();
        SuKienDangXuat();
        Clickqauyve();
    }

    private void Clickqauyve() {
        imgquayve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void SuKienDangXuat() {
        imgdangxuat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //cứu pháp dialog
                Dialog dialog =new Dialog(ThongTinActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogout);
                //anh xạ
                Button btnxco = dialog.findViewById(R.id.btnco);
                Button btnkhong = dialog.findViewById(R.id.btnkhong);

                btnxco.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(ThongTinActivity.this,MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(ThongTinActivity.this, "Bạn đã đẵng xuất", Toast.LENGTH_SHORT).show();
                        finish();
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

    private void GetThongTinKH() {
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        int ttdn = sharedPreferences.getInt("ttdn",0);
        if(ttdn == 1){
            String taikhoan  = sharedPreferences.getString("taikhoan","");
            String tentaikhoan  = sharedPreferences.getString("tentaikhoan","");
            String tennguoidung  = sharedPreferences.getString("tennguoidung","");
            String sodienthoai  = sharedPreferences.getString("sodienthoai","");
            String diachi  = sharedPreferences.getString("diachi","");
            tvtentk.setText(tentaikhoan);
            tvsdt.setText(sodienthoai);
            tvdiachi.setText(diachi);
            tvtenkh.setText(tennguoidung);
        }
    }

    private void Init() {
        imgquayve = findViewById(R.id.imgquayve);
        tvtentk = findViewById(R.id.tvuser);
        tvsdt = findViewById(R.id.tvsdt);
        tvdiachi = findViewById(R.id.tvdiachi);
        tvtenkh = findViewById(R.id.tvtenkh);
        textView12 = findViewById(R.id.textView12);
        imgdangxuat = findViewById(R.id.imgdangxuat);
        textView12.setPaintFlags(textView12.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}