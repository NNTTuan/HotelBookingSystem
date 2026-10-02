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

## ✨ Tính Năng Nổi Bật

- 🛏️ **Quản lý sơ đồ phòng trực quan**: Hiển thị danh sách 100 phòng (Standard, VIP, Penthouse), cập nhật trạng thái thời gian thực (*Trống / Đang ở*).
- 📸 **Quy trình Check-in thông minh**:
    - 👤 Nhập & quản lý thông tin khách hàng (Họ tên, CCCD/CMND, Số điện thoại).
    - 🎥 Tích hợp **Camera preview** live & tự động **Nhận diện khuôn mặt** bằng thuật toán OpenCV Haar Cascade.
    - 📄 Trích xuất dữ liệu tự động từ ảnh CCCD/CMND qua **Tess4J OCR** (hỗ trợ Tiếng Việt & Tiếng Anh).
- 💳 **Quy trình Check-out & Thanh toán tự động**:
    - 📅 Tự động tính tiền lưu trú chính xác theo số ngày ở.
    - 🛎️ Áp dụng linh hoạt Phí dịch vụ (`IServiceChargable`) và Giảm giá (`IDiscountable`) theo từng hạng phòng.
    - 🧾 Tự động hạch toán Thuế VAT **10%**.
    - 🪟 Hiển thị Cửa sổ Hóa đơn chi tiết (`Chi Tiết Hóa Đơn`) và hỗ trợ **In hóa đơn**.
- 💾 **Lưu trữ dữ liệu an toàn**: Quản lý dữ liệu bền vững qua các tập tin văn bản (`phong.txt`, `phieudatphong.txt`) và kho ảnh chân dung khách hàng.

---

## 📂 Cấu Trúc Dự Án (Project Structure)

```text
Hotel Booking System/
├── ⚙️️ .idea/                           # Cấu hình dự án IntelliJ IDEA
├── 📊 data/                            # File dữ liệu & kho lưu trữ hình ảnh
│   ├── 🖼️ images/                      # Thư mục lưu ảnh chân dung khách check-in
│   ├── 📝 phieudatphong.txt            # Cơ sở dữ liệu danh sách phiếu đặt phòng
│   └── 🏨 phong.txt                    # Cơ sở dữ liệu danh sách phòng
├── 📚 docs/                            # Tài liệu phân tích & kiến trúc hệ thống
│   └── 📐 diagrams/                    # Hệ thống sơ đồ thiết kế (Mermaid Format)
│       ├── 🔄 activity_checkin.mmd     # Sơ đồ hoạt động Check-in
│       ├── 🏗️ architecture.mmd         # Kiến trúc hệ thống
│       ├── 🧩 class_diagram.mmd        # Sơ đồ lớp (Class Diagram)
│       ├── 📦 component_diagram.mmd    # Sơ đồ thành phần
│       ├── 🗄️ erd_data_model.mmd       # Mô hình dữ liệu ERD
│       ├── 🗂️ package_diagram.mmd      # Sơ đồ đóng gói Package
│       ├── 🔄 room_state.mmd           # Sơ đồ chuyển đổi trạng thái phòng
│       ├── ⏱️ sequence_checkin.mmd     # Sơ đồ tuần tự Check-in
│       ├── ⏱️ sequence_checkout.mmd    # Sơ đồ tuần tự Check-out
│       └── 🎯 usecase_diagram.mmd      # Sơ đồ Use Case
├── 💻 src/
│   └── ☕ main/
│       ├── ☕ java/
│       │   └── 📦 com/hotel/
│       │       ├── 🏛️ model/           # Layer Lớp đối tượng & Interface OOP
│       │       │   ├── 🏷️ IDiscountable.java
│       │       │   ├── 🛎️ IServiceChargable.java
│       │       │   ├── 👤 KhachHang.java
│       │       │   ├── 🧾 PhieuDatPhong.java
│       │       │   ├── 🛏️ PhongKhachSan.java
│       │       │   ├── 🌟 PhongPenthouse.java
│       │       │   ├── 🏠 PhongStandard.java
│       │       │   └── 💎 PhongVIP.java
│       │       ├── ⚙️ service/         # Layer Xử lý nghiệp vụ & Tích hợp AI
│       │       │   ├── 📸 CameraService.java
│       │       │   └── 🏨 QuanLyKhachSan.java
│       │       ├── 🛠️ util/            # Utility classes (Xử lý ảnh, GUI layout)
│       │       │   ├── 🖼️ ImageUtils.java
│       │       │   └── 🎨 SwingUtils.java
│       │       ├── 🎨 view/            # Layer Giao diện người dùng (Java Swing)
│       │       │   ├── 🧩 components/
│       │       │   │   ├── 📷 CameraPreviewDialog.java
│       │       │   │   └── 🖼️ ImageAvatarPanel.java
│       │       │   ├── 📥 CheckInPanel.java
│       │       │   ├── 📤 CheckOutPanel.java
│       │       │   ├── 🖥️ MainFrame.java
│       │       │   └── 🗺️ QuanLyPhongPanel.java
│       │       └── 🚀 Main.java        # Điểm khởi chạy ứng dụng (Main Entry Point)
│       └── 📦 resources/               # Tài nguyên AI & Model dữ liệu
│           ├── 👁️ haarcascades/
│           │   └── 🎯 haarcascade_frontalface_alt.xml  # Haar Cascade Face Detection Model
│           └── 🔤 tessdata/
│               ├── 🇬🇧 eng.traineddata                   # Model OCR Tiếng Anh
│               └── 🇻🇳 vie.traineddata                   # Model OCR Tiếng Việt
└── 📖 README.md
```

