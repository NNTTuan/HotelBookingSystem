hotel-booking-system/
├── pom.xml                               # File cấu hình Maven & quản lý thư viện
├── MERMAID.md                            # Sơ đồ thiết kế hệ thống (Class & Sequence Diagram)
├── README.md                             # Tài liệu hướng dẫn dự án
│
└── src/
└── main/
├── java/                         # Mã nguồn Java chính
│   └── com/
│       └── hotel/
│           ├── model/            # TẦNG THỰC THỂ (ENTITIES)
│           │   ├── PhongKhachSan.java
│           │   ├── PhongStandard.java
│           │   ├── PhongVIP.java
│           │   ├── PhongPenthouse.java
│           │   ├── IDiscountable.java
│           │   ├── IServiceChargable.java
│           │   ├── KhachHang.java
│           │   └── PhieuDatPhong.java
│           │
│           ├── view/             # TẦNG GIAO DIỆN (JAVA SWING UI)
│           │   ├── MainFrame.java
│           │   ├── CheckInPanel.java
│           │   ├── CheckOutPanel.java
│           │   ├── QuanLyPhongPanel.java
│           │   └── components/
│           │       ├── CameraPreviewDialog.java
│           │       └── ImageAvatarPanel.java
│           │
│           ├── service/          # TẦNG XỬ LÝ NGHIỆP VỤ & AI/CAMERA
│           │   ├── QuanLyKhachSan.java
│           │   ├── CameraService.java
│           │   ├── OcrService.java
│           │   └── FaceDetectionService.java
│           │
│           ├── util/             # TẦNG TIỆN ÍCH (HELPERS)
│           │   ├── ImageUtils.java
│           │   └── SwingUtils.java
│           │
│           └── Main.java         # Khởi chạy ứng dụng (Main Entry Point)
│
└── resources/                    # TÀI NGUYÊN TĨNH & DỮ LIỆU AI
├── data/
│   └── phong.csv             # Tập tin dữ liệu 50 phòng mẫu
├── haarcascades/
│   └── haarcascade_frontalface_alt.xml
├── tessdata/
│   ├── eng.traineddata
│   └── vie.traineddata
├── icons/                    # Biểu tượng & Hình ảnh UI
└── captures/                 # Ảnh chụp CCCD & Chân dung khách hàng