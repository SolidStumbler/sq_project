import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is the main entry point of the application
 */
public class MainApp extends Application {

    /**
     * The main method which shows the entry point
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method initializes the gui and starts the application thread
     * @param stage The Stage of the Application
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
        Parent root = (Parent)loader.load();
        GUIController controller = (GUIController)loader.getController();
        controller.setStage(stage);

        Scene scene = new Scene(root);
        stage.setTitle("Verwaltungsbericht PMS");
        stage.setScene(scene);
        stage.show();

    }
}
