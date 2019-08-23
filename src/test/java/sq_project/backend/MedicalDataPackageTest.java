package sq_project.backend;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class MedicalDataPackageTest {
    @Test
    public void CreateSuccessfullyTest() throws FileNotFoundException {
        CSVPackage val = CSVUtils.parseFile(System.getProperty("user.dir") + "/src/test/test_data/valid_csv.csv");
        MedicalDataPackage data = MedicalDataPackage.createFromCSVPackage(val);
        assert(data.size() == 62);
    }

    @Test(expected = CollectedExceptionsException.class)
    public void CreateFailedTest() throws FileNotFoundException, CollectedExceptionsException {
        CSVPackage val = CSVUtils.parseFile(System.getProperty("user.dir") + "/src/test/test_data/invalid_csv.csv");
        MedicalDataPackage data = MedicalDataPackage.createFromCSVPackage(val);
        assert(data.size() == 59);
        if (data.hasError()) {
            System.out.println(data.getError().getMessage());
            throw data.getError();
        }
    }
}