package com.hotel.model;

public class PhongPenthouse extends PhongKhachSan {

    // Constructor khởi tạo
    public PhongPenthouse(int soPhong, int tang, String trangThai, double giaCoBan) {
        super(soPhong, tang, "Penthouse", trangThai, giaCoBan);
    }

    // Nếu lớp cha PhongKhachSan yêu cầu bắt buộc override phương thức tính tiền thuê, ta viết ở đây:
    @Override
    public double tinhTienThue(int soNgay) {
        // Công thức riêng cho Penthouse (ví dụ: giá gốc nhân số ngày + phụ thu dịch vụ cao cấp, sau đó tính thuế VAT)
        double tienGoc = tinhTienGoc(soNgay);
        double phiDichVuCaoCap = 500000.0; // Phụ thu dịch vụ penthouse
        double tienTruocThue = tienGoc + phiDichVuCaoCap;
        return tienTruocThue * (1 + THUE_VAT);
    }
}