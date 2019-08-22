package sq_project.backend;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

public class MedicalData {

    private LocalDate date;
    private String medicine;
    private int number;
    private double cost;

    private MedicalData(LocalDate date, String medicine, int number, double cost){
        this.date = date;
        this.medicine = medicine;
        this. number = number;
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public static MedicalData parseCSVLine(List<String> csvLine) throws InvalidLineException {
        if(csvLine.isEmpty()){
            throw new InvalidLineException("");
        }
        if(csvLine.size() != 4){
            throw new InvalidLineException(csvLine);
        }
        String dateStr = csvLine.get(0);
        String medicineStr = csvLine.get(1);
        String numberStr = csvLine.get(2);
        String costStr = csvLine.get(3);

        try{
            LocalDate date = LocalDate.parse(dateStr);
            int number = Integer.parseInt(numberStr);
            double cost = Double.parseDouble(costStr);
            return new MedicalData(date, medicineStr, number, cost);
        }catch(Exception e){
            throw new InvalidLineException(csvLine, e);
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}

