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
import sq_project.backend.CSVPackage;
import sq_project.backend.CSVUtils;
import sq_project.backend.MedicalData;
import sq_project.backend.MedicalDataPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    private Stage stage;

    private ObservableList<MedicalData> tableViewItems;

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
                try {
                    ArrayList<MedicalData> list_medicalData = MedicalDataPackage.createFromCSVPackage(csvPackage);
                    tableViewItems = FXCollections.observableArrayList(list_medicalData);
                }catch(Exception e) {}


                fillTableView();

                fillPieChart();

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
        configureTableColumns();
        //fillPieChart();
        //fillTableView();
    }

    private void fillTableView() {
        tableView.setItems(tableViewItems);
    }


    private void fillPieChart(){

        /*ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));
        pieChart.setData(pieChartData);*/

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        HashMap<String, Double> hashMap = new HashMap<>();

        for(MedicalData md : tableViewItems){
            if(hashMap.containsKey(md.getMedicine())){
                hashMap.put(md.getMedicine(), md.getCost() + hashMap.get(md.getMedicine()));
            }else{
                hashMap.put(md.getMedicine(), md.getCost());
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


}
