package com.poc.qrcode.usecase.converter;

import com.google.zxing.WriterException;

import java.awt.image.BufferedImage;

public interface QrCodeConverter {

    BufferedImage converteTextToBufferedImageQrCode(String text) throws WriterException;
}
