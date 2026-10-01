package com.hotel.util;

import javax.swing.*;
import java.awt.*;

public class SwingUtils {
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);

    public static void showErrorDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Lỗi Hệ Thống", JOptionPane.ERROR_MESSAGE);
    }

    public static void showErrorDialog(String message) {
        showErrorDialog(null, message);
    }

    public static void showSuccessDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showSuccessDialog(String message) {
        showSuccessDialog(null, message);
    }

    public static void setCustomFont(Component comp) {
        setCustomFont(comp, FONT_REGULAR);
    }

    public static void setCustomFont(Component comp, Font font) {
        if (comp == null) return;
        comp.setFont(font);
        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                setCustomFont(child, font);
            }
        }
    }
}