package com.evgeniymakovsky.utils;

import org.apache.log4j.Logger;

import java.util.Random;

/**
 * Class RandomStringGenerator generates String with random characters and digits
 */
public class RandomStringGenerator {

    final static Logger LOGGER = Logger.getLogger(RandomStringGenerator.class);

    /**
     * Method getRandomString generates String with random characters in upper, lower case
     * and digits.
     *
     * @param stringLength number of random characters in upper, lower case and digits in String
     * @return random filled String
     */
    public static String getRandomString(int stringLength) {
        StringBuffer stringBuffer = new StringBuffer(stringLength);

        while (true) {
            int random = getRandomNumberInRange(48, 122);
            if (random > 57 && random < 65) continue;
            if (random > 90 && random < 97) continue;
            char c = (char) random;
            if (stringBuffer.length() >= stringLength) break;
            stringBuffer.append(c);
        }
        LOGGER.info("String " + stringBuffer.toString() + " successfully generated!");
        return stringBuffer.toString();
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
