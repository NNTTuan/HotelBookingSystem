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

    %% ================= TẦNG SERVICE (BUSINESS LOGIC) =================
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

    %% ================= TẦNG VIEW (SWING UI) =================
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

    class CameraPreviewDialog {
        +capNhatKhungHinh(BufferedImage frame) void
    }

    %% ================= QUAN HỆ KẾ THỪA & IMPLEMENTS =================
    PhongKhachSan <|-- PhongStandard : extends
    PhongKhachSan <|-- PhongVIP : extends
    PhongKhachSan <|-- PhongPenthouse : extends
    IDiscountable <|.. PhongVIP : implements
    IServiceChargable <|.. PhongVIP : implements

    %% ================= QUAN HỆ CẤU TRÚC & TƯƠNG TÁC =================
    PhieuDatPhong "1" --> "1" PhongKhachSan : tham chiếu
    PhieuDatPhong "1" --> "1" KhachHang : thuộc về
    QuanLyKhachSan "1" o-- "*" PhongKhachSan : quản lý
    QuanLyKhachSan "1" o-- "*" PhieuDatPhong : quản lý

    MainFrame "1" *-- "1" CheckInPanel : chứa
    CheckInPanel ..> CameraService : sử dụng
    CheckInPanel ..> OcrService : sử dụng
    CheckInPanel ..> FaceDetectionService : sử dụng
    CheckInPanel ..> CameraPreviewDialog : hiển thị UI