---

## 🛠️ Công Nghệ & Thư Viện Sử Dụng

- ☕ **Ngôn ngữ chính**: Java (JDK 17+)  
- 🎨 **Giao diện (GUI)**: Java Swing, AWT, FlatLaf Look & Feel  
- 🤖 **Xử lý hình ảnh & Trí tuệ nhân tạo (AI)**:  
  - 📷 **OpenCV**: Xử lý luồng Webcam & tự động khung nhận diện khuôn mặt (`haarcascade_frontalface_alt.xml`).  
  - 👁️ **Tess4J (Tesseract OCR)**: Nhận diện văn bản từ ảnh CCCD/CMND (`vie.traineddata`).  
- 📊 **Tài liệu & Thiết kế**: Mermaid Diagrams (`.mmd`)

---

## 🏛️ Thiết Kế Hướng Đối Tượng & Hạng Phòng (OOP)

Hệ thống được thiết kế chặt chẽ theo các nguyên lý **Lập trình hướng đối tượng (OOP)**:

| 🛏️ Hạng Phòng | 💵 Giá Niêm Yết | 🛎️ Phí Dịch Vụ (`IServiceChargable`) | 🏷️ Giảm Giá (`IDiscountable`) | 🧾 Thuế VAT |
| :---- | :---: | :---: | :---: | :---: |
| 🏠 **PhongStandard** | `500.000 VNĐ` | ❌ Không | ❌ Không | `10%` |
| 💎 **PhongVIP** | `1.200.000 VNĐ` | ✅ `+200.000 VNĐ` | ✅ `-5%` | `10%` |
| 🌟 **PhongPenthouse** | `2.500.000 VNĐ` | ✅ `+500.000 VNĐ` | ✅ `-10%` | `10%` |

### 🧮 Công thức hạch toán hóa đơn:

\$\$\\text{Tiền Trước Thuế} \= (\\text{Giá Gốc} \\times \\text{Số Ngày}) \+ \\text{Phí Dịch Vụ} \- \\text{Tiền Giảm Giá}\$\$ \$\$\\text{Thuế VAT} \= \\text{Tiền Trước Thuế} \\times 10%\$\$ \$\$\\text{TỔNG THANH TOÁN} \= \\text{Tiền Trước Thuế} \+ \\text{Thuế VAT}\$\$

---

## 🚀 Hướng Dẫn Cài Đặt & Chạy Ứng Dụng

### 🖥️ Yêu cầu môi trường:

- ☕ **Java Development Kit (JDK)**: Phiên bản 17 trở lên.  
- 💻 **IDE khuyến nghị**: IntelliJ IDEA / Eclipse / NetBeans.  
- 📷 **Phần cứng**: Webcam (để trải nghiệm chức năng chụp ảnh & nhận diện khuôn mặt).

### 🛠️ Các bước thực hiện:

1. 🧬 **Clone dự án về máy:**  
     
   git clone \<repository\_url\>  
     
2. 📂 **Mở dự án** trong IntelliJ IDEA hoặc IDE yêu thích của bạn.  
3. ⚙️ **Cấu hình SDK**: Đảm bảo cấu hình dự án nhận đúng JDK 17+.  
4. ▶️ **Khởi chạy**: Mở tập tin `src/main/java/com/hotel/Main.java` và nhấn **Run** (`Shift + F10`).

---

## 📊 Sơ Đồ Thiết Kế Hệ Thống (Mermaid Diagrams)

Toàn bộ tài liệu thiết kế hệ thống nằm trong thư mục `docs/diagrams/`:

- 🎯 `usecase_diagram.mmd`: Tổng quan tất cả chức năng ứng dụng.  
- 🧩 `class_diagram.mmd`: Sơ đồ Lớp mô tả chi tiết thuộc tính & phương thức.  
- ⏱️ `sequence_checkin.mmd` & `sequence_checkout.mmd`: Sơ đồ tuần tự cho luồng nhận phòng & trả phòng.  
- 🔄 `room_state.mmd`: Mô hình vòng đời chuyển đổi trạng thái phòng.

---

## 👤 Tác Giả & Bản Quyền

- 🧑‍💻 **Tác giả**: Nguyễn Ngọc Trọng Tuân  
- 🏫 **Đơn vị**: Đại học Kinh tế \- Tài chính TP.HCM (UEF)  
- 📌 **Mục đích**: Đồ án môn học / Dự án Lập trình hướng đối tượng Java.