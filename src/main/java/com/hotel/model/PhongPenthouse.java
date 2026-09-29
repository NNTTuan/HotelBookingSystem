package com.hotel.model;

// Lớp PhongPenthouse kế thừa từ PhongKhachSan
public class PhongPenthouse extends PhongKhachSan {

    // Constructor
    public PhongPenthouse(String maPhong, double giaGoc) {
        super(maPhong, giaGoc);
    }

    // Cài đặt lại phương thức tính tiền thuê phòng Penthouse
    @Override
    public double tinhTienThue(int soNgay) {
        double tienGoc = giaGoc * soNgay;
        // Ví dụ: PhongPenthouse có thêm phí dịch vụ cố định 15%
        double phiDichVu = tienGoc * 0.15;
        double tongTien = tienGoc + phiDichVu;
        return tongTien + (tongTien * THUE_VAT);
    }
}