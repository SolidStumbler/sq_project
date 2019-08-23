package sq_project.backend;

import java.util.ArrayList;

/**
 * MedicalDataPackage contains a list of Typed MedicalData
 */
public class MedicalDataPackage extends ArrayList<MedicalData> {
    private CollectedExceptionsException error;

    private MedicalDataPackage(){}

    /**
     * @return The Exception that occured during creation
     */
    public CollectedExceptionsException getError(){
        return error;
    }

    /**
     * Did the creation of this package cause exceptions?
     * @return true if there is an exception present
     */
    public boolean hasError(){
        return error != null;
    }

    /**
     * Creates a MedicalDataPackage from a CSVPackage
     * @param csvPackage The CSVPackage contained in the newly created object
     * @return a new MedicalDataPackage
     */
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

