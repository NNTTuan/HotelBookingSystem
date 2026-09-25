package com.hotel.service;

import com.hotel.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuanLyKhachSan {
    private List<PhongKhachSan> dsPhong;
    private List<PhieuDatPhong> dsPhieuDat;

    public QuanLyKhachSan() {
        this.dsPhong = new ArrayList<>();
        this.dsPhieuDat = new ArrayList<>();
        initDataMau(); // Nạp sẵn dữ liệu phòng mẫu để chạy thử giao diện
    }

    /**
     * Khởi tạo dữ liệu phòng mẫu ban đầu
     */
    private void initDataMau() {
        dsPhong.add(new PhongStandard("STD101", 500000));
        dsPhong.add(new PhongStandard("STD102", 500000));
        dsPhong.add(new PhongVIP("VIP201", 1200000));
        dsPhong.add(new PhongVIP("VIP202", 1500000));
        dsPhong.add(new PhongPenthouse("PENT301", 3500000));
    }

    /**
     * Thêm phòng mới vào hệ thống
     */
    public void themPhong(PhongKhachSan p) {
        if (p != null) {
            dsPhong.add(p);
        }
    }

    /**
     * Tạo và lưu phiếu đặt phòng mới cho khách hàng
     */
    public PhieuDatPhong taoPhieuDat(KhachHang kh, PhongKhachSan p, int soNgay) {
        String maPhieu = "PDP" + String.format("%03d", dsPhieuDat.size() + 1);
        PhieuDatPhong phieu = new PhieuDatPhong(maPhieu, p, kh, soNgay);
        dsPhieuDat.add(phieu);
        return phieu;
    }

    /**
     * Lấy Top 3 phòng có tổng tiền thuê cao nhất tính theo số ngày
     */
    public List<PhongKhachSan> top3PhongGiaCaoNhat(int soNgay) {
        return dsPhong.stream()
                .sorted((p1, p2) -> Double.compare(p2.tinhTienThue(soNgay), p1.tinhTienThue(soNgay)))
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * Đọc danh sách phòng / phiếu đặt từ file đĩa
     */
    public void loadDataTuFile(String path) {
        // Triển khai đọc dữ liệu từ file txt hoặc object stream
    }

    /**
     * Lưu thông tin danh sách phòng / phiếu đặt ra file đĩa
     */
    public void saveDataVaoFile(String path) {
        // Triển khai ghi dữ liệu ra file
    }

    // ================= GETTERS & SETTERS =================

    public List<PhongKhachSan> getDsPhong() {
        return dsPhong;
    }

    public List<PhieuDatPhong> getDsPhieuDat() {
        return dsPhieuDat;
    }
}