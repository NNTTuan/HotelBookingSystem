package com.hotel.service;

import java.awt.image.BufferedImage;

class CameraService
{
    boolean isRunning = false;
    public void startCamera()
    {
        isRunning = true;
    }
    public void stopCamera()
    {
        isRunning = false;
    }
    public BufferedImage captureFrame()
    {
        BufferedImage image = null;
        return image;
    }
}