package com.hotel.view.console;

import com.hotel.model.KhachHang;
import com.hotel.model.PhongKhachSan;
import com.hotel.service.KhachHangValidator;
import com.hotel.service.QuanLyKhachSan;

import java.util.Scanner;

public class CheckInConsoleHandler {
    private final QuanLyKhachSan qlks;
    private final Scanner sc;

    public CheckInConsoleHandler(QuanLyKhachSan qlks, Scanner sc) {
        this.qlks = qlks;
        this.sc = sc;
    }

    public void thucHienCheckIn() {
        System.out.println("\n--- THỰC HIỆN CHECK-IN NHẬN PHÒNG ---");
        int soPhong = nhapSoPhongHopLe();
        if (soPhong == -1) return;

        PhongKhachSan phong = qlks.timPhong(soPhong);
        if (!kiemTraPhongTrong(phong)) return;

        KhachHang khach = nhapThongTinKhachHang();
        int soNgayThue = nhapSoNgayThue();

        try {
            boolean thanhCong = qlks.checkIn(soPhong, khach, soNgayThue);
            if (thanhCong) {
                System.out.println("[THÀNH CÔNG] Check-in thành công phòng " + soPhong
                        + " (" + soNgayThue + " ngày)!");
            } else {
                System.out.println("[THẤT BẠI] Không thể thực hiện check-in. Vui lòng kiểm tra trạng thái phòng.");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("[LỖI DỮ LIỆU] " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("[LỖI] Không thể thực hiện check-in: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private int nhapSoPhongHopLe() {
        System.out.print("Nhập số phòng muốn thuê: ");
        if (!sc.hasNextInt()) {
            System.out.println("[LỖI] Số phòng phải là một số nguyên!");
            sc.nextLine();
            return -1;
        }
        int soPhong = sc.nextInt();
        sc.nextLine();
        return soPhong;
    }

    private boolean kiemTraPhongTrong(PhongKhachSan phong) {
        if (phong == null) {
            System.out.println("[LỖI] Không tìm thấy số phòng này trong hệ thống!");
            return false;
        }
        if (!"TRONG".equalsIgnoreCase(phong.getTrangThai())) {
            System.out.println("[LỖI] Phòng không ở trạng thái TRONG (Hiện tại: "
+ phong.getTrangThai() + ")!");
        return false;
        }
        return true;
        }

private KhachHang nhapThongTinKhachHang() {
    String hoTen;
    while (true) {
        System.out.print("Nhập họ tên khách hàng: ");
        hoTen = sc.nextLine().trim();
        try {
            KhachHangValidator.kiemTraHoTen(hoTen);
            break;
        } catch (IllegalArgumentException ex) {
            System.out.println("[LỖI] " + ex.getMessage());
        }
    }

    String cccd;
    while (true) {
        System.out.print("Nhập số CCCD/Passport (12 chữ số): ");
        cccd = sc.nextLine().trim();
        try {
            KhachHangValidator.kiemTraCCCD(cccd);
            break;
        } catch (IllegalArgumentException ex) {
            System.out.println("[LỖI] " + ex.getMessage());
        }
    }

    String sdt;
    while (true) {
        System.out.print("Nhập số điện thoại (10 chữ số, bắt đầu bằng 0): ");
        sdt = sc.nextLine().trim();
        try {
            KhachHangValidator.kiemTraSoDienThoai(sdt);
            break;
        } catch (IllegalArgumentException ex) {
            System.out.println("[LỖI] " + ex.getMessage());
        }
    }

    return new KhachHang(hoTen, cccd, sdt, "none");
}

private int nhapSoNgayThue() {
    while (true) {
        System.out.print("Nhập số ngày thuê dự kiến: ");
        String input = sc.nextLine().trim();
        try {
            int soNgay = Integer.parseInt(input);
            KhachHangValidator.kiemTraSoNgayThue(soNgay);
            return soNgay;
        } catch (NumberFormatException ex) {
            System.out.println("[LỖI] Số ngày thuê phải là số nguyên hợp lệ. Chi tiết: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("[LỖI] " + ex.getMessage());
        }
    }
}
}
