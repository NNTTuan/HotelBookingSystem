package com.hotel.view;

import com.hotel.model.KhachHang;
import com.hotel.model.PhieuDatPhong;
import com.hotel.model.PhongKhachSan;
import com.hotel.service.QuanLyKhachSan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

public class CheckOutPanel extends JPanel {
    private JComboBox<Integer> cboSoPhong;
    private JSpinner spnSoNgay;

    private JLabel lblTenKhach;
    private JLabel lblCCCD;
    private JLabel lblSdt;
    private JLabel lblLoaiPhong;
    private JLabel lblDonGia;
    private JLabel lblTongTien;
    private JLabel lblAnhKhach;

    private QuanLyKhachSan quanLyKhachSan;
    private Runnable onSuccessCallback;
    private DecimalFormat currencyFormat = new DecimalFormat("#,### VNĐ");

    public CheckOutPanel(QuanLyKhachSan quanLyKhachSan, Runnable onSuccessCallback) {
        this.quanLyKhachSan = quanLyKhachSan;
        this.onSuccessCallback = onSuccessCallback;

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // 1. TOP BAR
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));

        JLabel lblSelect = new JLabel("Chọn Phòng Trả (Đang Ở):");
        lblSelect.setFont(new Font("Segoe UI", Font.BOLD, 14));

        cboSoPhong = new JComboBox<>();
        cboSoPhong.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cboSoPhong.setPreferredSize(new Dimension(150, 32));

        topPanel.add(lblSelect);
        topPanel.add(cboSoPhong);
        add(topPanel, BorderLayout.NORTH);

        // 2. CENTER PANEL
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));

        // Cột Trái: Thông tin khách + Ảnh thật
        JPanel pnlKhach = new JPanel(new BorderLayout(10, 10));
        pnlKhach.setBackground(Color.WHITE);
        pnlKhach.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                " Thông Tin Khách Hàng ",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14), new Color(24, 43, 73)
        ));

        lblAnhKhach = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblAnhKhach.setPreferredSize(new Dimension(160, 200));
        lblAnhKhach.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JPanel pnlKhachDetails = new JPanel(new GridLayout(3, 2, 5, 10));
        pnlKhachDetails.setBackground(Color.WHITE);
        pnlKhachDetails.setBorder(new EmptyBorder(10, 10, 10, 10));

        pnlKhachDetails.add(new JLabel("Họ và Tên:"));
        lblTenKhach = new JLabel("-", SwingConstants.LEFT);
        lblTenKhach.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pnlKhachDetails.add(lblTenKhach);

        pnlKhachDetails.add(new JLabel("Số CCCD:"));
        lblCCCD = new JLabel("-", SwingConstants.LEFT);
        lblCCCD.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pnlKhachDetails.add(lblCCCD);

        pnlKhachDetails.add(new JLabel("Số ĐT:"));
        lblSdt = new JLabel("-", SwingConstants.LEFT);
        lblSdt.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pnlKhachDetails.add(lblSdt);

        pnlKhach.add(lblAnhKhach, BorderLayout.WEST);
        pnlKhach.add(pnlKhachDetails, BorderLayout.CENTER);

        // Cột Phải: Thanh toán
        JPanel pnlHoaDon = new JPanel(new GridBagLayout());
        pnlHoaDon.setBackground(Color.WHITE);
        pnlHoaDon.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                " Chi Tiết Thanh Toán ",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14), new Color(24, 43, 73)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addDetailRow(pnlHoaDon, "Loại phòng:", lblLoaiPhong = new JLabel("-"), gbc, 0);
        addDetailRow(pnlHoaDon, "Đơn giá / ngày:", lblDonGia = new JLabel("0 VNĐ"), gbc, 1);

        gbc.gridx = 0; gbc.gridy = 2;
        pnlHoaDon.add(new JLabel("Số ngày thực tế ở:"), gbc);
        gbc.gridx = 1;
        spnSoNgay = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
        spnSoNgay.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pnlHoaDon.add(spnSoNgay, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblTongTitle = new JLabel("TỔNG TIỀN THANH TOÁN:");
        lblTongTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnlHoaDon.add(lblTongTitle, gbc);

        gbc.gridx = 1;
        lblTongTien = new JLabel("0 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTongTien.setForeground(new Color(198, 40, 40));
        pnlHoaDon.add(lblTongTien, gbc);

        centerPanel.add(pnlKhach);
        centerPanel.add(pnlHoaDon);

        add(centerPanel, BorderLayout.CENTER);

        // 3. BOTTOM PANEL
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnCheckOut = new JButton("XÁC NHẬN THANH TOÁN & TRẢ PHÒNG");
        btnCheckOut.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCheckOut.setBackground(new Color(198, 40, 40));
        btnCheckOut.setForeground(Color.WHITE);
        btnCheckOut.setPreferredSize(new Dimension(320, 42));
        btnCheckOut.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnInHoaDon = new JButton("🖨️ In Hóa Đơn");
        btnInHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnInHoaDon.setPreferredSize(new Dimension(130, 42));

        bottomPanel.add(btnCheckOut);
        bottomPanel.add(btnInHoaDon);

        add(bottomPanel, BorderLayout.SOUTH);

        // Listeners
        cboSoPhong.addActionListener(e -> hienThiThongTinPhongDaChon());
        spnSoNgay.addChangeListener(e -> tinhTienToanBo());

        btnCheckOut.addActionListener(e -> xuLyCheckOut());
        btnInHoaDon.addActionListener(e -> inHoaDon());

        loadDanhSachPhongDangO();
    }

    private void addDetailRow(JPanel panel, String labelText, JLabel valueLabel, GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(valueLabel, gbc);
    }

    public void loadDanhSachPhongDangO() {
        cboSoPhong.removeAllItems();
        List<PhongKhachSan> dsPhong = quanLyKhachSan.getDanhSachPhong();
        for (PhongKhachSan p : dsPhong) {
            if ("DANG_O".equals(p.getTrangThai())) {
                cboSoPhong.addItem(p.getSoPhong());
            }
        }

        if (cboSoPhong.getItemCount() > 0) {
            cboSoPhong.setSelectedIndex(0);
            hienThiThongTinPhongDaChon();
        } else {
            xoaTrangForm();
        }
    }

    private void hienThiThongTinPhongDaChon() {
        Integer soPhong = (Integer) cboSoPhong.getSelectedItem();
        if (soPhong == null) {
            xoaTrangForm();
            return;
        }

        // Lấy thông tin phòng
        PhongKhachSan phong = quanLyKhachSan.timPhong(soPhong);
        if (phong != null) {
            lblLoaiPhong.setText(phong.getLoaiPhong());
            double donGia = phong.tinhTienThue(1);
            lblDonGia.setText(currencyFormat.format(donGia));
        }

        // Lấy phiếu đặt phòng thật từ file phieudatphong.txt
        PhieuDatPhong phieu = quanLyKhachSan.timPhieuDangHoatDong(soPhong);
        if (phieu != null && phieu.getKhachHang() != null) {
            KhachHang khach = phieu.getKhachHang();
            lblTenKhach.setText(khach.getHoTen());
            lblCCCD.setText(khach.getSoCCCD());
            lblSdt.setText(khach.getSoDienThoai());

            // Đọc ảnh webcam thực tế từ đường dẫn đã chụp
            String path = khach.getAnhKhuonMatPath();
            if (path != null && !path.isEmpty()) {
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
                    Image img = icon.getImage().getScaledInstance(160, 200, Image.SCALE_SMOOTH);
                    lblAnhKhach.setIcon(new ImageIcon(img));
                    lblAnhKhach.setText("");
                } else {
                    lblAnhKhach.setIcon(null);
                    lblAnhKhach.setText("File ảnh không tồn tại");
                }
            } else {
                lblAnhKhach.setIcon(null);
                lblAnhKhach.setText("Khách không chụp ảnh");
            }
        } else {
            lblTenKhach.setText("Chưa có thông tin");
            lblCCCD.setText("Chưa có thông tin");
            lblSdt.setText("Chưa có thông tin");
            lblAnhKhach.setIcon(null);
            lblAnhKhach.setText("Không có dữ liệu");
        }

        tinhTienToanBo();
    }

    private void tinhTienToanBo() {
        Integer soPhong = (Integer) cboSoPhong.getSelectedItem();
        if (soPhong == null) return;

        PhongKhachSan phong = quanLyKhachSan.timPhong(soPhong);
        if (phong != null) {
            int soNgay = (int) spnSoNgay.getValue();
            double tongTien = phong.tinhTienThue(soNgay);
            lblTongTien.setText(currencyFormat.format(tongTien));
        }
    }

    private void xoaTrangForm() {
        lblTenKhach.setText("-");
        lblCCCD.setText("-");
        lblSdt.setText("-");
        lblLoaiPhong.setText("-");
        lblDonGia.setText("0 VNĐ");
        lblTongTien.setText("0 VNĐ");
        lblAnhKhach.setIcon(null);
        lblAnhKhach.setText("Không có dữ liệu");
    }

    private void xuLyCheckOut() {
        Integer soPhong = (Integer) cboSoPhong.getSelectedItem();
        if (soPhong == null) {
            JOptionPane.showMessageDialog(this, "Không có phòng nào đang ở để trả!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int soNgay = (int) spnSoNgay.getValue();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Xác nhận thanh toán " + lblTongTien.getText() + " và trả phòng " + soPhong + "?",
                "Xác nhận Check-out",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean thanhCong = quanLyKhachSan.checkOut(soPhong, soNgay);
            if (thanhCong) {
                JOptionPane.showMessageDialog(this, "Trả phòng " + soPhong + " thành công!\nTrạng thái phòng chuyển sang: Chờ dọn dẹp.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadDanhSachPhongDangO();
                if (onSuccessCallback != null) onSuccessCallback.run();
            } else {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thực hiện trả phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void inHoaDon() {
        Integer soPhong = (Integer) cboSoPhong.getSelectedItem();
        if (soPhong == null) return;

        String billText = "====================================\n" +
                "       HÓA ĐƠN THANH TOÁN KHÁCH SẠN     \n" +
                "====================================\n" +
                "Phòng: P." + soPhong + "\n" +
                "Khách hàng: " + lblTenKhach.getText() + "\n" +
                "Số CCCD: " + lblCCCD.getText() + "\n" +
                "Loại phòng: " + lblLoaiPhong.getText() + "\n" +
                "Số ngày ở: " + spnSoNgay.getValue() + " ngày\n" +
                "------------------------------------\n" +
                "TỔNG CỘNG: " + lblTongTien.getText() + "\n" +
                "====================================\n" +
                " Cảm ơn quý khách và hẹn gặp lại! ";

        JTextArea textArea = new JTextArea(billText);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);

        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Chi Tiết Hóa Đơn - P." + soPhong, JOptionPane.INFORMATION_MESSAGE);
    }
}