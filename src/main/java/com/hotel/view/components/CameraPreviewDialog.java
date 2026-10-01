package com.hotel.view.components;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraPreviewDialog extends JDialog {
    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private String capturedImagePath = "";

    public CameraPreviewDialog(Frame parent, boolean modal) {
        super(parent, "Xem Trước & Chụp Ảnh Webcam", modal);
        setSize(680, 550);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Lấy Webcam mặc định của máy
        webcam = Webcam.getDefault();

        if (webcam == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thiết bị Webcam trên máy tính!", "Lỗi Webcam", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Đặt độ phân giải chuẩn VGA (640x480)
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        // Khởi tạo Panel Live Preview
        webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setFPSDisplayed(true);
        webcamPanel.setDisplayDebugInfo(false);
        webcamPanel.setImageSizeDisplayed(true);
        webcamPanel.setMirrored(true); // Lật gương giao diện cho tự nhiên

        add(webcamPanel, BorderLayout.CENTER);

        // Thanh công cụ nút bấm phía dưới
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnCapture = new JButton("📷 Chụp Ảnh");
        JButton btnCancel = new JButton("Hủy");

        btnCapture.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCapture.setBackground(new Color(46, 125, 50));
        btnCapture.setForeground(Color.WHITE);
        btnCapture.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        btnCapture.addActionListener(e -> xuLyChupAnh());
        btnCancel.addActionListener(e -> {
            dongWebcam();
            dispose();
        });

        bottomPanel.add(btnCapture);
        bottomPanel.add(btnCancel);

        add(bottomPanel, BorderLayout.SOUTH);

        // Giải phóng Webcam khi tắt cửa sổ
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dongWebcam();
            }
        });
    }

    private void xuLyChupAnh() {
        if (webcam != null && webcam.isOpen()) {
            BufferedImage image = webcam.getImage();
            if (image != null) {
                try {
                    File dir = new File("data/images");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String fileName = "khach_" + timeStamp + ".jpg";
                    File outputFile = new File(dir, fileName);

                    ImageIO.write(image, "JPG", outputFile);
                    capturedImagePath = outputFile.getAbsolutePath();

                    JOptionPane.showMessageDialog(this, "Đã chụp và lưu ảnh thành công!\n" + fileName, "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    dongWebcam();
                    dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi lưu ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void dongWebcam() {
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
        }
    }

    public String getCapturedImagePath() {
        return capturedImagePath;
    }
}