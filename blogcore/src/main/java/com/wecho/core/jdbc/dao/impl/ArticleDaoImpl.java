package com.wecho.core.jdbc.dao.impl;

import com.wecho.core.jdbc.dao.ArticleDao;
import com.wecho.core.jdbc.template.JdbcUtil;
import com.wecho.core.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleDaoImpl implements ArticleDao {

    private final JdbcUtil jdbcUtil;

    @Autowired
    public ArticleDaoImpl(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    @Override
    public List<Article> listArticles() {
        return null;
    }

    @Override
    public Article getArticle(String id) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return jdbcUtil.selectByPrimaryKey(Article.class,"article");
    }

    @Override
    public Article updateArticle(Article article) {
        return null;
    }

    @Override
    public Article updateArticleSelective(Article article) {
        return null;
    }
}
