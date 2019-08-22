package sq_project.backend;

import java.util.Date;

public class MedicalData {

    private Date date;
    private String medicine;
    private int number;
    private double cost;

    public MedicalData(int number, double cost) {
        this.number = number;
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
}
