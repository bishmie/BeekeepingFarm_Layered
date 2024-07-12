package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/beekeeperManage.fxml"))));
        stage.setTitle("Login Form");
        // meka poddak balannako. seen eka trans action eka nam hari. SQL err ekak naththam
        stage.centerOnScreen();
        stage.show();
    }
}