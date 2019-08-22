package sq_project.backend;

import java.util.ArrayList;

public class MedicalDataPackage extends ArrayList<MedicalData> {
    private MedicalDataPackage(){}
    public static MedicalDataPackage createFromCSVPackage(CSVPackage csvPackage){
        MedicalDataPackage retVal = new MedicalDataPackage();
        for(int i = 0; i < csvPackage.size(); i++){
            try {
                retVal.add(MedicalData.parseCSVLine(csvPackage.get(i)));
            } catch (InvalidLineException e) {
                e.printStackTrace();
            }
        }
        return retVal;
    }
}

