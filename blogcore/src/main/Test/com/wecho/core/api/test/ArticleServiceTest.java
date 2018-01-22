package com.wecho.core.api.test;

import com.wecho.core.service.ArticleService;
import com.wecho.core.test.base.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleServiceTest extends SpringTestBase{

    @Autowired
    private ArticleService articleService;

    @Test
    public void testListArticles(){
        System.out.println(articleService.listArticles().size());;
    }

}