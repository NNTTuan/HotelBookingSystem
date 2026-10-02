package com.hotel.view;

import com.hotel.model.KhachHang;
import com.hotel.model.PhieuDatPhong;
import com.hotel.model.PhongKhachSan;
import com.hotel.service.QuanLyKhachSan;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;

public class CheckOutPanel extends JPanel {
    private final QuanLyKhachSan quanLyKhachSan;
    private PhieuDatPhong phieuHienTai;

    // UI Input Search
    private JTextField txtTimCCCD;
    private JButton btnTimKiem;

    // UI Displays
    private JLabel lblSoPhongVal;
    private JLabel lblTenKhachVal;
    private JLabel lblSdtVal;
    private JLabel lblLoaiPhongVal;
    private JLabel lblGiaCoBanVal;
    private JLabel lblTongTienVal;
    private JLabel lblAnhKhach;
    private JSpinner spnSoNgay;

    // Action Buttons
    private JButton btnXacNhanThanhToan;
    private JButton btnInHoaDon;

    public CheckOutPanel(QuanLyKhachSan quanLyKhachSan) {
        this.quanLyKhachSan = quanLyKhachSan;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 1. THANH TÌM KIẾM THEO CCCD (TOP)
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Tra cứu thông tin trả phòng"));

        pnlSearch.add(new JLabel("Nhập số CCCD/CMND khách hàng:"));
        txtTimCCCD = new JTextField(20);
        txtTimCCCD.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnTimKiem = new JButton("Tìm khách");

        pnlSearch.add(txtTimCCCD);
        pnlSearch.add(btnTimKiem);
        add(pnlSearch, BorderLayout.NORTH);

        // 2. CHI TIẾT THÔNG TIN THUÊ PHÒNG (CENTER)
        JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 15, 0));

        // Column Left: Thông tin chi tiết
        JPanel pnlInfo = new JPanel(new GridBagLayout());
        pnlInfo.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu lưu trú"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        lblSoPhongVal = createBoldLabel("---");
        lblTenKhachVal = createBoldLabel("---");
        lblSdtVal = createBoldLabel("---");
        lblLoaiPhongVal = createBoldLabel("---");
        lblGiaCoBanVal = createBoldLabel("0 VNĐ");

        spnSoNgay = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        spnSoNgay.setPreferredSize(new Dimension(80, 25));

        lblTongTienVal = new JLabel("0 VNĐ");
        lblTongTienVal.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTongTienVal.setForeground(new Color(180, 40, 40));

        addFormRow(pnlInfo, gbc, 0, "Số phòng:", lblSoPhongVal);
        addFormRow(pnlInfo, gbc, 1, "Họ và tên:", lblTenKhachVal);
        addFormRow(pnlInfo, gbc, 2, "Số điện thoại:", lblSdtVal);
        addFormRow(pnlInfo, gbc, 3, "Loại phòng:", lblLoaiPhongVal);
        addFormRow(pnlInfo, gbc, 4, "Giá niêm yết:", lblGiaCoBanVal);
        addFormRow(pnlInfo, gbc, 5, "Số ngày ở:", spnSoNgay);
        addFormRow(pnlInfo, gbc, 6, "Tổng tiền thanh toán:", lblTongTienVal);

        pnlCenter.add(pnlInfo);

        // Column Right: Ảnh khuôn mặt đối soát
        JPanel pnlImage = new JPanel(new BorderLayout());
        pnlImage.setBorder(BorderFactory.createTitledBorder("Ảnh chân dung nhận diện"));
        lblAnhKhach = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblAnhKhach.setPreferredSize(new Dimension(250, 250));
        pnlImage.add(lblAnhKhach, BorderLayout.CENTER);

        pnlCenter.add(pnlImage);
        add(pnlCenter, BorderLayout.CENTER);

        // 3. NÚT THAO TÁC (BOTTOM)
        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnInHoaDon = new JButton("In Hóa Đơn");
        btnXacNhanThanhToan = new JButton("XÁC NHẬN THANH TOÁN & TRẢ PHÒNG");
        btnXacNhanThanhToan.setBackground(new Color(40, 140, 60));
        btnXacNhanThanhToan.setForeground(Color.WHITE);

        btnInHoaDon.setEnabled(false);
        btnXacNhanThanhToan.setEnabled(false);

        pnlActions.add(btnInHoaDon);
        pnlActions.add(btnXacNhanThanhToan);
        add(pnlActions, BorderLayout.SOUTH);

        // 4. BẮT SỰ KIỆN
        btnTimKiem.addActionListener(e -> xuLyTimKiem());
        txtTimCCCD.addActionListener(e -> xuLyTimKiem());
        spnSoNgay.addChangeListener(e -> capNhatTongTien());
        btnInHoaDon.addActionListener(e -> xuLyInHoaDon());
        btnXacNhanThanhToan.addActionListener(e -> xuLyThanhToan());
    }

    private void xuLyTimKiem() {
        String cccd = txtTimCCCD.getText().trim();
        if (cccd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số CCCD cần tìm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        phieuHienTai = quanLyKhachSan.timPhieuDangHoatDongTheoCCCD(cccd);
        if (phieuHienTai == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng đang ở có CCCD: " + cccd, "Thất bại", JOptionPane.ERROR_MESSAGE);
            xoaTrangForm();
            return;
        }

        PhongKhachSan phong = quanLyKhachSan.timPhong(phieuHienTai.getSoPhong());
        KhachHang khach = phieuHienTai.getKhachHang();

        lblSoPhongVal.setText(String.valueOf(phong.getSoPhong()));
        lblTenKhachVal.setText(khach.getHoTen());
        lblSdtVal.setText(khach.getSoDienThoai());
        lblLoaiPhongVal.setText(phong.getLoaiPhong());
        lblGiaCoBanVal.setText(String.format("%,.0f VNĐ/ngày", phong.getGiaCoBan()));

        // Tải ảnh chân dung khách
        if (khach.getAnhKhuonMatPath() != null && new File(khach.getAnhKhuonMatPath()).exists()) {
            ImageIcon icon = new ImageIcon(khach.getAnhKhuonMatPath());
            Image img = icon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
            lblAnhKhach.setIcon(new ImageIcon(img));
            lblAnhKhach.setText("");
        } else {
            lblAnhKhach.setIcon(null);
            lblAnhKhach.setText("Không có ảnh hiển thị");
        }

        btnInHoaDon.setEnabled(true);
        btnXacNhanThanhToan.setEnabled(true);
        capNhatTongTien();
    }

    private void capNhatTongTien() {
        if (phieuHienTai == null) return;
        PhongKhachSan phong = quanLyKhachSan.timPhong(phieuHienTai.getSoPhong());
        int soNgay = (int) spnSoNgay.getValue();
        double tongTien = phong.tinhTienThue(soNgay);
        lblTongTienVal.setText(String.format("%,.0f VNĐ", tongTien));
    }

    private void xuLyThanhToan() {
        if (phieuHienTai == null) return;

        int opt = JOptionPane.showConfirmDialog(this,
                "Xác nhận thanh toán cho phòng " + phieuHienTai.getSoPhong() + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (opt == JOptionPane.YES_OPTION) {
            int soNgay = (int) spnSoNgay.getValue();
            boolean ok = quanLyKhachSan.checkOut(phieuHienTai.getSoPhong(), soNgay);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Check-out thành công! Phòng đã chuyển sang trạng thái chờ dọn.");
                xoaTrangForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xử lý trả phòng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xuLyInHoaDon() {
        if (phieuHienTai == null) return;
        PhongKhachSan phong = quanLyKhachSan.timPhong(phieuHienTai.getSoPhong());
        int soNgay = (int) spnSoNgay.getValue();

        StringBuilder bill = new StringBuilder();
        bill.append("=========================================\n");
        bill.append("            HÓA ĐƠN THANH TOÁN           \n");
        bill.append("=========================================\n");
        bill.append("Số phòng: ").append(phong.getSoPhong()).append("\n");
        bill.append("Khách hàng: ").append(phieuHienTai.getKhachHang().getHoTen()).append("\n");
        bill.append("Số CCCD: ").append(phieuHienTai.getKhachHang().getSoCCCD()).append("\n");
        bill.append("Số ngày lưu trú: ").append(soNgay).append("\n");
        bill.append("-----------------------------------------\n");
        bill.append("Tiền phòng gốc: ").append(String.format("%,.0f VNĐ", phong.tinhTienGoc(soNgay))).append("\n");
        bill.append("Phí dịch vụ:    +").append(String.format("%,.0f VNĐ", phong.tinhPhiDichVu(soNgay))).append("\n");
        bill.append("Giảm giá ưu đãi: -").append(String.format("%,.0f VNĐ", phong.tinhTienGiamGia(soNgay))).append("\n");
        bill.append("-----------------------------------------\n");
        bill.append("TỔNG CỘNG:      ").append(String.format("%,.0f VNĐ", phong.tinhTienThue(soNgay))).append("\n");
        bill.append("=========================================\n");

        JTextArea textArea = new JTextArea(bill.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);

        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Chi Tiết Hóa Đơn", JOptionPane.INFORMATION_MESSAGE);
    }

    private void xoaTrangForm() {
        phieuHienTai = null;
        txtTimCCCD.setText("");
        lblSoPhongVal.setText("---");
        lblTenKhachVal.setText("---");
        lblSdtVal.setText("---");
        lblLoaiPhongVal.setText("---");
        lblGiaCoBanVal.setText("0 VNĐ");
        lblTongTienVal.setText("0 VNĐ");
        lblAnhKhach.setIcon(null);
        lblAnhKhach.setText("Chưa có ảnh");
        btnInHoaDon.setEnabled(false);
        btnXacNhanThanhToan.setEnabled(false);
    }

    private JLabel createBoldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        return label;
    }

    private void addFormRow(JPanel pnl, GridBagConstraints gbc, int row, String label, Component comp) {
        gbc.gridy = row;
        gbc.gridx = 0;
        pnl.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        pnl.add(comp, gbc);
    }
}