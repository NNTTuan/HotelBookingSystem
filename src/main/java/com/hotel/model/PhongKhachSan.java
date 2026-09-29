package com.hotel.model;

// Lớp trừu tượng PhongKhachSan theo sơ đồ UML
public abstract class PhongKhachSan {
    // Thuộc tính protected (#)
    protected String maPhong;
    protected double giaGoc;

    // Thuộc tính static public (được gạch chân trong UML) (+)
    public static double THUE_VAT = 0.1; // Ví dụ thuế VAT 10%

    // Thuộc tính static private (được gạch chân trong UML) (-)
    private static int tongSoPhong = 0;

    // Constructor (Hàm khởi tạo)
    public PhongKhachSan(String maPhong, double giaGoc) {
        this.maPhong = maPhong;
        this.giaGoc = giaGoc;
        tongSoPhong++; // Tăng tổng số phòng mỗi khi tạo một phòng mới
    }

    // Phương thức trừu tượng (chữ nghiêng trong UML) - Các lớp con sẽ tự cài đặt
    public abstract double tinhTienThue(int soNgay);

    // Phương thức static getter để lấy tổng số phòng
    public static int getTongSoPhong() {
        return tongSoPhong;
    }

    // Getter cho mã phòng
    public String getMaPhong() {
        return maPhong;
    }

    // Getter cho giá gốc
    public double getGiaGoc() {
        return giaGoc;
    }
}