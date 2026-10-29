package com.hotel.view.console;

import com.hotel.model.KhachHang;
import com.hotel.model.PhieuDatPhong;
import com.hotel.model.PhongKhachSan;
import com.hotel.service.QuanLyKhachSan;

import java.util.List;
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

    public void xemLichSuPhong() {
        System.out.println("\n--- TRA CỨU LỊCH SỬ KHÁCH THUÊ THEO PHÒNG ---");
        System.out.print("Nhập số phòng cần tra cứu lịch sử: ");

        if (!sc.hasNextInt()) {
            System.out.println("[LỖI] Số phòng phải là số nguyên!");
            sc.nextLine();
            return;
        }
        int soPhong = sc.nextInt();
        sc.nextLine();

        PhongKhachSan phong = qlks.timPhong(soPhong);
        if (phong == null) {
            System.out.println("[LỖI] Không tìm thấy phòng số " + soPhong + " trong hệ thống!");
            return;
        }

        List<PhieuDatPhong> dsLichSu = qlks.timLichSuDatPhongTheoSoPhong(soPhong);
        System.out.println("\nLịch sử lưu trú của Phòng " + soPhong + " (" + phong.getLoaiPhong() + " - " + phong.getTrangThai() + "):");
        if (dsLichSu.isEmpty()) {
            System.out.println("-> Phòng này chưa có lịch sử thuê nào trước đó.");
            return;
        }

        System.out.printf("%-15s | %-20s | %-12s | %-10s | %-10s\n", "Mã Phiếu", "Họ Tên Khách", "CCCD", "Số Ngày", "Trạng Thái");
        System.out.println("----------------------------------------------------------------------------------");
        for (PhieuDatPhong p : dsLichSu) {
            KhachHang k = p.getKhachHang();
            System.out.printf("%-15s | %-20s | %-12s | %-10d | %-10s\n",
                    p.getMaPhieu(),
                    (k != null ? k.getHoTen() : "N/A"),
                    (k != null ? k.getSoCCCD() : "N/A"),
                    p.getSoNgayThue(),
                    p.getTrangThai()
            );
        }
        System.out.println("----------------------------------------------------------------------------------");
    }
}