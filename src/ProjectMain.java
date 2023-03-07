import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ProjectMain extends Application {
	ObservableList<Question> list = FXCollections.observableArrayList();
	ComboBox<Question> viewCombo = new ComboBox<Question>();
	Button theme = new Button("Dark theme");
	Question temp = new Question("Please add a question first.", "", "", "", "", 'A');

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		ReadObjectFromFile();
		viewCombo.getItems().addAll(list);
		viewCombo.setPrefSize(500, 450);

		HBox options = new HBox();
		Button newQ = new Button("New Question");
		Button view = new Button("View Question");
		String def = newQ.getStyle();
		options.getChildren().addAll(newQ, view);
		options.setPadding(new Insets(15));
		options.setSpacing(25);
		options.setAlignment(Pos.CENTER);
		Text title = new Text("Questions Bank");
		title.setFont(new Font(30));
		title.setTextAlignment(TextAlignment.CENTER);
		title.setWrappingWidth(280);

		title.setOnMouseEntered(n ->{
			title.setFill(Color.RED);
		});
		title.setOnMouseExited(n ->{
			
			title.setFill(Color.BLACK);
		});
		
		// Main stage
		BorderPane border = new BorderPane();
		VBox optionsVbox = new VBox(title, options);
		optionsVbox.setAlignment(Pos.BASELINE_CENTER);
		border.setPadding(new Insets(10));
		border.setCenter(optionsVbox);
		border.setRight(theme);
		Scene scene = new Scene(border, 450, 125);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Questions Bank");
		primaryStage.setResizable(false);
		primaryStage.show();

		// New scene
		newSceneC newSceneO = new newSceneC(primaryStage, scene, view, list, theme, temp);
		Scene newScene = newSceneO.getScene();

		// Edit scene
		viewSceneC viewSceneO = new viewSceneC(primaryStage, scene, view, list, viewCombo, theme, temp);
		Scene viewScene = viewSceneO.getScene();

		// Main handlers
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				event.consume();
				WriteObjectToFile(list);
				primaryStage.close();
			}
		});
		newQ.setOnAction(e -> {
			// go to new scene
			primaryStage.setScene(newScene);
		});
		
		view.setOnAction(e -> {
			// go to view scene
			viewCombo.setItems(list);
			viewCombo.setValue(list.get(list.size() - 1));
			
			if (list.size() > 1 && list.contains(temp)) {
				viewSceneO.edit.setDisable(false);
				viewSceneO.delete.setDisable(false);
				list.remove(temp);
			}
			if (viewCombo.getValue() == temp) {
				viewSceneO.edit.setDisable(true);
				viewSceneO.delete.setDisable(true);
			} else if (viewCombo.getValue() == null) {
				viewSceneO.edit.setDisable(true);
				viewSceneO.delete.setDisable(true);
			} else {
				viewSceneO.edit.setDisable(false);
				viewSceneO.delete.setDisable(false);
			}
			viewCombo.setItems(list);
			viewCombo.setValue(list.get(list.size() - 1));
			viewSceneO.lock(true);
			primaryStage.setScene(viewScene);
		});
		
		theme.setOnAction(e -> {
			// theme changer
			if (theme.getText().equals("Dark theme")) {
				border.setStyle("-fx-background-color: #333333");
				theme.setStyle("-fx-background-color: grey; -fx-text-fill: black");
				newQ.setStyle("-fx-background-color: grey; -fx-text-fill: black");
				view.setStyle("-fx-background-color: grey; -fx-text-fill: black");
				title.setFill(Color.WHITE);

				theme.setText("Light theme");
				newSceneO.setStyle();
				viewSceneO.setStyle();

			} else {
				border.setStyle("-fx-background-color: white");
				theme.setStyle(def);
				newQ.setStyle(def);
				view.setStyle(def);
				title.setFill(Color.BLACK);
				theme.setText("Dark theme");
				newSceneO.setStyle();
				viewSceneO.setStyle();
			}
		});
	}

	// getter for the theme (dark/light)
	public String getStyle() {
		return theme.getStyle();
	}

	// reading out of file method
	@SuppressWarnings("unchecked")
	public void ReadObjectFromFile() {
		try {
			FileInputStream fileIn = new FileInputStream("ProjectFile.dat");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			ArrayList<Question> serlist = (ArrayList<Question>) objectIn.readObject();
			for (int i = 0; i < serlist.size(); i++)
				list.add(serlist.get(i));
			list.add(0, temp);
			objectIn.close();
		} catch (Exception e) {
			list.add(0, temp);
		}
	}

	// writing into file method
	public void WriteObjectToFile(ObservableList<Question> Obj) {
		try {
			FileOutputStream fileOut = new FileOutputStream("ProjectFile.dat");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			ArrayList<Question> serObj = new ArrayList<Question>();
			for (int i = 0; i < Obj.size(); i++)
				if (Obj.get(i) != temp)
					serObj.add(Obj.get(i));
			
			objectOut.writeObject(serObj);
			objectOut.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}