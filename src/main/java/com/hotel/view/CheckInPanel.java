package com.hotel.view;

import com.hotel.model.KhachHang;
import com.hotel.model.PhieuDatPhong;
import com.hotel.model.PhongKhachSan;
import com.hotel.service.CameraService;
import com.hotel.service.FaceDetectionService;
import com.hotel.service.OcrService;
import com.hotel.service.QuanLyKhachSan;
import com.hotel.util.ImageUtils;
import com.hotel.util.SwingUtils;
import com.hotel.view.components.CameraPreviewDialog;
import com.hotel.view.components.ImageAvatarPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CheckInPanel extends JPanel {
    private QuanLyKhachSan qlkh;
    private CameraService cameraService;
    private OcrService ocrService;
    private FaceDetectionService faceService;

    // Component Giao diện
    private JTextField txtCCCD;
    private JTextField txtHoTen;
    private JTextField txtNgaySinh;
    private JTextField txtSoNgayThue;
    private ImageAvatarPanel avatarPanel;

    // Lưu trữ ảnh chân dung vừa chụp được
    private BufferedImage capturedFaceImage;

    public CheckInPanel(QuanLyKhachSan qlkh) {
        this.qlkh = qlkh;
        this.cameraService = new CameraService();
        this.ocrService = new OcrService();
        this.faceService = new FaceDetectionService();

        initUI();
    }

    /**
     * Dựng bố cục giao diện cho màn hình Check-in
     */
    private void initUI() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Tiêu đề chính
        JLabel lblTitle = new JLabel("TIẾP NHẬN CHECK-IN KHÁCH HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(SwingUtils.FONT_TITLE);
        add(lblTitle, BorderLayout.NORTH);

        // 2. Khu vực Form nhập liệu (Bên trái)
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 15));

        formPanel.add(new JLabel("Số CCCD:"));
        txtCCCD = new JTextField();
        formPanel.add(txtCCCD);

        formPanel.add(new JLabel("Họ và Tên:"));
        txtHoTen = new JTextField();
        formPanel.add(txtHoTen);

        formPanel.add(new JLabel("Ngày Sinh (dd/MM/yyyy):"));
        txtNgaySinh = new JTextField();
        formPanel.add(txtNgaySinh);

        formPanel.add(new JLabel("Số Ngày Thuê:"));
        txtSoNgayThue = new JTextField("1");
        formPanel.add(txtSoNgayThue);

        // 3. Khu vực Ảnh chân dung & Nút chức năng (Bên phải)
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        avatarPanel = new ImageAvatarPanel();
        rightPanel.add(avatarPanel, BorderLayout.CENTER);

        // 4. Panel Thanh Nút Bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnQuetCCCD = new JButton("Quét CCCD (OCR)");
        JButton btnChupAnhMat = new JButton("Chụp Ảnh Mặt");
        JButton btnXacNhan = new JButton("Xác Nhận Check-In");

        // Gán sự kiện click cho các nút
        btnQuetCCCD.addActionListener(e -> xuLyQuetCCCD());
        btnChupAnhMat.addActionListener(e -> xuLyChupAnhMat());
        btnXacNhan.addActionListener(e -> xacNhanCheckIn());

        buttonPanel.add(btnQuetCCCD);
        buttonPanel.add(btnChupAnhMat);
        buttonPanel.add(btnXacNhan);

        // Ghép các thành phần vào Panel chính
        add(formPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        // Đồng bộ font chữ cho toàn bộ Panel
        SwingUtils.setCustomFont(this);
    }

    /**
     * Bật camera, chụp ảnh thẻ CCCD và tự động trích xuất thông tin bằng OCR
     */
    public void xuLyQuetCCCD() {
        cameraService.startCamera();
        BufferedImage frame = cameraService.captureFrame();
        cameraService.stopCamera();

        if (frame == null) {
            SwingUtils.showErrorDialog(this, "Không thể lấy hình ảnh từ Camera!");
            return;
        }

        // Gọi OcrService để trích xuất thông tin
        KhachHang kh = ocrService.trichXuatThongTinCCCD(frame);
        if (kh != null) {
            txtCCCD.setText(kh.getSoCCCD());
            txtHoTen.setText(kh.getHoTen());
            txtNgaySinh.setText(kh.getNgaySinh());
            SwingUtils.showSuccessDialog(this, "Trích xuất thông tin CCCD thành công!");
        } else {
            SwingUtils.showErrorDialog(this, "Không thể nhận diện thông tin trên thẻ CCCD. Vui lòng nhập tay!");
        }
    }

    /**
     * Bật Dialog xem trước camera, nhận diện và crop riêng vùng khuôn mặt
     */
    public void xuLyChupAnhMat() {
        cameraService.startCamera();

        // Mở Dialog xem trước Camera
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        CameraPreviewDialog previewDialog = new CameraPreviewDialog((Frame) parentWindow);

        BufferedImage currentFrame = cameraService.captureFrame();
        previewDialog.capNhatKhungHinh(currentFrame);

        if (currentFrame != null) {
            // Nhận diện và tự động crop khuôn mặt từ khung hình
            this.capturedFaceImage = faceService.phatHienVaCropMat(currentFrame);

            if (this.capturedFaceImage != null) {
                // Hiển thị ảnh khuôn mặt lên ImageAvatarPanel
                avatarPanel.setAvatar(this.capturedFaceImage);
                SwingUtils.showSuccessDialog(this, "Đã phát hiện và chụp khuôn mặt thành công!");
            } else {
                SwingUtils.showErrorDialog(this, "Không phát hiện thấy khuôn mặt trong khung hình!");
            }
        }

        cameraService.stopCamera();
    }

    /**
     * Kiểm tra dữ liệu, khởi tạo Khách hàng và Phiếu đặt phòng
     */
    public void xacNhanCheckIn() {
        String cccd = txtCCCD.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        String ngaySinh = txtNgaySinh.getText().trim();
        String strSoNgay = txtSoNgayThue.getText().trim();

        // 1. Kiểm tra tính hợp lệ của dữ liệu nhập
        if (cccd.isEmpty() || hoTen.isEmpty() || ngaySinh.isEmpty()) {
            SwingUtils.showErrorDialog(this, "Vui lòng điền đầy đủ thông tin khách hàng!");
            return;
        }

        int soNgay;
        try {
            soNgay = Integer.parseInt(strSoNgay);
            if (soNgay <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            SwingUtils.showErrorDialog(this, "Số ngày thuê phải là một số nguyên dương!");
            return;
        }

        // 2. Tạo đối tượng Khách hàng
        KhachHang khachHang = new KhachHang(cccd, hoTen, ngaySinh);

        // Lưu ảnh mặt xuống thư mục captures/ nếu đã chụp
        if (capturedFaceImage != null) {
            String imagePath = "src/main/resources/captures/" + cccd + "_face.png";
            ImageUtils.saveImage(capturedFaceImage, imagePath);
            khachHang.setDuongDanAnhMat(imagePath);
        }

        // 3. Thông báo tạo thành công
        SwingUtils.showSuccessDialog(this, "Check-in thành công cho khách hàng: " + hoTen);
        clearForm();
    }

    /**
     * Xóa sạch form nhập liệu sau khi hoàn tất Check-in
     */
    private void clearForm() {
        txtCCCD.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        txtSoNgayThue.setText("1");
        avatarPanel.setAvatar(null);
        capturedFaceImage = null;
    }
}