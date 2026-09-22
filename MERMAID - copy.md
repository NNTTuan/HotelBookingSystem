classDiagram
%% ================= TẦNG MODEL =================
class PhongKhachSan {
<<abstract>>
#String maPhong
#double giaGoc
+double THUE_VAT$
-int tongSoPhong$
+PhongKhachSan(String maPhong, double giaGoc)
+tinhTienThue(int soNgay)* double
+getTongSoPhong()$ int
}

    class PhongStandard {
        +tinhTienThue(int soNgay) double
    }

    class PhongVIP {
        +tinhTienThue(int soNgay) double
        +tinhGiamGia() double
        +tinhPhiDichVu() double
    }

    class PhongPenthouse {
        +tinhTienThue(int soNgay) double
    }

    class IDiscountable {
        <<interface>>
        +tinhGiamGia() double
    }

    class IServiceChargable {
        <<interface>>
        +tinhPhiDichVu() double
    }

    class KhachHang {
        -String soCCCD
        -String hoTen
        -String ngaySinh
        -String duongDanAnhMat
        +KhachHang(String soCCCD, String hoTen, String ngaySinh)
        +setDuongDanAnhMat(String path) void
    }

    class PhieuDatPhong {
        -String maPhieu
        -PhongKhachSan phong
        -KhachHang khachHang
        -int soNgayThue
        +PhieuDatPhong(String maPhieu, PhongKhachSan p, KhachHang kh, int soNgay)
        +tinhTongTienPhieu() double
    }

    %% ================= TẦNG SERVICE =================
    class QuanLyKhachSan {
        -List~PhongKhachSan~ dsPhong
        -List~PhieuDatPhong~ dsPhieuDat
        +themPhong(PhongKhachSan p) void
        +taoPhieuDat(KhachHang kh, PhongKhachSan p, int soNgay) PhieuDatPhong
        +top3PhongGiaCaoNhat(int soNgay) List~PhongKhachSan~
    }

    class CameraService {
        -boolean isRunning
        +startCamera() void
        +stopCamera() void
        +captureFrame() BufferedImage
    }

    class OcrService {
        -ITesseract tesseract
        +trichXuatThongTinCCCD(BufferedImage image) KhachHang
    }

    class FaceDetectionService {
        -CascadeClassifier faceDetector
        +phatHienVaCropMat(BufferedImage frame) BufferedImage
    }

    %% ================= TẦNG UTIL =================
    class ImageUtils {
        +resizeImage(BufferedImage img, int w, int h)$ BufferedImage
        +saveImage(BufferedImage img, String path)$ void
    }

    class SwingUtils {
        +setCustomFont(Component comp)$ void
        +showErrorDialog(String msg)$ void
    }

    %% ================= TẦNG VIEW & COMPONENTS =================
    class MainFrame {
        -QuanLyKhachSan qlkh
        +showPanel(String name) void
    }

    class CheckInPanel {
        -CameraService cameraService
        -OcrService ocrService
        -FaceDetectionService faceService
        +xuLyQuetCCCD() void
        +xuLyChupAnhMat() void
        +xacNhanCheckIn() void
    }

    class CheckOutPanel {
        -QuanLyKhachSan qlkh
        +xuLyCheckOut() void
        +inHoaDon() void
    }

    class QuanLyPhongPanel {
        -QuanLyKhachSan qlkh
        +capNhatDanhSachPhong() void
    }

    class CameraPreviewDialog {
        +capNhatKhungHinh(BufferedImage frame) void
    }

    class ImageAvatarPanel {
        -BufferedImage avatar
        +setAvatar(BufferedImage img) void
    }

    %% ================= ENTRY POINT =================
    class Main {
        +main(String[] args)$ void
    }

    %% ================= KẾ THỪA & IMPLEMENTS =================
    PhongKhachSan <|-- PhongStandard
    PhongKhachSan <|-- PhongVIP
    PhongKhachSan <|-- PhongPenthouse
    IDiscountable <|.. PhongVIP
    IServiceChargable <|.. PhongVIP

    %% ================= CẤU TRÚC & TƯƠNG TÁC =================
    PhieuDatPhong "1" --> "1" PhongKhachSan
    PhieuDatPhong "1" --> "1" KhachHang
    QuanLyKhachSan "1" o-- "*" PhongKhachSan
    QuanLyKhachSan "1" o-- "*" PhieuDatPhong

    Main ..> MainFrame : khởi chạy GUI

    MainFrame "1" *-- "1" QuanLyKhachSan : sở hữu
    MainFrame "1" *-- "1" CheckInPanel : chứa
    MainFrame "1" *-- "1" CheckOutPanel : chứa
    MainFrame "1" *-- "1" QuanLyPhongPanel : chứa

    CheckInPanel ..> CameraService
    CheckInPanel ..> OcrService
    CheckInPanel ..> FaceDetectionService
    CheckInPanel ..> CameraPreviewDialog
    CheckInPanel ..> ImageAvatarPanel
    CheckInPanel ..> ImageUtils

    CheckOutPanel ..> QuanLyKhachSan
    QuanLyPhongPanel ..> QuanLyKhachSan