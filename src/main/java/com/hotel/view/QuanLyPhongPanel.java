package com.hotel.view;

import com.hotel.model.PhongKhachSan;
import com.hotel.service.QuanLyKhachSan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class QuanLyPhongPanel extends JPanel {
    private QuanLyKhachSan quanLyKhachSan;
    private JPanel gridPanel;

    public QuanLyPhongPanel(QuanLyKhachSan quanLyKhachSan) {
        this.quanLyKhachSan = quanLyKhachSan;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Thanh chú thích trạng thái
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        legendPanel.add(taoItemChuThich("Trống", new Color(220, 245, 225), new Color(46, 125, 50)));
        legendPanel.add(taoItemChuThich("Đang Ở", new Color(255, 225, 225), new Color(198, 40, 40)));
        legendPanel.add(taoItemChuThich("Chờ Dọn Dẹp", new Color(255, 243, 205), new Color(230, 81, 0)));
        add(legendPanel, BorderLayout.NORTH);

        // Khung lưới 10x10 hiển thị 100 phòng
        gridPanel = new JPanel(new GridLayout(10, 10, 6, 6));
        add(gridPanel, BorderLayout.CENTER);

        capNhatSoDoPhong();
    }

    private JPanel taoItemChuThich(String text, Color bg, Color border) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        JLabel box = new JLabel("  ");
        box.setOpaque(true);
        box.setBackground(bg);
        box.setBorder(BorderFactory.createLineBorder(border, 1));
        box.setPreferredSize(new Dimension(18, 18));

        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        panel.add(box);
        panel.add(lbl);
        return panel;
    }

    public void capNhatSoDoPhong() {
        gridPanel.removeAll();
        List<PhongKhachSan> dsPhong = quanLyKhachSan.getDanhSachPhong();

        for (PhongKhachSan p : dsPhong) {
            JButton btn = new JButton();
            btn.setLayout(new BorderLayout());
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            JLabel lblSoPhong = new JLabel("P." + p.getSoPhong(), SwingConstants.CENTER);
            lblSoPhong.setFont(new Font("Segoe UI", Font.BOLD, 12));

            JLabel lblLoai = new JLabel(p.getLoaiPhong(), SwingConstants.CENTER);
            lblLoai.setFont(new Font("Segoe UI", Font.PLAIN, 10));

            btn.add(lblSoPhong, BorderLayout.CENTER);
            btn.add(lblLoai, BorderLayout.SOUTH);

            switch (p.getTrangThai()) {
                case "TRONG":
                    btn.setBackground(new Color(220, 245, 225));
                    lblSoPhong.setForeground(new Color(46, 125, 50));
                    lblLoai.setForeground(new Color(46, 125, 50));
                    btn.setBorder(BorderFactory.createLineBorder(new Color(165, 214, 167), 1));
                    break;
                case "DANG_O":
                    btn.setBackground(new Color(255, 225, 225));
                    lblSoPhong.setForeground(new Color(198, 40, 40));
                    lblLoai.setForeground(new Color(198, 40, 40));
                    btn.setBorder(BorderFactory.createLineBorder(new Color(239, 154, 154), 1));
                    break;
                case "DANG_DON":
                    btn.setBackground(new Color(255, 243, 205));
                    lblSoPhong.setForeground(new Color(230, 81, 0));
                    lblLoai.setForeground(new Color(230, 81, 0));
                    btn.setBorder(BorderFactory.createLineBorder(new Color(255, 224, 130), 1));
                    break;
            }

            btn.addActionListener(e -> {
                if ("DANG_DON".equals(p.getTrangThai())) {
                    int confirm = JOptionPane.showConfirmDialog(
                            this,
                            "Xác nhận phòng " + p.getSoPhong() + " đã dọn dẹp xong?",
                            "Xác nhận dọn phòng",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        quanLyKhachSan.xacNhanDonXong(p.getSoPhong());
                        capNhatSoDoPhong();
                    }
                }
            });

            gridPanel.add(btn);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }
}