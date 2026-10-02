package com.hotel.view;

import com.hotel.model.KhachHang;
import com.hotel.model.PhieuDatPhong;
import com.hotel.model.PhongKhachSan;
import com.hotel.service.QuanLyKhachSan;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CheckOutPanel extends JPanel {
    private QuanLyKhachSan quanLyKhachSan;
    private Runnable onSuccessCallback;
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
    private JLabel lblThueVatVal;
    private JLabel lblTongTienVal;
    private JLabel lblAnhKhach;
    private JSpinner spnSoNgay;

    // Action Buttons
    private JButton btnXacNhanThanhToan;
    private JButton btnInHoaDon;

    public CheckOutPanel() {
        this(null, null);
    }

    public CheckOutPanel(QuanLyKhachSan quanLyKhachSan) {
        this(quanLyKhachSan, null);
    }

    public CheckOutPanel(QuanLyKhachSan quanLyKhachSan, Runnable onSuccessCallback) {
        this.quanLyKhachSan = quanLyKhachSan;
        this.onSuccessCallback = onSuccessCallback;
        initUI();
    }

    public void setQuanLyKhachSan(QuanLyKhachSan quanLyKhachSan) {
        this.quanLyKhachSan = quanLyKhachSan;
    }

    public void loadDanhSachPhongDangO() {
        xoaTrangForm();
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
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;

        lblSoPhongVal = createBoldLabel("---");
        lblTenKhachVal = createBoldLabel("---");
        lblSdtVal = createBoldLabel("---");
        lblLoaiPhongVal = createBoldLabel("---");
        lblGiaCoBanVal = createBoldLabel("0 VNĐ");
        lblThueVatVal = createBoldLabel("0 VNĐ (10%)");

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
        addFormRow(pnlInfo, gbc, 6, "Thuế VAT (10%):", lblThueVatVal);
        addFormRow(pnlInfo, gbc, 7, "Tổng tiền thanh toán:", lblTongTienVal);

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
        if (quanLyKhachSan == null) {
            JOptionPane.showMessageDialog(this, "Dữ liệu quản lý chưa được khởi tạo!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

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

        PhongKhachSan phong = phieuHienTai.getPhong();
        KhachHang khach = phieuHienTai.getKhachHang();

        lblSoPhongVal.setText(phong != null ? String.valueOf(phong.getSoPhong()) : "---");
        lblTenKhachVal.setText(khach != null ? khach.getHoTen() : "---");
        lblSdtVal.setText(khach != null ? khach.getSoDienThoai() : "---");
        lblLoaiPhongVal.setText(phong != null ? phong.getLoaiPhong() : "---");
        lblGiaCoBanVal.setText(phong != null ? String.format("%,.0f VNĐ/ngày", phong.getGiaCoBan()) : "0 VNĐ");

        // Tải ảnh chân dung khách
        if (khach != null && khach.getAnhKhuonMatPath() != null && new File(khach.getAnhKhuonMatPath()).exists()) {
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
        if (phieuHienTai == null || phieuHienTai.getPhong() == null) return;
        PhongKhachSan phong = phieuHienTai.getPhong();
        int soNgay = (int) spnSoNgay.getValue();

        double tongTien = phong.tinhTienThue(soNgay);
        double thueVAT = tongTien - (tongTien / (1 + PhongKhachSan.THUE_VAT)); // Hoặc tính theo công thức giá trước thuế * 0.1

        lblThueVatVal.setText(String.format("%,.0f VNĐ", thueVAT));
        lblTongTienVal.setText(String.format("%,.0f VNĐ", tongTien));
    }

    private void xuLyThanhToan() {
        if (phieuHienTai == null || phieuHienTai.getPhong() == null) return;

        int soPhong = phieuHienTai.getPhong().getSoPhong();
        int opt = JOptionPane.showConfirmDialog(this,
                "Xác nhận thanh toán cho phòng " + soPhong + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (opt == JOptionPane.YES_OPTION) {
            int soNgay = (int) spnSoNgay.getValue();
            boolean ok = quanLyKhachSan.checkOut(soPhong, soNgay);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Check-out thành công! Phòng đã chuyển sang trạng thái chờ dọn.");
                xoaTrangForm();

                if (onSuccessCallback != null) {
                    onSuccessCallback.run();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Xử lý trả phòng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xuLyInHoaDon() {
        if (phieuHienTai == null || phieuHienTai.getPhong() == null) return;
        PhongKhachSan phong = phieuHienTai.getPhong();
        KhachHang khach = phieuHienTai.getKhachHang();
        int soNgay = (int) spnSoNgay.getValue();

        double tienGoc = phong.tinhTienGoc(soNgay);
        double phiDichVu = phong.tinhPhiDichVu(soNgay);
        double giamGia = phong.tinhTienGiamGia(soNgay);
        double tienTruocThue = tienGoc + phiDichVu - giamGia;
        double thueVat = tienTruocThue * PhongKhachSan.THUE_VAT;
        double tongCong = phong.tinhTienThue(soNgay);

        StringBuilder bill = new StringBuilder();
        bill.append("=========================================\n");
        bill.append("            HÓA ĐƠN THANH TOÁN           \n");
        bill.append("=========================================\n");
        bill.append("Số phòng: ").append(phong.getSoPhong()).append("\n");
        bill.append("Khách hàng: ").append(khach != null ? khach.getHoTen() : "").append("\n");
        bill.append("Số CCCD: ").append(khach != null ? khach.getSoCCCD() : "").append("\n");
        bill.append("Số ngày lưu trú: ").append(soNgay).append("\n");
        bill.append("-----------------------------------------\n");
        bill.append("Tiền phòng gốc: ").append(String.format("%,.0f VNĐ", tienGoc)).append("\n");
        bill.append("Phí dịch vụ:    +").append(String.format("%,.0f VNĐ", phiDichVu)).append("\n");
        bill.append("Giảm giá ưu đãi: -").append(String.format("%,.0f VNĐ", giamGia)).append("\n");
        bill.append("Thuế VAT (10%): +").append(String.format("%,.0f VNĐ", thueVat)).append("\n");
        bill.append("-----------------------------------------\n");
        bill.append("TỔNG CỘNG:      ").append(String.format("%,.0f VNĐ", tongCong)).append("\n");
        bill.append("=========================================\n");

        JTextArea textArea = new JTextArea(bill.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);

        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Chi Tiết Hóa Đơn", JOptionPane.INFORMATION_MESSAGE);
    }

    private void xoaTrangForm() {
        phieuHienTai = null;
        if (txtTimCCCD != null) txtTimCCCD.setText("");
        if (lblSoPhongVal != null) lblSoPhongVal.setText("---");
        if (lblTenKhachVal != null) lblTenKhachVal.setText("---");
        if (lblSdtVal != null) lblSdtVal.setText("---");
        if (lblLoaiPhongVal != null) lblLoaiPhongVal.setText("---");
        if (lblGiaCoBanVal != null) lblGiaCoBanVal.setText("0 VNĐ");
        if (lblThueVatVal != null) lblThueVatVal.setText("0 VNĐ (10%)");
        if (lblTongTienVal != null) lblTongTienVal.setText("0 VNĐ");
        if (lblAnhKhach != null) {
            lblAnhKhach.setIcon(null);
            lblAnhKhach.setText("Chưa có ảnh");
        }
        if (btnInHoaDon != null) btnInHoaDon.setEnabled(false);
        if (btnXacNhanThanhToan != null) btnXacNhanThanhToan.setEnabled(false);
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