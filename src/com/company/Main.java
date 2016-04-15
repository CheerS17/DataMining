package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Junqi Ma
 */
public class Main {

    public static void fbCrawler(String url, BufferedWriter bw) {
        String pageCode = null;
        String tempString = null;

        String currentAddress = null;
        String education = null;
        String name = null;

        String fileString = null;

        String geoPosition = null;
        String latitude = null;
        String longitude = null;

        int indexBegin = 0;
        int indexStop = 0;
        try {
//            long time2 = System.currentTimeMillis();
            Document doc = Jsoup.connect(url).get();
//            System.out.println(System.currentTimeMillis() - time2);
            pageCode = doc.html().replaceAll("\\s", "");
//            System.out.println(pageCode);


            //find username
            System.out.println("\n" + findUsername(url));
            bw.write(findUsername(url) + "\t");

            //find name
            tempString = pageCode;
            name = tempString.substring(tempString.indexOf("<titleid=\"pageTitle\">") + "<titleid=\"pageTitle\">".length(), tempString.indexOf("</title>"));
            if (name.contains("|Facebook")) {
                name = name.substring(0, name.indexOf("|Facebook"));
                System.out.println(name);
                bw.write(name + "\t");
            } else {
                System.out.println(name);
                bw.write(name + "\t");
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
                currentAddress = currentAddress.substring(indexBegin);
                System.out.println(currentAddress);
                geoPosition = getGeoGet(currentAddress);
                latitude = geoPosition;
                latitude = latitude.substring(latitude.indexOf(":") + 1, latitude.indexOf(","));
                longitude = geoPosition.substring(geoPosition.indexOf(",") + 1);
                longitude = longitude.substring(longitude.indexOf(":") + 1);
                bw.write(latitude + "\t" + longitude + "\t");

                //the location is in the annotation
            } else if (tempString.contains("<divclass=\"fsmfwnfcg\">Currentcity<a")){
                indexStop = tempString.indexOf("<divclass=\"fsmfwnfcg\">Currentcity<a") - 11;
                indexBegin = indexStop - 50;
                currentAddress = tempString.substring(indexBegin, indexStop);
                for (int i = currentAddress.length() - 1; i > 0; i--) {
                    if (currentAddress.charAt(i) == '>') {
                        indexBegin = i + 1;
                        break;
                    }
                }

                //print current city
                currentAddress = currentAddress.substring(indexBegin);
                System.out.println(currentAddress);
                geoPosition = getGeoGet(currentAddress);
                latitude = geoPosition;
                latitude = latitude.substring(latitude.indexOf(":") + 1, latitude.indexOf(","));
                longitude = geoPosition.substring(geoPosition.indexOf(",") + 1);
                longitude = longitude.substring(longitude.indexOf(":") + 1);
                bw.write(latitude + "\t" + longitude + "\t");
            }else {
                System.out.println("NoCurrentCity");
                bw.write("NoCurrentCity" + "\t" + "NoCurrentCity" + "\t");
            }

            //find education
            tempString = pageCode;
            if (tempString.contains("Education</span>")) {
                tempString = tempString.substring(tempString.indexOf("Education</span>") + "Education</span>".length());
                tempString = tempString.substring(tempString.indexOf("<ahref") + "<ahref".length());
                indexStop = tempString.indexOf("</a>");
                indexBegin = indexStop - 50;
                //if the buffer is 50 may be too large
                if (indexBegin > 0) {
                    education = tempString.substring(indexBegin, indexStop);
                } else {
                    //if too large, set it smaller
                    indexBegin = indexStop - 30;
                    education = tempString.substring(indexBegin, indexStop);
                }
                for (int i = education.length() - 1; i > 0; i--) {
                    if (education.charAt(i) == '>') {
                        indexBegin = i + 1;
                        break;
                    }
                }
                //print education
                education = education.substring(indexBegin);
                System.out.println(education);
                bw.write(education);
            } else {
                System.out.println("NoEducation");
                bw.write("NoEducation");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void linkedinCrawler(String url, BufferedWriter bw) {
        String pageCode = null;

        String currentAddress = null;
        String education = null;
        String name = null;

        String tempString;

        String geoPosition = null;
        String latitude = null;
        String longitude = null;

        try {
//            long time2 = System.currentTimeMillis();
            Document doc = Jsoup.connect(url).get();
//            System.out.println(System.currentTimeMillis() - time2);
            pageCode = doc.html().replaceAll("\\s", "");

            //find username
            System.out.println("\n" + findUsername(url));
            bw.write(findUsername(url) + "\t");

            //find name
            tempString = pageCode;
            name = tempString.substring(tempString.indexOf("<title>") + "<title>".length(), tempString.indexOf("|LinkedIn") - 1);
            System.out.println(name);
            bw.write(name + "\t");

            tempString = pageCode;
            //find current city
            if (tempString.contains("<spanclass=\"locality\">")) {
                tempString = tempString.substring(tempString.indexOf("<spanclass=\"locality\">"));
                currentAddress = tempString.substring("<spanclass=\"locality\">".length(), tempString.indexOf("</span>"));
                //print current city
                System.out.println(currentAddress);
                geoPosition = getGeoGet(currentAddress);
                latitude = geoPosition;
                latitude = latitude.substring(latitude.indexOf(":") + 1, latitude.indexOf(","));
                longitude = geoPosition.substring(geoPosition.indexOf(",") + 1);
                longitude = longitude.substring(longitude.indexOf(":") + 1);
                bw.write(latitude + "\t" + longitude + "\t");
            } else {
                System.out.println("NoCurrentCity");
                bw.write("NoCurrentCity" + "\t" + "NoCurrentCity" + "\t");
            }

            //find education
            tempString = pageCode;
            if (tempString.contains("<th>Education</th><td><ol><li><a")) {
                tempString = tempString.substring(tempString.indexOf("<th>Education</th><td><ol><li><a") + "<th>Education</th><td><ol><li><a".length());
                education = tempString.substring(tempString.indexOf(">") + 1, tempString.indexOf("</a>"));
                //print education
                System.out.println(education);
                bw.write(education);
            } else {
                System.out.println("NoEducation");
                bw.write("NoEducation");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void googlePlusCrawler(String url, BufferedWriter bw) {
        String pageCode = null;

        String currentAddress = null;
        String education = null;
        String name = null;

        String geoPosition = null;
        String latitude = null;
        String longitude = null;

        String tempString;
        try {
//            long time2 = System.currentTimeMillis();
            Document doc = Jsoup.connect(url).get();
//            System.out.println(System.currentTimeMillis() - time2);
            pageCode = doc.html().replaceAll("\\s", "");

            //find username
            System.out.println("\n" + findUsername(url));
            bw.write(findUsername(url) + "\t");

            //find name
            tempString = pageCode;
            name = tempString.substring(tempString.indexOf("<titleitemprop=\"name\">") + "<titleitemprop=\"name\">".length());
            name = name.substring(0, name.indexOf("-About"));
            System.out.println(name);
            bw.write(name + "\t");

            tempString = pageCode;
            //find current city
            if (tempString.contains("<divclass=\"adry4\">")) {
                tempString = tempString.substring(tempString.indexOf("<divclass=\"adry4\">") + "<divclass=\"adry4\">".length());
                currentAddress = tempString.substring(0, tempString.indexOf("</div>"));
                //print current city
                System.out.println(currentAddress);
                geoPosition = getGeoGet(currentAddress);
                latitude = geoPosition;
                latitude = latitude.substring(latitude.indexOf(":") + 1, latitude.indexOf(","));
                longitude = geoPosition.substring(geoPosition.indexOf(",") + 1);
                longitude = longitude.substring(longitude.indexOf(":") + 1);
                bw.write(latitude + "\t" + longitude + "\t");
            } else {
                System.out.println("NoCurrentCity");
                bw.write("NoCurrentCity" + "\t" + "NoCurrentCity" + "\t");
            }

            //find education
            tempString = pageCode;
            if (tempString.contains("<divclass=\"F9a\">Education</div>")) {
                tempString = tempString.substring(tempString.indexOf("<divclass=\"F9a\">Education</div>") + "<divclass=\"F9a\">Education</div>".length());
                education = tempString.substring(tempString.indexOf("<divclass=\"PLa\">") + "<divclass=\"PLa\">".length());
                education = education.substring(0, education.indexOf("</div>"));
                //print education
                System.out.println(education);
                bw.write(education);
            } else {
                System.out.println("NoEducation");
                bw.write("NoEducation");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String findUsername(String url) {
        String username = null;
        if (url.contains("https://www.facebook.com/")) {
            if (url.contains("id=")) {
                username = url.substring(url.indexOf("id=") + 3, url.indexOf("&"));
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

    public static String getGeoGet(String city) {
        String tempURL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + city + "&key=AIzaSyDGkuRyh5SXgaU-JY3WobKrCx_DOVVAUIU";
        HttpURLConnection httpURLConnection = null;
        String json;
        try {
            URL url = new URL(tempURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            json = response.toString().replaceAll("\\s", "");
            json = json.substring(json.indexOf("{\"lat\"") + 1);
            json = json.substring(0, json.indexOf("}"));
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
        //1-100
//        String pathFb = "Data/facebook.txt";
//        String pathLinkedIn = "Data/linkedin.txt";
//        String pathGooglePlus = "Data/googleplus.txt";

        //101-200
//        String pathFb = "Data/facebook101.txt";
//        String pathLinkedIn = "Data/linkedin101.txt";
//        String pathGooglePlus = "Data/googleplus101.txt";

        //201-300
        String pathFb = "Data/facebook201.txt";
        String pathLinkedIn = "Data/linkedin201.txt";
        String pathGooglePlus = "Data/googleplus201.txt";

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

//        Thread fbThread = new Thread(fbCrawler);
//        fbThread.start();
//        Thread linkedinThread = new Thread(linkedinCrawler);
//        linkedinThread.start();
//        Thread googleplusThread = new Thread(googleplusCrawler);
//        googleplusThread.start();

//        System.out.println(System.currentTimeMillis() - time1);

//        FileAddSequence();
//        FileAddLinkedInSequence();

    }

    public static void FileAddLinkedInSequence() {
        String str = null;
        String str2 = null;
        int num = 1;
        try {

//            FileWriter fw1 = new FileWriter("out/linkedin1.txt");
//            FileWriter fw1 = new FileWriter("out/googleplus1.txt");
            FileWriter fw1 = new FileWriter("out/facebook1.txt");
            BufferedWriter bw1 = new BufferedWriter(fw1);
//            FileReader fr1 = new FileReader("Data/linkedin.txt");
//            FileReader fr1 = new FileReader("Data/googleplus.txt");
            FileReader fr1 = new FileReader("Data/facebook.txt");
            BufferedReader br1 = new BufferedReader(fr1);
//            FileReader fr2 = new FileReader("out/linkedin.txt");
//            FileReader fr2 = new FileReader("out/googleplus.txt");
            FileReader fr2 = new FileReader("out/facebook.txt");
            BufferedReader br2 = new BufferedReader(fr2);
            while ((str = br1.readLine()) != null) {
                if (str.contains("http")) {
                    str2 = br2.readLine();
                    str2 = num + "\t" + str2;
                    bw1.write(str2);
                    bw1.newLine();
                }
                num++;
            }
            bw1.close();
            fw1.close();
            br2.close();
            fr2.close();
            br1.close();
            fr1.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void FileAddSequence() {
        String str = null;
        int num = 1;
        try {

            FileWriter fw1 = new FileWriter("out/linkedin1.txt");
            BufferedWriter bw1 = new BufferedWriter(fw1);
            FileReader fr1 = new FileReader("out/linkedin.txt");
            BufferedReader br1 = new BufferedReader(fr1);
            while ((str = br1.readLine()) != null) {
                str = num + "\t" + str;
                bw1.write(str);
                bw1.newLine();
                num++;
            }
            bw1.close();
            fw1.close();
            br1.close();
            fr1.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}