package com.sunbeam.mrtracker.model;

public class Mycart {

    int id;
    int Quantity;
    int totalAmount;
    int totalDiscount;
    int MRid;
    int ProductID;
    String image;
    String name;


    Mycart(){

    }

    public Mycart(int id, int quantity, int totalAmount, int totalDiscount, int MRid, int productID, String image, String name) {
        this.id = id;
        Quantity = quantity;
        this.totalAmount = totalAmount;
        this.totalDiscount = totalDiscount;
        this.MRid = MRid;
        ProductID = productID;
        this.image = image;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(int totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public int getMRid() {
        return MRid;
    }

    public void setMRid(int MRid) {
        this.MRid = MRid;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
