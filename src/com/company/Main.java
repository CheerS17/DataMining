package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Junqi Ma
 */
public class Main {

    public static void fbCrawler(String url) {
        String pageCode = null;
        String tempString = null;

        String currentAddress = null;
        String education = null;
        String name = null;

        int indexBegin = 0;
        int indexStop = 0;
        try {
//            long time2 = System.currentTimeMillis();
            Document doc = Jsoup.connect(url).get();
//            System.out.println(System.currentTimeMillis() - time2);
            pageCode = doc.html().replaceAll("\\s", "");
//            System.out.println(pageCode);
            //find name
            tempString = pageCode;
            name = tempString.substring(tempString.indexOf("<titleid=\"pageTitle\">") + "<titleid=\"pageTitle\">".length(), tempString.indexOf("</title>"));
            if (name.contains("|Facebook")) {
                System.out.println(name.substring(0, name.indexOf("|Facebook")));
            } else {
                System.out.println(name);
            }
            tempString = pageCode;
            //find current city
            if (tempString.contains("<divclass=\"fsmfwnfcg\">Currentcity</div>")) {
                indexStop = tempString.indexOf("<divclass=\"fsmfwnfcg\">Currentcity</div>") - 11;
                indexBegin = indexStop - 50;
                currentAddress = tempString.substring(indexBegin, indexStop);
                for (int i = currentAddress.length() - 1; i > 0; i--) {
                    if (currentAddress.charAt(i) == '>') {
                        indexBegin = i + 1;
                        break;
                    }
                }
                //print current city
                System.out.println(currentAddress.substring(indexBegin));
            } else {
                System.out.println("No CurrentCity");
            }

            //find education
            tempString = pageCode;
            if (tempString.contains("Education</span>")) {
                tempString = tempString.substring(tempString.indexOf("Education</span>") + "Education</span>".length());
                tempString = tempString.substring(tempString.indexOf("<ahref") + "<ahref".length());
                indexStop = tempString.indexOf("</a>");
                indexBegin = indexStop - 50;
                education = tempString.substring(indexBegin, indexStop);
                for (int i = education.length() - 1; i > 0; i--) {
                    if (education.charAt(i) == '>') {
                        indexBegin = i + 1;
                        break;
                    }
                }
                //print education
                System.out.println(education.substring(indexBegin));
            } else {
                System.out.println("No Education");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void linkedinCrawler(String url) {
        String pageCode = null;

        String currentAddress = null;
        String education = null;
        String name = null;

        String tempString;
        try {
//            long time2 = System.currentTimeMillis();
            Document doc = Jsoup.connect(url).get();
//            System.out.println(System.currentTimeMillis() - time2);
            pageCode = doc.html().replaceAll("\\s", "");
            //find name
            tempString = pageCode;
            name = tempString.substring(tempString.indexOf("<title>") + "<title>".length(), tempString.indexOf("|LinkedIn") - 1);
            System.out.println(name);

            tempString = pageCode;
            //find current city
            if (tempString.contains("<spanclass=\"locality\">")) {
                tempString = tempString.substring(tempString.indexOf("<spanclass=\"locality\">"));
                currentAddress = tempString.substring("<spanclass=\"locality\">".length(), tempString.indexOf("</span>"));
                //print current city
                System.out.println(currentAddress);
            } else {
                System.out.println("No CurrentCity");
            }

            //find education
            tempString = pageCode;
            if (tempString.contains("<th>Education</th><td><ol><li><a")) {
                tempString = tempString.substring(tempString.indexOf("<th>Education</th><td><ol><li><a") + "<th>Education</th><td><ol><li><a".length());
                education = tempString.substring(tempString.indexOf(">") + 1, tempString.indexOf("</a>"));
                //print education
                System.out.println(education);
            } else {
                System.out.println("No Education");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void googlePlusCrawler(String url) {
        String pageCode = null;

        String currentAddress = null;
        String education = null;
        String name = null;

        String tempString;
        try {
//            long time2 = System.currentTimeMillis();
            Document doc = Jsoup.connect(url).get();
//            System.out.println(System.currentTimeMillis() - time2);
            pageCode = doc.html().replaceAll("\\s", "");
            //find name
            tempString = pageCode;
            name = tempString.substring(tempString.indexOf("<titleitemprop=\"name\">") + "<titleitemprop=\"name\">".length());
            name = name.substring(0, name.indexOf("-About"));
            System.out.println(name);

            tempString = pageCode;
            //find current city
            if (tempString.contains("<divclass=\"adry4\">")) {
                tempString = tempString.substring(tempString.indexOf("<divclass=\"adry4\">") + "<divclass=\"adry4\">".length());
                currentAddress = tempString.substring(0, tempString.indexOf("</div>"));
                //print current city
                System.out.println(currentAddress);
            } else {
                System.out.println("No CurrentCity");
            }

            //find education
            tempString = pageCode;
            if (tempString.contains("<divclass=\"F9a\">Education</div>")) {
                tempString = tempString.substring(tempString.indexOf("<divclass=\"F9a\">Education</div>") + "<divclass=\"F9a\">Education</div>".length());
                education = tempString.substring(tempString.indexOf("<divclass=\"PLa\">") + "<divclass=\"PLa\">".length());
                education = education.substring(0, education.indexOf("</div>"));
                //print education
                System.out.println(education);
            } else {
                System.out.println("No Education");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String findUsername(String url) {
        String username = null;
        if (url.contains("https://www.facebook.com/")) {
            if (url.contains("id=")) {
                username = url.substring(url.indexOf("id=")+3, url.indexOf("&"));
            } else {
                username = url.substring("https://www.facebook.com/".length());
                username = username.substring(0, username.indexOf("/"));
            }
            return username;
        } else if (url.contains("https://plus.google.com/")) {
            if (url.contains("+")) {
                username = url.substring(url.indexOf("+") + 1);
                username = username.substring(0, username.indexOf("/"));
            } else if (url.contains("/u/0/")) {
                username = url.substring(url.indexOf("0/") + 2);
                username = username.substring(0, username.indexOf("/"));
            } else {
                username = url.substring(url.indexOf("com/") + 4);
                username = username.substring(0, username.indexOf("/"));
            }
            return username;

        } else if (url.contains("https://www.linkedin.com/in/")) {
            username = url.substring("https://www.linkedin.com/in/".length());
            if (username.contains("/")) {
                return username.substring(0, username.indexOf("/"));
            } else return username;
        } else return null;
    }

    public static void main(String[] args) {
        Main main = new Main();
//        long time1 = System.currentTimeMillis();
        ArrayList<String> fbUrls;
        List<String> linkedinUrls;
        List<String> googleplusUrls;

        /**
         * please change the file path here
         */
        String pathFb = "/Users/majunqi0102/Documents/DataMining/Data/fb.txt";
        String pathLinkedIn = "/Users/majunqi0102/Documents/DataMining/Data/linkedin.txt";
        String pathGooglePlus = "/Users/majunqi0102/Documents/DataMining/Data/googleplus.txt";

        FileHelper fbFileHelper = new FileHelper();
        FileHelper linkedinFileHelper = new FileHelper();
        FileHelper googlePlusFileHelper = new FileHelper();

        CrawlRunnable fbCrawler = new CrawlRunnable();
        CrawlRunnable linkedinCrawler = new CrawlRunnable();
        CrawlRunnable googleplusCrawler = new CrawlRunnable();

        fbFileHelper.setPath(pathFb);
        linkedinFileHelper.setPath(pathLinkedIn);
        googlePlusFileHelper.setPath(pathGooglePlus);

        fbCrawler.setUrls(fbFileHelper.getUrls());
        fbCrawler.setWebsite("https://www.facebook.com");
        fbCrawler.setMain(main);
        linkedinCrawler.setUrls(linkedinFileHelper.getUrls());
        linkedinCrawler.setWebsite("https://www.linkedin.com/");
        linkedinCrawler.setMain(main);
        googleplusCrawler.setUrls(googlePlusFileHelper.getUrls());
        googleplusCrawler.setWebsite("https://plus.google.com/");
        googleplusCrawler.setMain(main);


        /**
         * please run one thread one time
         */
        Thread fbThread = new Thread(fbCrawler);
        fbThread.start();
//        Thread linkedinThread = new Thread(linkedinCrawler);
//        linkedinThread.start();
//        Thread googleplusThread = new Thread(googleplusCrawler);
//        googleplusThread.start();
        
//        System.out.println(System.currentTimeMillis() - time1);

    }

}