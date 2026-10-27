package com.hotel.view.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BieuDoDoanhThuPanel extends JPanel {
    private Map<String, Double> dataDoanhThuThang;

    public void setData(Map<String, Double> data) {
        this.dataDoanhThuThang = data;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dataDoanhThuThang == null || dataDoanhThuThang.isEmpty()) {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            g.drawString("Chưa có dữ liệu doanh thu để hiển thị biểu đồ.", 50, 50);
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

        double maxVal = dataDoanhThuThang.values().stream().max(Double::compareTo).orElse(1.0);
        if (maxVal == 0) maxVal = 1.0;

        int numBars = dataDoanhThuThang.size();
        int usableWidth = width - 2 * padding;
        int barWidth = Math.max(20, usableWidth / (numBars * 2));
        int spacing = Math.max(10, (usableWidth - (numBars * barWidth)) / (numBars + 1));

        int i = 0;
        for (Map.Entry<String, Double> entry : dataDoanhThuThang.entrySet()) {
            String thang = entry.getKey();
            double doanhThu = entry.getValue();

            int barHeight = (int) ((doanhThu / maxVal) * (height - 2 * padding - 50));
            int x = padding + spacing + i * (barWidth + spacing);
            int y = height - padding - barHeight;

            g2d.setColor(new Color(33, 150, 243));
            g2d.fillRect(x, y, barWidth, barHeight);
            g2d.setColor(new Color(24, 43, 73));
            g2d.drawRect(x, y, barWidth, barHeight);

            g2d.setColor(new Color(30, 30, 30));
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 9));
            String valStr = String.format("%,.0f", doanhThu);

            FontMetrics fm = g2d.getFontMetrics();
            int strWidth = fm.stringWidth(valStr);
            g2d.drawString(valStr, x + (barWidth - strWidth) / 2, y - 6);
            g2d.drawString(thang, x + (barWidth - fm.stringWidth(thang)) / 2, height - padding + 18);
            i++;
        }
    }
}