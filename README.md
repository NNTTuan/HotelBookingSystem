# 🏨 Hệ Thống Quản Lý Khách Sạn

**Hotel Booking System — Java Swing Desktop App & Console CLI**

Ứng dụng quản lý khách sạn được xây dựng bằng Java, hỗ trợ hai chế độ sử dụng: giao diện dòng lệnh (Console CLI) và giao diện đồ họa Java Swing. Hệ thống quản lý 20 phòng thuộc 3 hạng phòng, hỗ trợ quy trình check-in/check-out, chụp ảnh chân dung khách hàng qua webcam, thống kê doanh thu và lưu trữ dữ liệu bằng các tệp văn bản thay vì sử dụng SQL Database.

---

## 🌟 Tính năng chính

### 🏨 Quản lý phòng khách sạn

- Tự động khởi tạo **20 phòng**, gồm **5 tầng, mỗi tầng 4 phòng**.
- Phân loại phòng theo tầng:
    - **Tầng 1–2:** Standard (`PhongStandard`).
    - **Tầng 3–4:** VIP (`PhongVIP`).
    - **Tầng 5:** Penthouse (`PhongPenthouse`).
- Theo dõi trạng thái phòng:
    - 🟢 `TRONG` — Phòng trống.
    - 🔴 `DANG_O` — Đang có khách lưu trú.
    - 🟡 `DANG_DON` — Phòng đang chờ dọn dẹp.
- Sau khi dọn phòng xong, nhân viên có thể xác nhận để đưa phòng về trạng thái `TRONG`.

### 🔀 Hai chế độ giao diện

- **Java Swing GUI:** giao diện desktop gồm quản lý phòng, check-in, check-out và thống kê.
- **Console CLI:** thực hiện các thao tác quản lý phòng, check-in và check-out thông qua dòng lệnh.
- Hai chế độ sử dụng chung lớp nghiệp vụ `QuanLyKhachSan` và cùng đọc/ghi dữ liệu vào các tệp trong thư mục `data/`.

### 📸 Check-in và chụp ảnh webcam

- Nhập thông tin khách hàng: họ tên, CCCD/CMND và số điện thoại.
- Chọn phòng trống và nhập số ngày thuê dự kiến.
- Giao diện Swing cho phép mở webcam, xem hình ảnh trực tiếp và chụp ảnh chân dung để lưu cùng thông tin khách hàng.
- Ảnh được lưu trong thư mục `data/images/` và đường dẫn ảnh được lưu trong dữ liệu phiếu.

> **Lưu ý:** Webcam được dùng để xem hình ảnh trực tiếp và chụp ảnh chân dung; chương trình không thực hiện nhận diện khuôn mặt tự động. Luồng chụp ảnh hiện được triển khai trực tiếp trong `CheckInPanel`.

### 💳 Check-out và tính tiền

- Tra cứu phiếu lưu trú đang hoạt động bằng số CCCD/CMND.
- Hiển thị thông tin khách hàng, phòng, số ngày lưu trú và ảnh chân dung nếu có.
- Tính tiền phòng, phí dịch vụ (nếu có), giảm giá theo hạng phòng và VAT 10%.
- Cho phép xem chi tiết hóa đơn trong hộp thoại trước khi xác nhận thanh toán.
- Khi check-out thành công, phiếu được chuyển sang trạng thái `DA_TRA` và phòng chuyển sang `DANG_DON`.

### 📊 Thống kê

Màn hình thống kê trong Java Swing cung cấp:

- 5 thẻ tổng quan: tổng số phòng, số phòng trống, số phòng đang ở, số phòng chờ dọn và tổng doanh thu.
- Top 3 phòng có doanh thu cao nhất.
- Bảng doanh thu theo tháng.
- Biểu đồ tổng doanh thu theo tháng.
- Biểu đồ doanh thu theo loại phòng.
- Top 5 phòng có số lượt thuê nhiều nhất.
- Nút làm mới để tính toán và cập nhật lại dữ liệu thống kê.

Doanh thu trong thống kê được cộng từ các phiếu có trạng thái `DA_TRA` và có thông tin phòng hợp lệ.

### 💾 Lưu trữ dữ liệu bằng Flat-File

- Không sử dụng SQL Database.
- `data/phong.txt`: lưu thông tin phòng và trạng thái phòng.
- `data/phieudatphong.txt`: lưu thông tin phiếu, phòng, khách hàng, số ngày thuê, đường dẫn ảnh và trạng thái phiếu.
- `data/images/`: lưu ảnh chân dung được chụp trong quá trình check-in.
- Dữ liệu được đọc và ghi bằng Java I/O thông qua lớp `QuanLyKhachSan`.

---

## 📂 Cấu trúc dự án

