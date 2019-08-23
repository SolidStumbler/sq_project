package sq_project.backend;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Typed medical data from a csv line
 */
public class MedicalData {

    private DecimalFormat df = new DecimalFormat("#0.00");

    private LocalDate date;
    private String medicine;
    private int number;
    private double cost;
    private double share;
    private String shareText;
    private double costAll;
    private String costAllText;

    private MedicalData(LocalDate date, String medicine, int number, double cost){
        this.date = date;
        this.medicine = medicine;
        this.number = number;
        this.cost = cost;
        this.costAll = number * cost;
        this.costAllText = df.format(costAll);
    }

    /**
     * Get date
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set date
     * @param date date to be set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Get medicine
     * @return medicine
     */
    public String getMedicine() {
        return medicine;
    }

    /**
     * Set medicine
     * @param medicine medicine to be set
     */
    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    /**
     * Get number
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set number
     * @param number number to be set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Get cost
     * @return cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Set cost
     * @param cost cost to be set
     */
    public void setCost(double cost) {
        this.cost = cost;
        setCostAll(number * cost);
    }

    /**
     * Create a new MedicalData-object from line out of a csv-file
     * @param csvLine list of strings of a single csv-line
     * @return a new MedicalData-object
     * @throws InvalidLineException if the line could not be parsed
     */
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
            ParsePosition pos = new ParsePosition(0);
            NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
            Number costNumber = format.parse(costStr, pos);
            if(pos.getIndex() != costStr.length()){
                throw new ParseException(costStr, pos.getIndex());
            }
            double cost = costNumber.doubleValue();
            return new MedicalData(date, medicineStr, number, cost);
        }catch(Exception e){
            throw new InvalidLineException(csvLine, e);
        }
    }

    /**
     * Get share
     * @return share
     */
    public double getShare() {
        return share;
    }

    /**
     * Set share
     * @param share share to be set
     */
    public void setShare(double share) {
        this.share = share;
        setShareText(df.format(share));
    }

    /**
     * Get share text
     * @return shareText
     */
    public String getShareText() {
        return shareText;
    }

    /**
     * Set shareText
     * @param shareText share text to be set
     */
    public void setShareText(String shareText) {
        this.shareText = shareText;
    }

    /**
     * Get compound cost
     * @return costAll
     */
    public double getCostAll() {
        return costAll;
    }

    /**
     * Set compound cost
     * @param costAll compound cost to be set
     */
    public void setCostAll(double costAll) {
        this.costAll = costAll;
    }

    /**
     * Get text describing compound cost
     * @return costAllText
     */
    public String getCostAllText() {
        return costAllText;
    }

    /**
     * Set text describing compound cost
     * @param costAllText costAllText to be set
     */
    public void setCostAllText(String costAllText) {
        this.costAllText = costAllText;
    }
}

