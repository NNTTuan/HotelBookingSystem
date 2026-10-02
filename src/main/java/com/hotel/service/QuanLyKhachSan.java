package com.hotel.service;

import com.hotel.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuanLyKhachSan {
    private static final String DATA_DIR = "data";
    private static final String FILE_PHONG = "data/phong.txt";
    private static final String FILE_PHIEU = "data/phieudatphong.txt";

    private List<PhongKhachSan> danhSachPhong;
    private List<PhieuDatPhong> danhSachPhieu;

    public QuanLyKhachSan() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        this.danhSachPhong = docFilePhong();
        this.danhSachPhieu = docFilePhieu();
    }

    public List<PhongKhachSan> getDanhSachPhong() {
        return danhSachPhong;
    }

    public List<PhieuDatPhong> getDanhSachPhieu() {
        return danhSachPhieu;
    }

    // 1. Quản lý File Phòng
    private List<PhongKhachSan> khoiTao100Phong() {
        List<PhongKhachSan> ds = new ArrayList<>();
        for (int tang = 1; tang <= 10; tang++) {
            for (int p = 1; p <= 10; p++) {
                int soPhong = tang * 100 + p;
                if (tang <= 4) {
                    ds.add(new PhongStandard(soPhong, tang, "TRONG"));
                } else if (tang <= 8) {
                    ds.add(new PhongVIP(soPhong, tang, "TRONG"));
                } else {
                    ds.add(new PhongPenthouse(soPhong, tang, "TRONG"));
                }
            }
        }
        return ds;
    }

    public List<PhongKhachSan> docFilePhong() {
        File file = new File(FILE_PHONG);
        if (!file.exists() || file.length() == 0) {
            List<PhongKhachSan> dsMoi = khoiTao100Phong();
            luuFilePhong(dsMoi);
            return dsMoi;
        }

        List<PhongKhachSan> ds = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    int soPhong = Integer.parseInt(parts[0].trim());
                    int tang = Integer.parseInt(parts[1].trim());
                    String loai = parts[2].trim();
                    String trangThai = parts[3].trim();

                    if ("Standard".equalsIgnoreCase(loai)) {
                        ds.add(new PhongStandard(soPhong, tang, trangThai));
                    } else if ("VIP".equalsIgnoreCase(loai)) {
                        ds.add(new PhongVIP(soPhong, tang, trangThai));
                    } else if ("Penthouse".equalsIgnoreCase(loai)) {
                        ds.add(new PhongPenthouse(soPhong, tang, trangThai));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ds.isEmpty()) {
            ds = khoiTao100Phong();
            luuFilePhong(ds);
        }

        return ds;
    }

    public void luuFilePhong(List<PhongKhachSan> ds) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PHONG))) {
            for (PhongKhachSan p : ds) {
                bw.write(p.getSoPhong() + "," + p.getTang() + "," + p.getLoaiPhong() + "," + p.getTrangThai());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 2. Quản lý File Phiếu Đặt Phòng
    public List<PhieuDatPhong> docFilePhieu() {
        List<PhieuDatPhong> ds = new ArrayList<>();
        File file = new File(FILE_PHIEU);
        if (!file.exists()) return ds;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                // Format: maPhieu;soPhong;hoTen;soCCCD;sdt;anhPath;soNgayThue;trangThaiPhieu
                String[] parts = line.split(";");
                if (parts.length >= 8) {
                    String maPhieu = parts[0].trim();
                    int soPhong = Integer.parseInt(parts[1].trim());
                    String hoTen = parts[2].trim();
                    String cccd = parts[3].trim();
                    String sdt = parts[4].trim();
                    String anhPath = parts[5].trim();
                    int soNgay = Integer.parseInt(parts[6].trim());
                    String trangThaiPhieu = parts[7].trim();

                    PhongKhachSan phong = timPhong(soPhong);
                    KhachHang khach = new KhachHang(hoTen, cccd, sdt, anhPath);
                    ds.add(new PhieuDatPhong(maPhieu, phong, khach, soNgay, trangThaiPhieu));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public void luuFilePhieu() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PHIEU))) {
            for (PhieuDatPhong p : danhSachPhieu) {
                KhachHang k = p.getKhachHang();
                int soPhong = (p.getPhong() != null) ? p.getPhong().getSoPhong() : 0;
                bw.write(p.getMaPhieu() + ";" +
                        soPhong + ";" +
                        (k != null ? k.getHoTen() : "") + ";" +
                        (k != null ? k.getSoCCCD() : "") + ";" +
                        (k != null ? k.getSoDienThoai() : "") + ";" +
                        (k != null ? k.getAnhKhuonMatPath() : "") + ";" +
                        p.getSoNgayThue() + ";" +
                        p.getTrangThai());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Tra cứu phiếu đặt phòng đang hoạt động theo số phòng
    public PhieuDatPhong timPhieuDangHoatDong(int soPhong) {
        for (PhieuDatPhong p : danhSachPhieu) {
            if (p.getPhong() != null
                    && p.getPhong().getSoPhong() == soPhong
                    && ("DANG_O".equalsIgnoreCase(p.getTrangThai()) || "DANG_HOAT_DONG".equalsIgnoreCase(p.getTrangThai()))) {
                return p;
            }
        }
        return null;
    }

    // Tra cứu phiếu đặt phòng đang hoạt động theo số CCCD khách hàng (Dùng cho Check-out Panel)
    public PhieuDatPhong timPhieuDangHoatDongTheoCCCD(String soCCCD) {
        if (soCCCD == null || soCCCD.trim().isEmpty()) {
            return null;
        }
        String cleanCCCD = soCCCD.trim();
        for (PhieuDatPhong p : danhSachPhieu) {
            if (("DANG_O".equalsIgnoreCase(p.getTrangThai()) || "DANG_HOAT_DONG".equalsIgnoreCase(p.getTrangThai()))
                    && p.getKhachHang() != null
                    && cleanCCCD.equalsIgnoreCase(p.getKhachHang().getSoCCCD().trim())) {
                return p;
            }
        }
        return null;
    }

    // 3. Nghiệp vụ Check-In / Check-Out
    public boolean checkIn(int soPhong, KhachHang khach) {
        PhongKhachSan phong = timPhong(soPhong);
        if (phong == null || !"TRONG".equalsIgnoreCase(phong.getTrangThai())) {
            return false;
        }

        // Cập nhật trạng thái phòng
        phong.setTrangThai("DANG_O");
        luuFilePhong(danhSachPhong);

        // Tạo phiếu đặt mới
        String maPhieu = "PDP" + System.currentTimeMillis();
        PhieuDatPhong phieuMoi = new PhieuDatPhong(maPhieu, phong, khach, 1, "DANG_O");
        danhSachPhieu.add(phieuMoi);
        luuFilePhieu();

        return true;
    }

    public boolean checkOut(int soPhong, int soNgayThucTe) {
        PhongKhachSan phong = timPhong(soPhong);
        if (phong == null || !"DANG_O".equalsIgnoreCase(phong.getTrangThai())) {
            return false;
        }

        // Đổi trạng thái phòng thành đang dọn
        phong.setTrangThai("DANG_DON");
        luuFilePhong(danhSachPhong);

        // Cập nhật phiếu đặt phòng sang DA_TRA
        PhieuDatPhong phieu = timPhieuDangHoatDong(soPhong);
        if (phieu != null) {
            phieu.setSoNgayThue(soNgayThucTe);
            phieu.setTrangThai("DA_TRA");
            luuFilePhieu();
        }

        return true;
    }

    public boolean xacNhanDonXong(int soPhong) {
        PhongKhachSan phong = timPhong(soPhong);
        if (phong == null) return false;

        phong.setTrangThai("TRONG");
        luuFilePhong(danhSachPhong);
        return true;
    }

    public PhongKhachSan timPhong(int soPhong) {
        for (PhongKhachSan p : danhSachPhong) {
            if (p.getSoPhong() == soPhong) return p;
        }
        return null;
    }
}