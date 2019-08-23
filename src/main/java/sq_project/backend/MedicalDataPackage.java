package sq_project.backend;

import java.util.ArrayList;

public class MedicalDataPackage extends ArrayList<MedicalData> {
    private CollectedExceptionsException error;

    private MedicalDataPackage(){}

    public CollectedExceptionsException getError(){
        return error;
    }

    public boolean hasError(){
        return error != null;
    }

    public static MedicalDataPackage createFromCSVPackage(CSVPackage csvPackage){
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
            retVal.setError(c);
        }
        return retVal;
    }

    private void setError(CollectedExceptionsException c) {
        this.error = c;
    }
}

