package com.sunbeam.mrtracker.model;


public class Product {

    int id;
    String name;
    int price;
    int discount;
    int priceWithDiscount;
    String description;
    String image;

    public Product()
    {
    }


    public Product(int id, String name, int price, int discount, String image, int priceWithDiscount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.priceWithDiscount = priceWithDiscount;
//        this.description = description;
       this.image = image;
     }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

     public int getPriceWithDiscount() {
          return priceWithDiscount;
      }

      public void setPriceWithDiscount(int priceWithDiscount) {
          this.priceWithDiscount = priceWithDiscount;
      }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
      this.image = image;
    }


}
