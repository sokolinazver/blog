package org.sokolprojext.blog.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.sokolprojext.blog.dao.mapper.MapCategoryMapper;
import org.sokolprojext.blog.entity.Category;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class SQLDAO {
    private final QueryRunner sql = new QueryRunner();

    public Map<Integer, Category> mapCategories(Connection c) throws SQLException {
        return sql.query(c, "select * from category", new MapCategoryMapper());
    }

}
