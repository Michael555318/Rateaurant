package com.example.basiclogins;

public class Restaurant {

    private String name, cuisine, websiteLink, address;
    private double rating;  // 0-5 with 0.5 increments
    private int price;
    public String ownerId;
    public String objectId;

    public Restaurant() { }

    public Restaurant(String name, String cuisine, String websiteLink, String address, int price, double rating, String ownerId, String objectId) {
        this.name= name;
        this.cuisine = cuisine;
        this.websiteLink = websiteLink;
        this.address = address;
        this.rating = rating;
        this.price = price;
        this.ownerId = ownerId;
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCuisine() {
        return cuisine;
    }
    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
    public String getWebsiteLink() {
        return websiteLink;
    }
    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
    public String getObjectId() {
        return objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", websiteLink='" + websiteLink + '\'' +
                ", address='" + address + '\'' +
                ", rating=" + rating +
                '}';
    }
}

