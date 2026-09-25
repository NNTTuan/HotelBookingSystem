package com.hotel.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageAvatarPanel extends JPanel {
    private BufferedImage avatar;

    public ImageAvatarPanel() {
        // Cấu hình khung chứa ảnh mặc định
        setPreferredSize(new Dimension(160, 200));
        setBorder(BorderFactory.createTitledBorder("Ảnh chân dung"));
        setBackground(Color.WHITE);
    }

    /**
     * Cập nhật ảnh đại diện mới và vẽ lại giao diện
     */
    public void setAvatar(BufferedImage img) {
        this.avatar = img;
        repaint(); // Bắt buộc gọi repaint() để Swing vẽ lại khung hình
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (avatar != null) {
            // Tối ưu chất lượng vẽ hình ảnh (chống mờ/vỡ hạt)
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            // Co giãn ảnh vừa khít với kích thước Panel
            g2d.drawImage(avatar, 0, 0, getWidth(), getHeight(), null);
        } else {
            // Vẫn chưa có ảnh -> Vẽ khung xám kèm chữ thông báo
            g.setColor(Color.GRAY);
            FontMetrics fm = g.getFontMetrics();
            String text = "Chưa có ảnh";
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = (getHeight() + fm.getAscent()) / 2;
            g.drawString(text, x, y);
        }
    }
}