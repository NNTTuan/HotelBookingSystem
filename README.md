# Hệ Thống Quản Lý Khách Sạn (Java Swing App)

Ứng dụng quản lý khách sạn thuần Java (Java Swing Desktop GUI) hỗ trợ quản lý sơ đồ 100 phòng, quy trình Check-in / Check-out, chụp ảnh diện mạo khách hàng qua Webcam và tự động đồng bộ dữ liệu vào các file text (`.txt`) ở thư mục gốc mà không cần sử dụng CSDL SQL.

---

## 🌟 Tính Năng Chính

* **Quản Lý 100 Phòng Khách Sạn (10 Tầng x 10 Phòng):**
    * Tự động khởi tạo và quản lý 100 phòng (từ phòng `101` đến `1010`).
    * Phân tầng theo class đối tượng:
        * **Tầng 1 – 4:** Phòng Standard (`PhongStandard`)
        * **Tầng 5 – 8:** Phòng VIP (`PhongVIP`)
        * **Tầng 9 – 10:** Phòng Penthouse (`PhongPenthouse`)
* **Sơ Đồ Lưới Trực Quan (Grid 10x10):**
    * Hiển thị trạng thái phòng theo màu sắc thời gian thực:
        * 🟢 **Xanh lá:** Phòng Trống (`TRONG`)
        * 🔴 **Đỏ:** Đang có khách (`DANG_O`)
        * 🟡 **Vàng:** Chờ dọn dẹp (`DANG_DON`)
* **Quy Trình Check-In & Check-Out:**
    * **Check-In:** Nhân viên nhập tay thông tin khách (Họ tên, CCCD/Hộ chiếu, SĐT) + Bật Webcam chụp ảnh chân dung.
    * **Check-Out:** Trả phòng, chuyển trạng thái về "Chờ dọn dẹp".
    * **Xác nhận dọn dẹp:** Đưa phòng về lại trạng thái "Trống" sẵn sàng nhận khách tiếp theo.
* **Tích Hợp Webcam Chụp Ảnh:**
    * Điều khiển Camera qua `CameraService` và `CameraPreviewDialog` để xem trước và chụp lưu ảnh khách hàng vào thư mục `data/images/`.
* **Lưu Trữ Dữ Liệu Thuần Java File (Java I/O):**
    * Không dùng Database/SQL. Tất cả trạng thái 100 phòng và hồ sơ khách hàng được đọc/ghi tự động qua các file flat-text trong thư mục `data/`.

---

## 📂 Cấu Trúc Thư Mục Dự Án

```text
📦 hotel-booking-system
├── 📄 pom.xml
├── 📊 MERMAID.md
├── 📜 README.md
└── 📁 src
    └── 📁 main
        ├── 📁 java
        │   └── 📁 com/hotel
        │       ├── 📁 model
        │       │   ├── 📄 PhongKhachSan.java
        │       │   ├── 📄 PhongStandard.java
        │       │   ├── 📄 PhongVIP.java
        │       │   ├── 📄 PhongPenthouse.java
        │       │   ├── 📄 IDiscountable.java
        │       │   ├── 📄 IServiceChargable.java
        │       │   ├── 📄 KhachHang.java
        │       │   └── 📄 PhieuDatPhong.java
        │       ├── 📁 view
        │       │   ├── 📄 MainFrame.java
        │       │   ├── 📄 CheckInPanel.java
        │       │   ├── 📄 CheckOutPanel.java
        │       │   ├── 📄 QuanLyPhongPanel.java
        │       │   └── 📁 components
        │       │       ├── 📄 CameraPreviewDialog.java
        │       │       └── 📄 ImageAvatarPanel.java
        │       ├── 📁 service
        │       │   ├── 📄 QuanLyKhachSan.java
        │       │   ├── 📄 CameraService.java
        │       │   ├── 📄 OcrService.java
        │       │   └── 📄 FaceDetectionService.java
        │       ├── 📁 util
        │       │   ├── 📄 ImageUtils.java
        │       │   └── 📄 SwingUtils.java
        │       └── 🚀 Main.java
        └── 📁 resources
            ├── 📁 data
            │   └── 📄 phong.csv
            └── 📁 haarcascades