package sq_project.backend;

import org.junit.Test;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MedicalDataTest {

    @Test
    public void parseGoodCSVLine() throws InvalidLineException {
        List<String> csvLine = Arrays.asList("01.11.2018", "Paracetamol", "5","1,49");
        try{
            MedicalData data = MedicalData.parseCSVLine(csvLine);
            assert (data.getCost() - 1.49d) < 0.1d;
            assert data.getDate().getMonth() == Month.NOVEMBER;
            assert data.getNumber() == 5;
        }catch (InvalidLineException e){
            System.out.println(e.getLine());
            e.printStackTrace();
            assert e == null;
        }

    }

    @Test(expected = InvalidLineException.class)
    public void parseBadCSVLine() throws InvalidLineException {
        List<String> csvLine = Arrays.asList("Datum der Sprechstunde",
                "Medikamentname",
                "Anzahl der Verschreibungen",
                "Kosten pro Verschreibung");
        MedicalData data = MedicalData.parseCSVLine(csvLine);
    }


}