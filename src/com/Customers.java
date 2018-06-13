package com;

/**
 * Created by SOTRUDNIK on 14.06.2017.
 */
public class Customers {

    private Integer id_cus;
    private String name, surname, patronymic, phone;


    public Customers() {
    }

    public Customers(Integer id_cus, String name, String surname, String patronymic, String phone) {

        this.id_cus = id_cus;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phone = phone;
    }


    public Integer getId_cus() {
        return id_cus;
    }

    public void setId_cus(int id_cus) {
        this.id_cus = id_cus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "Customers{" +
                "id_cus=" + id_cus +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public Customers clone() throws CloneNotSupportedException {
        return (Customers) super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.id_cus == null) {
            return false;
        }

        if (obj instanceof Customers && obj.getClass().equals(getClass())) {
            return this.id_cus.equals(((Customers) obj).id_cus);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (id_cus == null ? 0 : id_cus.hashCode());
        return hash;
    }

}