package com.sunbeam.mrtracker.model;

public class OrderListClass {

    String name;
    String image;
    int id;
    int Quantity;
    int totalDiscount;
    int totalAmount;
    String drname;
    String drphoneno;
    String PaymentMode;
    String OrderDate;
    String deliveryDate;
    String addressOFdr;

    OrderListClass(){

    }

    public OrderListClass(String name, String image, int id, int quantity, int totalDiscount, int totalAmount, String drname, String drphoneno, String paymentMode, String orderDate, String deliveryDate, String addressOFdr) {
        this.name = name;
        this.image = image;
        this.id = id;
        Quantity = quantity;
        this.totalDiscount = totalDiscount;
        this.totalAmount = totalAmount;
        this.drname = drname;
        this.drphoneno = drphoneno;
        PaymentMode = paymentMode;
        OrderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.addressOFdr = addressOFdr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(int totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDrname() {
        return drname;
    }

    public void setDrname(String drname) {
        this.drname = drname;
    }

    public String getDrphoneno() {
        return drphoneno;
    }

    public void setDrphoneno(String drphoneno) {
        this.drphoneno = drphoneno;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getAddressOFdr() {
        return addressOFdr;
    }

    public void setAddressOFdr(String addressOFdr) {
        this.addressOFdr = addressOFdr;
    }



}
