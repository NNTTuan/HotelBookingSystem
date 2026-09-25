package com.hotel;

import com.hotel.view.MainFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // 1. Nạp thư viện OpenCV Native trước khi khởi chạy ứng dụng
        try {
            nu.pattern.OpenCV.loadShared();
            System.out.println("Nạp thư viện OpenCV thành công!");
        } catch (UnsatisfiedLinkError | Exception e) {
            System.err.println("Lỗi nạp thư viện OpenCV Native: " + e.getMessage());
            System.err.println("Vui lòng kiểm tra lại dependency openpnp OpenCV trong pom.xml / build.gradle.");
        }

        // 2. Cài đặt Look and Feel giúp giao diện Swing đồng bộ với OS (Windows/Mac)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Không thể cài đặt System Look & Feel, sử dụng mặc định.");
        }

        // 3. Khởi chạy giao diện chính MainFrame trên Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}