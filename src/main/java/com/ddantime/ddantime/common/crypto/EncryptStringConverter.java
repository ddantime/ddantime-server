package com.ddantime.ddantime.common.crypto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EncryptStringConverter implements AttributeConverter<String, String> {
    private final CryptoService crypto;

    public EncryptStringConverter(CryptoService crypto) {
        this.crypto = crypto;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return crypto.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return crypto.decrypt(dbData);
    }
}