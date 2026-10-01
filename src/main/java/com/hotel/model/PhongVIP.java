package com.hotel.model;

public class PhongVIP extends PhongKhachSan implements IDiscountable {
    private double tiLeGiamGia = 0.1; // 10%

    public PhongVIP(int soPhong, int tang, String trangThai) {
        super(soPhong, tang, "VIP", trangThai, 1200000.0);
    }

    @Override
    public double getTiLeGiamGia() {
        return tiLeGiamGia;
    }

    @Override
    public double tinhTienGiamGia(double tongTienGoc) {
        return tongTienGoc * tiLeGiamGia;
    }

    @Override
    public double tinhTienThue(int soNgay) {
        double tienGoc = giaCoBan * soNgay;
        return tienGoc - tinhTienGiamGia(tienGoc);
    }
}