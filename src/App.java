import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class App extends Application {
    Scene scnWelcome, scnAbout, scnLogin, scnSignUp, scnDepLogin;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Property Management System");
        primaryStage.getIcons().add(new Image("file:house.png"));
        scnWelcome = makeWelcomeScene(primaryStage);
        scnAbout = makeAboutScene(primaryStage);
        scnLogin = makeLoginScene(primaryStage);
        scnSignUp = makeSignUpScene(primaryStage);
        scnDepLogin = makeDepLoginScene(primaryStage);
        primaryStage.setScene(scnWelcome);
        primaryStage.show();
    }

    public Scene makeWelcomeScene(Stage primaryStage) throws FileNotFoundException {
        Button btnAbout = new Button("About");
        Button btnLogin = new Button("Login");
        Button btnQuit = new Button("Quit");

        BorderPane bp = makeNewBorderPaneWithBtnBar("Welcome", btnAbout, btnLogin, btnQuit);

        InputStream inputHouse = new FileInputStream("house.png");
        Image imgHouse = new Image(inputHouse);
        ImageView imageView = new ImageView();
        imageView.setImage(imgHouse);
        imageView.setPreserveRatio(true);

        btnAbout.setOnAction(e -> primaryStage.setScene(scnAbout));
        btnLogin.setOnAction(e -> primaryStage.setScene(scnLogin));
        btnQuit.setOnAction(e -> System.exit(1));

        imageView.setX(20);
        imageView.setY(20);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        bp.setCenter(imageView);
        return new Scene(bp, 400, 400);
    }

    public Scene makeAboutScene(Stage primaryStage) {
        Button btnBack = new Button("Back");
        BorderPane bp = makeNewBorderPaneWithBtnBar("About", btnBack);
        Text text1 = new Text(
                "Our software company Tax Ireland Solutions Ltd under contract from the Department of Environment has developed this Property Charge Management System. The system will allows property owners to register each of their properties and to pay the property tax due for the properties. Property tax is a yearly tax on a property and it is due to be paid on Jan 1st each year. Property owners are able to view a list of their properties and the tax that is due currently per property and also any overdue tax (hasn't been paid for a previous year) and are able to query specific previous years and get a balancing statement for any particular year or property. The system maintains a record of all payments of the property charge on a yearly basis.");
        TextFlow textAbout = new TextFlow(text1);
        btnBack.setOnAction(e -> primaryStage.setScene(scnWelcome));
        bp.setCenter(textAbout);
        BorderPane.setMargin(textAbout, new Insets(12, 12, 12, 12));

        return new Scene(bp, 400, 400);
    }

    public Scene makeLoginScene(Stage primaryStage) {
        Button btnBack = new Button("Back");
        Button btnSignUp = new Button("Sign Up");
        Button btnLogin = new Button("Login");
        Button btnDepLogin = new Button("Department Login");
        BorderPane bp = makeNewBorderPaneWithBtnBar("Login", btnBack, btnSignUp, btnLogin, btnDepLogin);

        GridPane grid = makeNewGridPane();
        Label lblPPSN = new Label("PPS Number");
        grid.add(lblPPSN, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        Label lblPW = new Label("Password");
        grid.add(lblPW, 0, 2);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        bp.setCenter(grid);
        btnBack.setOnAction(e -> primaryStage.setScene(scnWelcome));
        btnLogin.setOnAction(e -> primaryStage.setScene(scnLogin));
        btnSignUp.setOnAction(e -> primaryStage.setScene(scnSignUp));
        btnDepLogin.setOnAction(e -> primaryStage.setScene(scnDepLogin));

        return new Scene(bp, 400, 400);
    }

    public Scene makeSignUpScene(Stage primaryStage) {
        Button btnBack = new Button("Cancel");
        Button btnSignUp = new Button("Sign Up");
        BorderPane bp = makeNewBorderPaneWithBtnBar("Sign up", btnBack, btnSignUp);

        GridPane grid = makeNewGridPane();
        Label lblName = new Label("Name");
        grid.add(lblName, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        Label lblPPSN = new Label("PPS Number");
        grid.add(lblPPSN, 0, 2);
        TextField userTextField2 = new TextField();
        grid.add(userTextField2, 1, 2);
        Label lblPW = new Label("Password");
        grid.add(lblPW, 0, 3);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);

        bp.setCenter(grid);

        btnBack.setOnAction(e -> primaryStage.setScene(scnWelcome));
        // btnSignUp.setOnAction(e -> primaryStage.setScene(scnSignUp));;

        return new Scene(bp, 400, 400);
    }

    public Scene makeDepLoginScene(Stage primaryStage) {
        Button btnBack = new Button("Back");
        Button btnLogin = new Button("Login");
        BorderPane bp = makeNewBorderPaneWithBtnBar("Department Login", btnBack, btnLogin);

        GridPane grid = makeNewGridPane();
        Label lblWorkID = new Label("Work ID");
        grid.add(lblWorkID, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        Label lblPW = new Label("Password");
        grid.add(lblPW, 0, 2);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        bp.setCenter(grid);

        btnBack.setOnAction(e -> primaryStage.setScene(scnWelcome));
        // btnLogin.setOnAction(e -> primaryStage.setScene(scnLogin));

        return new Scene(bp, 400, 400);
    }

    public BorderPane makeNewBorderPaneWithBtnBar(String title, Button... btns) {
        BorderPane bp = new BorderPane();
        Label lblTitle = new Label(title);

        VBox vBoxTitleBar = new VBox(20);
        HBox hBoxButtonBar = new HBox(20);
        vBoxTitleBar.getChildren().addAll(lblTitle);
        hBoxButtonBar.getChildren().addAll(btns);
        hBoxButtonBar.setAlignment(Pos.CENTER);
        vBoxTitleBar.setAlignment(Pos.CENTER);
        BorderPane.setMargin(hBoxButtonBar, new Insets(12, 12, 12, 12));
        BorderPane.setMargin(vBoxTitleBar, new Insets(12, 12, 12, 12));
        bp.setTop(vBoxTitleBar);
        bp.setBottom(hBoxButtonBar);
        return bp;
    }

    public GridPane makeNewGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        return grid;
    }

}