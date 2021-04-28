package com.poc.qrcode.http;

import com.google.zxing.WriterException;
import com.poc.qrcode.usecase.MultipleQrCodeUseCase;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final MultipleQrCodeUseCase multipleQrCodeUseCase;

    public QrCodeController(MultipleQrCodeUseCase multipleQrCodeUseCase) {
        this.multipleQrCodeUseCase = multipleQrCodeUseCase;
    }

    @GetMapping("generate/{user}")
    public ModelAndView generate(@PathVariable("user") String user,
                                 @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
                                 @RequestParam(value = "time", required = false, defaultValue = "500") Integer time) throws IOException, WriterException {
        var qrcode = multipleQrCodeUseCase.generate(user, size, time);
        return new ModelAndView("index").addObject("qrCodes", qrcode);
    }

    @GetMapping("validate/{user}")
    public boolean validarQrCode(@PathVariable("user") String user,
                                 @RequestParam("codes") String[] codes) {
        return multipleQrCodeUseCase.validate(user, codes);
    }
}
