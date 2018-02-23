package com.wecho.core.service;

import com.wecho.core.dao.ArticleMapper;
import com.wecho.core.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    /**
     *
     * @return
     */
    public List<Article> listArticles(){
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("validFlag","1");
        return articleMapper.selectByExample(example);
    }
}