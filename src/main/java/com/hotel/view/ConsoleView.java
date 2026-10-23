package com.hotel.view;

import com.hotel.service.QuanLyKhachSan;
import com.hotel.view.console.CheckInConsoleHandler;
import com.hotel.view.console.CheckOutConsoleHandler;
import com.hotel.view.console.PhongConsoleHandler;

import java.util.Scanner;

public class ConsoleView {
    private final QuanLyKhachSan qlks;
    private final Scanner sc;

    private final PhongConsoleHandler phongHandler;
    private final CheckInConsoleHandler checkInHandler;
    private final CheckOutConsoleHandler checkOutHandler;

    public ConsoleView() {
        this.qlks = new QuanLyKhachSan();
        this.sc = new Scanner(System.in);

        // Khởi tạo các handler và truyền service vào
        this.phongHandler = new PhongConsoleHandler(qlks);
        this.checkInHandler = new CheckInConsoleHandler(qlks, sc);
        this.checkOutHandler = new CheckOutConsoleHandler(qlks, sc);
    }

    public void start() {
        int option;
        do {
            hienThiMenu();
            option = sc.hasNextInt() ? sc.nextInt() : -1;
            sc.nextLine();

            switch (option) {
                case 1: {
                    phongHandler.hienThiDanhSachPhong();
                    break;
                }
                case 2: {
                    checkInHandler.thucHienCheckIn();
                    break;
                }
                case 3:
                    checkOutHandler.thucHienCheckOut();
                    break;
                case 4: {
                    phongHandler.xacNhanDonDep();
                    break;
                }
                case 0: {
                    System.out.println("\nCảm ơn bạn đã sử dụng phần mềm Quản lý Khách sạn!");
                    break;
                }
                default: {
                    System.out.println("\n[LỖI] Lựa chọn không hợp lệ! Vui lòng chọn số từ 0 đến 4.");
                }
            }
        } while (option != 0);
    }

    private void hienThiMenu() {
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
    }
}