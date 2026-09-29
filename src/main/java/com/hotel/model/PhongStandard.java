package com.hotel.model;

// Lớp PhongStandard kế thừa từ PhongKhachSan
public class PhongStandard extends PhongKhachSan {

    // Constructor gọi constructor của lớp cha (PhongKhachSan)
    public PhongStandard(String maPhong, double giaGoc) {
        super(maPhong, giaGoc);
    }

    // Cài đặt lại (Override) phương thức tính tiền thuê
    @Override
    public double tinhTienThue(int soNgay) {
        double tienGoc = giaGoc * soNgay;
        return tienGoc + (tienGoc * THUE_VAT); // Tính thêm thuế VAT
    }
}