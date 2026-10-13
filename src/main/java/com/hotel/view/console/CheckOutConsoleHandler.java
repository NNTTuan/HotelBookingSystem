package com.hotel.view.console;

import com.hotel.model.PhieuDatPhong;
import com.hotel.service.QuanLyKhachSan;

import java.util.Scanner;

public class CheckOutConsoleHandler {
    private final QuanLyKhachSan qlks;
    private final Scanner sc;

    public CheckOutConsoleHandler(QuanLyKhachSan qlks, Scanner sc) {
        this.qlks = qlks;
        this.sc = sc;
    }

    public void thucHienCheckOut() {
        System.out.println("\n--- THỰC HIỆN CHECK-OUT & TÍNH TIỀN HÓA ĐƠN ---");
        System.out.print("Nhập số CCCD của khách hàng đang ở: ");
        String cccd = sc.nextLine().trim();

        PhieuDatPhong phieu = qlks.timPhieuDangHoatDongTheoCCCD(cccd);
        if (phieu == null) {
            System.out.println("[LỖI] Không tìm thấy phiếu lưu trú hoạt động cho CCCD: " + cccd);
            return;
        }

        hienThiThongTinPhieu(phieu);
        int soNgayThucTe = nhapSoNgayThucTe(phieu.getSoNgayThue());

        phieu.setSoNgayThue(soNgayThucTe);
        double tongTien = phieu.tinhTongTienPhieu();

        hienThiHoaDon(tongTien);

        if (xacnhanThanhToan()) {
            boolean kq = qlks.checkOut(phieu.getPhong().getSoPhong(), soNgayThucTe);
            if (kq) {
                System.out.println("[THÀNH CÔNG] Thanh toán hoàn tất! Phòng đã chuyển sang trạng thái DANG_DON.");
            } else {
                System.out.println("[LỖI] Xử lý check-out thất bại.");
            }
        } else {
            System.out.println("[ĐÃ HỦY] Giao dịch check-out đã bị hủy.");
        }
    }

    private void hienThiThongTinPhieu(PhieuDatPhong phieu) {
        System.out.println("\n--- THÔNG TIN PHIẾU ĐẶT PHÒNG ---");
        System.out.println("Mã phiếu: " + phieu.getMaPhieu());
        System.out.println("Số phòng: " + (phieu.getPhong() != null ? phieu.getPhong().getSoPhong() : "N/A"));
        System.out.println("Họ tên khách: " + (phieu.getKhachHang() != null ? phieu.getKhachHang().getHoTen() : "N/A"));
        System.out.println("Số ngày thuê ban đầu: " + phieu.getSoNgayThue());
    }

    private int nhapSoNgayThucTe(int soNgayBanDau) {
        System.out.print("Nhập số ngày lưu trú thực tế: ");
        int soNgay = sc.hasNextInt() ? sc.nextInt() : soNgayBanDau;
        sc.nextLine();
        return soNgay;
    }

    private void hienThiHoaDon(double tongTien) {
        System.out.println("------------------------------------------------------------");
        System.out.printf("=> TỔNG THANH TOÁN (Gồm VAT & Ưu đãi): %,.2f VNĐ\n", tongTien);
        System.out.println("------------------------------------------------------------");
    }

    private boolean xacnhanThanhToan() {
        System.out.print("Xác nhận thanh toán và trả phòng? (y/n): ");
        String xacNhan = sc.nextLine().trim();
        return xacNhan.equalsIgnoreCase("y");
    }
}