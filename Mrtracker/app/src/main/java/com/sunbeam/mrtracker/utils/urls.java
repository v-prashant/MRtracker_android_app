package com.sunbeam.mrtracker.utils;



public class urls {


    static String url = "http://192.168.43.127";


    public static String images(){
        return url + ":4000/";
    }
    public static String allopathic(){
        return url + ":4000/MRlogin/allopathic";
    }
    public static String homoeopathy(){
        return url + ":4000/MRlogin/homoeopathy";
    }
    public static String ayurvedic(){
        return url + ":4000/MRlogin/ayurvedic";
    }
    public static String home(){
        return  url + ":4000/login/dashboard/product";
    }
    public static String search(){
        return  url + ":4000/MRlogin/search";
    }


    public static String login(){
        return  url + ":4000/MRlogin";
    }

}

