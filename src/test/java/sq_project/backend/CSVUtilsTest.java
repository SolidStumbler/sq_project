package sq_project.backend;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

public class CSVUtilsTest {

    @Test
    public void test_valid_data() {

        String line = "01.11.2018;Paracetamol;5;1,49";
        List<String> result = CSVUtils.parseLine(line);

        assert result != null;
        assert result.size() == 4;
        assert "01.11.2018".equalsIgnoreCase(
                result.get(0)
        );
        assert "Paracetamol".equals(result.get(1));
        assert "5".equals(result.get(2));
        assert "1,49".equals(result.get(3));
    }

    @Test(expected = FileNotFoundException.class)
    public void testNonExistingCSV() throws FileNotFoundException {
        CSVUtils.parseFile("/this/is/a/completely/bogus/file/path", ';', '\"', true);
    }

    @Test
    public void testExistingCSV() throws FileNotFoundException {
        CSVPackage val = CSVUtils.parseFile(System.getProperty("user.dir") + "/src/test/test_data/valid_csv.csv", ';', '\"', true);
        assert val.size() == 62;
    }
}