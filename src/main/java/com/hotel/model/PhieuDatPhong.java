package com.hotel.model;

public class PhieuDatPhong {
    private String maPhieu;
    private PhongKhachSan phong;
    private KhachHang khachHang;
    private int soNgayThue;
    private String trangThai; // "DANG_O" hoặc "DA_TRA"

    public PhieuDatPhong(String maPhieu, PhongKhachSan phong, KhachHang khachHang, int soNgayThue, String trangThai) {
        this.maPhieu = maPhieu;
        this.phong = phong;
        this.khachHang = khachHang;
        this.soNgayThue = soNgayThue;
        this.trangThai = trangThai;
    }

    public double tinhTongTienPhieu() {
        if (phong == null) return 0.0;
        return phong.tinhTienThue(soNgayThue);
    }

    public String getMaPhieu() { return maPhieu; }
    public void setMaPhieu(String maPhieu) { this.maPhieu = maPhieu; }

    public PhongKhachSan getPhong() { return phong; }
    public void setPhong(PhongKhachSan phong) { this.phong = phong; }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }

    public int getSoNgayThue() { return soNgayThue; }
    public void setSoNgayThue(int soNgayThue) { this.soNgayThue = soNgayThue; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}