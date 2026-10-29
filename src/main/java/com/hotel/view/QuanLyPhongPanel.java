package com.hotel.view;

import com.hotel.model.KhachHang;
import com.hotel.model.PhieuDatPhong;
import com.hotel.model.PhongKhachSan;
import com.hotel.service.QuanLyKhachSan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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

        // Khung lưới hiển thị danh sách phòng
        gridPanel = new JPanel(new GridLayout(5, 4, 6, 6));
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
            lblSoPhong.setFont(new Font("Segoe UI", Font.BOLD, 15));

            JLabel lblLoai = new JLabel(p.getLoaiPhong(), SwingConstants.CENTER);
            lblLoai.setFont(new Font("Segoe UI", Font.PLAIN, 13));

            btn.add(lblSoPhong, BorderLayout.CENTER);
            btn.add(lblLoai, BorderLayout.SOUTH);

            switch (p.getTrangThai()) {
                case "TRONG":
                    btn.setBackground(new Color(220, 245, 225));
                    lblSoPhong.setForeground(new Color(46, 125, 50));
                    lblLoai.setForeground(new Color(46, 125, 50));
                    btn.setBorder(BorderFactory.createLineBorder(new Color(165, 214, 167), 3));
                    break;
                case "DANG_O":
                    btn.setBackground(new Color(255, 225, 225));
                    lblSoPhong.setForeground(new Color(198, 40, 40));
                    lblLoai.setForeground(new Color(198, 40, 40));
                    btn.setBorder(BorderFactory.createLineBorder(new Color(239, 154, 154), 3));
                    break;
                case "DANG_DON":
                    btn.setBackground(new Color(255, 243, 205));
                    lblSoPhong.setForeground(new Color(230, 81, 0));
                    lblLoai.setForeground(new Color(230, 81, 0));
                    btn.setBorder(BorderFactory.createLineBorder(new Color(255, 224, 130), 3));
                    break;
            }

            // Bắt sự kiện khi bấm vào từng phòng -> Hiển thị Dialog chi tiết & Lịch sử
            btn.addActionListener(e -> {
                JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi Tiết & Lịch Sử Thuê - Phòng " + p.getSoPhong(), true);
                dialog.setSize(650, 420);
                dialog.setLocationRelativeTo(this);
                dialog.setLayout(new BorderLayout(10, 10));

                // 1. Khung thông tin tổng quan phòng
                JPanel topInfo = new JPanel(new GridLayout(2, 2, 8, 8));
                topInfo.setBorder(BorderFactory.createTitledBorder("Thông tin phòng"));
                topInfo.add(new JLabel("Loại phòng: " + p.getLoaiPhong()));
                topInfo.add(new JLabel("Tầng: " + p.getTang()));
                topInfo.add(new JLabel("Giá cơ bản: " + String.format("%,.0f VNĐ/ngày", p.getGiaCoBan())));
                topInfo.add(new JLabel("Trạng thái hiện tại: " + p.getTrangThai()));
                dialog.add(topInfo, BorderLayout.NORTH);

                // 2. Bảng lịch sử khách hàng đã từng ở phòng này
                String[] cols = {"Mã Phiếu", "Họ Tên Khách", "CCCD/Passport", "Số Ngày", "Trạng Thái"};
                DefaultTableModel historyModel = new DefaultTableModel(cols, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // Không cho phép chỉnh sửa bảng lịch sử trực tiếp
                    }
                };

                List<PhieuDatPhong> dsLichSu = quanLyKhachSan.timLichSuDatPhongTheoSoPhong(p.getSoPhong());
                for (PhieuDatPhong phieu : dsLichSu) {
                    KhachHang kh = phieu.getKhachHang();
                    historyModel.addRow(new Object[]{
                            phieu.getMaPhieu(),
                            kh != null ? kh.getHoTen() : "N/A",
                            kh != null ? kh.getSoCCCD() : "N/A",
                            phieu.getSoNgayThue(),
                            phieu.getTrangThai()
                    });
                }

                JTable tableHistory = new JTable(historyModel);
                tableHistory.setRowHeight(22);
                JScrollPane scrollPane = new JScrollPane(tableHistory);
                scrollPane.setBorder(BorderFactory.createTitledBorder("Lịch sử lưu trú của phòng"));
                dialog.add(scrollPane, BorderLayout.CENTER);

                // 3. Khung nút chức năng ở góc dưới
                JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

                // Nếu phòng đang ở trạng thái DANG_DON -> Thêm nút xác nhận dọn phòng trực tiếp
                if ("DANG_DON".equalsIgnoreCase(p.getTrangThai())) {
                    JButton btnDonXong = new JButton("Xác nhận đã dọn xong");
                    btnDonXong.setBackground(new Color(40, 140, 60));
                    btnDonXong.setForeground(Color.WHITE);
                    btnDonXong.setFocusPainted(false);
                    btnDonXong.addActionListener(ex -> {
                        quanLyKhachSan.xacNhanDonXong(p.getSoPhong());
                        capNhatSoDoPhong();
                        dialog.dispose();
                        JOptionPane.showMessageDialog(this, "Đã chuyển phòng " + p.getSoPhong() + " về trạng thái TRONG!");
                    });
                    bottomPanel.add(btnDonXong);
                }

                JButton btnClose = new JButton("Đóng");
                btnClose.addActionListener(ex -> dialog.dispose());
                bottomPanel.add(btnClose);

                dialog.add(bottomPanel, BorderLayout.SOUTH);
                dialog.setVisible(true);
            });

            gridPanel.add(btn);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }
}