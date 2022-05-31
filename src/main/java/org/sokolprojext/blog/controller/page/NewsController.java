package org.sokolprojext.blog.controller.page;

import org.sokolprojext.blog.Constants;
import org.sokolprojext.blog.controller.AbstractController;
import org.sokolprojext.blog.entity.Article;
import org.sokolprojext.blog.entity.Category;
import org.sokolprojext.blog.exception.ApplicationException;
import org.sokolprojext.blog.model.Items;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/news", "/news/*"})
public class NewsController extends AbstractController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = req.getRequestURI();
        Items<Article> items = null;
        if (requestUrl.endsWith("/news") || requestUrl.endsWith("/news/")) {
            items = getBusinessService().listArticles(0, Constants.LIMIT_ARTICLES_PER_PAGE);
        } else {
            String categoryUrl = requestUrl.replace("/news","");
            items = getBusinessService().listArticlesByCategory(categoryUrl,0,Constants.LIMIT_ARTICLES_PER_PAGE);
            Category category = getBusinessService().findCategoryByUrl(categoryUrl);
            req.setAttribute("selectedCategory", category);
        }


        req.setAttribute("list", items.getItems());
        forwardToPage("/news.jsp", req, resp);
    }
}
