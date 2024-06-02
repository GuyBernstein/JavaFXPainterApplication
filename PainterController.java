// PainterController.java
// This file contains the controller class for the Painter JavaFX application.
// It handles user interactions, manages the application state, and updates the GUI.

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class PainterController {
   // Enum representing the available shape types in the application
   // Used to determine which shape to draw based on user selection
   private enum ShapeType {
      RECTANGLE, CIRCLE, LINE
   }

   // Enum representing the available opacity types in the application
   // Used to determine the fill style (opaque or transparent) of shapes
   private enum OpacityType {
      OPAQUE, TRANSPARENT
   }

   // Instance variables that refer to GUI components in the FXML file
   // These are injected by the FXMLLoader when the FXML file is loaded
   @FXML private RadioButton blackRadioButton; // RadioButton for black color
   @FXML private RadioButton redRadioButton;    // RadioButton for red color
   @FXML private RadioButton greenRadioButton;  // RadioButton for green color
   @FXML private RadioButton blueRadioButton;   // RadioButton for blue color

   @FXML private RadioButton rectangleRadioButton; // RadioButton for rectangle shape
   @FXML private RadioButton circleRadioButton;    // RadioButton for circle shape
   @FXML private RadioButton lineRadioButton;      // RadioButton for line shape
   @FXML private RadioButton opaqueRadioButton;    // RadioButton for opaque fill
   @FXML private RadioButton transparentRadioButton; // RadioButton for transparent fill

   @FXML private Pane drawingAreaPane; // Pane representing the drawing area
   @FXML private ToggleGroup colorToggleGroup; // ToggleGroup for color RadioButtons
   @FXML private ToggleGroup shapeToggleGroup; // ToggleGroup for shape RadioButtons
   @FXML private ToggleGroup opacityToggleGroup; // ToggleGroup for opacity RadioButtons

   // Instance variables for managing the state of the Painter application
   private ShapeType currentShapeType = ShapeType.RECTANGLE; // Current selected shape type (default: RECTANGLE)
   private Paint brushColor = Color.BLACK; // Current selected brush color (default: BLACK)
   private OpacityType currentOpacityType = OpacityType.OPAQUE; // Current selected opacity type (default: OPAQUE)
   private double startX, startY; // Coordinates of the starting point when drawing a shape

   // Initialize method called by the FXMLLoader after the FXML file is loaded
   // Sets up the initial state of the RadioButtons by associating user data with them
   public void initialize() {
      rectangleRadioButton.setUserData(ShapeType.RECTANGLE); // Associate RECTANGLE enum with rectangleRadioButton
      circleRadioButton.setUserData(ShapeType.CIRCLE); // Associate CIRCLE enum with circleRadioButton
      lineRadioButton.setUserData(ShapeType.LINE); // Associate LINE enum with lineRadioButton
      blackRadioButton.setUserData(Color.BLACK); // Associate BLACK color with blackRadioButton
      redRadioButton.setUserData(Color.RED); // Associate RED color with redRadioButton
      greenRadioButton.setUserData(Color.GREEN); // Associate GREEN color with greenRadioButton
      blueRadioButton.setUserData(Color.BLUE); // Associate BLUE color with blueRadioButton
      opaqueRadioButton.setUserData(OpacityType.OPAQUE); // Associate OPAQUE enum with opaqueRadioButton
      transparentRadioButton.setUserData(OpacityType.TRANSPARENT); // Associate TRANSPARENT enum with transparentRadioButton
   }

   // Event handler for the onMousePressed event on the drawing area
   // Captures the starting coordinates of the shape being drawn
   @FXML
   private void drawingAreaMousePressed(MouseEvent e) {
      startX = e.getX(); // Get the x-coordinate of the mouse press event
      startY = e.getY(); // Get the y-coordinate of the mouse press event
   }

   // Event handler for the onMouseReleased event on the drawing area
   // Creates the appropriate shape based on the user's selection and adds it to the drawing area
   @FXML
   private void drawingAreaMouseReleased(MouseEvent e) {
      Shape shape = null; // Variable to store the created shape

      // Determine the shape to create based on the currentShapeType
      switch (currentShapeType) {
         case RECTANGLE:
            // Calculate the dimensions and position of the rectangle
            double width = Math.abs(e.getX() - startX);
            double height = Math.abs(e.getY() - startY);
            double x = Math.min(startX, e.getX());
            double y = Math.min(startY, e.getY());
            shape = new Rectangle(x, y, width, height); // Create a new Rectangle shape
            break;
         case CIRCLE:
            // Calculate the radius of the circle based on the start and end points
            double radius = Math.sqrt(Math.pow(e.getX() - startX, 2) + Math.pow(e.getY() - startY, 2));
            shape = new Circle(startX, startY, radius); // Create a new Circle shape
            break;
         case LINE:
            shape = new Line(startX, startY, e.getX(), e.getY()); // Create a new Line shape
            break;
      }

      // If a shape was created, set its fill and stroke properties and add it to the drawing area
      if (shape != null) {
         // Set the fill based on the currentOpacityType (OPAQUE: brushColor, TRANSPARENT: Color.TRANSPARENT)
         shape.setFill(currentOpacityType == OpacityType.OPAQUE ? brushColor : Color.TRANSPARENT);
         shape.setStroke(brushColor); // Set the stroke color to the selected brushColor
         drawingAreaPane.getChildren().add(shape); // Add the shape to the drawing area
      }
   }

   // Event handler for the ActionEvent fired by the color RadioButtons
   // Updates the brushColor based on the selected RadioButton
   @FXML
   private void colorRadioButtonSelected(ActionEvent e) {
      // Get the user data associated with the selected RadioButton (which is a Color)
      brushColor = (Color) colorToggleGroup.getSelectedToggle().getUserData();
   }

   // Event handler for the ActionEvent fired by the opacity RadioButtons
   // Updates the currentOpacityType based on the selected RadioButton
   @FXML
   private void opacityRadioButtonSelected(ActionEvent e) {
      // Get the user data associated with the selected RadioButton (which is an OpacityType)
      currentOpacityType = (OpacityType) opacityToggleGroup.getSelectedToggle().getUserData();
   }

   // Event handler for the ActionEvent fired by the shape RadioButtons
   // Updates the currentShapeType based on the selected RadioButton
   @FXML
   private void shapeRadioButtonSelected(ActionEvent e) {
      // Get the user data associated with the selected RadioButton (which is a ShapeType)
      currentShapeType = (ShapeType) shapeToggleGroup.getSelectedToggle().getUserData();
   }

   // Event handler for the ActionEvent fired by the Undo Button
   // Removes the last added shape from the drawing area
   @FXML
   private void undoButtonPressed(ActionEvent event) {
      int count = drawingAreaPane.getChildren().size(); // Get the number of shapes in the drawing area

      // If there are any shapes, remove the last one added
      if (count > 0) {
         drawingAreaPane.getChildren().remove(count - 1);
      }
   }

   // Event handler for the ActionEvent fired by the Clear Button
   // Removes all shapes from the drawing area
   @FXML
   private void clearButtonPressed(ActionEvent event) {
      drawingAreaPane.getChildren().clear(); // Clear all shapes from the drawing area
   }
}