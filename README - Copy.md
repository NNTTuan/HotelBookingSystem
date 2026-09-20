hotel-booking-system/

├── pom.xml                            # \[VỊ TRÍ CHUẨN] Nằm ở thư mục gốc dự án

├── MERMAID.md

├── README.md

│

└── src/

&#x20;   └── main/

&#x20;       ├── java/                      # Chứa toàn bộ Package \& Mã nguồn Java

&#x20;       │   └── com/

&#x20;       │       └── hotel/

&#x20;       │           ├── model/

&#x20;       │           │   ├── PhongKhachSan.java

&#x20;       │           │   ├── PhongStandard.java

&#x20;       │           │   ├── PhongVIP.java

&#x20;       │           │   ├── PhongPenthouse.java

&#x20;       │           │   ├── IDiscountable.java

&#x20;       │           │   ├── IServiceChargable.java

&#x20;       │           │   ├── KhachHang.java

&#x20;       │           │   └── PhieuDatPhong.java

&#x20;       │           │

&#x20;       │           ├── view/

&#x20;       │           │   ├── MainFrame.java

&#x20;       │           │   ├── CheckInPanel.java

&#x20;       │           │   ├── CheckOutPanel.java

&#x20;       │           │   ├── QuanLyPhongPanel.java

&#x20;       │           │   └── components/

&#x20;       │           │       ├── CameraPreviewDialog.java

&#x20;       │           │       └── ImageAvatarPanel.java

&#x20;       │           │

&#x20;       │           ├── service/

&#x20;       │           │   ├── QuanLyKhachSan.java

&#x20;       │           │   ├── CameraService.java

&#x20;       │           │   ├── OcrService.java

&#x20;       │           │   └── FaceDetectionService.java

&#x20;       │           │

&#x20;       │           ├── util/

&#x20;       │           │   ├── ImageUtils.java

&#x20;       │           │   └── SwingUtils.java

&#x20;       │           │

&#x20;       │           └── Main.java

&#x20;       │

&#x20;       └── resources/                 # Chứa dữ liệu AI, Icons, Ảnh chụp

&#x20;           ├── haarcascades/

&#x20;           │   └── haarcascade\_frontalface\_alt.xml

&#x20;           ├── tessdata/

&#x20;           │   ├── eng.traineddata

&#x20;           │   └── vie.traineddata

&#x20;           ├── icons/

&#x20;           └── captures/

