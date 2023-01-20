package com.increff.store.util;

import java.util.Random;

public class CreateRandomSequence {
    public static String createRandomOrderCode() {
        Integer leftLimit = 97; // letter 'a'
        Integer rightLimit = 122; // letter 'z'
        Integer targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return(generatedString);
    }
}
