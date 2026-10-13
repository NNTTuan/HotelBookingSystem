package com.hotel.view.console;

import com.hotel.model.PhongKhachSan;
import com.hotel.service.QuanLyKhachSan;

import java.util.Scanner;

public class PhongConsoleHandler {
    private final QuanLyKhachSan qlks;
    private final Scanner sc;

    public PhongConsoleHandler(QuanLyKhachSan qlks) {
        this.qlks = qlks;
        this.sc = new Scanner(System.in);
    }

    public void hienThiDanhSachPhong() {
        System.out.println("\n--- DANH SÁCH 20 PHÒNG KHÁCH SẠN ---");
        System.out.printf("%-10s | %-8s | %-15s | %-15s\n", "Số Phòng", "Tầng", "Loại Phòng", "Trạng Thái");
        System.out.println("------------------------------------------------------------");

        for (PhongKhachSan p : qlks.getDanhSachPhong()) {
            System.out.printf("%-10d | %-8d | %-15s | %-15s\n",
                    p.getSoPhong(), p.getTang(), p.getLoaiPhong(), p.getTrangThai());
        }
        System.out.println("------------------------------------------------------------");
    }

    public void xacNhanDonDep() {
        System.out.println("\n--- XÁC NHẬN DỌN DẸP PHÒNG ---");
        System.out.print("Nhập số phòng cần xác nhận đã dọn xong: ");

        if (!sc.hasNextInt()) {
            System.out.println("[LỖI] Số phòng phải là số nguyên!");
            sc.nextLine();
            return;
        }
        int soPhong = sc.nextInt();
        sc.nextLine();

        PhongKhachSan phong = qlks.timPhong(soPhong);
        if (phong == null || !"DANG_DON".equalsIgnoreCase(phong.getTrangThai())) {
            System.out.println("[LỖI] Phòng không tồn tại hoặc không ở trạng thái chờ dọn!");
            return;
        }

        boolean kq = qlks.xacNhanDonXong(soPhong);
        if (kq) {
            System.out.println("[THÀNH CÔNG] Phòng " + soPhong + " đã được chuyển về trạng thái TRONG!");
        } else {
            System.out.println("[LỖI] Không thể cập nhật trạng thái phòng.");
        }
    }
}