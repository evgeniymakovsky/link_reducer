package com.evgeniymakovsky.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RandomStringGeneratorTest {

    @Test(expected = NegativeArraySizeException.class)
    public void getRandomString_call_negativeArgument_expect_NegativeArraySizeException() {
        RandomStringGenerator.getRandomString(-1);
    }

    @Test
    public void getRandomString_expect_ok() {
        String randomString = RandomStringGenerator.getRandomString(5);
        assertTrue(!randomString.isEmpty());
        assertTrue(randomString.length()==5);
    }
}
