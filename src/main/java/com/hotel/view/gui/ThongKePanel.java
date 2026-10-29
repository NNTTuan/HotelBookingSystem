package com.hotel.view.gui;

import com.hotel.model.PhongKhachSan;
import com.hotel.model.PhieuDatPhong;
import com.hotel.service.QuanLyKhachSan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ThongKePanel extends JPanel {
    private QuanLyKhachSan quanLy;
    private JLabel lblTongPhong, lblPhongTrong, lblPhongDangO, lblPhongDangDon, lblDoanhThu;
    private JTable tblTopDoanhThu, tblDoanhThuThang, tblTopThueNhieu;
    private DefaultTableModel modelTopDoanhThu, modelDoanhThuThang, modelTopThueNhieu;
    private BieuDoDoanhThuPanel panelBieuDoTong;
    private BieuDoDoanhThuTheoLoaiPanel panelBieuDoTheoLoai;

    public ThongKePanel(QuanLyKhachSan quanLy) {
        this.quanLy = quanLy;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 1. Phan tren: cac the tong quan.
        JPanel panelCards = new JPanel(new GridLayout(1, 5, 10, 0));

        lblTongPhong = createCard("20", new Color(33, 150, 243));
        lblPhongTrong = createCard("0", new Color(76, 175, 80));
        lblPhongDangO = createCard("0", new Color(255, 152, 0));
        lblPhongDangDon = createCard("0", new Color(156, 39, 176));
        lblDoanhThu = createCard("0 VNĐ", new Color(233, 30, 99));

        panelCards.add(createCardWrapper("Tổng Số Phòng", lblTongPhong));
        panelCards.add(createCardWrapper("Phòng Trống", lblPhongTrong));
        panelCards.add(createCardWrapper("Đang Lưu Trú", lblPhongDangO));
        panelCards.add(createCardWrapper("Chờ Dọn", lblPhongDangDon));
        panelCards.add(createCardWrapper("Tổng Doanh Thu", lblDoanhThu));
        add(panelCards, BorderLayout.NORTH);

        // 2. Phan giua: cac tab thong ke chi tiet.
        JTabbedPane tabThongKeChiTiet = new JTabbedPane();
        tabThongKeChiTiet.setFont(new Font("Segoe UI", Font.BOLD, 12));

        String[] colsTopDoanhThu = {
                "Số Phòng", "Tầng", "Loại Phòng", "Tổng Doanh Thu (VNĐ)"
        };
        modelTopDoanhThu = new DefaultTableModel(colsTopDoanhThu, 0);
        tblTopDoanhThu = new JTable(modelTopDoanhThu);
        tabThongKeChiTiet.addTab(
                "Top 3 Phòng Doanh Thu Cao Nhất",
                new JScrollPane(tblTopDoanhThu)
        );

        String[] colsDoanhThuThang = {"Tháng/Năm", "Tổng Doanh Thu (VNĐ)"};
        modelDoanhThuThang = new DefaultTableModel(colsDoanhThuThang, 0);
        tblDoanhThuThang = new JTable(modelDoanhThuThang);
        tabThongKeChiTiet.addTab(
                "Doanh Thu Hằng Tháng (Bảng)",
                new JScrollPane(tblDoanhThuThang)
        );

        panelBieuDoTong = new BieuDoDoanhThuPanel();
        tabThongKeChiTiet.addTab("Biểu Đồ Tổng Doanh Thu", panelBieuDoTong);

        panelBieuDoTheoLoai = new BieuDoDoanhThuTheoLoaiPanel();
        tabThongKeChiTiet.addTab(
                "Biểu Đồ Doanh Thu Theo Loại Phòng",
                panelBieuDoTheoLoai
        );

        String[] colsTopThue = {"Số Phòng", "Tầng", "Loại Phòng", "Số Lượt Thuê"};
        modelTopThueNhieu = new DefaultTableModel(colsTopThue, 0);
        tblTopThueNhieu = new JTable(modelTopThueNhieu);
        tabThongKeChiTiet.addTab(
                "Top 5 Phòng Thuê Nhiều Nhất",
                new JScrollPane(tblTopThueNhieu)
        );

        add(tabThongKeChiTiet, BorderLayout.CENTER);

        // 3. Phan duoi: nut lam moi.
        JButton btnRefresh = new JButton("Làm mới thống kê");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRefresh.addActionListener(e -> capNhatDuLieuThongKe());

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBottom.add(btnRefresh);
        add(panelBottom, BorderLayout.SOUTH);

        capNhatDuLieuThongKe();
    }

    private JLabel createCard(String value, Color color) {
        JLabel lbl = new JLabel(value, JLabel.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(color);
        return lbl;
    }

    private JPanel createCardWrapper(String title, JLabel lblValue) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        panel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel(title, JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitle.setForeground(new Color(100, 100, 100));

        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(lblValue, BorderLayout.CENTER);
        return panel;
    }

    public void capNhatDuLieuThongKe() {
        if (quanLy == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Dữ liệu quản lý khách sạn chưa được khởi tạo.",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Doc lai file phieu truoc khi thong ke de lay du lieu moi nhat.
        // Neu khong doc duoc file, giu nguyen thong ke hien tai.
        if (!quanLy.taiLaiDanhSachPhieuTuFile()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không thể đọc lại dữ liệu từ data/phieudatphong.txt. Thống kê chưa được cập nhật.",
                    "Lỗi đọc dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        List<PhongKhachSan> dsPhong = quanLy.getDanhSachPhong();
        List<PhieuDatPhong> dsPhieu = quanLy.getDanhSachPhieu();

        if (dsPhong == null) {
            dsPhong = Collections.emptyList();
        }
        if (dsPhieu == null) {
            dsPhieu = Collections.emptyList();
        }

        int tong = dsPhong.size();
        int trong = 0;
        int dangO = 0;
        int dangDon = 0;

        for (PhongKhachSan p : dsPhong) {
            if (p == null || p.getTrangThai() == null) {
                continue;
            }

            String tt = p.getTrangThai().trim().toUpperCase(Locale.ROOT);
            if (tt.contains("TRONG")) {
                trong++;
            } else if (tt.contains("DANG_O")) {
                dangO++;
            } else if (tt.contains("DANG_DON")) {
                dangDon++;
            }
        }

        double tongDoanhThuTatCa = 0;
        Map<Integer, Double> mapDoanhThuPhong = new HashMap<>();
        Map<String, Double> mapDoanhThuThang = new TreeMap<>();
        Map<String, Map<String, Double>> mapDoanhThuThangVaLoai = new TreeMap<>();
        Map<Integer, Integer> mapLuotThuePhong = new HashMap<>();

        for (PhongKhachSan p : dsPhong) {
            if (p != null) {
                mapLuotThuePhong.put(p.getSoPhong(), 0);
            }
        }

        for (PhieuDatPhong phieu : dsPhieu) {
            if (phieu == null || phieu.getPhong() == null) {
                continue;
            }

            int soPhong = phieu.getPhong().getSoPhong();
            mapLuotThuePhong.put(
                    soPhong,
                    mapLuotThuePhong.getOrDefault(soPhong, 0) + 1
            );

            if (!"DA_TRA".equalsIgnoreCase(phieu.getTrangThai())) {
                continue;
            }

            int soNgay = phieu.getSoNgayThue();
            double tienTruocThue = phieu.getPhong().tinhTienTruocThue(soNgay);
            double vat = phieu.getPhong().tinhTienThueVAT(soNgay);
            double tongTienPhieu = tienTruocThue + vat;

            tongDoanhThuTatCa += tongTienPhieu;
            mapDoanhThuPhong.put(
                    soPhong,
                    mapDoanhThuPhong.getOrDefault(soPhong, 0.0) + tongTienPhieu
            );

            String thangNam = "01/2026";
            try {
                String tsStr = phieu.getMaPhieu().replace("PDP", "");
                long timestamp = Long.parseLong(tsStr);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
                thangNam = sdf.format(new Date(timestamp));
            } catch (Exception ignored) {
                // Giu thang mac dinh neu ma phieu khong chua timestamp hop le.
            }

            mapDoanhThuThang.put(
                    thangNam,
                    mapDoanhThuThang.getOrDefault(thangNam, 0.0) + tongTienPhieu
            );

            String loaiPhong = phieu.getPhong().getLoaiPhong();
            mapDoanhThuThangVaLoai.putIfAbsent(thangNam, new HashMap<>());
            Map<String, Double> loaiMap = mapDoanhThuThangVaLoai.get(thangNam);
            loaiMap.put(
                    loaiPhong,
                    loaiMap.getOrDefault(loaiPhong, 0.0) + tongTienPhieu
            );
        }

        lblTongPhong.setText(String.valueOf(tong));
        lblPhongTrong.setText(String.valueOf(trong));
        lblPhongDangO.setText(String.valueOf(dangO));
        lblPhongDangDon.setText(String.valueOf(dangDon));
        lblDoanhThu.setText(String.format("%,.0f VNĐ", tongDoanhThuTatCa));

        // Nap Top 3 phong co doanh thu cao nhat.
        modelTopDoanhThu.setRowCount(0);
        mapDoanhThuPhong.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(3)
                .forEach(entry -> {
                    PhongKhachSan p = quanLy.timPhong(entry.getKey());
                    if (p != null) {
                        modelTopDoanhThu.addRow(new Object[]{
                                p.getSoPhong(),
                                p.getTang(),
                                p.getLoaiPhong(),
                                String.format("%,.0f", entry.getValue())
                        });
                    }
                });

        // Nap bang doanh thu hang thang.
        modelDoanhThuThang.setRowCount(0);
        mapDoanhThuThang.forEach((thang, doanhThu) ->
                modelDoanhThuThang.addRow(new Object[]{
                        thang,
                        String.format("%,.0f", doanhThu)
                })
        );

        // Cap nhat ca hai bieu do.
        if (panelBieuDoTong != null) {
            panelBieuDoTong.setData(mapDoanhThuThang);
            panelBieuDoTong.repaint();
        }
        if (panelBieuDoTheoLoai != null) {
            panelBieuDoTheoLoai.setData(mapDoanhThuThangVaLoai);
            panelBieuDoTheoLoai.repaint();
        }

        // Nap Top 5 phong co so luot thue nhieu nhat.
        modelTopThueNhieu.setRowCount(0);
        mapLuotThuePhong.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> {
                    PhongKhachSan p = quanLy.timPhong(entry.getKey());
                    if (p != null) {
                        modelTopThueNhieu.addRow(new Object[]{
                                p.getSoPhong(),
                                p.getTang(),
                                p.getLoaiPhong(),
                                entry.getValue()
                        });
                    }
                });
    }
}
