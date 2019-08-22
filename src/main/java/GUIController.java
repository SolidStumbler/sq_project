import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sq_project.backend.CSVPackage;
import sq_project.backend.CSVUtils;
import sq_project.backend.MedicalData;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    private Stage stage;

    @FXML
    private MenuItem menuItem_import;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<MedicalData, Date> col_datum;

    @FXML
    private TableColumn<MedicalData, String> col_mediName;

    @FXML
    private TableColumn<MedicalData, Integer> col_anzahlVerschreibung;

    @FXML
    private TableColumn<MedicalData, Double> col_kostenProVerschreibung;

    @FXML
    private TableColumn<Double, Double> col_prozentualerAnteil;

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

    private void configureTableColumns(){
        col_datum.setCellValueFactory(
                new PropertyValueFactory<MedicalData, Date>("date")
        );

        col_mediName.setCellValueFactory(
                new PropertyValueFactory<MedicalData,String>("medicine")
        );

        col_anzahlVerschreibung.setCellValueFactory(
                new PropertyValueFactory<MedicalData,Integer>("number")
        );

        col_kostenProVerschreibung.setCellValueFactory(
                new PropertyValueFactory<MedicalData, Double>("cost")
        );

        col_prozentualerAnteil.setCellValueFactory(
                new PropertyValueFactory<Double, Double>("prozent")
        );

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setStage(Stage stage){
        this.stage = stage;
    }


}
