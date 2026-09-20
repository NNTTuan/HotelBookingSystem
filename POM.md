THẺ GỐC <project>:

│

├── CẤU HÌNH THÔNG TIN DỰ ÁN (Project Metadata)

│   ├── modelVersion: 4.0.0

│   ├── groupId: com.hotel (Tên miền ngược đại diện cho tổ chức/nhóm)

│   ├── artifactId: hotel-booking-system (Tên tên dự án)

│   └── version: 1.0.0-SNAPSHOT (Phiên bản đang phát triển)

│

├── CẤU HÌNH THÔNG SỐ CHƯƠNG TRÌNH <properties>

│   ├── maven.compiler.source: 17 (Ép trình biên dịch dùng Java 17 LTS)

│   ├── maven.compiler.target: 17 (Ép bytecode tương thích Java 17 LTS)

│   └── project.build.sourceEncoding: UTF-8 (Đảm bảo không lỗi font tiếng Việt)

│

├── DANH SÁCH THƯ VIỆN BẮT BUỘC <dependencies>

│   │

│   ├── \[1. Giao diện Swing FlatLaf]

│   │   ├── groupId: com.formdev

│   │   ├── artifactId: flatlaf

│   │   └── version: 3.4.1 (Mới nhất giúp UI Dark/Light Mode hiện đại)

│   │

│   ├── \[2. Điều khiển Webcam (Sarxos)]

│   │   ├── groupId: com.github.sarxos

│   │   ├── artifactId: webcam-capture

│   │   └── version: 0.3.12 (Tương thích với webcam-capture-0.3.12.jar trong lib)\[cite: 1]

│   │

│   ├── \[3. Nhận diện Khuôn mặt (JavaCV / OpenCV Wrapper)]\[cite: 1]

│   │   ├── groupId: org.bytedeco

│   │   ├── artifactId: javacv-platform

│   │   └── version: 1.5.9 (Trọn gói OpenCV \& FFmpeg cho Java)\[cite: 1]

│   │

│   ├── \[4. Trích xuất chữ từ CCCD (Tess4J - Tesseract OCR)]

│   │   ├── groupId: net.sourceforge.tess4j

│   │   ├── artifactId: tess4j

│   │   └── version: 5.10.0 (Hỗ trợ đọc tiếng Việt từ tessdata)

│   │

│   └── \[5. Ghi Log Hệ thống (SLF4J Simple)]\[cite: 1]

│       ├── groupId: org.slf4j

│       ├── artifactId: slf4j-simple

│       └── version: 1.7.36 (Giải quyết lỗi log từ webcam \& javacv)\[cite: 1]

│

└── CẤU HÌNH BIÊN DỊCH \& ĐÓNG GÓI <build>

&#x20;   └── DANH SÁCH PLUGINS <plugins>

&#x20;       └── \[Maven Compiler Plugin]

&#x20;           ├── groupId: org.apache.maven.plugins

&#x20;           ├── artifactId: maven-compiler-plugin

&#x20;           └── version: 3.11.0

