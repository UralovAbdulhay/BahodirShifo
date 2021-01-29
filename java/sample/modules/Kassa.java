package sample.modules;

import java.util.ArrayList;

public class Kassa {
    private ArrayList<Bemor> bemorList;
    private ArrayList<Payment> paymentList;
    private double summKassa;
    private double sumKirim;
    private double sumChiqim;

//    public Kassa(ArrayList<Bemor> bemorList) {
//        this.bemorList = bemorList;
//        this.paymentList = new ArrayList<>();
//        this.summKassa = 0;
//        this.sumKirim = 0;
//        this.sumChiqim = 0;
//
//        for (Bemor bemor:bemorList) {
//            paymentList.add(bemor.getPayment());
//        }
//        setSummKassa();
//    }


    private void setSummKassa(){
        for (Payment payment:
             paymentList) {
            if (payment.getPaymentAmount()>0){
                sumKirim += payment.getPaymentAmount();
            }else if (payment.getPaymentAmount()<0){
                sumChiqim += payment.getPaymentAmount();
            }
        }
        summKassa = sumKirim - sumChiqim;
    }


    public ArrayList<Bemor> getBemorList() {
        return bemorList;
    }

    public void setBemorList(ArrayList<Bemor> bemorList) {
        this.bemorList = bemorList;
    }

    public ArrayList<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(ArrayList<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public double getSummKassa() {
        return summKassa;
    }

    public void setSummKassa(double summKassa) {
        this.summKassa = summKassa;
    }

    public double getSumKirim() {
        return sumKirim;
    }

    public void setSumKirim(double sumKirim) {
        this.sumKirim = sumKirim;
    }

    public double getSumChiqim() {
        return sumChiqim;
    }

    public void setSumChiqim(double sumChiqim) {
        this.sumChiqim = sumChiqim;
    }
}





