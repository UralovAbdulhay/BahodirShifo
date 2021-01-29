package sample.modules;

import sample.modules.address.Address;
import sample.modules.inform.Passport;
import sample.modules.address.Tuman;
import sample.modules.address.Viloyat;
import sample.modules.inform.Phone;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Bemor {
    private int id;
    private String name;
    private String surname;
    private boolean gender;
    private Phone phone1;
    private Phone phone2;
    private LocalDate birthDate;
    private Passport passport;
    private Address address;
    private LocalDate comeDate;

    public Bemor(int id, String name, String surname,
                 boolean gender, Phone phone1, LocalDate birthDate,
                 Address address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.phone1 = phone1;
        this.birthDate = birthDate;
        this.address = address;
    }

    public Bemor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Phone getPhone1() {
        return phone1;
    }

    public void setPhone1(Phone phone1) {
        this.phone1 = phone1;
    }

    public Phone getPhone2() {
        return phone2;
    }

    public void setPhone2(Phone phone2) {
        this.phone2 = phone2;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getBirthDateStr() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return birthDate.format(dateFormatter);

    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getComeDate() {
        return comeDate;
    }

    public void setComeDate(LocalDate comeDate) {
        this.comeDate = comeDate;
    }

    @Override
    public String toString() {
        return "Bemor{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
