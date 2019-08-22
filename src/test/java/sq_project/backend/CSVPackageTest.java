package sq_project.backend;

import org.junit.Test;

import java.io.FileNotFoundException;

public class CSVPackageTest {
    @Test
    public void testValidCSV() throws FileNotFoundException {
        CSVPackage val = CSVUtils.parseFile(System.getProperty("user.dir") + "/src/test/test_data/valid_csv.csv");
        assert val.size() == 63;
        assert "Datum der Sprechstunde".equals(val.get(0).get(0)) == false;
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void testValuesOutOfRange(){

    }

    @Test
    public void getHeaders() {
    }

    @Test
    public void get() {
    }

    @Test
    public void size() {
    }
}
