package com.zhd.pojo;

public class Supplier {
    private Short id;

    private String name;

    private String address;

    public Short getId() {
        return id;
    }

    public Supplier setId(Short id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Supplier setName(String name) {
        this.name = name == null ? null : name.trim();
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Supplier setAddress(String address) {
        this.address = address == null ? null : address.trim();
        return this;
    }

    public static String[] getSimpleKeys(){
        return new String[]{"id", "name"};
    }

    public static String[] getKeys(){
        return new String[]{"id", "name", "address"};
    }

    public static String[] getNames(){
        return new String[]{"编号","供应商名","供应商地址"};
    }

    public static String[] getSimpleNames(){
        return new String[]{"编号","供应商名"};
    }
    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}