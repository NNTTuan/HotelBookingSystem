# Hệ Thống Quản Lý Khách Sạn (Java Swing App & Console)

Ứng dụng quản lý khách sạn hỗ trợ song song 2 chế độ (Console CLI và Java Swing Desktop GUI). Hệ thống quản lý sơ đồ 20 phòng, quy trình Check-in/Check-out linh hoạt với thời gian lưu trú (`soNgayThue`), chụp ảnh diện mạo khách hàng qua Webcam thực tế và tự động đồng bộ dữ liệu vào các file text (`phong.txt`, `phieudatphong.txt`) mà không cần CSDL SQL.

---

## 🌟 Tính Năng Chính

* **Quản Lý 20 Phòng Khách Sạn (5 Tầng x 4 Phòng):**
    * Tự động khởi tạo và phân tầng theo đối tượng:
        * **Tầng 1 – 2:** Phòng Standard (`PhongStandard`)
        * **Tầng 3 – 4:** Phòng VIP (`PhongVIP`)
        * **Tầng 5:** Phòng Penthouse (`PhongPenthouse`)
* **Sơ Đồ Trực Quan & Đa Giao Diện (Dual-Mode):**
    * Hỗ trợ Console CLI và Java Swing GUI đồng bộ dữ liệu chặt chẽ.
    * Hiển thị trạng thái phòng theo thời gian thực:
        * 🟢 **Xanh lá:** Phòng Trống (`TRONG`)
        * 🔴 **Đỏ:** Đang có khách (`DANG_O`)
        * 🟡 **Vàng:** Chờ dọn dẹp (`DANG_DON`)
* **Quy Trình Check-In & Check-Out Nâng Cao:**
    * **Check-In:** Nhân viên nhập thông tin khách (Họ tên, CCCD, SĐT), **số ngày thuê dự kiến**, và mở **Webcam thực tế** chụp ảnh chân dung lưu vào hệ thống.
    * **Check-Out:** Tra cứu nhanh qua số CCCD, tự động lấy số ngày lưu trú từ phiếu đặt phòng, tính toán tổng tiền (gồm VAT & ưu đãi) và chuyển trạng thái phòng sang "Chờ dọn dẹp".
    * **Xác nhận dọn dẹp:** Đưa phòng về lại trạng thái "Trống" sẵn sàng phục vụ.
* **Lưu Trữ Dữ Liệu Flat-File (Java I/O):**
    * Không dùng Database/SQL. Toàn bộ thông tin được lưu trữ tự động trong thư mục `data/` qua các tệp văn bản.

---

## ✨ Tính Năng Nổi Bật

- 🛏️ **Quản lý sơ đồ phòng trực quan**: Hiển thị danh sách 20 phòng (Standard, VIP, Penthouse), cập nhật trạng thái thời gian thực (_Trống / Đang ở / Chờ dọn_).
- 📸 **Quy trình Check-in tiện lợi**:
    - 👤 Nhập thông tin khách hàng (Họ tên, CCCD/CMND, Số điện thoại).
    - 🎥 Tích hợp **Camera preview** live & chụp ảnh trực tiếp qua Webcam.
- 💳 **Quy trình Check-out & Thanh toán tự động**:
    - 📅 Tự động tính tiền lưu trú chính xác theo số ngày ở.
    - 🛎️️ Áp dụng Phí dịch vụ (`IServiceChargable`) và Giảm giá (`IDiscountable`) theo từng hạng phòng.
    - 🧾 Tự động hạch toán Thuế VAT **10%**.
    - 🪟 Hiển thị Cửa sổ Hóa đơn xem trước và hỗ trợ in ấn.
- 💾 **Lưu trữ dữ liệu thuần File I/O**: Quản lý dữ liệu qua các tập tin phẳng (`phong.txt`, `phieudatphong.txt`) và thư mục lưu ảnh chân dung khách hàng (`data/images/`).

---

## 📂 Cấu Trúc Dự Án (Project Structure)

