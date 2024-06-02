/*
Guy Bernstein
id: 206558439
Main application class that loads and displays the Painter's GUI.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Painter extends Application {   
   @Override
   public void start(Stage stage) throws Exception {
      Parent root = 
         FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Painter.fxml")));
      
      Scene scene = new Scene(root);
      stage.setTitle("Painter"); // displayed in window's title bar
      stage.setScene(scene);
      stage.show();
   }

   public static void main(String[] args) {
      launch(args);
   }
}
