package com.hotel.view.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BieuDoDoanhThuTheoLoaiPanel extends JPanel {
    private Map<String, Map<String, Double>> dataDoanhThuTheoLoai;

    public void setData(Map<String, Map<String, Double>> data) {
        this.dataDoanhThuTheoLoai = data;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dataDoanhThuTheoLoai == null || dataDoanhThuTheoLoai.isEmpty()) {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            g.drawString("Chưa có dữ liệu doanh thu theo loại phòng.", 50, 50);
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int padding = 60;

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawLine(padding, height - padding, width - padding, height - padding);
        g2d.drawLine(padding, padding, padding, height - padding);

        double maxVal = 1.0;
        for (Map<String, Double> loaiMap : dataDoanhThuTheoLoai.values()) {
            for (double val : loaiMap.values()) {
                if (val > maxVal) maxVal = val;
            }
        }

        int numMonths = dataDoanhThuTheoLoai.size();
        int usableWidth = width - 2 * padding;
        int groupWidth = usableWidth / Math.max(1, numMonths);

        String[] loaiPhongList = {"Standard", "VIP", "Penthouse"};
        Color[] colors = {
                new Color(33, 150, 243),  // Standard
                new Color(255, 152, 0),   // VIP
                new Color(156, 39, 176)   // Penthouse
        };

        int index = 0;
        for (Map.Entry<String, Map<String, Double>> entry : dataDoanhThuTheoLoai.entrySet()) {
            String thang = entry.getKey();
            Map<String, Double> loaiMap = entry.getValue();

            int groupX = padding + index * groupWidth + 10;
            int barWidth = Math.max(5, (groupWidth - 20) / 3);

            int subIndex = 0;
            for (String loai : loaiPhongList) {
                double doanhThu = loaiMap.getOrDefault(loai, 0.0);
                int barHeight = (int) ((doanhThu / maxVal) * (height - 2 * padding - 50));

                int x = groupX + subIndex * (barWidth + 2);
                int y = height - padding - barHeight;

                g2d.setColor(colors[subIndex]);
                g2d.fillRect(x, y, barWidth, barHeight);
                g2d.setColor(colors[subIndex].darker());
                g2d.drawRect(x, y, barWidth, barHeight);

                subIndex++;
            }

            g2d.setColor(Color.DARK_GRAY);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 9));
            g2d.drawString(thang, groupX + (groupWidth - 20) / 4 - 5, height - padding + 18);

            index++;
        }

        // Chú thích (Legend)
        int legendX = width - 280;
        int legendY = 20;
        for (int k = 0; k < loaiPhongList.length; k++) {
            g2d.setColor(colors[k]);
            g2d.fillRect(legendX + k * 90, legendY, 12, 12);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2d.drawString(loaiPhongList[k], legendX + k * 90 + 16, legendY + 10);
        }
    }
}