package com.example.water_distribution_final_project;

public class Orders
{
    String type,Id;
    int UnitPrice,Quantity;
    String VendorId,VendorName,VendorAddress,VendorPhone;
    String CustomerId,CustomerName,CustomerAddress,CustomerPhone;



    public Orders(String type, int unitPrice, int quantity, String vendorId, String vendorName, String vendorAddress, String vendorPhone, String customerId, String customerName, String customerAddress, String customerPhone, String ID)
    {
        this.type = type;
        UnitPrice = unitPrice;
        Quantity = quantity;
        VendorId = vendorId;
        VendorName = vendorName;
        VendorAddress = vendorAddress;
        VendorPhone = vendorPhone;
        CustomerId = customerId;
        CustomerName = customerName;
        CustomerAddress = customerAddress;
        CustomerPhone = customerPhone;
        this.Id=ID;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getUnitPrice()
    {
        return UnitPrice;
    }

    public void setUnitPrice(int unitPrice)
    {
        UnitPrice = unitPrice;
    }

    public int getQuantity()
    {
        return Quantity;
    }

    public void setQuantity(int quantity)
    {
        Quantity = quantity;
    }

    public String getVendorId()
    {
        return VendorId;
    }

    public void setVendorId(String vendorId) {
        VendorId = vendorId;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getVendorAddress() {
        return VendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        VendorAddress = vendorAddress;
    }

    public String getVendorPhone() {
        return VendorPhone;
    }

    public void setVendorPhone(String vendorPhone) {
        VendorPhone = vendorPhone;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return CustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        CustomerPhone = customerPhone;
    }





}
