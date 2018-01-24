package com.wecho.core.jdbc.dao;

import com.wecho.core.model.Article;

public interface ArticleDao {
    /**
     * 根据id获取文章
     * @param id Article PK
     * @return Article
     */
    Article getArticle(String id);

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
