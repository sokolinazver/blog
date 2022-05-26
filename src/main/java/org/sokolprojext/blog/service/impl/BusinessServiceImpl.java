package org.sokolprojext.blog.service.impl;

import org.sokolprojext.blog.dao.SQLDAO;
import org.sokolprojext.blog.entity.Category;
import org.sokolprojext.blog.exception.ApplicationException;
import org.sokolprojext.blog.service.BusinessService;

import javax.sql.DataSource;
import java.sql.Connection;
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
}
