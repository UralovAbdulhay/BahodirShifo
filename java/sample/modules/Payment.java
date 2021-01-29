package sample.modules;

import java.text.DecimalFormat;

public class Payment {
    private int id;
    private double paymentAmount;
    private boolean paymentType;                    //paymentType: Naqt - true;  Terminal - false
    private boolean checked;                       //
    private double sale;
    private String paymentComment;

    public Payment(int id, double paymentAmount, boolean paymentType,
                   boolean cheeked, String paymentComment) {
        this.id = id;
        this.paymentAmount = paymentAmount;
        this.paymentType = paymentType;
        this.checked = cheeked;
        this.paymentComment = paymentComment;
    }

    public Payment(double paymentAmount, boolean paymentType,
                   boolean cheeked) {
        this.paymentAmount = paymentAmount;
        this.paymentType = paymentType;
        this.checked = cheeked;
        this.paymentComment = "";
    }

    public Payment() {
    }

    public double getPaymentAmount() {
        this.paymentAmount -= this.paymentAmount * getSale();
        return this.paymentAmount;
    }

    public String getPaymentAmountStr() {
        return new DecimalFormat("#,##0.00").format(getPaymentAmount());
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public boolean isPaymentType() {
        return paymentType;
    }

    public void setPaymentType(boolean paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getPaymentComment() {
        return paymentComment;
    }

    public void setPaymentComment(String paymentComment) {
        this.paymentComment = paymentComment;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "\nPayment{" +
                "\npaymentAmount=" + paymentAmount +
                ", \npaymentType=" + paymentType +
                ", \nchecked=" + checked +
                ", \nsale=" + sale +
                ", \npaymentComment='" + paymentComment + '\'' +
                "\n}";
    }
}
