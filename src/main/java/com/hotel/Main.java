package com.hotel;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.hotel.view.MainFrame;
import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("==============================================================");
        System.out.println("LOẠI APP");
        System.out.println("1. Desktop Application (Java Swing GUI)");
        System.out.println("2. Console Application");
        System.out.println("Chọn kiểu app bạn muốn hiển thị: ");
        int choice = sc.nextInt();
        System.out.println("==============================================================");
        switch (choice) {
            case 1:
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

        });break;
        case 2:
    }
}}