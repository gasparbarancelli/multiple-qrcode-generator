package com.poc.qrcode.usecase;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface GifWriterUseCase {

    ByteArrayOutputStream generateGif(List<BufferedImage> images, int time) throws IOException;

}
