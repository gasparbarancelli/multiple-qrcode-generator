package com.poc.qrcode.usecase;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface MultipleQrCodeUseCase {

    String generate(String user, int size, int time) throws IOException, WriterException;

    boolean validate(String user, String[] codes);
}
