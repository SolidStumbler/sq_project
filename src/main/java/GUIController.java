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

/**
 * This Class represents all the GUI-Elements and their interactions
 */
public class GUIController implements Initializable {

    //Loads all the items from the csv and doesn't change
    private ObservableList<MedicalData> initialData;

    //Represents the stage of the application
    private Stage stage;

    //Represents the data of the TableView
    private ObservableList<MedicalData> tableViewItems;

    //Shows the total costs of the filtered items
    @FXML
    private Label lbl_gesamt;

    //Represent the MenuItem for importing files
    @FXML
    private MenuItem menuItem_import;

    //Represents the TableView
    @FXML
    private TableView<MedicalData> tableView;

    //Represents the column 'Datum'
    @FXML
    private TableColumn<MedicalData, Date> col_datum;

    //Represents the column 'Medikamentenname'
    @FXML
    private TableColumn<MedicalData, String> col_mediName;

    //Represents the column 'Anzahl Verschreibungen'
    @FXML
    private TableColumn<MedicalData, Integer> col_anzahlVerschreibung;

    //Represents the column 'Kosten pro Verschreibung'
    @FXML
    private TableColumn<MedicalData, Double> col_kostenProVerschreibung;

    //Represents the column 'Anteil in %'
    @FXML
    private TableColumn<MedicalData, String> col_prozentualerAnteil;

    //Represents the PieChart
    @FXML
    private PieChart pieChart;

    //Represents the DatePicker for the startDate
    @FXML
    private DatePicker datepickerFrom;

    //Represents the DatePicker for the endDate
    @FXML
    private DatePicker datepickerTo;

    //Represents the Button for start filtering
    @FXML
    private Button btn_refresh;

    //Represents the column 'Gesamtkosten'
    @FXML
    private TableColumn<MedicalData, String> col_gesamtKosten;

    /**
     * This method handles the click on the menuitem 'Import CSV...' and opens a FileChooser
     * @param event The called ActionEvent
     */
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

                btn_refresh.setDisable(false);

            }catch(FileNotFoundException ex){
                showErrorDialog("Fehler", "Folgender Fehler ist aufgetreten:", ex.getMessage());
            }catch (CollectedExceptionsException ex){
                showErrorDialog("Fehler", "Beim Einlesen der CSV-Datei ist folgender Fehler aufgetreten.\nDie unten stehenden Zeilen wurden nicht geladen:", ex.getMessage());
            }


        }
    }

    /**
     * This method configures the FileChooser so that only CSV-Files can be opened
     * @param fileChooser The FileChooser which should be configured
     */
    private void configureFileChooser(FileChooser fileChooser){
        fileChooser.setTitle("Öffne CSV-Datei");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );

    }

    /**
     * This method configures the TableColumns so that each column contains the corresponding data from the object
     * 'MedicalData'
     */
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

    /**
     * This method is called when the GUI is initialized
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
        btn_refresh.setDisable(true);
    }

    /**
     * This method fills the TableView with data
     */
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

    /**
     * This method fills the PieChart with data
     */
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

    /**
     * Sets the stage from the Application
     * @param stage
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * This method shows an error dialog
     * @param title The title of the dialog
     * @param headerText The header text of the dialog
     * @param contentText The content text of the dialog
     */
    private void showErrorDialog(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    /**
     * This method is called when the user clicks the Button 'Aktualisieren'
     * @param event The corresponding ActionEvent
     */
    @FXML
    void handleClickOnBtnRefresh(ActionEvent event) {

        LocalDate localDateFrom = datepickerFrom.getValue();
        LocalDate localDateTo = datepickerTo.getValue();

        //Wrong input
        if(localDateFrom == null || localDateTo == null){
            showErrorDialog("Fehler", "Folgender Fehler ist aufgetreten:", "Eingabe der Datumsfelder sind nicht gültig");
            return;
        }

        //Startdatum ist vor dem Enddatum und umgekehrt
        if(localDateFrom.isAfter(localDateTo)){
            showErrorDialog("Fehler", "Folgender Fehler ist aufgetreten:", "Das Startdatum liegt nach dem Enddatum");
            return;
        }

        tableViewItems.clear();
        tableView.getItems().clear();

        for (MedicalData md : initialData) {
            if (isDateWithinRange(md.getDate(), localDateFrom, localDateTo))
                tableViewItems.add(md);
        }

        fillTableView();
        fillPieChart();


        //System.out.println(localDateFrom + "; " + localDateTo);
    }

    /**
     * This method checks whether a date is in a specific range
     * @param dateToCheck The date which should be checked
     * @param startDate The StartDate of the range
     * @param endDate The EndDate of the range
     * @return
     */
    private boolean isDateWithinRange(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate){
        return (dateToCheck.isAfter(startDate.minusDays(1)) && (dateToCheck.isBefore(endDate.plusDays(1))));
    }



}
