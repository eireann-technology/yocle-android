package com.yocle.app;

public class RowItem {

    private String title;
    private int icon;
    private String pageUrl;
    private String counter;

    public RowItem(String title, int icon, String pageUrl, String counter) {

        this.title = title;
        this.icon = icon;
        this.pageUrl = pageUrl;
        this.counter = counter;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public int getIcon() {
        return icon;
    }


    public void setIcon(int icon) {
        this.icon = icon;
    }


    public String getPageUrl() {
        return pageUrl;
    }


    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getCounter() {
        return counter;
    }


    public void setCounter(String counter) {
        this.counter = counter;
    }

}
