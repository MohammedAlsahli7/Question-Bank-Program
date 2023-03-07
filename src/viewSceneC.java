import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class viewSceneC {

	// Main components
	protected ObservableList<Question> list;
	protected ComboBox<Question> viewCombo;
	private Text editTitle = new Text();
	private Scene viewScene;

	// Additional
	private ComboBox<Character> editRightAns = new ComboBox<Character>();
	private VBox editVbox;
	private String theme, def;

	// Text fields
	private TextField editQuestion = new TextField();
	private TextField editChoice1 = new TextField();
	private TextField editChoice2 = new TextField();
	private TextField editChoice3 = new TextField();
	private TextField editChoice4 = new TextField();

	// Labels
	private Label editLabelQuestion = new Label("The question:", editQuestion);
	private Label editLabelChoice1 = new Label("Choice A:", editChoice1);
	private Label editLabelChoice2 = new Label("Choice B:", editChoice2);
	private Label editLabelChoice3 = new Label("Choice C:", editChoice3);
	private Label editLabelChoice4 = new Label("Choice D:", editChoice4);
	private Label editLabelRight = new Label("The right answer:", editRightAns);
	private Label error = new Label();

	// Buttons
	private Button back = new Button("Back");
	private Button save = new Button("Save");
	protected Button edit = new Button("Edit");
	protected Button delete = new Button("Delete");
	private Button yes = new Button("Yes");
	private Button no = new Button("No");
	private Button themeB;

	public viewSceneC(Stage primaryStage, Scene scene, Button view, ObservableList<Question> list,
			ComboBox<Question> viewCombo, Button themeB, Question temp) {

		this.list = list;
		this.viewCombo = viewCombo;
		this.themeB = themeB;

		HBox buttons = new HBox(30);
		buttons.setAlignment(Pos.CENTER);
		buttons.setPadding(new Insets(25));
		buttons.getChildren().addAll(back, save, edit, delete);

		// Editing and viewing
		editLabelQuestion.setContentDisplay(ContentDisplay.RIGHT);
		editQuestion.setPrefSize(300, 25);
		editLabelChoice1.setContentDisplay(ContentDisplay.RIGHT);
		editChoice1.setPrefSize(250, 25);
		editLabelChoice2.setContentDisplay(ContentDisplay.RIGHT);
		editChoice2.setPrefSize(250, 25);
		editLabelChoice3.setContentDisplay(ContentDisplay.RIGHT);
		editChoice3.setPrefSize(250, 25);
		editLabelChoice4.setContentDisplay(ContentDisplay.RIGHT);
		editChoice4.setPrefSize(250, 25);
		editRightAns.getItems().addAll('A', 'B', 'C', 'D');
		editLabelRight.setContentDisplay(ContentDisplay.RIGHT);
		editTitle.setFont(new Font(30));
		editTitle.setTextAlignment(TextAlignment.CENTER);
		editTitle.setWrappingWidth(400);
		VBox editQVBox = new VBox(25);
		editQVBox.setPadding(new Insets(25));
		editQVBox.getChildren().addAll(editTitle, viewCombo, editLabelQuestion, editLabelChoice1, editLabelChoice2,
				editLabelChoice3, editLabelChoice4, editLabelRight);
		editVbox = new VBox(error, editQVBox, buttons);
		viewScene = new Scene(editVbox, 450, 530);

		// Styling
		error.setPrefSize(450, 25);
		error.setAlignment(Pos.CENTER);
		error.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent");
		Button defaul = new Button();
		def = defaul.getStyle();
		Timeline anim = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			error.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent");
		}));
		setStyle();

		// Delete
		Stage sure = new Stage();
		Text sureD = new Text("Are you sure you want to delete this question?");
		HBox deleteHbox = new HBox();
		VBox deleteVbox = new VBox();
		deleteHbox.setSpacing(25);
		deleteVbox.setSpacing(25);
		deleteHbox.setAlignment(Pos.CENTER);
		deleteVbox.setAlignment(Pos.CENTER);
		deleteHbox.getChildren().addAll(yes, no);
		deleteVbox.getChildren().addAll(sureD, deleteHbox);
		Scene deleteScene = new Scene(deleteVbox, 275, 100);

		// locking to view mode
		lock(true);

		// Handlers

		viewCombo.setOnAction(e -> {
			// if the value of the combo box changed it automatically lock to view mode
			// and if there is no questions it lock all nodes except back button
			if (viewCombo.getValue() == temp) {
				edit.setDisable(true);
				delete.setDisable(true);
			} else {
				edit.setDisable(false);
				delete.setDisable(false);
			}
			lock(true);

		});

		back.setOnAction(e -> {
			// return to the main scene or when editing return to view scene
			if (back.getText().equals("Back"))
				primaryStage.setScene(scene);
			else {
				lock(true);
				edit.setDisable(false);
			}});

		edit.setOnAction(e -> {
			lock(false);
			edit.setDisable(true);
		});

		save.setOnAction(e -> {
			// saves the changes in the question
			error.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent");
			if (!(editQuestion.getText().equals("") || editChoice1.getText().equals("")
					|| editChoice2.getText().equals("") || editChoice3.getText().equals("")
					|| editChoice4.getText().equals(""))) {
				Question q = viewCombo.getValue();
				q.setQuestion(editQuestion.getText());
				q.setChoice1(editChoice1.getText());
				q.setChoice2(editChoice2.getText());
				q.setChoice3(editChoice3.getText());
				q.setChoice4(editChoice4.getText());
				q.setRightChoice(editRightAns.getValue());
				error.setStyle("-fx-background-color: lime; -fx-text-fill: white");
				error.setText("Done!");
				anim.play();
				lock(true);
				edit.setDisable(false);
			} else {
				error.setStyle("-fx-background-color: red; -fx-text-fill: white");
				error.setText("Fill all the blanks please!");
				anim.play();

			}
		});

		delete.setOnAction(e -> {
			// deletes a question
			error.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent");
			if (themeB.getText().equals("Light theme")) {
				deleteVbox.setStyle("-fx-background-color: #333333");
				sureD.setFill(Color.WHITE);
			} else {
				deleteVbox.setStyle("-fx-background-color: transparent");
				sureD.setFill(Color.BLACK);
			}
			lock(true);
			viewCombo.setDisable(true);
			back.setDisable(true);
			delete.setDisable(true);
			edit.setDisable(true);
			sure.setScene(deleteScene);
			sure.setTitle("Are you sure?");
			sure.setResizable(false);
			if (!sure.isShowing())
				sure.show();
			else
				sure.setScene(deleteScene);
		});

		// ------- Delete handlers
		// making sure of the decision
		yes.setOnAction(e -> {
			// delete the question
			if (!(list.size() >= 1 && list.contains(temp)))
				list.add(0, temp);
			list.remove(viewCombo.getValue());
			viewCombo.setItems(list);
			error.setStyle("-fx-background-color: lime; -fx-text-fill: white");
			error.setText("Done!");
			anim.play();
			sure.close();
			viewCombo.setDisable(false);
			back.setDisable(false);
			delete.setDisable(false);
			edit.setDisable(false);
			view.fire();
		});

		no.setOnAction(e -> {
			// do not delete the question
			sure.close();
			viewCombo.setDisable(false);
			back.setDisable(false);
			delete.setDisable(false);
			edit.setDisable(false);
		});
		
		sure.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				event.consume();
				viewCombo.setDisable(false);
				back.setDisable(false);
				delete.setDisable(false);
				edit.setDisable(false);
				sure.close();
			}
		});

	}

	// Locking/unlocking the text fields and buttons
	public void lock(Boolean x) {
		viewCombo.setItems(list);
		if (x) {
			editTitle.setText("Viewing questions");
			back.setText("Back");
		} else {
			editTitle.setText("Editing questions");
			back.setText("Cancel");
		}
		save.setDisable(x);
		editQuestion.setDisable(x);
		editChoice1.setDisable(x);
		editChoice2.setDisable(x);
		editChoice3.setDisable(x);
		editChoice4.setDisable(x);
		editRightAns.setDisable(x);

		if (list.size() == 0 || viewCombo.getValue() == null) {
			editQuestion.setText("");
			editChoice1.setText("");
			editChoice2.setText("");
			editChoice3.setText("");
			editChoice4.setText("");
			editRightAns.setValue('A');
		} else {
			editQuestion.setText(viewCombo.getValue().getQuestion());
			editChoice1.setText(viewCombo.getValue().getChoice1());
			editChoice2.setText(viewCombo.getValue().getChoice2());
			editChoice3.setText(viewCombo.getValue().getChoice3());
			editChoice4.setText(viewCombo.getValue().getChoice4());
			editRightAns.setValue(viewCombo.getValue().getRightChoice());
		}
	}

	// theme setter (dark/light)
	public void setStyle() {
		if (themeB.getText().equals("Light theme")) {
			this.theme = "-fx-background-color: grey; -fx-text-fill: black";
			editLabelQuestion.setTextFill(Color.WHITE);
			editLabelChoice1.setTextFill(Color.WHITE);
			editLabelChoice2.setTextFill(Color.WHITE);
			editLabelChoice3.setTextFill(Color.WHITE);
			editLabelChoice4.setTextFill(Color.WHITE);
			editLabelRight.setTextFill(Color.WHITE);
			editTitle.setFill(Color.WHITE);
			editVbox.setStyle("-fx-background-color: #333333");
		} else {
			this.theme = def;
			editLabelQuestion.setTextFill(Color.BLACK);
			editLabelChoice1.setTextFill(Color.BLACK);
			editLabelChoice2.setTextFill(Color.BLACK);
			editLabelChoice3.setTextFill(Color.BLACK);
			editLabelChoice4.setTextFill(Color.BLACK);
			editLabelRight.setTextFill(Color.BLACK);
			editTitle.setFill(Color.BLACK);
			editVbox.setStyle("-fx-background-color: transparent");
		}
		edit.setStyle(theme);
		delete.setStyle(theme);
		save.setStyle(theme);
		back.setStyle(theme);
		editQuestion.setStyle(theme);
		editChoice1.setStyle(theme);
		editChoice2.setStyle(theme);
		editChoice3.setStyle(theme);
		editChoice4.setStyle(theme);
		editRightAns.setStyle(theme);
		viewCombo.setStyle(theme);
		yes.setStyle(theme);
		no.setStyle(theme);
	}

	// Scene getter
	public Scene getScene() {
		return viewScene;
	}
}
