package com.hotel.view;

import com.hotel.model.PhieuDatPhong;
import com.hotel.service.QuanLyKhachSan;
import com.hotel.util.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class CheckOutPanel extends JPanel {
    private QuanLyKhachSan qlkh;
    private JTextField txtMaPhieu;
    private JTextArea txtHoaDon;
    private PhieuDatPhong phieuHienTai; // Lưu thông tin phiếu vừa tìm thấy để in

    public CheckOutPanel(QuanLyKhachSan qlkh) {
        this.qlkh = qlkh;
        initUI();
    }

    /**
     * Dựng bố cục giao diện trả phòng & hóa đơn
     */
    private void initUI() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Tiêu đề màn hình
        JLabel lblTitle = new JLabel("QUẢN LÝ TRẢ PHÒNG & HÓA ĐƠN", SwingConstants.CENTER);
        lblTitle.setFont(SwingUtils.FONT_TITLE);
        add(lblTitle, BorderLayout.NORTH);

        // 2. Khung nhập mã phiếu (Thanh tìm kiếm)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.add(new JLabel("Nhập Mã Phiếu Đặt Phong:"));
        txtMaPhieu = new JTextField(15);
        searchPanel.add(txtMaPhieu);

        JButton btnCheckOut = new JButton("Thanh Toán & Trả Phòng");
        btnCheckOut.addActionListener(e -> xuLyCheckOut());
        searchPanel.add(btnCheckOut);

        // 3. Khung hiển thị chi tiết hóa đơn
        txtHoaDon = new JTextArea();
        txtHoaDon.setEditable(false);
        txtHoaDon.setFont(new Font("Monospaced", Font.PLAIN, 13)); // Font đơn cách giúp hóa đơn căn chỉnh thẳng hàng
        JScrollPane scrollPane = new JScrollPane(txtHoaDon);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Chi Tiết Hóa Đơn"));

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // 4. Thanh nút bấm phía dưới (Nút In Hóa Đơn)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnInHoaDon = new JButton("In Hóa Đơn");
        btnInHoaDon.addActionListener(e -> inHoaDon());
        bottomPanel.add(btnInHoaDon);

        add(bottomPanel, BorderLayout.SOUTH);

        // Đồng bộ Font chữ giao diện
        SwingUtils.setCustomFont(this);
    }

    /**
     * Xử lý tìm kiếm phiếu đặt phong, tính tiền và xuất hóa đơn ra màn hình
     */
    public void xuLyCheckOut() {
        String maPhieu = txtMaPhieu.getText().trim();

        if (maPhieu.isEmpty()) {
            SwingUtils.showErrorDialog(this, "Vui lòng nhập mã phiếu đặt phòng!");
            return;
        }

        // Tìm phiếu đặt phòng trong QuanLyKhachSan
        PhieuDatPhong phieu = timPhieuDatPhong(maPhieu);
        if (phieu == null) {
            SwingUtils.showErrorDialog(this, "Không tìm thấy phiếu đặt phòng có mã: " + maPhieu);
            return;
        }

        this.phieuHienTai = phieu;

        // Định dạng chuỗi văn bản hóa đơn
        StringBuilder sb = new StringBuilder();
        sb.append("=====================================================\n");
        sb.append("                 HÓA ĐƠN THANH TOÁN                  \n");
        sb.append("=====================================================\n");
        sb.append("Mã phiếu       : ").append(phieu.getMaPhieu()).append("\n");
        sb.append("Tên khách hàng : ").append(phieu.getKhachHang().getHoTen()).append("\n");
        sb.append("Số CCCD        : ").append(phieu.getKhachHang().getSoCCCD()).append("\n");
        sb.append("Mã phòng       : ").append(phieu.getPhong().getMaPhong()).append("\n");
        sb.append("Loại phòng     : ").append(phieu.getPhong().getClass().getSimpleName()).append("\n");
        sb.append("Giá gốc/ngày   : ").append(String.format("%,.0f VNĐ", phieu.getPhong().getGiaGoc())).append("\n");
        sb.append("-----------------------------------------------------\n");
        sb.append("TỔNG TIỀN      : ").append(String.format("%,.0f VNĐ", phieu.tinhTongTienPhieu())).append("\n");
        sb.append("=====================================================\n");

        txtHoaDon.setText(sb.toString());
        SwingUtils.showSuccessDialog(this, "Thanh toán thành công cho phiếu: " + maPhieu);
    }

    /**
     * In hóa đơn trực tiếp bằng lệnh in của Java Swing
     */
    public void inHoaDon() {
        if (phieuHienTai == null || txtHoaDon.getText().trim().isEmpty()) {
            SwingUtils.showErrorDialog(this, "Chưa có hóa đơn nào được tạo để in!");
            return;
        }

        try {
            // Mở hộp thoại in mặc định của hệ điều hành
            boolean isPrinted = txtHoaDon.print();
            if (isPrinted) {
                SwingUtils.showSuccessDialog(this, "Đã hoàn tất lệnh in hóa đơn!");
            }
        } catch (Exception e) {
            SwingUtils.showErrorDialog(this, "Lỗi trong quá trình in: " + e.getMessage());
        }
    }

    /**
     * Hàm trợ giúp tìm phiếu từ danh sách phiếu trong QuanLyKhachSan
     */
    private PhieuDatPhong timPhieuDatPhong(String maPhieu) {
        // Duyệt danh sách danh sách phiếu đặt phong trong QuanLyKhachSan
        for (PhieuDatPhong p : qlkh.getDsPhieuDat()) {
            if (p.getMaPhieu().equalsIgnoreCase(maPhieu)) {
                return p;
            }
        }
        return null;
    }
}