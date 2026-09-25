classDiagram
%% ================= TẦNG MODEL (ENTITIES) =================
class PhongKhachSan {
<<abstract>>
#String maPhong
#double giaGoc
+double THUE_VAT$
-int tongSoPhong$
+PhongKhachSan(String maPhong, double giaGoc)
+tinhTienThue(int soNgay)* double
+getTongSoPhong()$ int
+getMaPhong() String
+getGiaGoc() double
}

    class PhongStandard {
        +PhongStandard(String maPhong, double giaGoc)
        +tinhTienThue(int soNgay) double
    }

    class PhongVIP {
        +PhongVIP(String maPhong, double giaGoc)
        +tinhTienThue(int soNgay) double
        +tinhGiamGia() double
        +tinhPhiDichVu() double
    }

    class PhongPenthouse {
        +PhongPenthouse(String maPhong, double giaGoc)
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
        +getSoCCCD() String
        +getHoTen() String
        +getNgaySinh() String
        +getDuongDanAnhMat() String
    }

    class PhieuDatPhong {
        -String maPhieu
        -PhongKhachSan phong
        -KhachHang khachHang
        -int soNgayThue
        +PhieuDatPhong(String maPhieu, PhongKhachSan p, KhachHang kh, int soNgay)
        +tinhTongTienPhieu() double
        +getMaPhieu() String
        +getPhong() PhongKhachSan
        +getKhachHang() KhachHang
    }

    %% ================= TẦNG SERVICE (BUSINESS LOGIC) =================
    class QuanLyKhachSan {
        -List~PhongKhachSan~ dsPhong
        -List~PhieuDatPhong~ dsPhieuDat
        +themPhong(PhongKhachSan p) void
        +taoPhieuDat(KhachHang kh, PhongKhachSan p, int soNgay) PhieuDatPhong
        +top3PhongGiaCaoNhat(int soNgay) List~PhongKhachSan~
        +loadDataTuFile(String path) void
        +saveDataVaoFile(String path) void
    }

    class CameraService {
        -boolean isRunning
        +startCamera() void
        +stopCamera() void
        +captureFrame() BufferedImage
        +isRunning() boolean
    }

    class OcrService {
        -ITesseract tesseract
        +OcrService()
        +trichXuatThongTinCCCD(BufferedImage image) KhachHang
        -preprocessImage(BufferedImage image) BufferedImage
        -parseCCCD(String text) String
        -parseHoTen(String text) String
        -parseNgaySinh(String text) String
    }

    class FaceDetectionService {
        -CascadeClassifier faceDetector
        +FaceDetectionService()
        +phatHienVaCropMat(BufferedImage frame) BufferedImage
        -convertBufferedImageToMat(BufferedImage img) Mat
        -convertMatToBufferedImage(Mat mat) BufferedImage
    }

    %% ================= TẦNG UTIL =================
    class ImageUtils {
        +resizeImage(BufferedImage img, int w, int h)$ BufferedImage
        +saveImage(BufferedImage img, String path)$ void
        +convertToGrayscale(BufferedImage img)$ BufferedImage
    }

    class SwingUtils {
        +FONT_REGULAR$ Font
        +FONT_BOLD$ Font
        +FONT_TITLE$ Font
        +setCustomFont(Component comp)$ void
        +setCustomFont(Component comp, Font font)$ void
        +showErrorDialog(Component parent, String msg)$ void
        +showErrorDialog(String msg)$ void
        +showSuccessDialog(Component parent, String msg)$ void
        +showSuccessDialog(String msg)$ void
    }

    %% ================= TẦNG VIEW & COMPONENTS =================
    class MainFrame {
        -QuanLyKhachSan qlkh
        -CardLayout cardLayout
        -JPanel mainContentPanel
        +MainFrame()
        +showPanel(String name) void
    }

    class CheckInPanel {
        -CameraService cameraService
        -OcrService ocrService
        -FaceDetectionService faceService
        -JTextField txtCCCD
        -JTextField txtHoTen
        -JTextField txtNgaySinh
        +CheckInPanel(QuanLyKhachSan qlkh)
        +xuLyQuetCCCD() void
        +xuLyChupAnhMat() void
        +xacNhanCheckIn() void
    }

    class CheckOutPanel {
        -QuanLyKhachSan qlkh
        -JTextField txtMaPhieu
        +CheckOutPanel(QuanLyKhachSan qlkh)
        +xuLyCheckOut() void
        +inHoaDon() void
    }

    class QuanLyPhongPanel {
        -QuanLyKhachSan qlkh
        -JTable tablePhong
        +QuanLyPhongPanel(QuanLyKhachSan qlkh)
        +capNhatDanhSachPhong() void
    }

    class CameraPreviewDialog {
        -JLabel lblPreview
        +CameraPreviewDialog(Frame owner)
        +capNhatKhungHinh(BufferedImage frame) void
    }

    class ImageAvatarPanel {
        -BufferedImage avatar
        +ImageAvatarPanel()
        +setAvatar(BufferedImage img) void
        #paintComponent(Graphics g) void
    }

    %% ================= ENTRY POINT =================
    class Main {
        +main(String[] args)$ void
    }

    %% ================= QUAN HỆ KẾ THỪA & IMPLEMENTS =================
    PhongKhachSan <|-- PhongStandard
    PhongKhachSan <|-- PhongVIP
    PhongKhachSan <|-- PhongPenthouse
    IDiscountable <|.. PhongVIP
    IServiceChargable <|.. PhongVIP

    %% ================= QUAN HỆ CẤU TRÚC & TƯƠNG TÁC =================
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
    CheckInPanel ..> SwingUtils

    CheckOutPanel ..> QuanLyKhachSan
    CheckOutPanel ..> SwingUtils

    QuanLyPhongPanel ..> QuanLyKhachSan
    QuanLyPhongPanel ..> SwingUtils