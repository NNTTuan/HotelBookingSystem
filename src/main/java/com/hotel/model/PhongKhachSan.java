package com.hotel.model;

public abstract class PhongKhachSan {
    // 1. BIẾN STATIC THEO ĐỀ BÀI
    public static final double THUE_VAT = 0.1; // Thuế VAT 10%
    public static int tongSoLuongPhong = 0;    // Biến static đếm tổng số phòng đã tạo

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

        // Mỗi lần khởi tạo 1 phòng, tăng biến đếm static
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

    // --- CÁC HÀM TÍNH TOÁN CÔNG KHỦNG DÙNG CHO HÓA ĐƠN ---

    // 1. Tiền phòng gốc = Giá cơ bản * Số ngày
    public double tinhTienGoc(int soNgay) {
        return giaCoBan * soNgay;
    }

    // 2. Chi phí dịch vụ (nếu class triển khai IServiceChargable)
    public double tinhPhiDichVu(int soNgay) {
        if (this instanceof IServiceChargable) {
            return ((IServiceChargable) this).getPhiDichVu();
        }
        return 0.0;
    }

    // 3. Số tiền được giảm giá (nếu class triển khai IDiscountable)
    public double tinhTienGiamGia(int soNgay) {
        if (this instanceof IDiscountable) {
            double tienGoc = tinhTienGoc(soNgay);
            return ((IDiscountable) this).tinhTienGiamGia(tienGoc);
        }
        return 0.0;
    }

    // 4. Tiền trước thuế = (Tiền gốc + Phí dịch vụ) - Giảm giá
    public double tinhTienTruocThue(int soNgay) {
        return tinhTienGoc(soNgay) + tinhPhiDichVu(soNgay) - tinhTienGiamGia(soNgay);
    }

    // 5. Tiền thuế VAT
    public double tinhTienThueVAT(int soNgay) {
        return tinhTienTruocThue(soNgay) * THUE_VAT;
    }

    // 6. Tổng tiền thanh toán cuối cùng = Tiền trước thuế + Thuế VAT
    public double tinhTienThue(int soNgay) {
        return tinhTienTruocThue(soNgay) * (1 + THUE_VAT);
    }
}