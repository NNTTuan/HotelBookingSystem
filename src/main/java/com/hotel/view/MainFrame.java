package com.hotel.view;

import com.hotel.service.QuanLyKhachSan;
import com.hotel.util.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private QuanLyKhachSan qlkh;
    private CardLayout cardLayout;
    private JPanel mainContentPanel;

    // Hằng số định danh cho các thẻ Card
    public static final String CARD_CHECKIN = "CHECK_IN";
    public static final String CARD_CHECKOUT = "CHECK_OUT";
    public static final String CARD_QUAN_LY_PHONG = "QUAN_LY_PHONG";

    public MainFrame() {
        // Khởi tạo dữ liệu quản lý trung tâm
        this.qlkh = new QuanLyKhachSan();

        initUI();
    }

    /**
     * Dựng khung giao diện chính và tích hợp CardLayout
     */
    private void initUI() {
        setTitle("Hệ Thống Quản Lý Đặt Phòng Khách Sạn");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Tạo Thanh Điều Hướng (Navigation Bar) ở phía Tây (Bên trái)
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        navPanel.setBackground(new Color(240, 242, 245));

        JLabel lblMenu = new JLabel("DANH MỤC");
        lblMenu.setFont(SwingUtils.FONT_TITLE);
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        navPanel.add(lblMenu);
        navPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Nút chuyển màn hình Check-In
        JButton btnCheckIn = createNavButton("Tiếp Nhận Check-In");
        btnCheckIn.addActionListener(e -> showPanel(CARD_CHECKIN));
        navPanel.add(btnCheckIn);
        navPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Nút chuyển màn hình Check-Out
        JButton btnCheckOut = createNavButton("Trả Phòng & Hóa Đơn");
        btnCheckOut.addActionListener(e -> showPanel(CARD_CHECKOUT));
        navPanel.add(btnCheckOut);
        navPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Nút chuyển màn hình Quản Lý Phòng
        JButton btnQuanLyPhong = createNavButton("Quản Lý Phòng");
        btnQuanLyPhong.addActionListener(e -> showPanel(CARD_QUAN_LY_PHONG));
        navPanel.add(btnQuanLyPhong);

        // 2. Khởi tạo Khung chứa trung tâm dùng CardLayout
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);

        // Khởi tạo các Panel con và nạp vào CardLayout
        CheckInPanel checkInPanel = new CheckInPanel(qlkh);
        CheckOutPanel checkOutPanel = new CheckOutPanel(qlkh);
        QuanLyPhongPanel quanLyPhongPanel = new QuanLyPhongPanel(qlkh);

        mainContentPanel.add(checkInPanel, CARD_CHECKIN);
        mainContentPanel.add(checkOutPanel, CARD_CHECKOUT);
        mainContentPanel.add(quanLyPhongPanel, CARD_QUAN_LY_PHONG);

        // 3. Ghép các thành phần vào MainFrame
        add(navPanel, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);

        // Hiển thị màn hình mặc định ban đầu
        showPanel(CARD_CHECKIN);

        // Đồng bộ Font chữ giao diện
        SwingUtils.setCustomFont(this);
    }

    /**
     * Chuyển đổi giữa các màn hình Panel theo tên Card
     */
    public void showPanel(String name) {
        cardLayout.show(mainContentPanel, name);
    }

    /**
     * Helper tạo nút bấm điều hướng có kích thước chuẩn
     */
    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        return btn;
    }
}