```text
🏨 HotelBookingSystem/
│
├── 📁 data/                                      # 💾 Dữ liệu hệ thống
│   ├── 📁 images/                                # 🖼️ Ảnh chân dung khách hàng
│   ├── 📄 phieudatphong.txt                      # 🧾 Dữ liệu phiếu lưu trú
│   └── 📄 phong.txt                              # 🛏️ Dữ liệu phòng và trạng thái
│
├── 📁 docs/                                      # 📚 Tài liệu dự án
│   └── 📁 diagrams/                              # 📊 Sơ đồ Mermaid
│       ├── 📄 README.md                          # Danh mục và mô tả sơ đồ
│       ├── 🔄 activity_checkin.mmd               # Sơ đồ hoạt động check-in
│       ├── 🏗️ architecture.mmd                   # Sơ đồ kiến trúc tổng thể
│       ├── 🧩 class_diagram.mmd                  # Sơ đồ lớp, nhóm Model/Service/View
│       ├── 🧱 component_diagram.mmd              # Sơ đồ thành phần
│       ├── 🗂️ flatfile_data_model.mmd            # Mô hình dữ liệu Flat-File
│       ├── 📦 package_diagram.mmd                # Sơ đồ package Java
│       ├── 🚦 room_state.mmd                     # Sơ đồ trạng thái phòng
│       ├── 🔀 sequence_checkin.mmd               # Sơ đồ tuần tự check-in
│       ├── 🔀 sequence_checkout.mmd              # Sơ đồ tuần tự check-out
│       ├── 📊 sequence_statistics.mmd            # Sơ đồ tuần tự thống kê
│       └── 👥 usecase_diagram.mmd                # Sơ đồ ca sử dụng
│
├── 📁 src/                                       # 💻 Mã nguồn Java
│   └── 📁 main/
│       └── 📁 java/
│           └── 📁 com/
│               └── 📁 hotel/
│                   ├── 📁 model/                 # 🗂️ Các lớp dữ liệu và nghiệp vụ đối tượng
│                   │   ├── IDiscountable.java    # Interface giảm giá
│                   │   ├── IServiceChargable.java # Interface tính phí dịch vụ
│                   │   ├── KhachHang.java         # Thông tin khách hàng
│                   │   ├── PhieuDatPhong.java     # Phiếu lưu trú
│                   │   ├── PhongKhachSan.java     # Lớp cơ sở phòng khách sạn
│                   │   ├── PhongPenthouse.java    # Loại phòng Penthouse
│                   │   ├── PhongStandard.java     # Loại phòng Standard
│                   │   └── PhongVIP.java          # Loại phòng VIP
│                   │
│                   ├── 📁 service/               # ⚙️ Nghiệp vụ quản lý khách sạn
│                   │   └── QuanLyKhachSan.java    # Quản lý phòng, phiếu và lưu trữ
│                   │
│                   ├── 📁 view/                  # 🎨 Giao diện người dùng
│                   │   ├── 📁 console/            # ⌨️ Các handler cho Console CLI
│                   │   │   ├── CheckInConsoleHandler.java
│                   │   │   ├── CheckOutConsoleHandler.java
│                   │   │   └── PhongConsoleHandler.java
│                   │   │
│                   │   ├── 📁 gui/                # 🖥️ Các màn hình Java Swing
│                   │   │   ├── BieuDoDoanhThuPanel.java
│                   │   │   ├── BieuDoDoanhThuTheoLoaiPanel.java
│                   │   │   ├── CheckInPanel.java
│                   │   │   ├── CheckOutPanel.java
│                   │   │   └── ThongKePanel.java
│                   │   │
│                   │   ├── ConsoleView.java      # Giao diện Console chính
│                   │   ├── MainFrame.java        # Cửa sổ chính Swing
│                   │   └── QuanLyPhongPanel.java # Giao diện quản lý phòng
│                   │
│                   └── Main.java                 # Điểm khởi chạy chương trình
├── 📜 pom.xml                                    # ⚙️ Cấu hình Maven và thư viện
└── 📘 README.md                                  # 📖 Tài liệu dự án
```

> 💡 `target/` là thư mục build do Maven tạo ra. Thông thường không cần đưa thư mục này lên Git vì có thể tạo lại khi build. Cây thư mục trên không bao gồm các file `.class` và file phụ trợ được sinh tự động.

### 📝 Vai trò các thành phần chính

| Thành phần | Vai trò |
|---|---|
| `model/` | Mô tả phòng, khách hàng, phiếu lưu trú và các interface tính phí/giảm giá. |
| `service/` | Chứa `QuanLyKhachSan`, điều phối nghiệp vụ và đọc/ghi tệp dữ liệu. |
| `view/gui/` | Chứa các màn hình Swing, trong đó có check-in, check-out và thống kê. |
| `view/console/` | Chứa các handler xử lý thao tác trên Console CLI. |
| `view/` | Chứa các giao diện chính `ConsoleView`, `MainFrame` và `QuanLyPhongPanel`. |
| `docs/diagrams/` | Chứa 11 sơ đồ Mermaid phục vụ tài liệu phân tích và thiết kế. |
| `data/` | Chứa tệp văn bản và ảnh do chương trình sử dụng. |

