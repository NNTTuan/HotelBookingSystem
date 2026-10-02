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