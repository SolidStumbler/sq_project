import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sq_project.backend.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    private ObservableList<MedicalData> initialData;

    private Stage stage;

    private ObservableList<MedicalData> tableViewItems;

    @FXML
    private Label lbl_gesamt;

    @FXML
    private MenuItem menuItem_import;

    @FXML
    private TableView<MedicalData> tableView;

    @FXML
    private TableColumn<MedicalData, Date> col_datum;

    @FXML
    private TableColumn<MedicalData, String> col_mediName;

    @FXML
    private TableColumn<MedicalData, Integer> col_anzahlVerschreibung;

    @FXML
    private TableColumn<MedicalData, Double> col_kostenProVerschreibung;

    @FXML
    private TableColumn<MedicalData, String> col_prozentualerAnteil;

    @FXML
    private PieChart pieChart;

    @FXML
    private DatePicker datepickerFrom;

    @FXML
    private DatePicker datepickerTo;

    @FXML
    private Button btn_refresh;

    @FXML
    private TableColumn<MedicalData, String> col_gesamtKosten;

    @FXML
    void handleClickMenuItemImportCSV(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);

        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            try{
                CSVPackage csvPackage = CSVUtils.parseFile(file.getPath(), ';', '"', true);

                MedicalDataPackage medicalDataPackage = MedicalDataPackage.createFromCSVPackage(csvPackage);

                ArrayList<MedicalData> list_medicalData = medicalDataPackage;

                tableViewItems = FXCollections.observableArrayList(list_medicalData);

                initialData = FXCollections.observableArrayList(list_medicalData);

                fillTableView();

                fillPieChart();

                if(medicalDataPackage.hasError()){
                    throw medicalDataPackage.getError();
                }

            }catch(FileNotFoundException ex){
                showErrorDialog("Fehler", "Folgender Fehler ist aufgetreten:", ex.getMessage());
            }catch (CollectedExceptionsException ex){
                showErrorDialog("Fehler", "Beim Einlesen der CSV-Datei ist folgender Fehler aufgetreten.\nDie unten stehenden Zeilen wurden nicht geladen:", ex.getMessage());
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
                new PropertyValueFactory<MedicalData, String>("shareText")
        );

        col_gesamtKosten.setCellValueFactory(
                new PropertyValueFactory<MedicalData, String>("costAllText")
        );

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
    }

    private void fillTableView() {

        DecimalFormat df = new DecimalFormat("#0.00");

        double costAll = 0.0;

        for(MedicalData md : tableViewItems){
            costAll += md.getCostAll();
        }

        for(MedicalData md : tableViewItems){
            md.setShare(md.getCostAll() / costAll * 100.0);
        }

        tableView.setItems(tableViewItems);

        lbl_gesamt.setText(df.format(costAll) + "€");
    }



    private void fillPieChart(){

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        HashMap<String, Double> hashMap = new HashMap<>();

        for(MedicalData md : tableViewItems){
            if(hashMap.containsKey(md.getMedicine())){
                hashMap.put(md.getMedicine(), md.getCostAll() + hashMap.get(md.getMedicine()));
            }else{
                hashMap.put(md.getMedicine(), md.getCostAll());
            }


        }

        for(String s : hashMap.keySet()){
            pieChartData.add(new PieChart.Data(s, hashMap.get(s)));
        }

        pieChart.setData(pieChartData);



    }

    public void setStage(Stage stage){
        this.stage = stage;
    }


    private void showErrorDialog(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    @FXML
    void handleClickOnBtnRefresh(ActionEvent event) {

        //Validation Check

        LocalDate localDateFrom = datepickerFrom.getValue();
        LocalDate localDateTo = datepickerTo.getValue();

        tableViewItems.clear();
        tableView.getItems().clear();

        for(MedicalData md : initialData){
            if(isDateWithinRange(md.getDate(), localDateFrom, localDateTo))
            tableViewItems.add(md);
        }

        fillTableView();
        fillPieChart();

        //System.out.println(localDateFrom + "; " + localDateTo);
    }

    private boolean isDateWithinRange(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate){
        return (dateToCheck.isAfter(startDate.minusDays(1)) && (dateToCheck.isBefore(endDate.plusDays(1))));
    }

}