```text
HotelBookingSystem/
├── 📁 data/                                # Thư mục chứa Flat-File lưu trữ dữ liệu hệ thống
│   ├── 📄 phong.txt                        # Lưu danh sách 20 phòng & trạng thái thời gian thực
│   ├── 📄 phieudatphong.txt                # Lưu thông tin phiếu đặt/lưu trú & số ngày thuê
│   └── 📁 images/                          # Thư mục lưu ảnh chân dung khách chụp từ Webcam
│
├── 📁 docs/                                # Tài liệu thiết kế & Sơ đồ hệ thống (.mmd)
│   └── 📁 diagrams/
│       ├── 📊 activity_checkin.mmd         # Sơ đồ hoạt động Check-in chi tiết
│       ├── 📊 architecture.mmd             # Sơ đồ kiến trúc tổng quan hệ thống
│       ├── 📊 class_diagram.mmd            # Sơ đồ lớp (Class diagram)
│       ├── 📊 component_diagram.mmd        # Sơ đồ thành phần hệ thống
│       ├── 📊 erd_data_model.mmd           # Sơ đồ thực thể liên kết (ERD)
│       ├── 📊 package_diagram.mmd          # Sơ đồ gói hệ thống
│       ├── 📊 room_state.mmd               # Sơ đồ trạng thái vòng đời của phòng
│       ├── 📊 sequence_checkin.mmd         # Sơ đồ tuần tự quy trình Check-in
│       ├── 📊 sequence_checkout.mmd        # Sơ đồ tuần tự quy trình Check-out & thanh toán
│       └── 📊 usecase_diagram.mmd          # Sơ đồ Use Case tổng quan
│
├── 📁 src/
│   └── 📁 main/
│       └── 📁 java/
│           └── 📁 com/
│               └── 📁 hotel/
│                   ├── 🚀 Main.java                # Điểm khởi chạy ứng dụng (Entry Point)
│                   │
│                   ├── 📁 model/                   # Tầng đối tượng thực thể (OOP Entity)
│                   │   ├── 📋 IDiscountable.java       # Interface định nghĩa quy tắc giảm giá
│                   │   ├── 📋 IServiceChargable.java   # Interface định nghĩa quy tắc tính phí dịch vụ
│                   │   ├── 👤 KhachHang.java           # Lớp thực thể thông tin khách hàng
│                   │   ├── 🎫 PhieuDatPhong.java       # Lớp thực thể phiếu lưu trú & tính tiền
│                   │   ├── 🏛️ PhongKhachSan.java       # Lớp cơ sở trừu tượng đại diện phòng
│                   │   ├── 🏢 PhongPenthouse.java      # Lớp cụ thể dòng phòng Penthouse cao cấp
│                   │   ├── 🛏️ PhongStandard.java       # Lớp cụ thể dòng phòng tiêu chuẩn
│                   │   └── 👑 PhongVIP.java            # Lớp cụ thể dòng phòng VIP
│                   │
│                   ├── 📁 service/                 # Tầng nghiệp vụ & Quản lý File I/O
│                   │   └── ⚙️ QuanLyKhachSan.java      # Điều phối logic nghiệp vụ & đọc/ghi tệp phẳng
│                   │
│                   └── 📁 view/                    # Tầng giao diện (Console CLI & Java Swing GUI)
│                       ├── 📁 components/
│                       │   └── 📷 CameraPreviewDialog.java # Hộp thoại tích hợp Webcam chụp ảnh khuôn mặt
│                       ├── 📁 console/             # Tầng điều khiển giao diện dòng lệnh
│                       │   ├── 📥 CheckInConsoleHandler.java  # Xử lý logic nghiệp vụ Check-in qua dòng lệnh
│                       │   ├── 📤 CheckOutConsoleHandler.java # Xử lý logic nghiệp vụ Check-out & hóa đơn qua dòng lệnh
│                       │   └── 🧹 PhongConsoleHandler.java    # Xử lý hiển thị danh sách & xác nhận dọn phòng qua dòng lệnh
│                       ├── 📁 gui/                 # Tầng giao diện đồ họa Java Swing
│                       │   ├── 📝 CheckInPanel.java        # Giao diện màn hình Check-in & điều khiển Webcam
│                       │   └── 💳 CheckOutPanel.java       # Giao diện màn hình thanh toán & in hóa đơn
│                       ├── 💻 ConsoleView.java         # Điều hướng menu tương tác dòng lệnh chính
│                       ├── 🪟 MainFrame.java           # Khung cửa sổ chính quản lý Tab giao diện Swing
│                       └── 🔲 QuanLyPhongPanel.java    # Sơ đồ lưới ma trận 20 phòng trực quan thời gian thực
│
├── ⚙️ pom.xml                              # Quản lý cấu hình Maven dependencies (Webcam Capture API, v.v.)
└── 📖 README.md                            # Tài liệu hướng dẫn sử dụng và giới thiệu dự án chi tiết
```

---

## 🛠️ Công Nghệ & Thư Viện Sử Dụng

- ☕ **Ngôn ngữ chính**: Java (JDK 17+)
- 🎨 **Giao diện (GUI)**: Java Swing, FlatLaf Look & Feel
- 🤖 **Xử lý hình ảnh / Webcam**:
    - 📷 **Webcam Integration**: Sarxos Webcam Capture API
    - 📄 **Lưu trữ**: Java File I/O (BufferedReader / BufferedWriter)
- 📊 **Tài liệu & Thiết kế**: Mermaid Diagrams (`.mmd`)

---

## 🏛️ Thiết Kế Hướng Đối Tượng & Hạng Phòng (OOP)

Hệ thống được thiết kế chặt chẽ theo các nguyên lý **Lập trình hướng đối tượng (OOP)**:

| 🛏️ Hạng Phòng         | 💵 Giá Niêm Yết | 🛎️ Phí Dịch Vụ (`IServiceChargable`) | 🏷️ Giảm Giá (`IDiscountable`) | 🧾 Thuế VAT |
| :-------------------- | :-------------: | :----------------------------------: | :---------------------------: | :---------: |
| 🏠 **PhongStandard**  |  `500.000 VNĐ`  |               ❌ Không               |           ❌ Không            |    `10%`    |
| 💎 **PhongVIP**       | `1.200.000 VNĐ` |          ✅ `+200.000 VNĐ`           |           ✅ `-5%`            |    `10%`    |
| 🌟 **PhongPenthouse** | `2.500.000 VNĐ` |          ✅ `+500.000 VNĐ`           |           ✅ `-10%`           |    `10%`    |

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
   git clone \<repository_url\>

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

- 🧑‍💻 **Tác giả**: 
- 🏫 **Đơn vị**: Đại học Kinh tế \- Tài chính TP.HCM (UEF)
- 📌 **Mục đích**: Đồ án môn học / Dự án Lập trình hướng đối tượng Java.
