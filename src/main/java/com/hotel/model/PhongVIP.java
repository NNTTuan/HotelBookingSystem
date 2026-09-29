package com.hotel.model;

// Lớp PhongVIP kế thừa từ PhongKhachSan
public class PhongVIP extends PhongKhachSan {

    // Constructor
    public PhongVIP(String maPhong, double giaGoc) {
        super(maPhong, giaGoc);
    }

    // Tính tiền giảm giá (Ví dụ: Giảm 10% giá gốc)
    public double tinhGiamGia() {
        return giaGoc * 0.10;
    }

    // Tính phí dịch vụ đi kèm (Ví dụ: 200,000 VNĐ cố định)
    public double tinhPhiDichVu() {
        return 200000.0;
    }

    // Cài đặt lại phương thức tính tiền thuê phòng VIP
    @Override
    public double tinhTienThue(int soNgay) {
        // Giá 1 ngày sau khi trừ giảm giá
        double giaSauGiam = giaGoc - tinhGiamGia();
        
        // Tổng tiền = (Giá sau giảm * số ngày) + phí dịch vụ
        double tienChuaThue = (giaSauGiam * soNgay) + tinhPhiDichVu();
        
        // Cộng thêm thuế VAT
        return tienChuaThue + (tienChuaThue * THUE_VAT);
    }
}