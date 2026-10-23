package com.hotel.model;

public class PhongStandard extends PhongKhachSan {
    public static final double GIA_MAC_DINH = 500000.0; // 500.000 VNĐ/ngày

    public PhongStandard(int soPhong, int tang, String trangThai) {
        super(soPhong, tang, "Standard", trangThai, GIA_MAC_DINH);
    }

    public PhongStandard(int soPhong, int tang, String trangThai, double giaCoBan) {
        super(soPhong, tang, "Standard", trangThai, giaCoBan);
    }

    @Override
    public double tinhTienThue(int soNgay) {
        return tinhTienTruocThue(soNgay) * (1 + THUE_VAT);
    }
}