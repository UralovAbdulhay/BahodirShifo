package sample.modules.inform;

public class Passport {
    private int id;
    private String seria;
    private int raqam;

    public Passport(int id, String seria, int raqam) {
        this.id = id;
        this.seria = seria;
        this.raqam = raqam;
    }

    public Passport() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeria() {
        return seria;
    }

    public void setSeria(String seria) {
        this.seria = seria;
    }

    public int getRaqam() {
        return raqam;
    }

    public void setRaqam(int raqam) {
        this.raqam = raqam;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", seria='" + seria + '\'' +
                ", raqam=" + raqam +
                '}';
    }
}
