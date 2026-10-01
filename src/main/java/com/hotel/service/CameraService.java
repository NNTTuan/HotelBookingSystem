package com.hotel.service;

import com.hotel.util.ImageUtils;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;

public class CameraService {
    private VideoCapture capture;
    private boolean isRunning = false;

    public void startCamera() {
        if (!isRunning) {
            capture = new VideoCapture(0);
            if (capture.isOpened()) {
                isRunning = true;
            }
        }
    }

    public void stopCamera() {
        if (isRunning && capture != null) {
            capture.release();
            isRunning = false;
        }
    }

    public BufferedImage captureFrame() {
        if (!isRunning || capture == null || !capture.isOpened()) {
            return null;
        }
        Mat frame = new Mat();
        if (capture.read(frame) && !frame.empty()) {
            return ImageUtils.matToBufferedImage(frame);
        }
        return null;
    }

    public boolean isRunning() {
        return isRunning;
    }
}