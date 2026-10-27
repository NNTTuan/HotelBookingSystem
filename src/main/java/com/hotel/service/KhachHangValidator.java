package com.hotel.service;

import com.hotel.model.KhachHang;

/**
 * Kiểm tra dữ liệu bắt buộc trước khi thực hiện check-in.
 * CCCD/Passport: đúng 12 chữ số.
 * Số điện thoại: đúng 10 chữ số và bắt đầu bằng 0.
 */
public final class KhachHangValidator {

    private KhachHangValidator() {
        // Không cần khởi tạo đối tượng validator.
    }

    public static void kiemTraHoTen(String hoTen) {
        if (hoTen == null || hoTen.trim().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập họ tên khách hàng.");
        }
    }

    public static void kiemTraCCCD(String soCCCD) {
        String giaTri = soCCCD == null ? "" : soCCCD.trim();
        if (!giaTri.matches("[0-9]{12}")) {
            throw new IllegalArgumentException(
                    "Số CCCD/Passport phải gồm đúng 12 chữ số (0-9)."
            );
        }
    }

    public static void kiemTraSoDienThoai(String soDienThoai) {
        String giaTri = soDienThoai == null ? "" : soDienThoai.trim();
        if (!giaTri.matches("0[0-9]{9}")) {
            throw new IllegalArgumentException(
                    "Số điện thoại phải gồm đúng 10 chữ số và bắt đầu bằng số 0."
            );
        }
    }

    public static void kiemTraSoNgayThue(int soNgayThue) {
        if (soNgayThue <= 0) {
            throw new IllegalArgumentException("Số ngày thuê phải lớn hơn 0.");
        }
    }

    public static void kiemTraThongTinCheckIn(KhachHang khach, int soNgayThue) {
        if (khach == null) {
            throw new IllegalArgumentException("Thông tin khách hàng không được để trống.");
        }

        kiemTraHoTen(khach.getHoTen());
        kiemTraCCCD(khach.getSoCCCD());
        kiemTraSoDienThoai(khach.getSoDienThoai());
        kiemTraSoNgayThue(soNgayThue);
    }
}
