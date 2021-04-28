package com.poc.qrcode.usecase;

import java.util.Set;

public interface StorageCodesUseCase {

    boolean isValid(String user, Set<String> codes);

    void storage(String user, Set<String> codes);

}