---

## 🛠️ Công nghệ và thư viện

- ☕ **Ngôn ngữ:** Java.
- 🧰 **Build tool:** Apache Maven.
- 🖥️ **Giao diện desktop:** Java Swing.
- 🎨 **Look & Feel:** FlatLaf.
- 📷 **Webcam:** Sarxos Webcam Capture API.
- 🪵 **Logging:** SLF4J Simple.
- 📝 **Lưu trữ:** Java I/O (`BufferedReader`, `BufferedWriter`) và các tệp `.txt`.
- 📊 **Tài liệu thiết kế:** Mermaid (`.mmd`).
- 🗃️ **Cơ sở dữ liệu:** Không sử dụng SQL Database.

---

## 🏛️ Thiết kế hướng đối tượng và hạng phòng

Hệ thống sử dụng kế thừa để mô hình hóa các hạng phòng và interface để biểu diễn các hành vi tính phí dịch vụ, giảm giá.

| Hạng phòng | Giá cơ bản/ngày | Phí dịch vụ | Giảm giá trên tiền phòng gốc | VAT |
|---|---:|---:|---:|---:|
| `PhongStandard` | 500.000 VNĐ | Không | Không | 10% |
| `PhongVIP` | 1.200.000 VNĐ | 200.000 VNĐ/lượt lưu trú | 5% | 10% |
| `PhongPenthouse` | 2.500.000 VNĐ | 500.000 VNĐ/lượt lưu trú | 10% | 10% |

### 🧮 Công thức tính tiền

```text
Tiền phòng gốc = Giá cơ bản × Số ngày thuê
Tiền giảm giá  = Tiền phòng gốc × Tỷ lệ giảm giá
Tiền trước VAT = Tiền phòng gốc + Phí dịch vụ − Tiền giảm giá
Tiền VAT       = Tiền trước VAT × 10%
Tổng thanh toán = Tiền trước VAT + Tiền VAT
```

Phí dịch vụ trong code hiện tại là khoản cố định cho mỗi lượt lưu trú, không nhân với số ngày thuê.

---

## 🚀 Hướng dẫn cài đặt và chạy ứng dụng

### Yêu cầu môi trường

- JDK 17 trở lên.
- Apache Maven.
- IntelliJ IDEA, Eclipse, NetBeans hoặc IDE Java tương đương.
- Webcam nếu muốn sử dụng chức năng xem trực tiếp và chụp ảnh chân dung.

### Các bước thực hiện

1. **Clone repository:**

   ```bash
   git clone https://github.com/NNTTuan/HotelBookingSystem.git
   ```

2. Mở thư mục dự án bằng IDE và tải các dependency Maven.
3. Kiểm tra cấu hình JDK của dự án.
4. Chạy `src/main/java/com/hotel/Main.java` từ IDE.
5. Dữ liệu được đọc/ghi trong thư mục `data/` tương đối với thư mục làm việc khi chạy ứng dụng. Hãy đảm bảo ứng dụng có quyền tạo và ghi tệp tại vị trí này.

---

## 📊 Sơ đồ thiết kế hệ thống

Các sơ đồ Mermaid nằm trong `docs/diagrams/`:

| File | Nội dung |
|---|---|
| `architecture.mmd` | Tổng quan kiến trúc và cách các thành phần tương tác. |
| `component_diagram.mmd` | Các thành phần và quan hệ phụ thuộc. |
| `class_diagram.mmd` | Sơ đồ lớp, nhóm các lớp theo Model, Service và View; điểm khởi chạy `Main` được thể hiện riêng. |
| `package_diagram.mmd` | Cấu trúc package của chương trình. |
| `flatfile_data_model.mmd` | Mô hình dữ liệu trong bộ nhớ và cách lưu bằng tệp văn bản; không phải ERD SQL. |
| `usecase_diagram.mmd` | Các tác nhân và chức năng chính. |
| `activity_checkin.mmd` | Các bước hoạt động trong quy trình check-in. |
| `sequence_checkin.mmd` | Tương tác giữa các thành phần trong check-in trên GUI. |
| `sequence_checkout.mmd` | Tương tác giữa các thành phần trong check-out. |
| `sequence_statistics.mmd` | Luồng tổng quan lấy dữ liệu, tổng hợp và cập nhật giao diện thống kê. |
| `room_state.mmd` | Các trạng thái và chuyển trạng thái của phòng. |

Các sơ đồ phản ánh luồng webcam được triển khai trực tiếp trong `CheckInPanel`; bộ sơ đồ hiện tại không mô tả lớp `CameraPreviewDialog.java`.

---

## 👤 Tác giả và mục đích

- **Tác giả:** 
- **Đơn vị:** Trường Đại học Kinh tế - Tài chính TP.HCM (UEF)
- **Mục đích:** Đồ án môn học / dự án thực hành lập trình Java và lập trình hướng đối tượng.
