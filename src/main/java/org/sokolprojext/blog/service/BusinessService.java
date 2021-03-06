package org.sokolprojext.blog.service;

import org.sokolprojext.blog.entity.Article;
import org.sokolprojext.blog.entity.Category;
import org.sokolprojext.blog.entity.Comment;
import org.sokolprojext.blog.exception.RedirectToValidUrlException;
import org.sokolprojext.blog.model.Items;

import java.util.List;
import java.util.Map;

public interface BusinessService {
    Map<Integer, Category> mapCategories ();
    Items<Article> listArticles(int offset, int limit);

    Items<Article> listArticlesByCategory(String categoryUrl, int offset, int limit);

    Category findCategoryByUrl(String categoryUrl);

    Items<Article> listArticlesBySearchQuery(String searchQuery, int offset, int limit);

    Article viewArticle(Long idArticle, String requestUrl) throws RedirectToValidUrlException;

    List<Comment> listComments(long idArticle, int offset, int limit);
}
