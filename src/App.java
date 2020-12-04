import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class App extends Application {
    Scene scnWelcome, scnAbout, scnLogin, scnSignUp, scnDepLogin, scnDash, scnRegProp, scnPayTax, scnViewChoice,
            scnViewProp, scnPrevPayments;

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
        scnDash = makeDashScene(primaryStage);
        scnRegProp = makeRegPropScene(primaryStage);
        scnPayTax = makePayTaxScene(primaryStage);
        scnViewProp = makeViewPropScene(primaryStage);
        scnViewChoice = makeViewPropChoice(primaryStage);
        scnPrevPayments = makePrevPayments(primaryStage);
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
        return new Scene(bp, 600, 400);
    }

    public Scene makeAboutScene(Stage primaryStage) {
        Button btnBack = new Button("Back");
        BorderPane bp = makeNewBorderPaneWithBtnBar("About", btnBack);
        Text text1 = new Text(
                "Our software company Tax Ireland Solutions Ltd under contract from the Department of Environment has developed this Property Charge Management System. The system will allow property owners to register each of their properties and to pay the property tax due for the properties. Property tax is a yearly tax on a property and it is due to be paid on Jan 1st each year. Property owners are able to view a list of their properties and the tax that is due currently per property and also any overdue tax (hasn't been paid for a previous year) and are able to query specific previous years and get a balancing statement for any particular year or property. The system maintains a record of all payments of the property charge on a yearly basis.");
        TextFlow textAbout = new TextFlow(text1);
        btnBack.setOnAction(e -> primaryStage.setScene(scnWelcome));
        bp.setCenter(textAbout);
        BorderPane.setMargin(textAbout, new Insets(12, 12, 12, 12));

        return new Scene(bp, 600, 400);
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
        btnLogin.setOnAction(e -> primaryStage.setScene(scnDash));
        btnSignUp.setOnAction(e -> primaryStage.setScene(scnSignUp));
        btnDepLogin.setOnAction(e -> primaryStage.setScene(scnDepLogin));

        return new Scene(bp, 600, 400);
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

        return new Scene(bp, 600, 400);
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

        return new Scene(bp, 600, 400);
    }

    public Scene makeDashScene(Stage primaryStage) {
        Button btnBack = new Button("Logout");
        BorderPane bp = makeNewBorderPaneWithBtnBar("Owner Dashboard", btnBack);
        GridPane grid = makeNewGridPane();
        Label lblPPSN = new Label("PPSN:");
        Label lblName = new Label("Name:");
        HBox hboxTitleBar = ((HBox) bp.getTop());
        Label lblTitle = ((Label) hboxTitleBar.getChildren().get(0));
        BorderPane bpTOP = new BorderPane();
        bpTOP.setCenter(lblTitle);
        bpTOP.setRight(lblPPSN);
        bpTOP.setLeft(lblName);
        BorderPane.setMargin(bpTOP, new Insets(12, 12, 12, 12));
        bp.setCenter(grid);

        BorderPane bpCENTER = new BorderPane();
        GridPane btnGrid = makeNewGridPane();
        GridPane bottomGrid = makeNewGridPane();
        bpCENTER.setCenter(btnGrid);
        bpCENTER.setBottom(bottomGrid);

        Text txtYearBalSTM = new Text();
        Text txtPropBalSTM = new Text();
        txtYearBalSTM.setText("Get Yearly Balancing Statement:");
        txtPropBalSTM.setText("Get Balancing Statement for Property:");
        bottomGrid.add(txtYearBalSTM, 0, 0);
        bottomGrid.add(txtPropBalSTM, 0, 1);

        ObservableList<String> optYears = FXCollections.observableArrayList("2020", "2019", "2018");
        ObservableList<String> optEircodes = FXCollections.observableArrayList("V12T234", "E56I789", "F74K675");

        final ComboBox<String> cBoxYear = new ComboBox<>(optYears);
        final ComboBox<String> cBoxEirCcde = new ComboBox<>(optEircodes);
        bottomGrid.add(cBoxYear, 1, 0);
        cBoxYear.setMinWidth(120);
        cBoxEirCcde.setMinWidth(120);
        bottomGrid.add(cBoxEirCcde, 1, 1);
        ArrayList<Button> buttons = new ArrayList<>();
        Button btnRegProperty = new Button("Register Property");
        Button btnPayTax = new Button("Pay Tax");
        Button btnViewProperty = new Button("View My Properties");
        Button btnPrevPayment = new Button("Previous Payments");
        Button btnConfirmYear = new Button("Confirm");
        Button btnConfirmProperty = new Button("Confirm");
        buttons.add(btnRegProperty);
        buttons.add(btnPayTax);
        buttons.add(btnViewProperty);
        buttons.add(btnPrevPayment);
        for (Button b : buttons) {
            b.setMinWidth(150);
        }

        btnGrid.add(btnRegProperty, 0, 0);
        btnGrid.add(btnPayTax, 1, 0);
        btnGrid.add(btnViewProperty, 0, 1);
        btnGrid.add(btnPrevPayment, 1, 1);

        bottomGrid.add(btnConfirmYear, 2, 0);
        bottomGrid.add(btnConfirmProperty, 2, 1);

        btnBack.setOnAction(e -> primaryStage.setScene(scnWelcome));
        btnRegProperty.setOnAction(e -> primaryStage.setScene(scnRegProp));
        btnPayTax.setOnAction(e -> primaryStage.setScene(scnPayTax));
        btnPrevPayment.setOnAction(e -> primaryStage.setScene(scnPrevPayments));
        // btnConfirmYear.setOnAction(e -> primaryStage.setScene(scnWelcome));
        // btnConfirmProperty.setOnAction(e -> primaryStage.setScene(scnWelcome));
        btnViewProperty.setOnAction(e -> primaryStage.setScene(scnViewChoice));
        bp.setCenter(bpCENTER);
        bp.setTop(bpTOP);
        return new Scene(bp, 600, 400);
    }

    public Scene makeRegPropScene(Stage primaryStage) {
        Button btnBack = new Button("Cancel");
        Button btnConfirm = new Button("Confirm");

        BorderPane bp = makeNewBorderPaneWithBtnBar("Register Property", btnBack, btnConfirm);
        GridPane grid = makeNewGridPane();

        Label lblOwners = new Label("Additonal Owners:");
        grid.add(lblOwners, 0, 1);
        TextField ownersTextField = new TextField();
        grid.add(ownersTextField, 1, 1);

        Label lblAddrs = new Label("Address:");
        grid.add(lblAddrs, 0, 2);
        TextField addrsTextField = new TextField();
        grid.add(addrsTextField, 1, 2);

        Label lblEircodde = new Label("Eircode:");
        grid.add(lblEircodde, 0, 3);
        TextField eircodeTextField = new TextField();
        grid.add(eircodeTextField, 1, 3);

        Label lblEstMarketValue = new Label("Estimated Market Value:");
        grid.add(lblEstMarketValue, 0, 4);
        TextField valTextField = new TextField();
        grid.add(valTextField, 1, 4);

        Label lblLocationCategory = new Label("Location Category:");
        grid.add(lblLocationCategory, 0, 5);
        ObservableList<String> optLocationCategory = FXCollections.observableArrayList("City", "Large Town",
                "Small Town", "Village", "Countryside");
        final ComboBox<String> cBoxLocationCategory = new ComboBox<>(optLocationCategory);
        grid.add(cBoxLocationCategory, 1, 5);

        Label lblPrincipalResidence = new Label("Principal Private Residence:");
        grid.add(lblPrincipalResidence, 0, 6);
        CheckBox chkPrincipalResidence = new CheckBox("");
        chkPrincipalResidence.setIndeterminate(false);
        grid.add(chkPrincipalResidence, 1, 6);

        bp.setCenter(grid);
        btnBack.setOnAction(e -> primaryStage.setScene(scnDash));
        btnConfirm.setOnAction(e -> primaryStage.setScene(scnViewProp));

        return new Scene(bp, 600, 400);
    }

    public Scene makePayTaxScene(Stage primaryStage) {
        Button btnBack = new Button("Cancel");
        Button btnPay = new Button("Pay");
        btnBack.setOnAction(e -> primaryStage.setScene(scnWelcome));

        BorderPane bp = makeNewBorderPaneWithBtnBar("Pay Tax", btnBack, btnPay);
        GridPane grid = makeNewGridPane();

        Label lblProp = new Label("Property:");
        grid.add(lblProp, 0, 1);
        ObservableList<String> optProperties = FXCollections.observableArrayList("V12T234", "E56I789", "F74K675");
        final ComboBox<String> cBoxProperties = new ComboBox<>(optProperties);
        grid.add(cBoxProperties, 1, 1);

        Label lblBal = new Label("Balance Due :");
        grid.add(lblBal, 0, 2);
        Text bal = new Text();
        bal.setText("0");
        grid.add(bal, 1, 2);

        Label lblPayOff = new Label("Pay Off:");
        grid.add(lblPayOff, 0, 3);
        TextField payTextField = new TextField();
        grid.add(payTextField, 1, 3);

        bp.setCenter(grid);
        return new Scene(bp, 600, 400);
    }

    public Scene makeViewPropChoice(Stage primaryStage) {
        Button btnBack = new Button("Back");
        Button btnConfirm = new Button("Confirm");
        Button btnViewProperty = new Button("All");
        BorderPane bp = makeNewBorderPaneWithBtnBar("View Properties", btnBack);
        btnBack.setOnAction(e -> primaryStage.setScene(scnDash));
        btnViewProperty.setOnAction(e -> primaryStage.setScene(scnViewProp));
        // btnConfirm.setOnAction(e -> primaryStage.setScene(scnDash));

        GridPane btnGrid = makeNewGridPane();

        Label lblViewAll = new Label("View Properties:");
        Label lblViewByYear = new Label("View By Year:");
        ObservableList<String> optYears = FXCollections.observableArrayList("2020", "2019", "2018");
        final ComboBox<String> cBoxYear = new ComboBox<>(optYears);

        btnGrid.add(lblViewAll, 0, 0);
        btnGrid.add(btnViewProperty, 1, 0);
        btnGrid.add(lblViewByYear, 0, 2);
        btnGrid.add(cBoxYear, 1, 2);
        btnGrid.add(btnConfirm, 2, 2);

        btnViewProperty.setMinWidth(80);
        cBoxYear.setMinWidth(80);

        bp.setCenter(btnGrid);
        return new Scene(bp, 600, 400);
    }

    public Scene makeViewPropScene(Stage primaryStage) {
        Button btnBack = new Button("Back");
        BorderPane bp = makeBorderPaneWithBtnBarAndTable("My Properties", btnBack, "Eircode", "Address", "Owners",
                "Value", "Tax Due", "Tax Overdue", "Total Owed");
        btnBack.setOnAction(e -> primaryStage.setScene(scnDash));
        return new Scene(bp, 700, 400);
    }

    public Scene makePrevPayments(Stage primaryStage) {
        Button btnBack = new Button("Back");
        BorderPane bp = makeBorderPaneWithBtnBarAndTable("Pevious Payments", btnBack, "Eircode", "Address", "Owners",
                "Value", "Tax Due", "Paid");
        btnBack.setOnAction(e -> primaryStage.setScene(scnDash));
        return new Scene(bp, 600, 400);
    }

    // Methods
    public BorderPane makeNewBorderPaneWithBtnBar(String title, Button... btns) {
        BorderPane bp = new BorderPane();
        Label lblTitle = new Label(title);

        HBox hBoxTitleBar = new HBox(20);
        HBox hBoxButtonBar = new HBox(20);
        hBoxTitleBar.getChildren().addAll(lblTitle);
        hBoxButtonBar.getChildren().addAll(btns);
        hBoxButtonBar.setAlignment(Pos.CENTER);
        hBoxTitleBar.setAlignment(Pos.CENTER);
        BorderPane.setMargin(hBoxButtonBar, new Insets(12, 12, 12, 12));
        BorderPane.setMargin(hBoxTitleBar, new Insets(12, 12, 12, 12));

        bp.setTop(hBoxTitleBar);
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

    public BorderPane makeBorderPaneWithBtnBarAndTable(String title, Button b, String... tableColumnTitles) {
        BorderPane bp = makeNewBorderPaneWithBtnBar(title, b);
        bp.setCenter(makeTable(tableColumnTitles));
        return bp;
    }

    public TableView<String> makeTable(String... tableColumnTitles) {
        TableView<String> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPlaceholder(new Label("No rows to display"));
        for (String s : tableColumnTitles) {
            TableColumn col = new TableColumn(s);
            col.setMinWidth(100);
            tableView.getColumns().add(col);
        }

        return tableView;
    }

}