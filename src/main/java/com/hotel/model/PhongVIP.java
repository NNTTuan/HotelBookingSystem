package com.hotel.model;

public class PhongVIP extends PhongKhachSan implements IDiscountable, IServiceChargable {
    public static final double GIA_MAC_DINH = 1200000.0;

    public PhongVIP(int soPhong, int tang, String trangThai) {
        super(soPhong, tang, "VIP", trangThai, GIA_MAC_DINH);
    }

    public PhongVIP(int soPhong, int tang, String trangThai, double giaCoBan) {
        super(soPhong, tang, "VIP", trangThai, giaCoBan);
    }

    // --- Triển khai IServiceChargable ---
    @Override
    public double getPhiDichVu() {
        return 200000.0; // Phí dịch vụ VIP
    }

    @Override
    public double tinhPhiDichVu() {
        return getPhiDichVu();
    }

    // --- Triển khai IDiscountable ---
    @Override
    public double getTiLeGiamGia() {
        return 0.05; // Giảm giá 5% (0.05)
    }

    @Override
    public double tinhTienGiamGia(double tongTienGoc) {
        return tongTienGoc * getTiLeGiamGia();
    }

    // --- Tính tổng tiền thuê bao gồm VAT ---
    @Override
    public double tinhTienThue(int soNgay) {
        return tinhTienTruocThue(soNgay) * (1 + THUE_VAT);
    }
}