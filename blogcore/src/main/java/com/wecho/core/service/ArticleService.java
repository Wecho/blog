package com.wecho.core.service;

import com.wecho.core.dao.ArticleMapper;
import com.wecho.core.exception.NoSuchEntityException;
import com.wecho.core.jdbc.dao.ArticleDao;
import com.wecho.core.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleMapper articleMapper;

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

    public Article getArticle(String id) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchEntityException {
        Optional<Article> article = Optional.ofNullable(articleDao.getArticle(id));
        return article.orElseThrow(() -> new NoSuchEntityException("查询不到id为["+id+"]的数据"));
    }
}