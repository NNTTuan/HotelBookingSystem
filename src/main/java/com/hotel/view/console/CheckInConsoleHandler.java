package com.hotel.view.console;

import com.hotel.model.KhachHang;
import com.hotel.model.PhongKhachSan;
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

        boolean thanhCong = qlks.checkIn(soPhong, khach, soNgayThue);
        if (thanhCong) {
            System.out.println("[THÀNH CÔNG] Check-in thành công phòng " + soPhong + " (" + soNgayThue + " ngày)!");
        } else {
            System.out.println("[THẤT BẠI] Không thể thực hiện check-in.");
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
            System.out.println("[LỖI] Phòng không ở trạng thái TRONG (Hiện tại: " + phong.getTrangThai() + ")!");
            return false;
        }
        return true;
    }

    private KhachHang nhapThongTinKhachHang() {
        System.out.print("Nhập họ tên khách hàng: ");
        String hoTen = sc.nextLine();
        System.out.print("Nhập số CCCD/Passport: ");
        String cccd = sc.nextLine();
        System.out.print("Nhập số điện thoại: ");
        String sdt = sc.nextLine();
        return new KhachHang(hoTen, cccd, sdt, "none");
    }

    private int nhapSoNgayThue() {
        System.out.print("Nhập số ngày thuê dự kiến: ");
        int soNgay = sc.hasNextInt() ? sc.nextInt() : 1;
        sc.nextLine();
        return soNgay;
    }
}