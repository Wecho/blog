package com.wecho.core.jdbc.dao;

import com.wecho.core.model.Article;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ArticleDao {

    List<Article> listArticles();

    /**
     * 根据id获取文章
     * @param id Article PK
     * @return Article
     */
    Article getArticle(String id) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    /**
     * 更新文章-覆盖
     * @param article
     * @return
     */
    Article updateArticle(Article article);

    /**
     * 更新文章-
     * @param article
     * @return
     */
    Article updateArticleSelective(Article article);
}
