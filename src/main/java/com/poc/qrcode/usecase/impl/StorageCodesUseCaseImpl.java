package com.poc.qrcode.usecase.impl;

import com.poc.qrcode.usecase.StorageCodesUseCase;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class StorageCodesUseCaseImpl implements StorageCodesUseCase {

    public static final Map<String, Set<String>> CODE_BY_USER = new HashMap<>();

    @Override
    public boolean isValid(String user, Set<String> codes) {
        if (!CODE_BY_USER.containsKey(user)) {
            return false;
        }

        return codes.containsAll(CODE_BY_USER.get(user));
    }

    @Override
    public void storage(String user, Set<String> codes) {
        CODE_BY_USER.put(user, codes);
    }

}
