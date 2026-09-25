package com.hotel.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PhieuDatPhong {
    private String maPhieu;
    private PhongKhachSan phong;
    private KhachHang khachHang;
    private LocalDate ngayNhanPhong;
    private LocalDate ngayTraPhong;
    private double tienCoc;
    private TrangThaiPhieu trangThai;

    public enum TrangThaiPhieu {
        CHO_XAC_NHAN, DA_XAC_NHAN, DA_NHAN_PHONG, DA_TRA_PHONG, HUY
    }

    public PhieuDatPhong(String maPhieu, PhongKhachSan phong, KhachHang khachHang, 
                         LocalDate ngayNhanPhong, LocalDate ngayTraPhong, double tienCoc) {
        this.maPhieu = maPhieu;
        this.phong = phong;
        this.khachHang = khachHang;
        this.ngayNhanPhong = ngayNhanPhong;
        this.ngayTraPhong = ngayTraPhong;
        this.tienCoc = tienCoc;
        this.trangThai = TrangThaiPhieu.CHO_XAC_NHAN;
    }

    // Tự động tính số ngày ở dựa trên ngày nhận và ngày trả
    public long getSoNgayThue() {
        long soNgay = ChronoUnit.DAYS.between(ngayNhanPhong, ngayTraPhong);
        return soNgay > 0 ? soNgay : 1; // Mặc định ít nhất là 1 ngày
    }

    // Tính tổng tiền phòng
    public double tinhTongTienPhieu() {
        return getSoNgayThue() * phong.getGiaPhong();
    }

    // Tính số tiền còn lại khách phải thanh toán sau khi trừ tiền cọc
    public double tinhTienConLai() {
        return tinhTongTienPhieu() - tienCoc;
    }
`
    // Getter và Setter
    public String getMaPhieu() {
        return maPhieu;
    }

    public PhongKhachSan getPhong() {
        return phong;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public LocalDate getNgayNhanPhong() {
        return ngayNhanPhong;
    }

    public LocalDate getNgayTraPhong() {
        return ngayTraPhong;
    }

    public double getTienCoc() {
        return tienCoc;
    }

    public TrangThaiPhieu getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiPhieu trangThai) {
        this.trangThai = trangThai;
    }
}