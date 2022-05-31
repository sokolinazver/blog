<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<ul id="mobile-category-menu" class="categories dropdown menu align-right hide-for-large" data-dropdown-menu>
    <li>
        <a href="javascript:void(0);">Categories</a>
        <ul class="menu" style="display:none;">
            <c:forEach var="categoryEntry" items="${CATEGORY_MAP }">
                <c:set var="cat" value="${categoryEntry.value}"/>
                <li class="${selectedCategory.id == categoryEntry.key ? 'selected' : ''} item">
                    <a href="/news${cat.url }">${cat.name} <span>(${cat.articles})</span></a>
                </li>

            </c:forEach>
        </ul>
    </li>
</ul>
