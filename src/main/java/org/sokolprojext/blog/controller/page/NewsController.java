package org.sokolprojext.blog.controller.page;

import org.sokolprojext.blog.controller.AbstractController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/news","/news/*"})
public class NewsController extends AbstractController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getBusinessService();
       forwardToPage("news.jsp",req,resp);
    }
}
