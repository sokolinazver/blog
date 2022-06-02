package org.sokolprojext.blog.controller.page;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.sokolprojext.blog.Constants;
import org.sokolprojext.blog.controller.AbstractController;
import org.sokolprojext.blog.entity.Article;
import org.sokolprojext.blog.model.Items;
import org.sokolprojext.blog.model.Pagination;

@WebServlet("/search")
public class SearchController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        if (StringUtils.isNotBlank(query)) {
            int offset = getOffset(req, Constants.LIMIT_ARTICLES_PER_PAGE);
            Items<Article> items = getBusinessService().listArticlesBySearchQuery(query, 0, Constants.LIMIT_ARTICLES_PER_PAGE);
            req.setAttribute("list", items.getItems());
            req.setAttribute("count", items.getCount());
            req.setAttribute("searchQuery", query);
            Pagination pagination = new Pagination.Builder("/search?query=" + URLEncoder.encode(query, "utf8") + "&", offset, items.getCount()).withLimit(Constants.LIMIT_ARTICLES_PER_PAGE).build();
            req.setAttribute("pagination",pagination);
            forwardToPage("search.jsp", req, resp);
        } else {
            resp.sendRedirect("/news");
        }

    }
}
