package com.hotel.view;

import com.hotel.model.PhongKhachSan;
import com.hotel.model.PhongVIP;
import com.hotel.service.QuanLyKhachSan;
import com.hotel.util.SwingUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QuanLyPhongPanel extends JPanel {
    private QuanLyKhachSan qlkh;
    private JTable tablePhong;
    private DefaultTableModel tableModel;

    public QuanLyPhongPanel(QuanLyKhachSan qlkh) {
        this.qlkh = qlkh;
        initUI();
        capNhatDanhSachPhong();
    }

    /**
     * Dựng giao diện bảng danh sách phòng
     */
    private void initUI() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Tiêu đề
        JLabel lblTitle = new JLabel("DANH SÁCH PHÒNG KHÁCH SẠN", SwingConstants.CENTER);
        lblTitle.setFont(SwingUtils.FONT_TITLE);
        add(lblTitle, BorderLayout.NORTH);

        // 2. Bảng JTable hiển thị thông tin phòng
        String[] columnNames = {"Mã Phòng", "Loại Phòng", "Giá Gốc (VNĐ/Ngày)", "Phí Dịch Vụ", "Giảm Giá"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Khóa không cho người dùng sửa trực tiếp trên bảng
            }
        };

        tablePhong = new JTable(tableModel);
        tablePhong.setRowHeight(28);
        tablePhong.getTableHeader().setFont(SwingUtils.FONT_BOLD);

        JScrollPane scrollPane = new JScrollPane(tablePhong);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Thông Tin Chi Tiết Các Phòng"));
        add(scrollPane, BorderLayout.CENTER);

        // 3. Thanh nút bấm điều khiển
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnCapNhat = new JButton("Làm Mới Danh Sách");
        btnCapNhat.addActionListener(e -> capNhatDanhSachPhong());

        bottomPanel.add(btnCapNhat);
        add(bottomPanel, BorderLayout.SOUTH);

        // Đồng bộ Font chữ cho toàn bộ Panel
        SwingUtils.setCustomFont(this);
    }

    /**
     * Lấy danh sách phòng từ QuanLyKhachSan và đổ dữ liệu lên JTable
     */
    public void capNhatDanhSachPhong() {
        // Xóa dữ liệu cũ trên bảng
        tableModel.setRowCount(0);

        if (qlkh == null || qlkh.getDsPhong() == null) return;

        // Lặp qua danh sách phòng trong QuanLyKhachSan
        for (PhongKhachSan p : qlkh.getDsPhong()) {
            String loaiPhong = p.getClass().getSimpleName();
            String giaGoc = String.format("%,.0f VNĐ", p.getGiaGoc());

            String phiDichVu = "-";
            String giamGia = "-";

            // Áp dụng Đa hình (Polymorphism): Kiểm tra nếu là Phòng VIP thì tính phí dịch vụ & giảm giá
            if (p instanceof PhongVIP) {
                PhongVIP vip = (PhongVIP) p;
                phiDichVu = String.format("%,.0f VNĐ", vip.tinhPhiDichVu());
                giamGia = String.format("%,.0f VNĐ", vip.tinhGiamGia());
            }

            Object[] rowData = {
                    p.getMaPhong(),
                    loaiPhong,
                    giaGoc,
                    phiDichVu,
                    giamGia
            };
            tableModel.addRow(rowData);
        }
    }
}