package com.hotel.model;

public abstract class PhongKhachSan {

    public static final double THUE_VAT = 0.1; // Thuế VAT 10%
    public static int tongSoLuongPhong = 0;    // Biến static đếm tổng số phòng đã tạo

    protected int soPhong; //Mã phòng tách ra thành số phòng và tầng.
    protected int tang;
    protected String loaiPhong;
    protected String trangThai;
    protected double giaCoBan;

    public PhongKhachSan(int soPhong, int tang, String loaiPhong, String trangThai, double giaCoBan) {
        this.soPhong = soPhong;
        this.tang = tang;
        this.loaiPhong = loaiPhong;
        this.trangThai = trangThai;
        this.giaCoBan = giaCoBan;

        tongSoLuongPhong++;
    }

    // Getters & Setters
    public int getSoPhong() { return soPhong; }
    public int getTang() { return tang; }
    public String getLoaiPhong() { return loaiPhong; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public double getGiaCoBan() { return giaCoBan; }
    public static int getTongSoLuongPhong() { return tongSoLuongPhong; }

    public double tinhTienGoc(int soNgay) {
        return giaCoBan * soNgay;
    }

    public double tinhPhiDichVu(int soNgay) {
        if (this instanceof IServiceChargable) {
            return ((IServiceChargable) this).getPhiDichVu();
        }
        return 0.0;
    }

    public double tinhTienGiamGia(int soNgay) {
        if (this instanceof IDiscountable) {
            double tienGoc = tinhTienGoc(soNgay);
            return ((IDiscountable) this).tinhTienGiamGia(tienGoc);
        }
        return 0.0;
    }

    public double tinhTienTruocThue(int soNgay) {
        return tinhTienGoc(soNgay) + tinhPhiDichVu(soNgay) - tinhTienGiamGia(soNgay);
    }

    public double tinhTienThueVAT(int soNgay) {
        return tinhTienTruocThue(soNgay) * THUE_VAT;
    }

    public abstract double tinhTienThue(int soNgay);
}