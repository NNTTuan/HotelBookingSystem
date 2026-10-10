package com.hotel;

import com.hotel.view.ConsoleView; // Thêm dòng này vào
import com.hotel.view.MainFrame;
import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("==========================================");
            System.out.println("           CHỌN LOẠI ỨNG DỤNG KHÁCH SẠN    ");
            System.out.println("==========================================");
            System.out.println("1. Desktop Application (Java Swing GUI)");
            System.out.println("2. Console Application (Dòng lệnh)");
            System.out.print("Chọn kiểu app bạn muốn hiển thị (1 hoặc 2): ");

            int choice = sc.hasNextInt() ? sc.nextInt() : 0;
            System.out.println("==========================================");

            switch (choice) {
                case 1:
                    // Khởi chạy giao diện Swing GUI
                    try {
                        UIManager.setLookAndFeel(new FlatIntelliJLaf());
                        UIManager.put("Button.arc", 10);
                        UIManager.put("Component.arc", 8);
                        UIManager.put("TextComponent.arc", 8);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SwingUtilities.invokeLater(() -> {
                        MainFrame frame = new MainFrame();
                        frame.setVisible(true);
                    });
                    System.out.println("=> Đang khởi động giao diện Swing GUI...");
                    return;

                case 2:
                    // Khởi chạy phiên bản Console thông qua lớp ConsoleView độc lập
                    System.out.println("=> Đang khởi chạy phiên bản Console Application...\n");
                    ConsoleView consoleView = new ConsoleView();
                    consoleView.start(); // Gọi phương thức chạy menu dòng lệnh
                    return;

                default:
                    System.out.println("Lựa chọn của bạn không tồn tại hoặc không hợp lệ!");
                    System.out.println("Vui lòng chỉ nhập số 1 hoặc số 2.\n");
            }
        } while (true);
    }
}