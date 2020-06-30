package com.sem.e_health;

public class Client {
    private   String name;
    private String lastName;
    private String phone;
    private String age;
    private String imageUri;

    public Client(String name, String lastName, String phone, String age) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
    }
    public Client(String name, String lastName, String phone, String age, String imageUri) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.imageUri = imageUri;
    }

    public Client() {
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
    private String namaLastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getnamaLastName() {
        namaLastName =  lastName + " " + name ;
        return namaLastName;
    }
}
