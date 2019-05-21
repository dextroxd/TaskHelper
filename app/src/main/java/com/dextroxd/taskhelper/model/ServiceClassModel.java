package com.dextroxd.taskhelper.model;

public class ServiceClassModel
{
    public String name;
    public String address;
    public String phoneno;
    public String servicetype;
    public int i;

    public ServiceClassModel() {

    }

    public ServiceClassModel(String name, String address, String phoneno, String servicetype, int i) {
        this.name = name;
        this.address = address;
        this.phoneno = phoneno;
        this.servicetype = servicetype;
        this.i = i;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public String getServicetype() {
        return servicetype;
    }

    public int getI() {
        return i;
    }
}
