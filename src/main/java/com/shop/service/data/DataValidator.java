package com.shop.service.data;

import org.springframework.stereotype.Service;

@Service
public class DataValidator {

    public boolean isBlankText(String text) {
        return text == null || text.isBlank();
    }
}
