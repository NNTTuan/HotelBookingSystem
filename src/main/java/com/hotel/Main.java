package com.hotel;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.hotel.view.MainFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Áp dụng Look & Feel FlatLaf hiện đại
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 8);
            UIManager.put("TextComponent.arc", 8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}