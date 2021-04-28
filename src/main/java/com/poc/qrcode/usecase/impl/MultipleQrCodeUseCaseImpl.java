package com.poc.qrcode.usecase.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.poc.qrcode.usecase.GenerateCodesUserCase;
import com.poc.qrcode.usecase.GifWriterUseCase;
import com.poc.qrcode.usecase.MultipleQrCodeUseCase;
import com.poc.qrcode.usecase.StorageCodesUseCase;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Service
public class MultipleQrCodeUseCaseImpl implements MultipleQrCodeUseCase {

    private final StorageCodesUseCase storageCodesUseCase;
    private final GenerateCodesUserCase generateCodesUserCase;
    private final GifWriterUseCase gifWriterUseCase;

    public MultipleQrCodeUseCaseImpl(StorageCodesUseCase storageCodesUseCase, GenerateCodesUserCase generateCodesUserCase, GifWriterUseCase gifWriterUseCase) {
        this.storageCodesUseCase = storageCodesUseCase;
        this.generateCodesUserCase = generateCodesUserCase;
        this.gifWriterUseCase = gifWriterUseCase;
    }

    @Override
    public String generate(String user, int size, int time) throws IOException, WriterException {
        var codes = generateCodesUserCase.generate(size);
        storageCodesUseCase.storage(user, codes);

        var qrCodes = generateQrCodes(codes);
        var gif = gifWriterUseCase.generateGif(qrCodes, time);
        return Base64.getEncoder().encodeToString(gif.toByteArray());
    }

    @Override
    public boolean validate(String user, String[] codigos) {
        return storageCodesUseCase.isValid(user, Set.of(codigos));
    }

    private BufferedImage generateQrCode(String code) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(code, BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    private List<BufferedImage> generateQrCodes(Set<String> codes) throws WriterException {
        List<BufferedImage> qrCodes = new ArrayList<>(codes.size());
        for (String code : codes) {
            qrCodes.add(generateQrCode(code));
        }
        return qrCodes;
    }

}
