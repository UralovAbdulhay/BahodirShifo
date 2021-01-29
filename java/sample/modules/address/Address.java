package sample.modules.address;

public class Address {
    private int id;
    private String uy;
    private Viloyat viloyat;
    private Tuman tuman;

    public Address() {
    }

    public Address(int id, String uy, Viloyat viloyat, Tuman tuman) {
        this.id = id;
        this.uy = uy;
        this.viloyat = viloyat;
        this.tuman = tuman;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUy() {
        return uy;
    }

    public void setUy(String uy) {
        this.uy = uy;
    }

    public Viloyat getViloyat() {
        return viloyat;
    }

    public void setViloyat(Viloyat viloyat) {
        this.viloyat = viloyat;
    }

    public Tuman getTuman() {
        return tuman;
    }

    public void setTuman(Tuman tuman) {
        this.tuman = tuman;
    }

    @Override
    public String toString() {
        return "Address{" +
                "\nid=" + id +
                ", \nuy='" + uy + '\'' +
                ", \nviloyat=" + viloyat +
                ", \ntuman=" + tuman +
                "\n}";
    }
}
