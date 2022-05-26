package org.sokolprojext.blog.controller.page;

import org.sokolprojext.blog.Constants;
import org.sokolprojext.blog.controller.AbstractController;
import org.sokolprojext.blog.entity.Article;
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
        if (requestUrl.endsWith("/news") || requestUrl.startsWith("/news/")) {
            items = getBusinessService().listArticles(0, Constants.LIMIT_ARTICLES_PER_PAGE);
        } else {
            //TODO display articles for category
        }


        req.setAttribute("list", items.getItems());
        forwardToPage("/news.jsp", req, resp);
    }
}
