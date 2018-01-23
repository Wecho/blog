package com.wecho.core.api;

import com.wecho.core.model.Article;
import com.wecho.core.service.ArticleService;
import com.wecho.core.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleAjax {

    private final ArticleService articleService;

    @Autowired
    public ArticleAjax(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(value = "/all")
    public ResultBean<List<Article>> listArticles(){
        return new ResultBean<>(articleService.listArticles());
    }

}
