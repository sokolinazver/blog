package org.sokolprojext.blog.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sokolprojext.blog.service.impl.ServiceManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServiceManager.getInstance(sce.getServletContext());
        LOGGER.info(">> Application started");
        System.out.println(">>>> application STARTED LISTENER");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServiceManager.getInstance(sce.getServletContext()).destroy();
        LOGGER.info(">> Application destroyer");


    }
}
