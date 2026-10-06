package com.hotel.model;

public class PhongPenthouse extends PhongKhachSan implements IServiceChargable, IDiscountable {
    public static final double GIA_MAC_DINH = 2500000.0;

    public PhongPenthouse(int soPhong, int tang, String trangThai) {
        super(soPhong, tang, "Penthouse", trangThai, GIA_MAC_DINH);
    }

    public PhongPenthouse(int soPhong, int tang, String trangThai, double giaCoBan) {
        super(soPhong, tang, "Penthouse", trangThai, giaCoBan);
    }

    // --- Triển khai IServiceChargable ---
    @Override
    public double getPhiDichVu() {
        return 500000.0; // Phí dịch vụ Penthouse
    }

    @Override
    public double tinhPhiDichVu() {
        return getPhiDichVu();
    }

    // --- Triển khai IDiscountable ---
    @Override
    public double getTiLeGiamGia() {
        return 0.10; // Giảm giá 10% (0.10)
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