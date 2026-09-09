package com.shortlinkx.util;

import com.shortlinkx.repository.UrlRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ShortCodeGenerator {

    private static final String BASE62 =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final int CODE_LENGTH = 7;

    private final SecureRandom random = new SecureRandom();

    private final UrlRepository urlRepository;

    public ShortCodeGenerator(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String generateUniqueCode() {

        String code;

        do {
            code = generateCode();
        } while (urlRepository.existsByShortCode(code));

        return code;
    }

    private String generateCode() {

        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {

            int index = random.nextInt(BASE62.length());

            code.append(BASE62.charAt(index));
        }

        return code.toString();
    }
}