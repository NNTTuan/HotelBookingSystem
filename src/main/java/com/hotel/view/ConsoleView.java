package com.hotel.view;

import com.hotel.model.KhachHang;
import com.hotel.model.PhieuDatPhong;
import com.hotel.model.PhongKhachSan;
import com.hotel.service.QuanLyKhachSan;

import java.util.Scanner;

public class ConsoleView {
    private QuanLyKhachSan qlks;
    private Scanner sc;

    public ConsoleView() {
        this.qlks = new QuanLyKhachSan();
        this.sc = new Scanner(System.in);
    }

    /**
     * Khởi chạy vòng lặp Menu Console chính
     */
    public void start() {
        int option;
        do {
            System.out.println("\n==================================================");
            System.out.println("       HỆ THỐNG QUẢN LÝ KHÁCH SẠN (CONSOLE)        ");
            System.out.println("==================================================");
            System.out.println("1. Xem danh sách & Trạng thái 20 phòng");
            System.out.println("2. Thực hiện Check-in nhận phòng");
            System.out.println("3. Thực hiện Check-out & Tính tiền hóa đơn");
            System.out.println("4. Xác nhận dọn dẹp phòng (DANG_DON -> TRONG)");
            System.out.println("0. Thoát chương trình");
            System.out.println("==================================================");
            System.out.print("Nhập lựa chọn của bạn (0-4): ");

            option = sc.hasNextInt() ? sc.nextInt() : -1;
            sc.nextLine(); // Đọc bỏ ký tự xuống dòng thừa

            switch (option) {
                case 1:
                    hienThiDanhSachPhong();
                    break;
                case 2:
                    thucHienCheckIn();
                    break;
                case 3:
                    thucHienCheckOut();
                    break;
                case 4:
                    xacNhanDonDep();
                    break;
                case 0:
                    System.out.println("\nCảm ơn bạn đã sử dụng phần mềm Quản lý Khách sạn!");
                    break;
                default:
                    System.out.println("\n[LỖI] Lựa chọn không hợp lệ! Vui lòng chọn số từ 0 đến 4.");
            }
        } while (option != 0);
    }

    /**
     * Chức năng 1: Hiển thị danh sách 20 phòng từ file phong.txt
     */
    private void hienThiDanhSachPhong() {
        System.out.println("\n--- DANH SÁCH 20 PHÒNG KHÁCH SẠN ---");
        System.out.printf("%-10s | %-8s | %-15s | %-15s\n", "Số Phòng", "Tầng", "Loại Phòng", "Trạng Thái");
        System.out.println("------------------------------------------------------------");

        for (PhongKhachSan p : qlks.getDanhSachPhong()) {
            System.out.printf("%-10d | %-8d | %-15s | %-15s\n",
                    p.getSoPhong(), p.getTang(), p.getLoaiPhong(), p.getTrangThai());
        }
        System.out.println("------------------------------------------------------------");
    }

    /**
     * Chức năng 2: Check-in nhận phòng
     */
    private void thucHienCheckIn() {
        System.out.println("\n--- THỰC HIỆN CHECK-IN NHẬN PHÒNG ---");
        System.out.print("Nhập số phòng muốn thuê (vd: 101, 201, 301...): ");

        if (!sc.hasNextInt()) {
            System.out.println("[LỖI] Số phòng phải là một số nguyên!");
            sc.nextLine();
            return;
        }
        int soPhong = sc.nextInt();
        sc.nextLine();

        PhongKhachSan phong = qlks.timPhong(soPhong);
        if (phong == null) {
            System.out.println("[LỖI] Không tìm thấy số phòng này trong hệ thống!");
            return;
        }
        if (!"TRONG".equalsIgnoreCase(phong.getTrangThai())) {
            System.out.println("[LỖI] Phòng này không ở trạng thái TRONG (Hiện tại: " + phong.getTrangThai() + ")!");
            return;
        }

        System.out.print("Nhập họ tên khách hàng: ");
        String hoTen = sc.nextLine();
        System.out.print("Nhập số CCCD/Passport: ");
        String cccd = sc.nextLine();
        System.out.print("Nhập số điện thoại: ");
        String sdt = sc.nextLine();

        // Vì bản Console không dùng Webcam, ta truyền chuỗi trống hoặc "none" vào đường dẫn ảnh
        KhachHang khach = new KhachHang(hoTen, cccd, sdt, "none");

        boolean thanhCong = qlks.checkIn(soPhong, khach);
        if (thanhCong) {
            System.out.println("[THÀNH CÔNG] Check-in thành công cho phòng " + soPhong + "!");
        } else {
            System.out.println("[THẤT BẠI] Không thể thực hiện check-in. Vui lòng kiểm tra lại dữ liệu!");
        }
    }

    /**
     * Chức năng 3: Check-out và hạch toán tài chính đa hình
     */
    private void thucHienCheckOut() {
        System.out.println("\n--- THỰC HIỆN CHECK-OUT & TÍNH TIỀN HÓA ĐƠN ---");
        System.out.print("Nhập số CCCD của khách hàng đang ở: ");
        String cccd = sc.nextLine().trim();

        PhieuDatPhong phieu = qlks.timPhieuDangHoatDongTheoCCCD(cccd);
        if (phieu == null) {
            System.out.println("[LỖI] Không tìm thấy phiếu lưu trú hoạt động cho số CCCD: " + cccd);
            return;
        }

        System.out.println("\n--- THÔNG TIN PHIẾU ĐẶT PHÒNG ---");
        System.out.println("Mã phiếu: " + phieu.getMaPhieu());
        System.out.println("Số phòng: " + (phieu.getPhong() != null ? phieu.getPhong().getSoPhong() : "N/A"));
        System.out.println("Họ tên khách: " + (phieu.getKhachHang() != null ? phieu.getKhachHang().getHoTen() : "N/A"));
        System.out.println("Số ngày thuê ban đầu: " + phieu.getSoNgayThue());

        System.out.print("Nhập số ngày lưu trú thực tế: ");
        int soNgayThucTe = sc.hasNextInt() ? sc.nextInt() : phieu.getSoNgayThue();
        sc.nextLine();

        // Gọi hàm tính tổng tiền từ lớp PhieuDatPhong / PhongKhachSan (hỗ trợ đa hình OOP)
        if (phieu.getPhong() != null) {
            // Cập nhật tạm số ngày để tính chính xác
            phieu.setSoNgayThue(soNgayThucTe);
            double tongTien = phieu.tinhTongTienPhieu();

            System.out.println("------------------------------------------------------------");
            System.out.printf("=> TỔNG THANH TOÁN (Gồm VAT & Ưu đãi): %,.2f VNĐ\n", tongTien);
            System.out.println("------------------------------------------------------------");

            System.out.print("Xác nhận thanh toán và trả phòng? (y/n): ");
            String xacNhan = sc.nextLine().trim();
            if (xacNhan.equalsIgnoreCase("y")) {
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
    }

    /**
     * Chức năng 4: Xác nhận dọn dẹp phòng xong (DANG_DON -> TRONG)
     */
    private void xacNhanDonDep() {
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
        if (phong == null) {
            System.out.println("[LỖI] Không tìm thấy phòng số " + soPhong + "!");
            return;
        }

        if (!"DANG_DON".equalsIgnoreCase(phong.getTrangThai())) {
            System.out.println("[LỖI] Phòng này không ở trạng thái chờ dọn (Hiện tại: " + phong.getTrangThai() + ")!");
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