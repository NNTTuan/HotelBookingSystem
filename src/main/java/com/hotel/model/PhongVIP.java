package com.hotel.model;

public class PhongVIP extends PhongKhachSan implements IDiscountable, IServiceChargable {
    private double mucGiamGia;
    private double phiDichVuCoDinh;

    public PhongVIP() {
        super();
    }

    public PhongVIP(String maPhong, double giaGoc, double mucGiamGia, double phiDichVuCoDinh) {
        super(maPhong, giaGoc);
        this.mucGiamGia = mucGiamGia;
        this.phiDichVuCoDinh = phiDichVuCoDinh;
    }

    public double getMucGiamGia() {
        return mucGiamGia;
    }

    public void setMucGiamGia(double mucGiamGia) {
        this.mucGiamGia = mucGiamGia;
    }

    public double getPhiDichVuCoDinh() {
        return phiDichVuCoDinh;
    }

    public void setPhiDichVuCoDinh(double phiDichVuCoDinh) {
        this.phiDichVuCoDinh = phiDichVuCoDinh;
    }

    @Override
    public double tinhGiamGia(double soTienGoc) {
        return soTienGoc * mucGiamGia;
    }

    @Override
    public double tinhPhiDichVu() {
        return phiDichVuCoDinh;
    }

    @Override
    public double tinhTienThue(int soNgay) {
        double tienCoBan = getGiaGoc() * soNgay;
        double tienSauGiam = tienCoBan - tinhGiamGia(tienCoBan);
        double tongTruocThue = tienSauGiam + tinhPhiDichVu();

        return tongTruocThue + (tongTruocThue * THUE_VAT);
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.printf(" | Loại: VIP | Phí DV: %,.0f VNĐ | Giảm giá: %.0f%%\n",
                phiDichVuCoDinh, mucGiamGia * 100);
    }
}