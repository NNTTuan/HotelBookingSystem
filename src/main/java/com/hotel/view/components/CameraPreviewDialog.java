package com.hotel.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CameraPreviewDialog extends JDialog {
    private JLabel lblPreview;

    public CameraPreviewDialog(Frame owner) {
        super(owner, "Xem Trước Camera", true); // true = Modal Dialog (khóa màn hình chính khi đang mở)

        // 1. Cấu hình Cửa sổ Popup
        setLayout(new BorderLayout());
        setSize(640, 480);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // 2. Tạo Label chứa khung hình Camera
        lblPreview = new JLabel("Đang kết nối Camera...", SwingConstants.CENTER);
        add(lblPreview, BorderLayout.CENTER);
    }

    /**
     * Nhận khung hình BufferedImage từ CameraService và hiển thị lên màn hình
     */
    public void capNhatKhungHinh(BufferedImage frame) {
        if (frame != null) {
            lblPreview.setText(""); // Xóa chữ thông báo ban đầu
            lblPreview.setIcon(new ImageIcon(frame));
            lblPreview.repaint();
        }
    }
}