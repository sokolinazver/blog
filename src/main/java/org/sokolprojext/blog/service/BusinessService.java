package org.sokolprojext.blog.service;

import org.sokolprojext.blog.entity.Article;
import org.sokolprojext.blog.entity.Category;
import org.sokolprojext.blog.model.Items;

import java.util.Map;

public interface BusinessService {
    Map<Integer, Category> mapCategories ();
    Items<Article> listArticles(int offset, int limit);
}
