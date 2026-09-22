package com.hotel.util;

import javax.swing.*;
import java.awt.*;

public class SwingUtils {

    // Định nghĩa font chữ mặc định chuẩn giao diện hiện đại
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);

    /**
     * Hiển thị hộp thoại thông báo lỗi
     */
    public static void showErrorDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Lỗi Hệ Thống",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void showErrorDialog(String message) {
        showErrorDialog(null, message);
    }

    /**
     * Hiển thị hộp thoại thông báo thành công
     */
    public static void showSuccessDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Thông Báo",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void showSuccessDialog(String message) {
        showSuccessDialog(null, message);
    }

    /**
     * Đặt font chữ mặc định cho một component.
     * Nếu là Panel/Container, hàm sẽ tự động áp dụng đệ quy cho toàn bộ component con bên trong.
     */
    public static void setCustomFont(Component comp) {
        setCustomFont(comp, FONT_REGULAR);
    }

    /**
     * Đặt font chữ tùy chỉnh cụ thể cho component và các component con.
     */
    public static void setCustomFont(Component comp, Font font) {
        if (comp == null) return;

        comp.setFont(font);

        // Đệ quy áp dụng font cho tất cả component con (nếu comp là JPanel, JFrame, JDialog...)
        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                setCustomFont(child, font);
            }
        }
    }
}