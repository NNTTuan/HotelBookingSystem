hotel-booking-system/

├── .idea/                             # Cấu hình dự án (IntelliJ IDEA)

├── .vscode/                           # Cấu hình dự án (VS Code)

├── lib/                               # Chứa các file .jar thư viện ngoài (Webcam, Tesseract, OpenCV)

│   ├── ffmpeg-4.4-1.5.6-javadoc.jar

│   ├── javacv-1.5.9.jar

│   ├── slf4j-api-1.7.36.jar

│   ├── slf4j-simple-1.7.36.jar

│   └── webcam-capture-0.3.12.jar

├── resources/                         # Chứa tài nguyên động và tập tin mô hình AI

│   ├── haarcascades/                  # Chứa file XML nhận diện khuôn mặt (haarcascade\_frontalface\_alt.xml)

│   │	└── aarcascade\_frontalface\_alt.xml

│   ├── tessdata/                      # Chứa dữ liệu huấn luyện Tesseract OCR (vie.traineddata cho tiếng Việt)

│   │   ├── eng.traineddata            

│   │   └── vie.traineddata

│   ├── icons/                         # Chứa biểu tượng UI (JPG, PNG)

│   └── captures/                      # Thư mục lưu trữ ảnh chụp CCCD \& chân dung khách hàng

│

├── src/

│    └── com/

│        └── hotel/

│            ├── model/                 # TẦNG THỰC THỂ (ENTITIES)

│            │   ├── PhongKhachSan.java            # Abstract Base Class

│            │   ├── PhongStandard.java            # Lớp con

│            │   ├── PhongVIP.java                 # Lớp con + triển khai Interface

│            │   ├── PhongPenthouse.java           # Lớp con

│            │   ├── IDiscountable.java            # Interface giảm giá

│            │   ├── IServiceChargable.java        # Interface phí dịch vụ

│            │   ├── KhachHang.java                # \[MỚI] Thực thể thông tin khách (CCCD, Họ tên, Ngày sinh, Ảnh mặt)

│            │   └── PhieuDatPhong.java            # \[MỚI] Thực thể liên kết Phòng - Khách hàng - Thời gian đặt/trả

│            │

│            ├── view/                  # TẦNG GIAO DIỆN NGHỆ THUẬT (SWING UI - JFRAME \& JPANEL)

│            │   ├── MainFrame.java                # Cửa sổ chính (JFrame chứa SidebarNavigation \& CardLayout)

│            │   ├── CheckInPanel.java             # Panel xử lý đặt phòng \& quét dữ liệu

│            │   ├── CheckOutPanel.java            # Panel xử lý trả phòng \& in hóa đơn

│            │   ├── QuanLyPhongPanel.java         # Panel sơ đồ danh sách phòng (Grid/Table View)

│            │   └── components/                   # Dựng các Component Swing tùy biến

│            │       ├── CameraPreviewDialog.java  # JDialog hiển thị luồng Video trực tiếp từ Webcam

│            │       └── ImageAvatarPanel.java     # Panel vẽ ảnh đại diện khuôn mặt cắt từ Camera

│            │

│            ├── service/               # TẦNG XỬ LÝ NGHIỆP VỤ (BUSINESS LOGIC)

│            │   ├── QuanLyKhachSan.java           # Quản lý danh sách, tìm kiếm Top 3 bằng Stream API

│            │   ├── CameraService.java            # \[MỚI] Quản lý kết nối luồng Video từ Webcam (Start/Stop/Snapshot)

│            │   ├── OcrService.java               # \[MỚI] Xử lý tiền xử lý ảnh \& trích xuất văn bản từ CCCD

│            │   └── FaceDetectionService.java     # \[MỚI] Phát hiện, cắt vùng khuôn mặt (Bounding Box) từ luồng ảnh

│            │

│            ├── util/                  # TẦNG TIỆN ÍCH (HELPER / UTILITIES)

│            │   ├── ImageUtils.java               # Chuyển đổi BufferedImage, Crop, Resize, xoay ảnh

│            │   └── SwingUtils.java               # Hỗ trợ tùy chỉnh giao diện (Look \& Feel, Dialogs)

│            │

│            └── Main.java              # Điểm khởi chạy ứng dụng (SwingUtilities.invokeLater)

├── MERMAID.md

└── README.md

