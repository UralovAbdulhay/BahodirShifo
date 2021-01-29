package sample.modules;

import java.time.LocalDate;
import java.util.Date;

public class Vrach {

    private int id;
    private String name;
    private String surname;
    private String lastName;
    private Kasb kasb;
    private boolean gender;
    private LocalDate birthDate;
    private LocalDate comeDate;

    public Vrach(String name, String surname, String lastName, Kasb kasb,
                 boolean gender, LocalDate birthDate, LocalDate comeDate) {
        this(0, name, surname, lastName, kasb,
                gender, birthDate, comeDate);
    }


    public Vrach(int id, String name, String surname, String lastName, Kasb kasb,
                 boolean gender, LocalDate birthDate, LocalDate comeDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.lastName = lastName;
        this.kasb = kasb;
        this.gender = gender;
        this.birthDate = birthDate;
        this.comeDate = comeDate;
    }

    public Vrach() {
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

    public Kasb getKasb() {
        return kasb;
    }

    public void setKasb(Kasb kasb) {
        this.kasb = kasb;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getComeDate() {
        return comeDate;
    }

    public void setComeDate(LocalDate comeDate) {
        this.comeDate = comeDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return name + " " + lastName + " " + surname;
    }

}
