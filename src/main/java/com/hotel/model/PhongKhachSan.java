package com.hotel.model;

public abstract class PhongKhachSan {
    protected int soPhong;
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
    }

    // Getters & Setters
    public int getSoPhong() { return soPhong; }
    public int getTang() { return tang; }
    public String getLoaiPhong() { return loaiPhong; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public double getGiaCoBan() { return giaCoBan; }

    // --- CÁC HÀM TÍNH TOÁN BÓC TÁCH CHO HÓA ĐƠN ---

    // 1. Tiền phòng gốc = Giá cơ bản * Số ngày
    public double tinhTienGoc(int soNgay) {
        return giaCoBan * soNgay;
    }

    // 2. Chi phí dịch vụ (nếu class triển khai IServiceChargable)
    public double tinhPhiDichVu(int soNgay) {
        if (this instanceof IServiceChargable) {
            return ((IServiceChargable) this).tinhPhiDichVu();
        }
        return 0.0; // Phòng Standard / VIP không có phí dịch vụ này
    }

    // 3. Số tiền được giảm giá (nếu class triển khai IDiscountable)
    public double tinhTienGiamGia(int soNgay) {
        if (this instanceof IDiscountable) {
            double tienGoc = tinhTienGoc(soNgay);
            return ((IDiscountable) this).tinhTienGiamGia(tienGoc);
        }
        return 0.0; // Phòng Standard không giảm giá
    }

    // 4. Tổng tiền thanh toán cuối cùng = (Tiền gốc + Phí dịch vụ) - Giảm giá
    public double tinhTienThue(int soNgay) {
        return tinhTienGoc(soNgay) + tinhPhiDichVu(soNgay) - tinhTienGiamGia(soNgay);
    }
}