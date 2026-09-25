package com.hotel.service;

import java.awt.image.BufferedImage;

public class CameraService {
    private boolean isRunning = false;

    public void startCamera() {
        this.isRunning = true;
        // TODO: Khởi tạo JavaCV FrameGrabber / OpenCV VideoCapture ở bước tích hợp AI
    }

    public void stopCamera() {
        this.isRunning = false;
        // TODO: Giải phóng tài nguyên Camera ở bước tích hợp AI
    }

    public BufferedImage captureFrame() {
        if (!isRunning) {
            return null;
        }
        BufferedImage image = null;
        // TODO: Chụp và chuyển đổi khung hình từ Camera sang BufferedImage ở bước tích hợp AI
        return image;
    }

    public boolean isRunning() {
        return isRunning;
    }
}