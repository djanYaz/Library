package com.librarymanagement.library.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="reader")

public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Reader() {
    }

    public Reader(String first_name, String last_name, String city, String phone, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.city = city;
        this.phone = phone;
        this.email = email;
    }

    @Column(name="first_name")
    private String first_name;

    @Column(name="last_name")
    private String last_name;

    @Column(name="city")
    private String city;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;

    @Column(name="borrow_number")
    private Integer borrow_number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBorrow_number() {
        return borrow_number;
    }

    public void setBorrow_number(Integer borrow_number) {
        this.borrow_number = borrow_number;
    }
}