package com.poc.qrcode.usecase.impl;

import com.google.zxing.WriterException;
import com.poc.qrcode.usecase.GifSequenceWriter;
import com.poc.qrcode.usecase.QrCodeBase64UseCase;
import com.poc.qrcode.usecase.converter.QrCodeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class QrCodeBase64UseCaseImpl implements QrCodeBase64UseCase {

    @Autowired
    private QrCodeConverter qrCodeConverter;

    public static final Map<String, String[]> codigosPorUsuaros = new HashMap<>();


    @Override
    public String gerarQrCodeBase64ParaUsuario(String usuario, Integer time) throws IOException, WriterException {
        final int size = 3;
        String[] codigos = new String[size];
        BufferedImage[] base64s = new BufferedImage[size];
        int i = 0;
        while (i < size) {
            String codigo = UUID.randomUUID().toString();
            codigos[i] = codigo;
            base64s[i] = createQrCodeBase64(codigo);
            i++;
        }
        codigosPorUsuaros.put(usuario, codigos);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageOutputStream output = new MemoryCacheImageOutputStream(out);

        GifSequenceWriter writer = new GifSequenceWriter(output, base64s[0].getType(), time, true);

        for (BufferedImage img : base64s) {
            writer.writeToSequence(img);
        }

        writer.close();
        output.close();
        out.close();

        return Base64.getEncoder().encodeToString(out.toByteArray());
    }

    @Override
    public boolean validarCodigosUsuario(String usuario, String codigo1, String codigo2, String codigo3) {
        if (!codigosPorUsuaros.containsKey(usuario) || StringUtils.isEmpty(codigo1) || StringUtils.isEmpty(codigo2) || StringUtils.isEmpty(codigo3)) {
            return false;
        }
        String[] userValues = codigosPorUsuaros.get(usuario);
        var codigosUsuario = Set.of(userValues);
        var codigosValidar = Set.of(codigo1, codigo2, codigo3);
        if (codigosValidar.size() != userValues.length || !codigosValidar.containsAll(codigosUsuario)) {
            return false;
        }
        return true;
    }

    private BufferedImage createQrCodeBase64(String text) throws WriterException, IOException {
        return qrCodeConverter.converteTextToBufferedImageQrCode(text);
    }
}
