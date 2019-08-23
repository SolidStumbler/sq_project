package sq_project.backend;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class MedicalData {

    DecimalFormat df = new DecimalFormat("#0.00");

    private LocalDate date;
    private String medicine;
    private int number;
    private double cost;
    private double share;
    private String shareText;

    private MedicalData(LocalDate date, String medicine, int number, double cost){
        this.date = date;
        this.medicine = medicine;
        this.number = number;
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
            LocalDate date = LocalDate.parse(dateStr, formatter);
            int number = Integer.parseInt(numberStr);
            NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
            Number costNumber = format.parse(costStr);
            double cost = costNumber.doubleValue();
            return new MedicalData(date, medicineStr, number, cost);
        }catch(Exception e){
            throw new InvalidLineException(csvLine, e);
        }
    }

    public double getShare() {
        return share;
    }

    public void setShare(double share) {
        this.share = share;
        setShareText(df.format(share));
    }

    public String getShareText() {
        return shareText;
    }

    public void setShareText(String shareText) {
        this.shareText = shareText;

    }
}

