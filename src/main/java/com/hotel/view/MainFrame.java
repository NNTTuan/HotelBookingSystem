package com.hotel.view;

import com.hotel.service.QuanLyKhachSan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {
    private QuanLyKhachSan quanLyKhachSan;
    private QuanLyPhongPanel phongPanel;
    private CheckInPanel checkInPanel;
    private CheckOutPanel checkOutPanel;

    public MainFrame() {
        setTitle("Hệ Thống Quản Lý Khách Sạn");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        quanLyKhachSan = new QuanLyKhachSan();

        // Header ứng dụng
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(24, 43, 73));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ KHÁCH SẠN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        // TabbedPane hiện đại
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));

        phongPanel = new QuanLyPhongPanel(quanLyKhachSan);

        Runnable refreshAll = () -> {
            phongPanel.capNhatSoDoPhong();
            checkInPanel.loadDanhSachPhongTrong();
            checkOutPanel.loadDanhSachPhongDangO();
        };

        checkInPanel = new CheckInPanel(quanLyKhachSan, refreshAll);
        checkOutPanel = new CheckOutPanel(quanLyKhachSan, refreshAll);

        tabbedPane.addTab("Sơ Đồ Phòng (100 Phòng)", phongPanel);
        tabbedPane.addTab("Nhận Phòng (Check-In)", checkInPanel);
        tabbedPane.addTab("Trả Phòng (Check-Out)", checkOutPanel);

        tabbedPane.addChangeListener(e -> refreshAll.run());

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
}