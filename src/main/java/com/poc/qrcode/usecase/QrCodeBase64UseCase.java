package com.poc.qrcode.usecase;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QrCodeBase64UseCase {

    String gerarQrCodeBase64ParaUsuario(String usuario, Integer time) throws IOException, WriterException;

    boolean validarCodigosUsuario(String usuario, String codigo1, String codigo2, String codigo3);
}
