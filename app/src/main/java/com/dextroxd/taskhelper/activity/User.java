package com.dextroxd.taskhelper.activity;
import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class User {
    public String name_User;
    public String phone_User;
    public  String address_USer;
    public String email_User;
    public int services;

//    public boolean washing_Machine;
//    public boolean refrigerator;
//    public boolean air_Conditioning;
//    public boolean oven_Micro;
//    public boolean house_Sanitiation;
//    public boolean computer_Laptop;

    public User() {
    }

//    public User(String name_User, String phone_User, String address_USer, String email_User, boolean washing_Machine, boolean refrigerator, boolean air_Conditioning, boolean oven_Micro, boolean house_Sanitiation, boolean computer_Laptop) {
//        this.name_User = name_User;
//        this.phone_User = phone_User;
//        this.address_USer = address_USer;
//        this.email_User = email_User;
//        this.washing_Machine = washing_Machine;
//        this.refrigerator = refrigerator;
//        this.air_Conditioning = air_Conditioning;
//        this.oven_Micro = oven_Micro;
//        this.house_Sanitiation = house_Sanitiation;
//        this.computer_Laptop = computer_Laptop;
//    }

    public User(String name_User, String phone_User, String address_USer, String email_User, int services) {
        this.name_User = name_User;
        this.phone_User = phone_User;
        this.address_USer = address_USer;
        this.email_User = email_User;
        this.services = services;
    }


//    public String getName_User() {
//        return name_User;
//    }
//
//    public String getPhone_User() {
//        return phone_User;
//    }
//
//    public String getAddress_USer() {
//        return address_USer;
//    }
//
//    public String getEmail_User() {
//        return email_User;
//    }
//
//    public boolean isWashing_Machine() {
//        return washing_Machine;
//    }
//
//    public boolean isRefrigerator() {
//        return refrigerator;
//    }
//
//    public boolean isAir_Conditioning() {
//        return air_Conditioning;
//    }
//
//    public boolean isOven_Micro() {
//        return oven_Micro;
//    }
//
//    public boolean isHouse_Sanitiation() {
//        return house_Sanitiation;
//    }
//
//    public boolean isComputer_Laptop() {
//        return computer_Laptop;
//    }
}
