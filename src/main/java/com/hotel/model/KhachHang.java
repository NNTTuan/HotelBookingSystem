package com.hotel.model;

public class KhachHang {
    // Các thuộc tính private (-)
    private String soCCCD;
    private String hoTen;
    private String ngaySinh;
    private String duongDanAnhMat;

    // Hàm khởi tạo (Constructor) nhận 3 tham số theo UML
    public KhachHang(String soCCCD, String hoTen, String ngaySinh) {
        this.soCCCD = soCCCD;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.duongDanAnhMat = ""; // Mặc định để rỗng nếu chưa cài đặt đường dẫn ảnh
    }

    // Phương thức gán đường dẫn ảnh mặt (+)
    public void setDuongDanAnhMat(String path) {
        this.duongDanAnhMat = path;
    }

    // Phương thức lấy số CCCD (+)
    public String getSoCCCD() {
        return soCCCD;
    }

    // Phương thức lấy họ tên (+)
    public String getHoTen() {
        return hoTen;
    }

    // Phương thức lấy ngày sinh (+)
    public String getNgaySinh() {
        return ngaySinh;
    }

    // Phương thức lấy đường dẫn ảnh mặt (+)
    public String getDuongDanAnhMat() {
        return duongDanAnhMat;
    }
}