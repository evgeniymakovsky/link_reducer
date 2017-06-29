package com.evgeniymakovsky.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RedirectControllerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void redirectControllerTest_bean_not_null() {
        RedirectController redirectController = applicationContext.getBean("redirectController", RedirectController.class);
        assertNotNull(redirectController);

        redirectController = (RedirectController) applicationContext.getBean("redirectController");
        assertNotNull(redirectController);
    }
}
