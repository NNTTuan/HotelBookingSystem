package com.hotel.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageAvatarPanel extends JPanel {
    private BufferedImage avatar;

    public ImageAvatarPanel() {
        setPreferredSize(new Dimension(280, 340));
        setMinimumSize(new Dimension(240, 300));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Ảnh Chân Dung / CCCD", 0, 0, new Font("Segoe UI", Font.BOLD, 14)));
    }

    public void setAvatar(BufferedImage img) {
        this.avatar = img;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int padX = 15;
        int padY = 30;
        int w = getWidth() - (padX * 2);
        int h = getHeight() - padY - 15;

        if (avatar != null) {
            g2d.drawImage(avatar, padX, padY, w, h, null);
        } else {
            g2d.setColor(new Color(230, 230, 230));
            g2d.fillRect(padX, padY, w, h);
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            FontMetrics fm = g2d.getFontMetrics();
            String text = "Chưa có ảnh";
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = (getHeight() + fm.getAscent()) / 2;
            g2d.drawString(text, x, y);
        }
    }
}