package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
        int i = 201;
        int j = 201;
        System.out.println(getWebsite());
        Iterator it = urls.iterator();
        try {
//            FileWriter fw1 = new FileWriter("out/facebook.txt");
//            FileWriter fw1 = new FileWriter("out/facebook101.txt");
            FileWriter fw1 = new FileWriter("out/facebook201.txt");
            BufferedWriter bw1 = new BufferedWriter(fw1);
//            FileWriter fw2 = new FileWriter("out/linkedin.txt");
//            FileWriter fw2 = new FileWriter("out/linkedin101.txt");
            FileWriter fw2 = new FileWriter("out/linkedin201.txt");
            BufferedWriter bw2 = new BufferedWriter(fw2);
//            FileWriter fw3 = new FileWriter("out/googleplus.txt");
//            FileWriter fw3 = new FileWriter("out/googleplus101.txt");
            FileWriter fw3 = new FileWriter("out/googleplus201.txt");
            BufferedWriter bw3 = new BufferedWriter(fw3);

            while (it.hasNext()) {
                if ((url = it.next().toString()).contains(website)) {
                    System.out.println(i);
                    System.out.println(j++);
                    if (website.contains(fb)) {
//                        if (i == 204) {
                            bw1.write(i + "\t");
                            main.fbCrawler(url, bw1);
                            bw1.newLine();
//                        }
                    } else if (website.contains(linkedin)) {
//                        if (i > 287) {
                            bw2.write(i + "\t");
                            main.linkedinCrawler(url, bw2);
                            bw2.newLine();
//                        }
                    } else if (website.contains(googleplus)) {
//                        if (i == 284) {
                            bw3.write(i + "\t");
                            main.googlePlusCrawler(url, bw3);
                            bw3.newLine();
//                        }
                    }
                    System.out.println();
                    Thread.sleep(1000);
                }
                i++;
            }
            bw1.close();
            fw1.close();
            bw2.close();
            fw2.close();
            bw3.close();
            fw3.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }
}
