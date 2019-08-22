import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sq_project.backend.CSVPackage;
import sq_project.backend.CSVUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    private Stage stage;

    @FXML
    private MenuItem menuItem_import;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> col_datum;

    @FXML
    private TableColumn<?, ?> col_mediName;

    @FXML
    private TableColumn<?, ?> col_anzahlVerschreibung;

    @FXML
    private TableColumn<?, ?> col_kostenProVerschreibung;

    @FXML
    private TableColumn<?, ?> col_prozentualerAnteil;

    @FXML
    private PieChart pieChart;

    @FXML
    private DatePicker datepickerFrom;

    @FXML
    private DatePicker datepickerTo;

    @FXML
    private Button btn_refresh;

    @FXML
    void handleClickMenuItemImportCSV(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);

        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            try{
                CSVPackage csvPackage = CSVUtils.parseFile(file.getPath(), ';', '"', true);
                System.out.println(csvPackage.getValues(0));
            }catch(FileNotFoundException ex){
                System.err.println(ex.getMessage());
            }

        }
    }

    private void configureFileChooser(FileChooser fileChooser){
        fileChooser.setTitle("Öffne CSV-Datei");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setStage(Stage stage){
        this.stage = stage;
    }


}
