package sq_project.backend;

import java.util.ArrayList;

public class MedicalDataPackage extends ArrayList<MedicalData> {
    private MedicalDataPackage(){}

    public static MedicalDataPackage createFromCSVPackage(CSVPackage csvPackage) throws CollectedExceptionsException {
        MedicalDataPackage retVal = new MedicalDataPackage();
        CollectedExceptionsException c = new CollectedExceptionsException();
        for(int i = 0; i < csvPackage.size(); i++){
            try {
                retVal.add(MedicalData.parseCSVLine(csvPackage.get(i)));
            } catch (InvalidLineException e) {
                c.add(e, i + 1);
            }

        }
        if(!c.isEmpty()){
            throw c;
        }
        return retVal;
    }
}

