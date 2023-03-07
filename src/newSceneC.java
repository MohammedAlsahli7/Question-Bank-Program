import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class newSceneC {

	// Main components
	private Text newTitle = new Text("Adding new questions");
	private Scene newScene;

	// Additional
	private ComboBox<Character> newRightAns = new ComboBox<Character>();
	private VBox newQVBox = new VBox(25);
	private HBox buttons = new HBox(30);
	private VBox newVbox;
	private String theme;
	private String def;

	// Text fields
	private TextField newQuestion = new TextField();
	private TextField newChoice1 = new TextField();
	private TextField newChoice2 = new TextField();
	private TextField newChoice3 = new TextField();
	private TextField newChoice4 = new TextField();

	// Labels
	private Label error = new Label();
	private Label newLabelQuestion = new Label("The question:", newQuestion);
	private Label newLabelChoice1 = new Label("Choice A:", newChoice1);
	private Label newLabelChoice2 = new Label("Choice B:", newChoice2);
	private Label newLabelChoice3 = new Label("Choice C:", newChoice3);
	private Label newLabelChoice4 = new Label("Choice D:", newChoice4);
	private Label newLabelRight = new Label("The right answer:", newRightAns);

	// Buttons
	private Button back = new Button("Back");
	private Button add = new Button("Add");
	private Button themeB;

	public newSceneC(Stage primaryStage, Scene scene, Button view, ObservableList<Question> list, Button themeB,
			Question temp) {

		this.themeB = themeB;

		Button defaul = new Button();
		def = defaul.getStyle();

		buttons.setAlignment(Pos.CENTER);
		buttons.setPadding(new Insets(25));
		buttons.getChildren().addAll(back, add);
		error.setPrefSize(450, 25);
		error.setAlignment(Pos.CENTER);
		error.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent");

		newVbox = new VBox(error, newQVBox, buttons);

		newLabelQuestion.setContentDisplay(ContentDisplay.RIGHT);
		newQuestion.setPrefSize(300, 25);
		newLabelChoice1.setContentDisplay(ContentDisplay.RIGHT);
		newChoice1.setPrefSize(250, 25);
		newLabelChoice2.setContentDisplay(ContentDisplay.RIGHT);
		newChoice2.setPrefSize(250, 25);
		newLabelChoice3.setContentDisplay(ContentDisplay.RIGHT);
		newChoice3.setPrefSize(250, 25);
		newLabelChoice4.setContentDisplay(ContentDisplay.RIGHT);
		newChoice4.setPrefSize(250, 25);
		newRightAns.getItems().addAll('A', 'B', 'C', 'D');
		newLabelRight.setContentDisplay(ContentDisplay.RIGHT);
		newRightAns.setValue('A');
		newTitle.setFont(new Font(30));
		newTitle.setTextAlignment(TextAlignment.CENTER);
		newTitle.setWrappingWidth(400);

		newQVBox.setPadding(new Insets(25));
		newQVBox.getChildren().addAll(newTitle, newLabelQuestion, newLabelChoice1, newLabelChoice2, newLabelChoice3,
				newLabelChoice4, newLabelRight);

		setStyle();
		Timeline anim = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
			error.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent");
		}));

		newScene = new Scene(newVbox, 450, 475);

		// Handlers
		back.setOnAction(e -> {
			// return to the main scene
			if (back.getText().equals("Back")) {
				newQuestion.setText("");
				newChoice1.setText("");
				newChoice2.setText("");
				newChoice3.setText("");
				newChoice4.setText("");
				newRightAns.setValue('A');
				primaryStage.setScene(scene);
			}});

		add.setOnAction(e -> {
			// adds a new question
			error.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent");
			if (!(newQuestion.getText().equals("") || newChoice1.getText().equals("") || newChoice2.getText().equals("")
					|| newChoice3.getText().equals("") || newChoice4.getText().equals(""))) {

				list.add(new Question(newQuestion.getText(), newChoice1.getText(), newChoice2.getText(),
						newChoice3.getText(), newChoice4.getText(), newRightAns.getValue()));
				error.setStyle("-fx-background-color: lime; -fx-text-fill: white");
				error.setText("Done!");
				anim.play();
				newQuestion.clear();
				newChoice1.clear();
				newChoice2.clear();
				newChoice3.clear();
				newChoice4.clear();
				newRightAns.setValue('A');
			} else {
				error.setStyle("-fx-background-color: red; -fx-text-fill: white");
				error.setText("Fill all the blanks please!");
				anim.play();
			}
		});
	}

	// theme setter (dark/light)
	public void setStyle() {
		if (themeB.getText().equals("Light theme")) {
			this.theme = "-fx-background-color: grey; -fx-text-fill: black";
			newLabelQuestion.setTextFill(Color.WHITE);
			newLabelChoice1.setTextFill(Color.WHITE);
			newLabelChoice2.setTextFill(Color.WHITE);
			newLabelChoice3.setTextFill(Color.WHITE);
			newLabelChoice4.setTextFill(Color.WHITE);
			newLabelRight.setTextFill(Color.WHITE);
			newTitle.setFill(Color.WHITE);
			newVbox.setStyle("-fx-background-color: #333333");
		} else {
			this.theme = def;
			newLabelQuestion.setTextFill(Color.BLACK);
			newLabelChoice1.setTextFill(Color.BLACK);
			newLabelChoice2.setTextFill(Color.BLACK);
			newLabelChoice3.setTextFill(Color.BLACK);
			newLabelChoice4.setTextFill(Color.BLACK);
			newLabelRight.setTextFill(Color.BLACK);
			newTitle.setFill(Color.BLACK);
			newVbox.setStyle("-fx-background-color: transparent");
		}
		add.setStyle(theme);
		back.setStyle(theme);
		newQuestion.setStyle(theme);
		newChoice1.setStyle(theme);
		newChoice2.setStyle(theme);
		newChoice3.setStyle(theme);
		newChoice4.setStyle(theme);
		newRightAns.setStyle(theme);

	}

	// Scene getter
	public Scene getScene() {
		return newScene;
	}
}
