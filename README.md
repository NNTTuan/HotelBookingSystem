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
# 🏨 Hotel Booking System

## 📂 Cấu trúc thư mục dự án

```text
🏨 HotelBookingSystem/
│
├── 📁 data/                                      # 💾 Dữ liệu hệ thống
│   ├── 🖼️ images/                                # Hình ảnh và tài nguyên
│   ├── 📄 phieudatphong.txt                       # Dữ liệu phiếu đặt phòng
│   └── 📄 phong.txt                              # Dữ liệu phòng khách sạn
│
├── 📁 docs/                                      # 📚 Tài liệu dự án
│   └── 📁 diagrams/                              # 📊 Sơ đồ phân tích và thiết kế
│       ├── 🔄 activity_checkin.mmd                # Sơ đồ hoạt động nhận phòng
│       ├── 🏗️ architecture.mmd                   # Sơ đồ kiến trúc hệ thống
│       ├── 🧩 class_diagram.mmd                  # Sơ đồ lớp (Class Diagram)
│       ├── 🧱 component_diagram.mmd              # Sơ đồ thành phần (Component Diagram)
│       ├── 🗃️ erd_data_model.mmd                 # Sơ đồ mô hình dữ liệu (ERD)
│       ├── 📦 package_diagram.mmd                # Sơ đồ gói (Package Diagram)
│       ├── 🚦 room_state.mmd                     # Sơ đồ trạng thái phòng
│       ├── 🔀 sequence_checkin.mmd                # Sơ đồ tuần tự nhận phòng
│       ├── 🔀 sequence_checkout.mmd               # Sơ đồ tuần tự trả phòng
│       └── 👥 usecase_diagram.mmd                 # Sơ đồ ca sử dụng (Use Case)
│
├── 📁 src/                                       # 💻 Mã nguồn chương trình
│   └── 📁 main/ java/ com/
│       └── 📁 hotel/                     # 🏨 Package chính
│           │
│           ├── 📁 model/                 # 🗂️ Mô hình dữ liệu và nghiệp vụ
│           │   ├── 🔌 IDiscountable.java # Interface giảm giá
│           │   ├── 🔌 IServiceChargeable.java # Interface tính phí dịch vụ
│           │   ├── 👤 KhachHang.java      # Thông tin khách hàng
│           │   ├── 🧾 PhieuDatPhong.java  # Phiếu đặt phòng
│           │   ├── 🏠 PhongKhachSan.java  # Lớp cơ sở phòng khách sạn
│           │   ├── 🏢 PhongPenthouse.java # Loại phòng Penthouse
│           │   ├── 🛏️ PhongStandard.java  # Loại phòng Standard
│           │   └── ⭐ PhongVIP.java       # Loại phòng VIP
│           │
│           ├── 📁 service/                # ⚙️ Xử lý nghiệp vụ
│           │   └── 🏨 QuanLyKhachSan.java # Quản lý khách sạn
│           │
│           ├── 📁 view/                   # 🎨 Giao diện người dùng
│           │   │
│           │   ├── 📁 components/         # 🧩 Thành phần giao diện dùng chung
│           │   │   └── 📷 CameraPreviewDialog.java   # Hộp thoại xem trước camera
│           │   │
│           │   ├── 📁 console/            # ⌨️ Giao diện dòng lệnh
│           │   │   ├── 📥 CheckInConsoleHandler.java  # Xử lý nhận phòng
│           │   │   ├── 📤 CheckOutConsoleHandler.java # Xử lý trả phòng
│           │   │   └── 🏠 PhongConsoleHandler.java  # Xử lý quản lý phòng
│           │   │
│           │   ├── 📁 gui/                # 🖥️ Giao diện đồ họa Swing
│           │   │   ├── 📈 BieuDoDoanhThuPanel.java  # Biểu đồ doanh thu
│           │   │   ├── 📊 BieuDoDoanhThuTheoLoaiPanel.java   # Doanh thu theo loại phòng
│           │   │   ├── 📥 CheckInPanel.java   # Giao diện nhận phòng
│           │   │   ├── 📤 CheckOutPanel.java   # Giao diện trả phòng
│           │   │   └── 📋 ThongKePanel.java   # Giao diện thống kê
│           │   │
│           │   ├── 🖥️ ConsoleView.java     # Giao diện console chính
│           │   ├── 🪟 MainFrame.java       # Cửa sổ chính của ứng dụng
│           │   └── 🏠 QuanLyPhongPanel.java   # Giao diện quản lý phòng
│           │
│           └── 🚀 Main.java                # Điểm khởi chạy chương trình
│
├── 📁 target/                                    # 🏗️ Kết quả biên dịch Maven
│
├── 📜 pom.xml                                    # ⚙️ Cấu hình Maven và thư viện
│
└── 📘 README.md                                  # 📖 Tài liệu giới thiệu dự án
```

## 📝 Chú thích các thư mục chính

| 📁 Thành phần | 🎯 Chức năng |
|---|---|
| `data/` | Lưu dữ liệu phòng, phiếu đặt phòng và hình ảnh. |
| `docs/diagrams/` | Lưu sơ đồ UML, kiến trúc và mô hình dữ liệu bằng Mermaid. |
| `model/` | Quản lý các lớp dữ liệu, loại phòng và interface nghiệp vụ. |
| `service/` | Xử lý nghiệp vụ và điều phối hoạt động quản lý khách sạn. |
| `view/console/` | Xử lý tương tác thông qua giao diện dòng lệnh. |
| `view/gui/` | Chứa các màn hình giao diện đồ họa Swing. |
| `view/components/` | Chứa những thành phần giao diện có thể tái sử dụng. |
| `Main.java` | Khởi chạy chương trình. |
| `pom.xml` | Quản lý cấu hình build và các thư viện phụ thuộc. |
| `target/` | Chứa sản phẩm biên dịch do Maven tạo ra. |

> 💡 **Lưu ý:** Emoji trong cây thư mục chỉ mang tính trang trí cho README, không phải tên thư mục hoặc tên file thực tế. Thư mục `target/` thường không cần đưa lên Git vì Maven có thể tự tạo lại khi build dự án.


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
