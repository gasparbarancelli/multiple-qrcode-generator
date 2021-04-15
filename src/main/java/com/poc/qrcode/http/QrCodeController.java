package com.poc.qrcode.http;

import com.google.zxing.WriterException;
import com.poc.qrcode.usecase.QrCodeBase64UseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/qrCode")
public class QrCodeController {
    public static final Map<String, String[]> codigosPorUsuaros = new HashMap<>();

    @Autowired
    private QrCodeBase64UseCase qrCodeBase64UseCase;

    @GetMapping("gerar/{usuario}")
    public ModelAndView generate(@PathVariable("usuario") String usuario, @RequestParam(value = "time", required = false, defaultValue = "500") Integer time) throws IOException, WriterException {
        return new ModelAndView("index").addObject("qrCodes", qrCodeBase64UseCase.gerarQrCodeBase64ParaUsuario(usuario, time));
    }

    @GetMapping("validar/{usuario}")
    public boolean validarQrCode(@PathVariable("usuario") String usuario,
                                 @RequestParam("codigo1") String codigo1,
                                 @RequestParam("codigo2") String codigo2,
                                 @RequestParam("codigo3") String codigo3) {
        return qrCodeBase64UseCase.validarCodigosUsuario(usuario, codigo1, codigo2, codigo3);
    }
}
