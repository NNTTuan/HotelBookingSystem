package com.hotel.view;

import com.hotel.model.KhachHang;
import com.hotel.service.QuanLyKhachSan;
import com.hotel.view.components.CameraPreviewDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CheckInPanel extends JPanel {
    private JTextField txtHoTen;
    private JTextField txtCCCD;
    private JTextField txtSdt;
    private JComboBox<Integer> cboSoPhong;
    private JLabel lblAnhMat;
    private String currentImagePath = "";

    private QuanLyKhachSan quanLyKhachSan;
    private Runnable onSuccessCallback;

    public CheckInPanel(QuanLyKhachSan quanLyKhachSan, Runnable onSuccessCallback) {
        this.quanLyKhachSan = quanLyKhachSan;
        this.onSuccessCallback = onSuccessCallback;
        setLayout(new GridBagLayout());

        // Tạo Card Container
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
                new EmptyBorder(25, 35, 25, 35)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề form
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel lblHeader = new JLabel("THÔNG TIN CHECK-IN KHÁCH HÀNG", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHeader.setForeground(new Color(24, 43, 73));
        cardPanel.add(lblHeader, gbc);

        gbc.gridwidth = 1;

        // Form Fields
        addFormField(cardPanel, "Họ và Tên khách:", txtHoTen = new JTextField(20), gbc, 1);
        addFormField(cardPanel, "Số CCCD / Hộ chiếu:", txtCCCD = new JTextField(20), gbc, 2);
        addFormField(cardPanel, "Số điện thoại:", txtSdt = new JTextField(20), gbc, 3);

        gbc.gridx = 0; gbc.gridy = 4;
        cardPanel.add(new JLabel("Chọn phòng nhận:"), gbc);
        gbc.gridx = 1;
        cboSoPhong = new JComboBox<>();
        cardPanel.add(cboSoPhong, gbc);

        // Nút Webcam
        gbc.gridx = 0; gbc.gridy = 5;
        JButton btnChupAnh = new JButton("📷 Mở Webcam & Chụp Mặt");
        cardPanel.add(btnChupAnh, gbc);

        gbc.gridx = 1;
        lblAnhMat = new JLabel("Chưa có ảnh chụp");
        lblAnhMat.setForeground(Color.GRAY);
        cardPanel.add(lblAnhMat, gbc);

        // Nút Submit
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        JButton btnCheckIn = new JButton("XÁC NHẬN CHECK-IN");
        btnCheckIn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCheckIn.setPreferredSize(new Dimension(200, 40));
        btnCheckIn.setBackground(new Color(30, 136, 229));
        btnCheckIn.setForeground(Color.WHITE);
        btnCheckIn.setFocusPainted(false);
        btnCheckIn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cardPanel.add(btnCheckIn, gbc);

        add(cardPanel);

        // Event Listeners
        btnChupAnh.addActionListener(e -> {
            Window parentWindow = SwingUtilities.getWindowAncestor(this);
            CameraPreviewDialog dialog = new CameraPreviewDialog((Frame) parentWindow, true);
            dialog.setVisible(true);

            String imageCaptured = dialog.getCapturedImagePath();
            if (imageCaptured != null && !imageCaptured.isEmpty()) {
                currentImagePath = imageCaptured;
                lblAnhMat.setText("Đã lưu ảnh thành công!");
                lblAnhMat.setForeground(new Color(46, 125, 50));
            }
        });

        btnCheckIn.addActionListener(e -> xuLyCheckIn());
        loadDanhSachPhongTrong();
    }

    private void addFormField(JPanel panel, String labelText, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(field, gbc);
    }

    public void loadDanhSachPhongTrong() {
        cboSoPhong.removeAllItems();
        quanLyKhachSan.getDanhSachPhong().stream()
                .filter(p -> "TRONG".equals(p.getTrangThai()))
                .forEach(p -> cboSoPhong.addItem(p.getSoPhong()));
    }

    private void xuLyCheckIn() {
        String hoTen = txtHoTen.getText().trim();
        String cccd = txtCCCD.getText().trim();
        String sdt = txtSdt.getText().trim();
        Integer soPhong = (Integer) cboSoPhong.getSelectedItem();

        if (hoTen.isEmpty() || cccd.isEmpty() || soPhong == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin khách và chọn phòng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        KhachHang khach = new KhachHang(hoTen, cccd, sdt, currentImagePath);
        boolean thanhCong = quanLyKhachSan.checkIn(soPhong, khach);

        if (thanhCong) {
            JOptionPane.showMessageDialog(this, "Check-in thành công cho phòng " + soPhong, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            txtHoTen.setText("");
            txtCCCD.setText("");
            txtSdt.setText("");
            lblAnhMat.setText("Chưa có ảnh chụp");
            lblAnhMat.setForeground(Color.GRAY);
            currentImagePath = "";

            loadDanhSachPhongTrong();
            if (onSuccessCallback != null) onSuccessCallback.run();
        } else {
            JOptionPane.showMessageDialog(this, "Thao tác Check-in thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}