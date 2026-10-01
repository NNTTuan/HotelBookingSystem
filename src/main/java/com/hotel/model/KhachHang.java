package com.hotel.model;

public class KhachHang {
    private String hoTen;
    private String soCCCD;
    private String soDienThoai;
    private String anhKhuonMatPath;

    public KhachHang(String hoTen, String soCCCD, String soDienThoai, String anhKhuonMatPath) {
        this.hoTen = hoTen;
        this.soCCCD = soCCCD;
        this.soDienThoai = soDienThoai;
        this.anhKhuonMatPath = anhKhuonMatPath;
    }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getSoCCCD() { return soCCCD; }
    public void setSoCCCD(String soCCCD) { this.soCCCD = soCCCD; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getAnhKhuonMatPath() { return anhKhuonMatPath; }
    public void setAnhKhuonMatPath(String anhKhuonMatPath) { this.anhKhuonMatPath = anhKhuonMatPath; }
}