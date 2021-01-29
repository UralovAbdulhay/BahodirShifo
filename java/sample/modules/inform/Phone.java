package sample.modules.inform;

public class Phone {
    private int id;
    private String number;

    public Phone() {
    }

    public Phone(int id, String number) {
        this.id = id;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "\nPhone{" +
                "\nid=" + id +
                ", \nnumber='" + number + '\'' +
                "\n}";
    }
}
