package com.hotel.service;

import com.hotel.model.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    private List<PhongKhachSan> khoiTao20Phong() {
        List<PhongKhachSan> ds = new ArrayList<>();

        for (int tang = 1; tang <= 5; tang++) {
            for (int p = 1; p <= 4; p++) {
                int soPhong = tang * 100 + p;

                if (tang <= 2) {
                    ds.add(new PhongStandard(soPhong, tang, "TRONG"));
                } else if (tang <= 4) {
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
            List<PhongKhachSan> dsMoi = khoiTao20Phong();
            luuFilePhong(dsMoi);
            return dsMoi;
        }

        List<PhongKhachSan> ds = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    try {
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
                    } catch (NumberFormatException ex) {
                        System.err.println("Bo qua dong phong khong hop le: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ds.isEmpty()) {
            ds = khoiTao20Phong();
            luuFilePhong(ds);
        }

        return ds;
    }

    public void luuFilePhong(List<PhongKhachSan> ds) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PHONG))) {
            for (PhongKhachSan p : ds) {
                bw.write(p.getSoPhong() + "," + p.getTang() + ","
                        + p.getLoaiPhong() + "," + p.getTrangThai());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<PhieuDatPhong> docFilePhieu() {
        List<PhieuDatPhong> ds = new ArrayList<>();
        File file = new File(FILE_PHIEU);

        if (!file.exists()) {
            return ds;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Dung -1 de khong bo mat truong rong o cuoi dong.
                String[] parts = line.split(";", -1);
                if (parts.length < 8) {
                    System.err.println("Bo qua dong phieu khong du 8 truong: " + line);
                    continue;
                }

                try {
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
                    ds.add(new PhieuDatPhong(
                            maPhieu, phong, khach, soNgay, trangThaiPhieu
                    ));
                } catch (NumberFormatException ex) {
                    System.err.println("Bo qua dong phieu co du lieu so khong hop le: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ds;
    }

    /**
     * Doc lai danh sach phieu tu data/phieudatphong.txt va cap nhat danh sach
     * dang duoc su dung trong bo nho. Tra ve false neu khong doc duoc file.
     */
    public boolean taiLaiDanhSachPhieuTuFile() {
        File file = new File(FILE_PHIEU);

        // Khong co file thi xem nhu danh sach phieu rong.
        if (!file.exists()) {
            danhSachPhieu.clear();
            return true;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<PhieuDatPhong> dsMoi = new ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(";", -1);
                if (parts.length < 8) {
                    System.err.println("Bo qua dong phieu khong du 8 truong: " + line);
                    continue;
                }

                try {
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
                    dsMoi.add(new PhieuDatPhong(
                            maPhieu, phong, khach, soNgay, trangThaiPhieu
                    ));
                } catch (NumberFormatException ex) {
                    System.err.println("Bo qua dong phieu co du lieu so khong hop le: " + line);
                }
            }

            // Cap nhat cung mot danh sach de cac thanh phan dang tham chieu
            // danhSachPhieu khong bi giu lai bang du lieu cu.
            danhSachPhieu.clear();
            danhSachPhieu.addAll(dsMoi);
            return true;
        } catch (IOException e) {
            System.err.println("Khong the doc lai file " + FILE_PHIEU);
            e.printStackTrace();
            return false;
        }
    }

    public void luuFilePhieu() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PHIEU))) {
            for (PhieuDatPhong p : danhSachPhieu) {
                KhachHang k = p.getKhachHang();
                int soPhong = (p.getPhong() != null) ? p.getPhong().getSoPhong() : 0;

                bw.write(p.getMaPhieu() + ";"
                        + soPhong + ";"
                        + (k != null ? k.getHoTen() : "") + ";"
                        + (k != null ? k.getSoCCCD() : "") + ";"
                        + (k != null ? k.getSoDienThoai() : "") + ";"
                        + (k != null ? k.getAnhKhuonMatPath() : "") + ";"
                        + p.getSoNgayThue() + ";"
                        + p.getTrangThai());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PhieuDatPhong timPhieuDangHoatDong(int soPhong) {
        for (PhieuDatPhong p : danhSachPhieu) {
            if (p.getPhong() != null
                    && p.getPhong().getSoPhong() == soPhong
                    && ("DANG_O".equalsIgnoreCase(p.getTrangThai())
                    || "DANG_HOAT_DONG".equalsIgnoreCase(p.getTrangThai()))) {
                return p;
            }
        }
        return null;
    }

    public PhieuDatPhong timPhieuDangHoatDongTheoCCCD(String soCCCD) {
        if (soCCCD == null || soCCCD.trim().isEmpty()) {
            return null;
        }

        String cleanCCCD = soCCCD.trim();
        for (PhieuDatPhong p : danhSachPhieu) {
            if (("DANG_O".equalsIgnoreCase(p.getTrangThai())
                    || "DANG_HOAT_DONG".equalsIgnoreCase(p.getTrangThai()))
                    && p.getKhachHang() != null
                    && p.getKhachHang().getSoCCCD() != null
                    && cleanCCCD.equalsIgnoreCase(p.getKhachHang().getSoCCCD().trim())) {
                return p;
            }
        }
        return null;
    }

    public boolean checkIn(int soPhong, KhachHang khach, int soNgayThue) {
        PhongKhachSan phong = timPhong(soPhong);
        if (phong == null || !"TRONG".equalsIgnoreCase(phong.getTrangThai())) {
            return false;
        }

        phong.setTrangThai("DANG_O");
        luuFilePhong(danhSachPhong);

        String maPhieu = "PDP" + System.currentTimeMillis();
        PhieuDatPhong phieuMoi = new PhieuDatPhong(
                maPhieu, phong, khach, soNgayThue, "DANG_O"
        );
        danhSachPhieu.add(phieuMoi);
        luuFilePhieu();

        return true;
    }

    public boolean checkOut(int soPhong, int soNgayThucTe) {
        PhongKhachSan phong = timPhong(soPhong);
        if (phong == null || !"DANG_O".equalsIgnoreCase(phong.getTrangThai())) {
            return false;
        }

        phong.setTrangThai("DANG_DON");
        luuFilePhong(danhSachPhong);

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
        if (phong == null || !"DANG_DON".equalsIgnoreCase(phong.getTrangThai())) {
            return false;
        }

        phong.setTrangThai("TRONG");
        luuFilePhong(danhSachPhong);
        return true;
    }

    public PhongKhachSan timPhong(int soPhong) {
        for (PhongKhachSan p : danhSachPhong) {
            if (p.getSoPhong() == soPhong) {
                return p;
            }
        }
        return null;
    }

    public List<PhieuDatPhong> timLichSuDatPhongTheoSoPhong(int soPhong) {
        List<PhieuDatPhong> dsLichSu = new ArrayList<>();

        if (danhSachPhieu != null) {
            for (PhieuDatPhong p : danhSachPhieu) {
                if (p.getPhong() != null && p.getPhong().getSoPhong() == soPhong) {
                    dsLichSu.add(p);
                }
            }
        }

        return dsLichSu;
    }
}
