package com.wecho.core.model.test;

import com.wecho.core.model.User;
import com.wecho.core.spring.config.LoginConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= LoginConfig.class)
public class TestUser {
/*    @Autowired
    User user;
    @Test
    public void testUser(){
        Assert.assertNotNull(user);
    }*/
}