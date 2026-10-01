package com.hotel.model;

public class PhongVIP extends PhongKhachSan {
    public PhongVIP(int soPhong, int tang, String trangThai) {
        super(soPhong, tang, "VIP", trangThai);
    }

    @Override
    public double tinhTienThue(int soNgayThue) {
        return soNgayThue * 1200000.0; // Giá 1.200.000 VNĐ / ngày
    }
}