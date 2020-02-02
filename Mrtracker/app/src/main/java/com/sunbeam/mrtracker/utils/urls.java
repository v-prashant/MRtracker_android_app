package com.sunbeam.mrtracker.utils;



public class urls {



    static String url = "http://192.168.0.103";

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
    public static String signUp(){
        return  url + ":4000/login/dashboard/user";
    }

    public static String AddtoCart() {
        return url + ":4000/MRlogin/cart";
    }

    public static String CartList(){
        return url + ":4000/MRlogin/addcart";
    }

    public static String home1(){
        return  url + ":4000/login/dashboard/product/get";
    }

    public static String EditInCart() {
        return url + ":4000/MRlogin/cartEdit";
    }

    public static String cartDelete() {
        return url + ":4000/MRlogin/cartDelete";
    }

    public static String confirmToOrder() {
        return url + ":4000/MRlogin/cart/confirmorder";
    }

    public static String listOfOrders() {
        return url + ":4000/MRlogin/orders";
    }


}

