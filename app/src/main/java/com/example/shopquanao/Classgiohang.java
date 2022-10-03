package com.example.shopquanao;

public class Classgiohang {
    String tensp;
    int giasp;
    String hinh;
    int sl;
    String size;

    public Classgiohang(String tensp, int giasp, String hinh, int sl, String size) {
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinh = hinh;
        this.sl = sl;
        this.size = size;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
