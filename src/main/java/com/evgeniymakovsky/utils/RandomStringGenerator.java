package com.evgeniymakovsky.utils;

import org.apache.log4j.Logger;

import java.util.Random;

public class RandomStringGenerator {

    final static Logger logger = Logger.getLogger(RandomStringGenerator.class);

    public static String getRandomString(int stringLength) {
        logger.info("Start getRandomString(int stringLength)");
        StringBuffer stringBuffer = new StringBuffer(stringLength);

        while (true) {
            int random = getRandomNumberInRange(48, 122);
            if (random > 57 && random < 65) continue;
            if (random > 90 && random < 97) continue;
            char c = (char) random;
            if (stringBuffer.length() >= stringLength) break;
            stringBuffer.append(c);
        }
        logger.info("String " + stringBuffer.toString() + " successfully generated!");
        return stringBuffer.toString();
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(getRandomString(5));
        }
    }
}
