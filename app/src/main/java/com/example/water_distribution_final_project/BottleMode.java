package com.example.water_distribution_final_project;

public class BottleMode
{
    int Price;
    String type;
    String owner_id;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    String created_at;

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    int stockQuantity;

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    String owner_name;

    public BottleMode(int price, String type, String owner_id,String ownerName, int stockQuantity, String created_at)
    {
        this.Price = price;
        this.type = type;
        this.owner_id = owner_id;
        this.owner_name = ownerName;
        this.stockQuantity = stockQuantity;
        this.created_at = created_at;
    }
    public void setPrice(int price)
    {
        Price = price;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setOwner_id(String owner_id)
    {
        this.owner_id = owner_id;
    }

    public int getPrice()
    {
        return Price;
    }
    public String getType()
    {
        return type;
    }

    public String getOwner_id()
    {
        return owner_id;
    }
}
