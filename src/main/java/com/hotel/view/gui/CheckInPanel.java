package com.hotel.view.gui;

import com.hotel.model.KhachHang;
import com.hotel.model.PhongKhachSan;
import com.hotel.service.QuanLyKhachSan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class CheckInPanel extends JPanel {
    private QuanLyKhachSan quanLyKhachSan;
    private Runnable onCheckInSuccess;

    private JComboBox<String> cbPhongTrong;
    private JTextField txtHoTen, txtCCCD, txtSDT, txtSoNgayThue;
    private JButton btnCheckIn, btnMoCamera;
    private JLabel lblTrangThaiAnh;
    private String currentAnhPath = "none";

    public CheckInPanel(QuanLyKhachSan quanLyKhachSan, Runnable onCheckInSuccess) {
        this.quanLyKhachSan = quanLyKhachSan;
        this.onCheckInSuccess = onCheckInSuccess;

        setLayout(new GridBagLayout()); // Căn giữa toàn bộ panel
        setBackground(new Color(245, 247, 250)); // Màu nền xám nhạt hiện đại

        // Panel card trắng bọc nội dung ở giữa
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
                new EmptyBorder(30, 40, 30, 40)
        ));

        // Tiêu đề card
        JLabel lblTitle = new JLabel("THÔNG TIN CHECK-IN KHÁCH HÀNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(new Color(24, 43, 73));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblTitle);
        cardPanel.add(Box.createVerticalStrut(25));

        // Form nhập liệu dạng GridBag cho các trường thông tin
        JPanel formGrid = new JPanel(new GridBagLayout());
        formGrid.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Họ và tên
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        JLabel lbl1 = new JLabel("Họ và Tên khách:");
        lbl1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formGrid.add(lbl1, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        txtHoTen = new JTextField(20);
        txtHoTen.setPreferredSize(new Dimension(250, 30));
        formGrid.add(txtHoTen, gbc);

        // 2. CCCD
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        JLabel lbl2 = new JLabel("Số CCCD / Hộ chiếu:");
        lbl2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formGrid.add(lbl2, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        txtCCCD = new JTextField(20);
        formGrid.add(txtCCCD, gbc);

        // 3. Số điện thoại
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        JLabel lbl3 = new JLabel("Số điện thoại:");
        lbl3.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formGrid.add(lbl3, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.7;
        txtSDT = new JTextField(20);
        formGrid.add(txtSDT, gbc);

        // 4. Số ngày thuê
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        JLabel lbl4 = new JLabel("Số ngày thuê:");
        lbl4.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formGrid.add(lbl4, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.7;
        txtSoNgayThue = new JTextField("1", 20);
        formGrid.add(txtSoNgayThue, gbc);

        // 5. Chọn phòng
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        JLabel lbl5 = new JLabel("Chọn phòng nhận:");
        lbl5.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formGrid.add(lbl5, gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 0.7;
        cbPhongTrong = new JComboBox<>();
        cbPhongTrong.setPreferredSize(new Dimension(250, 30));
        formGrid.add(cbPhongTrong, gbc);

        cardPanel.add(formGrid);
        cardPanel.add(Box.createVerticalStrut(15));

        // Phần nút bấm Camera & Trạng thái ảnh
        JPanel camPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        camPanel.setBackground(Color.WHITE);

        btnMoCamera = new JButton("📷 Mở Webcam & Chụp Mặt");
        btnMoCamera.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnMoCamera.setFocusPainted(false);
        camPanel.add(btnMoCamera);

        lblTrangThaiAnh = new JLabel("Chưa có ảnh chụp");
        lblTrangThaiAnh.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblTrangThaiAnh.setForeground(Color.GRAY);
        camPanel.add(lblTrangThaiAnh);

        cardPanel.add(camPanel);
        cardPanel.add(Box.createVerticalStrut(20));

        // Nút Xác nhận Check-In màu xanh nổi bật
        btnCheckIn = new JButton("XÁC NHẬN CHECK-IN");
        btnCheckIn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCheckIn.setForeground(Color.WHITE);
        btnCheckIn.setBackground(new Color(13, 110, 253));
        btnCheckIn.setFocusPainted(false);
        btnCheckIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCheckIn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cardPanel.add(btnCheckIn);

        // Thêm card vào giữa màn hình chính
        add(cardPanel, new GridBagConstraints());

        // Load dữ liệu ban đầu
        loadDanhSachPhongTrong();

        // Bắt sự kiện
        btnMoCamera.addActionListener(e -> moGiaoDienChupAnh());
        btnCheckIn.addActionListener(e -> thucHienCheckIn());
    }

    public void loadDanhSachPhongTrong() {
        cbPhongTrong.removeAllItems();
        if (quanLyKhachSan != null) {
            for (PhongKhachSan p : quanLyKhachSan.getDanhSachPhong()) {
                if ("TRONG".equalsIgnoreCase(p.getTrangThai())) {
                    cbPhongTrong.addItem(String.valueOf(p.getSoPhong()));
                }
            }
        }
    }

    private void moGiaoDienChupAnh() {
        File dir = new File("data/images");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String cccd = txtCCCD.getText().trim();
        if (cccd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số CCCD trước khi chụp ảnh!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtCCCD.requestFocus();
            return;
        }

        String fileName = "data/images/khach_" + cccd + ".jpg";

        // Tạo cửa sổ Dialog chứa Webcam
        JDialog camDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chụp ảnh khuôn mặt khách hàng", true);
        camDialog.setSize(500, 500);
        camDialog.setLocationRelativeTo(this);
        camDialog.setLayout(new BorderLayout());

        // Sử dụng thư viện Webcam Capture (com.github.sarxos.webcam)
        com.github.sarxos.webcam.Webcam webcam = com.github.sarxos.webcam.Webcam.getDefault();
        if (webcam == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy Webcam nào trên thiết bị của bạn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Đặt kích thước hiển thị cho webcam (VD: VGA 640x480 hoặc mặc định)
        webcam.setViewSize(com.github.sarxos.webcam.WebcamResolution.VGA.getSize());
        com.github.sarxos.webcam.WebcamPanel webcamPanel = new com.github.sarxos.webcam.WebcamPanel(webcam);
        webcamPanel.setFPSDisplayed(true);
        webcamPanel.setMirrored(true);

        JButton btnChupNgay = new JButton("📸 Chụp & Lưu Ảnh");
        btnChupNgay.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnChupNgay.setBackground(new Color(13, 110, 253));
        btnChupNgay.setForeground(Color.WHITE);

        btnChupNgay.addActionListener(e -> {
            try {
                // Lấy hình ảnh hiện tại từ webcam
                java.awt.image.BufferedImage image = webcam.getImage();
                if (image != null) {
                    // Lưu ảnh ra file JPEG
                    javax.imageio.ImageIO.write(image, "JPG", new File(fileName));

                    currentAnhPath = fileName;
                    lblTrangThaiAnh.setText("Đã chụp: khach_" + cccd + ".jpg");
                    lblTrangThaiAnh.setForeground(new Color(40, 140, 60));

                    webcam.close(); // Tắt webcam
                    camDialog.dispose(); // Đóng cửa sổ
                    JOptionPane.showMessageDialog(this, "Chụp ảnh khuôn mặt thành công!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(camDialog, "Lỗi khi lưu ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Khi đóng cửa sổ dialog thì nhớ tắt webcam để giải phóng phần cứng
        camDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                webcam.close();
            }
        });

        camDialog.add(webcamPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(btnChupNgay);
        camDialog.add(bottomPanel, BorderLayout.SOUTH);

        camDialog.setVisible(true);
    }

    private void thucHienCheckIn() {
        if (cbPhongTrong.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Không có phòng trống nào khả dụng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int soPhong = Integer.parseInt(cbPhongTrong.getSelectedItem().toString());
            String hoTen = txtHoTen.getText().trim();
            String cccd = txtCCCD.getText().trim();
            String sdt = txtSDT.getText().trim();
            int soNgay = Integer.parseInt(txtSoNgayThue.getText().trim());

            if (hoTen.isEmpty() || cccd.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ họ tên và CCCD!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            KhachHang khach = new KhachHang(hoTen, cccd, sdt, currentAnhPath);
            boolean success = quanLyKhachSan.checkIn(soPhong, khach, soNgay);

            if (success) {
                JOptionPane.showMessageDialog(this, "Check-in thành công phòng " + soPhong + " (" + soNgay + " ngày)!");
                // Clear form
                txtHoTen.setText("");
                txtCCCD.setText("");
                txtSDT.setText("");
                txtSoNgayThue.setText("1");
                currentAnhPath = "none";
                lblTrangThaiAnh.setText("Chưa có ảnh chụp");

                if (onCheckInSuccess != null) {
                    onCheckInSuccess.run();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Check-in thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số ngày thuê phải là kiểu số nguyên hợp lệ!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
        }
    }
}