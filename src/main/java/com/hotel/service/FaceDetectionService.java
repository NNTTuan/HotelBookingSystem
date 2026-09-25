package com.hotel.service;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class FaceDetectionService {
    private CascadeClassifier faceDetector;

    public FaceDetectionService() {
        // Đường dẫn đến file mô hình Haar Cascade trong folder resources
        String cascadePath = "src/main/resources/haarcascades/haarcascade_frontalface_alt.xml";
        this.faceDetector = new CascadeClassifier(cascadePath);

        if (this.faceDetector.empty()) {
            System.err.println("Lỗi: Không thể nạp file mô hình haarcascade!");
        }
    }

    /**
     * Nhận vào khung hình từ Camera và cắt riêng vùng chứa khuôn mặt
     */
    public BufferedImage phatHienVaCropMat(BufferedImage frame) {
        if (frame == null || faceDetector.empty()) {
            return null;
        }

        // Bước 1: Chuyển BufferedImage (Swing) -> Mat (OpenCV)
        Mat srcMat = convertBufferedImageToMat(frame);

        // Bước 2: Chuyển sang ảnh xám & cân bằng sáng để tăng độ chính xác
        Mat grayMat = new Mat();
        Imgproc.cvtColor(srcMat, grayMat, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayMat, grayMat);

        // Bước 3: Quét tìm các khuôn mặt trong hình
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(
                grayMat,
                faceDetections,
                1.1,             // scaleFactor: Tỷ lệ thu nhỏ ảnh mỗi lần quét
                3,               // minNeighbors: Số vùng lân cận tối thiểu để xác nhận là mặt
                0,               // flags
                new Size(30, 30),// minSize: Kích thước mặt tối thiểu
                new Size()       // maxSize
        );

        Rect[] faces = faceDetections.toArray();

        // Bước 4: Nếu tìm thấy khuôn mặt, crop lấy khuôn mặt đầu tiên
        if (faces.length > 0) {
            Rect primaryFace = faces[0]; // Lấy khuôn mặt rõ nhất
            Mat croppedMat = new Mat(srcMat, primaryFace); // Cắt khung hình
            return convertMatToBufferedImage(croppedMat);
        }

        return null; // Không tìm thấy khuôn mặt nào
    }

    /**
     * Chuyển đổi từ BufferedImage (Java Swing) sang Mat (OpenCV)
     */
    private Mat convertBufferedImageToMat(BufferedImage img) {
        BufferedImage convertedImg = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR
        );
        convertedImg.getGraphics().drawImage(img, 0, 0, null);

        byte[] pixels = ((DataBufferByte) convertedImg.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, pixels);
        return mat;
    }

    /**
     * Chuyển đổi từ Mat (OpenCV) sang BufferedImage (Java Swing)
     */
    private BufferedImage convertMatToBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] buffer = new byte[bufferSize];
        mat.get(0, 0, buffer);

        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
        return image;
    }
}