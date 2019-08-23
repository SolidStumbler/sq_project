package sq_project.backend;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class MedicalDataPackageTest {
    @Test
    public void CreateSuccessfullyTest() throws FileNotFoundException, CollectedExceptionsException {
        CSVPackage val = CSVUtils.parseFile(System.getProperty("user.dir") + "/src/test/test_data/valid_csv.csv");
        MedicalDataPackage data = MedicalDataPackage.createFromCSVPackage(val);
        assert(data.size() == 62);
    }
}