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
    * Điều khiển Camera qua `CameraService` và `CameraPreviewDialog` để xem trước và chụp lưu ảnh khách hàng vào thư mục `src/main/resources/captures/`.
* **Lưu Trữ Dữ Liệu Thuần Java File (Java I/O):**
    * Không dùng Database/SQL. Tất cả trạng thái 100 phòng và hồ sơ khách hàng được đọc/ghi tự động qua các file flat-text trong thư mục `data/`.

---

## 📂 Cấu Trúc Thư Mục Dự Án (Dọn Dẹp & Cập Nhật)

```text
ThumucDuAn/                             <-- Thư mục gốc (Root Project)
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── hotel/
│       │           ├── model/          # Mô hình dữ liệu
│       │           │   ├── IDiscountable.java
│       │           │   ├── IServiceChargable.java
│       │           │   ├── KhachHang.java
│       │           │   ├── PhongKhachSan.java
│       │           │   ├── PhongStandard.java
│       │           │   ├── PhongVIP.java
│       │           │   └── PhongPenthouse.java
│       │           ├── service/        # Dịch vụ nghiệp vụ chính
│       │           │   ├── CameraService.java
│       │           │   └── QuanLyKhachSan.java (Xử lý 100 phòng, Check-In/Out & Lưu File)
│       │           ├── util/           # Tiện ích bổ trợ UI/Ảnh
│       │           │   ├── ImageUtils.java
│       │           │   └── SwingUtils.java
│       │           ├── view/           # Màn hình giao diện Swing GUI
│       │           │   ├── components/
│       │           │   │   ├── CameraPreviewDialog.java
│       │           │   │   └── ImageAvatarPanel.java
│       │           │   ├── CheckInPanel.java
│       │           │   ├── CheckOutPanel.java
│       │           │   ├── QuanLyPhongPanel.java
│       │           │   └── MainFrame.java
│       │           └── Main.java       # Khởi chạy ứng dụng
│       └── resources/
│           └── captures/               # Lưu ảnh chân dung chụp từ Webcam
├── data/                               # Nằm CÙNG CẤP với src/ (Tự động tạo ra)
│   ├── phong.txt                       # Trạng thái 100 phòng
│   └── khachhang.txt                   # Danh sách khách hàng
├── README.md
└── pom.xml

📦HotelBookingSystemTest
 ┣ 📂.idea
 ┣ 📂data
 ┃ ┣ 📂images
 ┃ ┣ 📜khachhang.txt
 ┃ ┣ 📜phieudatphong.txt
 ┃ ┗ 📜phong.csv
 ┣ 📂src
 ┃ ┗ 📂main
 ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┗ 📂hotel
 ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜IDiscountable.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜IServiceChargable.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜KhachHang.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PhieuDatPhong.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PhongKhachSan.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PhongPenthouse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PhongStandard.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PhongVIP.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CameraService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜QuanLyKhachSan.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂util
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ImageUtils.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SwingUtils.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂view
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂components
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CameraPreviewDialog.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ImageAvatarPanel.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CheckInPanel.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CheckOutPanel.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MainFrame.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜QuanLyPhongPanel.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜Main.java
 ┃ ┃ ┗ 📂resources
 ┃ ┃ ┃ ┣ 📂captures
 ┃ ┃ ┃ ┗ 📂haarcascades
 ┃ ┃ ┃ ┃ ┗ 📜haarcascade_frontalface_alt.xml
 ┣ 📂target
 ┃ ┣ 📂classes
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂hotel
 ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┣ 📂util
 ┃ ┃ ┃ ┃ ┗ 📂view
 ┃ ┃ ┃ ┃ ┃ ┗ 📂components
 ┃ ┣ 📂generated-sources
 ┃ ┃ ┗ 📂annotations
 ┃ ┗ 📂test-classes
 ┣ 📂tessdata
 ┃ ┣ 📜eng.traineddata
 ┃ ┗ 📜vie.traineddata
 ┣ 📜.gitignore
 ┣ 📜pom.xml
 ┣ 📜README-copy.md
 ┗ 📜README.md