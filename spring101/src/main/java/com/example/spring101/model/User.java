package com.example.spring101.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class User {
   // @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private String email;
    private String password;
    private double balance;
    private double laonAmount;



    public User(String id, String name, String email, String password, double balance,double laonAmount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = 0;
        this.password = password;
        this.laonAmount = 0;
    }

//    public void add(int amount){
//        balance += amount;
//    }
//    public void sub(int amount){
//        this.balance -= amount;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getLaonAmount() {
        return laonAmount;
    }

    public void setLaonAmount(double laonAmount) {
        this.laonAmount = laonAmount;
    }
}
