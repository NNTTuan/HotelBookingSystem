package com.hotel.service;

import com.hotel.model.KhachHang;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OcrService {
    private ITesseract tesseract;

    public OcrService() {
        // Khởi tạo Tess4J engine
        this.tesseract = new Tesseract();
        // Trỏ đường dẫn đến thư mục tessdata chứa font tiếng Việt
        this.tesseract.setDatapath("src/main/resources/tessdata");
        this.tesseract.setLanguage("vie"); // Sử dụng vie.traineddata
    }

    /**
     * Hàm chính thực hiện nhận dạng và trả về đối tượng KhachHang
     */
    public KhachHang trichXuatThongTinCCCD(BufferedImage image) {
        if (image == null) return null;

        try {
            // Bước 1: Tiền xử lý ảnh (Làm nét, chỉnh độ sáng/tương phản)
            BufferedImage processedImage = preprocessImage(image);

            // Bước 2: Đọc văn bản thô từ ảnh bằng Tess4J
            String rawText = tesseract.doOCR(processedImage);

            // Bước 3: Bóc tách thông tin bằng Regex
            String cccd = parseCCCD(rawText);
            String hoTen = parseHoTen(rawText);
            String ngaySinh = parseNgaySinh(rawText);

            // Bước 4: Tạo và trả về đối tượng KhachHang
            if (cccd != null || hoTen != null) {
                return new KhachHang(cccd, hoTen, ngaySinh);
            }
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ================= BÁC TÁCH DỮ LIỆU (HELPER METHODS) =================

    private BufferedImage preprocessImage(BufferedImage image) {
        // TODO: Chuyển ảnh màu sang ảnh xám / tăng độ tương phản để OCR đọc chính xác hơn
        return image;
    }

    private String parseCCCD(String text) {
        // Tìm chuỗi gồm 12 chữ số liên tiếp
        Pattern pattern = Pattern.compile("\\b\\d{12}\\b");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group() : "";
    }

    private String parseHoTen(String text) {
        // TODO: Viết biểu thức chính quy (Regex) tìm tên in hoa sau từ "Họ và tên" / "Full name"
        return "";
    }

    private String parseNgaySinh(String text) {
        // Tìm chuỗi định dạng dd/mm/yyyy
        Pattern pattern = Pattern.compile("\\b\\d{2}/\\d{2}/\\d{4}\\b");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group() : "";
    }
}