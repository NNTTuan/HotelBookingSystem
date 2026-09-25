package com.hotel.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    /**
     * Thay đổi kích thước ảnh (Resize)
     */
    public static BufferedImage resizeImage(BufferedImage image, int newWidth, int newHeight) {
        if (image == null) return null;

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();

        // Bổ sung chất lượng mượt hình khi co giãn
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return resizedImage;
    }

    /**
     * Lưu BufferedImage ra file đĩa (Ví dụ: png, jpg)
     */
    public static void saveImage(BufferedImage image, String filePath) {
        if (image == null || filePath == null || filePath.isEmpty()) return;

        try {
            File outputFile = new File(filePath);

            // Tự động tạo thư mục nếu chưa có (ví dụ thư mục captures/)
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }

            // Lấy đuôi file (png/jpg) từ đường dẫn
            String formatName = filePath.substring(filePath.lastIndexOf(".") + 1);
            ImageIO.write(image, formatName, outputFile);
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu ảnh: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Chuyển đổi ảnh màu sang ảnh xám (Grayscale)
     */
    public static BufferedImage convertToGrayscale(BufferedImage image) {
        if (image == null) return null;

        int width = image.getWidth();
        int height = image.getHeight();

        // Sử dụng TYPE_BYTE_GRAY để ép màu về mức xám
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = grayImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return grayImage;
    }
}