package com.hotel.model;

public abstract class PhongKhachSan {
    private int soPhong;
    private int tang;
    private String loaiPhong;
    private String trangThai; // TRONG, DANG_O, DANG_DON

    public PhongKhachSan(int soPhong, int tang, String loaiPhong, String trangThai) {
        this.soPhong = soPhong;
        this.tang = tang;
        this.loaiPhong = loaiPhong;
        this.trangThai = trangThai;
    }

    // Phương thức trừu tượng tính tiền thuê phòng
    public abstract double tinhTienThue(int soNgayThue);

    public int getSoPhong() { return soPhong; }
    public void setSoPhong(int soPhong) { this.soPhong = soPhong; }

    public int getTang() { return tang; }
    public void setTang(int tang) { this.tang = tang; }

    public String getLoaiPhong() { return loaiPhong; }
    public void setLoaiPhong(String loaiPhong) { this.loaiPhong = loaiPhong; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}