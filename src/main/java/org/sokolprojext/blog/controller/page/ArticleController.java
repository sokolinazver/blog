package org.sokolprojext.blog.controller.page;

import org.apache.commons.lang3.StringUtils;
import org.sokolprojext.blog.Constants;
import org.sokolprojext.blog.controller.AbstractController;
import org.sokolprojext.blog.entity.Article;
import org.sokolprojext.blog.entity.Comment;
import org.sokolprojext.blog.exception.RedirectToValidUrlException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/article/*")
public class ArticleController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String requestUrl = req.getRequestURI();
            String id = StringUtils.split(requestUrl, "/")[1];
            Article article = getBusinessService().viewArticle(Long.parseLong(id), requestUrl);
            if (article == null) {
                resp.sendRedirect("/404?url=" + requestUrl);
            } else {
                req.setAttribute("article", article);
                List<Comment> comments = getBusinessService().listComments(article.getId(),0, Constants.LIMIT_COMMENTS_PER_PAGE);
                req.setAttribute("comments", comments);
                forwardToPage("article.jsp", req, resp);
            }
        } catch (RedirectToValidUrlException e) {
            resp.sendRedirect(e.getUrl());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException exception) {
            resp.sendRedirect("/news");
        }
    }
}
