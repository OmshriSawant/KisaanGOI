package com.example.kisaangoi;

public class GoiDetail {
    private String GroceryName, GroceryPlace, GroceryPrice;

    public GoiDetail(){
    }
    public GoiDetail(String groceryName , String groceryPlace, String groceryPrice){
        GroceryName = groceryName;
        GroceryPlace = groceryPlace;
        GroceryPrice = groceryPrice;
    }

    public String getGroceryName() {
        return GroceryName;
    }

    public void setGroceryName(String groceryName) {
        GroceryName = groceryName;
    }

    public String getGroceryPlace() {
        return GroceryPlace;
    }

    public void setGroceryPlace(String groceryPlace) {
        GroceryPlace = groceryPlace;
    }

    public String getGroceryPrice() {
        return GroceryPrice;
    }

    public void setGroceryPrice(String groceryPrice) {
        GroceryPrice = groceryPrice;
    }
}
