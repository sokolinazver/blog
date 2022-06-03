package org.sokolprojext.blog.exception;

public class RedirectToValidUrlException extends Exception {

    String url;

    public RedirectToValidUrlException( String url) {
        super("Should be redirect to" + url);
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
