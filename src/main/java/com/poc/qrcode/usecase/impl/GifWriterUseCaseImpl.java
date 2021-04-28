package com.poc.qrcode.usecase.impl;

import com.poc.qrcode.usecase.GifWriterUseCase;
import com.poc.qrcode.util.GifSequenceWriter;
import org.springframework.stereotype.Service;

import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class GifWriterUseCaseImpl implements GifWriterUseCase {

    @Override
    public ByteArrayOutputStream generateGif(List<BufferedImage> images, int time) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageOutputStream ios = new MemoryCacheImageOutputStream(baos);

        var imageType = images.get(0).getType();
        GifSequenceWriter writer = new GifSequenceWriter(ios, imageType, time, true);
        for (BufferedImage qrCode : images) {
            writer.writeToSequence(qrCode);
        }
        writer.close();
        ios.close();
        baos.close();

        return baos;
    }

}
