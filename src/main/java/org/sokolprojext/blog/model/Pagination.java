package org.sokolprojext.blog.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

// Use new Pagination.Builder() to build pagination instance

public class Pagination extends AbstractModel {
    private final String previousUrl;
    private final String nextUrl;
    private final List<PageItem> pages;

    private Pagination(String previousUrl, String nextUrl, List<PageItem> pages) {
        this.previousUrl = previousUrl;
        this.nextUrl = nextUrl;
        this.pages = pages;
    }

    public boolean isPrevious() {
        return previousUrl != null;
    }

    public boolean isNext() {
        return nextUrl != null;
    }


    public String getPreviousUrl() {
        return previousUrl;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public List<PageItem> getPages() {
        return pages;
    }

    public static final class PageItem extends AbstractModel {
        private final String url;
        private final String caption;

        public PageItem(String url, String caption) {
            this.url = url;
            this.caption = caption;
        }

        public String getUrl() {
            return url;
        }

        public String getCaption() {
            return caption;
        }

        public boolean isEllipsis() {
            return url == null && caption == null;
        }

        public boolean isCurrent() {
            return url == null && caption != null;
        }

        public boolean isPageItem() {
            return url != null && caption != null;
        }

        private static PageItem createCurrent(String caption) {
            return new PageItem(null, caption);
        }

        private static PageItem createEllipsis() {
            return new PageItem(null, null);
        }

        private static PageItem createPageItems(String url, String caption) {
            return new PageItem(url, caption);
        }
    }

    public static class Builder extends AbstractModel {
        private static final int DEFAULT_MAX_PAGINATION_BUTTONS_PER_PAGE = 9;
        private static final int DEFAULT_LIMIT_ITEMS_PER_PAGE = 10;
        private String baseUrl;
        private int offset;
        private int limit;
        private int count;
        private int maxPaginationbuttonsPerPage;

        public Builder(String baseUrl, int offset, int count) {
            super();
            this.baseUrl = baseUrl;
            this.count = count;
            this.offset = offset;
            this.limit = DEFAULT_LIMIT_ITEMS_PER_PAGE;
            this.maxPaginationbuttonsPerPage = DEFAULT_MAX_PAGINATION_BUTTONS_PER_PAGE;
        }

        public Builder withLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder withMaxPaginationButtonsPerPage(int maxPaginationButtonsPerPage) {
            this.maxPaginationbuttonsPerPage = maxPaginationButtonsPerPage;
            return this;
        }

        private List<PageItem> createPageItems(int currentPage, int minPage, int maxPage) {
            List<PageItem> pages = new ArrayList<>();
            for (int page = minPage; page < maxPage; page++) {
                if (currentPage == page) {
                    pages.add(PageItem.createCurrent(String.valueOf(page)));
                } else {
                    pages.add(PageItem.createPageItems(createUrlForPage(page), String.valueOf(page)));
                }
            }
            return pages;
        }

        private String createUrlForPage(int page) {
            if (page > 1) {
                return baseUrl + "page=" + page;
            } else {
                return baseUrl.substring(0, baseUrl.length() - 1);
            }
        }

        private String getPreviousUrl(int currentPage) {
            if (currentPage > 1) {
                return baseUrl + "page=" + (currentPage - 1);
            } else {
                return null;
            }
        }

        private String getNextUrl(int currentPage) {
            if (offset + limit < count) {
                return baseUrl + "page=" + (currentPage + 1);
            } else {
                return null;
            }
        }

        private int getMaxPage() {
            int maxPage = count / limit;
            if (count % limit > 0) {
                maxPage++;
            }
            return maxPage;
        }

        private List<PageItem> createButtonsOnly(int currentPage, int maxPage) {
            return createPageItems(currentPage, 1, maxPage);
        }

        private List<PageItem> createButtonsWithLastPageOnly(int currentPage, int maxPage) {
            List<PageItem> pages = createPageItems(currentPage, 1, (maxPaginationbuttonsPerPage - 2));
            pages.add(PageItem.createEllipsis());
            pages.add(PageItem.createPageItems(createUrlForPage(maxPage), String.valueOf(maxPage)));
            return pages;
        }

        private List<PageItem> createButtonsWithFirstPageOnly(int currentPage, int maxPage) {
            List<PageItem> pages = new ArrayList<>();
            pages.add(PageItem.createPageItems(createUrlForPage(1), "1"));
            pages.add(PageItem.createEllipsis());
            pages.addAll(createPageItems(currentPage, maxPage - (maxPaginationbuttonsPerPage - 3), maxPage));
            return pages;
        }

        private List<PageItem> createButtonsWithMiddlePages(int currentPage, int maxPage) {
            int shiftValue = (maxPaginationbuttonsPerPage - 5) / 2;
            List<PageItem> pages = new ArrayList<>();
            pages.add(PageItem.createPageItems(createUrlForPage(1), "1"));
            pages.add(PageItem.createEllipsis());
            pages.addAll(createPageItems(currentPage, currentPage - shiftValue, currentPage + shiftValue));
            pages.add(PageItem.createEllipsis());
            pages.add(PageItem.createPageItems(createUrlForPage(maxPage), String.valueOf(maxPage)));
            return pages;
        }

        public Pagination build() {
            if (count <= limit) {
                return null;
            }
            int currentPage = offset / limit + 1;
            String previousUrl = getPreviousUrl(currentPage);
            String nextUrl = getNextUrl(currentPage);
            int maxPage = getMaxPage();
            List<PageItem> pages;
            if (maxPage <= maxPaginationbuttonsPerPage) {
                pages = createButtonsOnly(currentPage, maxPage);
            } else {
                int borderValue = (maxPaginationbuttonsPerPage - 1) / 2;
                if (currentPage < (maxPaginationbuttonsPerPage - borderValue)) {
                    pages = createButtonsWithLastPageOnly(currentPage, maxPage);
                } else if (currentPage > maxPage - (maxPaginationbuttonsPerPage - borderValue)) {
                    pages = createButtonsWithFirstPageOnly(currentPage, maxPage);
                } else {
                    pages = createButtonsWithMiddlePages(currentPage, maxPage);
                }
            }
            return new Pagination(previousUrl, nextUrl, pages);
        }
    }
}
