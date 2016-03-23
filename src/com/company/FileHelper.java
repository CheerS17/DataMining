package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by majunqi on 3/22/16.
 */
public  class FileHelper {
    private String path;

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public ArrayList<String> getUrls() {
        ArrayList<String> urls = new ArrayList<String>();
        String s;
        try {
            BufferedReader bf = new BufferedReader(new FileReader(path));
            while ((s = bf.readLine()) != null) {
                if (s == "") {
                    urls.add("no fb links");
                } else {
                    urls.add(s);
                }
            }
            bf.close();
            return urls;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
