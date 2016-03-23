package com.company;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by majunqi0102 on 3/22/16.
 */
public class CrawlRunnable implements Runnable {
    private ArrayList<String> urls;
    private String website;
    private Main main;

    private String fb = "https://www.facebook.com";
    private String linkedin = "https://www.linkedin.com/";
    private String googleplus = "https://plus.google.com/";

    public void setMain(Main main) {
        this.main = main;
    }


    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    @Override
    public void run() {
        String url;
        int i = 1;
        int j = 1;
        System.out.println(getWebsite());
        Iterator it = urls.iterator();
        while (it.hasNext()) {
            if ((url = it.next().toString()).contains(website)) {
                System.out.println(i);
                System.out.println(j++);
                if (website.contains(fb)) {
                    main.fbCrawler(url);
                } else if (website.contains(linkedin)) {
                    main.linkedinCrawler(url);
                } else if (website.contains(googleplus)) {
                    main.googlePlusCrawler(url);
                }
                System.out.println("\n" + main.findUsername(url));
                System.out.println();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
    }
}
