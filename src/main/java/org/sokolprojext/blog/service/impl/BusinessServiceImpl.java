package org.sokolprojext.blog.service.impl;

import org.sokolprojext.blog.dao.SQLDAO;
import org.sokolprojext.blog.entity.Article;
import org.sokolprojext.blog.entity.Category;
import org.sokolprojext.blog.entity.Comment;
import org.sokolprojext.blog.exception.ApplicationException;
import org.sokolprojext.blog.exception.RedirectToValidUrlException;
import org.sokolprojext.blog.model.Items;
import org.sokolprojext.blog.service.BusinessService;
import org.w3c.dom.ls.LSOutput;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

class BusinessServiceImpl implements BusinessService {
    private final DataSource dataSource;
    private final SQLDAO sql;

    BusinessServiceImpl(ServiceManager serviceManager) {
        super();
        this.dataSource = serviceManager.dataSource;
        this.sql = new SQLDAO();
    }

    @Override
    public Map<Integer, Category> mapCategories() {
        try (Connection c = dataSource.getConnection()) {
            return sql.mapCategories(c);
        } catch (Exception e) {
            throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
        }
    }

    @Override
    public Items<Article> listArticles(int offset, int limit) {
        try (Connection c = dataSource.getConnection()) {
            Items<Article> items = new Items<>();
            items.setItems(sql.listArticles(c, offset, limit));
            items.setCount(sql.countArticles(c));
            return items;
        } catch (SQLException e) {
            throw new ApplicationException("Can't execute db command:" + e.getMessage(), e);
        }
    }

    @Override
    public Items<Article> listArticlesByCategory(String categoryUrl, int offset, int limit) {
        try (Connection c = dataSource.getConnection()) {
            Items<Article> items = new Items<>();
            items.setItems(sql.listArticlesByCategory(c, categoryUrl, offset, limit));
            items.setCount(sql.countArticlesByCategory(c, categoryUrl));
            return items;
        } catch (SQLException e) {
            throw new ApplicationException("Can't execute db command:" + e.getMessage(), e);
        }
    }

    @Override
    public Category findCategoryByUrl(String categoryUrl) {
        try (Connection c = dataSource.getConnection()) {
            return sql.findCategoryByUrl(c, categoryUrl);
        } catch (SQLException e) {
            throw new ApplicationException("Cant' execute db command: " + e.getMessage(),e);
        }
    }

    @Override
    public Items<Article> listArticlesBySearchQuery( String searchQuery, int offset, int limit) {
        try (Connection c = dataSource.getConnection()) {
            Items<Article> items = new Items<>();
            items.setItems(sql.listArticlesBYSearchQuery(c, searchQuery, offset, limit));
            items.setCount(sql.countArticlesBySearchQuery(c, searchQuery));
            return items;
        } catch (SQLException e) {
            throw new ApplicationException("Can't execute db command:" + e.getMessage(), e);
        }
    }

    @Override
    public Article viewArticle(Long idArticle, String requestUrl) throws RedirectToValidUrlException {
        try (Connection c = dataSource.getConnection()) {
            Article article = sql.findArticleById(c, idArticle);
            if (article == null) {
                return null;
            }
            if (!article.getArticleLink().equals(requestUrl)) {
                throw new RedirectToValidUrlException(article.getArticleLink());
            } else {
                article.setViews(article.getViews() + 1);
                sql.updateArticleViews(c, article);
                c.commit();
                return article;
            }
        } catch (SQLException e) {
            throw new ApplicationException("Can't execute db command: " + e.getMessage(),e);
        }
    }

    @Override
    public List<Comment> listComments(long idArticle, int offset, int limit) {
        try (Connection c = dataSource.getConnection()) {
            return sql.listComments(c, idArticle, offset, limit);
        } catch (SQLException e) {
            throw new ApplicationException("Can't execute db command: " + e.getMessage(),e);
        }
    }
}
