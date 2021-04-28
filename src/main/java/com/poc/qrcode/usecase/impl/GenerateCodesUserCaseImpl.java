package com.poc.qrcode.usecase.impl;

import com.poc.qrcode.usecase.GenerateCodesUserCase;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class GenerateCodesUserCaseImpl implements GenerateCodesUserCase {

    @Override
    public Set<String> generate(int size) {
        Set<String> codes = new HashSet<>(size);
        for (int i = 0; i < size; i++) {
            String codigo = UUID.randomUUID().toString();
            codes.add(codigo);
        }
        return codes;
    }

}
