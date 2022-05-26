package org.sokolprojext.blog.service;

import org.sokolprojext.blog.entity.Category;

import java.util.Map;

public interface BusinessService {
    Map<Integer, Category> mapCategories ();
}
