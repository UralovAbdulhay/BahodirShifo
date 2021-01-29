package sample.modules;


import com.jfoenix.controls.JFXButton;

import java.text.DecimalFormat;
import java.util.Objects;

public abstract class Xizmat implements Cloneable {



    public enum XizmatType {Analise, Konsultatsiya, Tekshirish}
    protected XizmatType xizmatType;
    protected int id;
    protected String name;
    protected Double cost;
    protected Vrach vrach;

    protected JFXButton xizmatBt;


    public Xizmat(String name, Double cost, Vrach vrach) {
        this.name = name;
        this.cost = cost;
        this.vrach = vrach;
    }

    public Xizmat(Xizmat xizmat) {
        this.name = xizmat.getName();
        this.cost = xizmat.getCost();
        this.vrach = xizmat.getVrach();

        this.id = xizmat.getId();
        this.xizmatType = xizmat.getXizmatType();
        this.xizmatBt = xizmat.xizmatBt;
        this.xizmatBt.setFocusTraversable(false);

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

    public Double getCost() {
        return cost;
    }

    public String getNarxStr() {
        return decimalFormat(getCost());
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public XizmatType getXizmatType() {
        return xizmatType;
    }

    public void setXizmatType(XizmatType xizmatType) {
        this.xizmatType = xizmatType;
    }

    public Vrach getVrach() {
        return vrach;
    }

    public void setVrach(Vrach vrach) {
        this.vrach = vrach;
    }

    public JFXButton getXizmatBt() {
        return xizmatBt;
    }

    public void setXizmatBt(JFXButton xizmatBt) {
        this.xizmatBt = xizmatBt;
    }

    private String decimalFormat(double d) {
        return new DecimalFormat("#,##0.00").format(d);
    }


    @Override
    public String toString() {
        return "Xizmat{" +
                "xizmatType=" + xizmatType +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", vrach=" + vrach +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Xizmat)) return false;
        Xizmat xizmat = (Xizmat) o;
        return getId() == xizmat.getId() &&
                getXizmatType() == xizmat.getXizmatType() &&
                Objects.equals(getName(), xizmat.getName()) &&
                Objects.equals(getCost(), xizmat.getCost()) &&
                Objects.equals(getVrach(), xizmat.getVrach());
    }



    @Override
    public int hashCode() {
        return Objects.hash(getXizmatType(), getId(), getName(), getCost(), getVrach());
    }

}
