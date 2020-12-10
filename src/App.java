import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
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
    Scene scnWelcome, scnAbout, scnLogin, scnSignUp, scnDepLogin, scnDash, scnDepDash, scnRegProp, scnPayTax,
            scnViewChoice, scnViewProp, scnPrevPayments, scnOverdueProp, scnPropTaxStat, scnPayData, scnYearBalance,
            scnPropBalance, scnOverduePropTable, scnPropPayment;
    SystemManager sm;
    Owner currentOwner;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        sm = new SystemManager();
        primaryStage.setTitle("Property Management System");
        primaryStage.getIcons().add(new Image("file:house.png"));
        scnWelcome = makeWelcomeScene(primaryStage);
        primaryStage.setScene(scnWelcome);
        primaryStage.show();
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(500);
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
        btnAbout.setOnAction(e -> {
            scnAbout = makeAboutScene(primaryStage);
            primaryStage.setScene(scnAbout);
        });
        btnLogin.setOnAction(e -> {
            scnLogin = makeLoginScene(primaryStage);
            primaryStage.setScene(scnLogin);
        });
        btnQuit.setOnAction(e -> System.exit(1));
        imageView.setX(20);
        imageView.setY(20);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        bp.setCenter(imageView);
        return new Scene(bp);
    }

    public Scene makeAboutScene(Stage primaryStage) {
        Button btnBack = new Button("Back");
        BorderPane bp = makeNewBorderPaneWithBtnBar("About", btnBack);
        Text text1 = new Text(
                "Our software company Tax Ireland Solutions Ltd under contract from the Department of Environment has developed this Property Charge Management System. The system will allow property owners to register each of their properties and to pay the property tax due for the properties. Property tax is a yearly tax on a property and it is due to be paid on Jan 1st each year. Property owners are able to view a list of their properties and the tax that is due currently per property and also any overdue tax (hasn't been paid for a previous year) and are able to query specific previous years and get a balancing statement for any particular year or property. The system maintains a record of all payments of the property charge on a yearly basis.");
        TextFlow textAbout = new TextFlow(text1);
        btnBack.setOnAction(e -> {
            primaryStage.setScene(scnWelcome);
        });
        bp.setCenter(textAbout);
        BorderPane.setMargin(textAbout, new Insets(12, 12, 12, 12));
        return new Scene(bp);
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
        userTextField.setPromptText("e.g. 1234567AB");
        grid.add(userTextField, 1, 1);
        Label lblPW = new Label("Password");
        grid.add(lblPW, 0, 2);
        PasswordField pwBox = new PasswordField();

        grid.add(pwBox, 1, 2);
        bp.setCenter(grid);
        btnBack.setOnAction(e -> {
            primaryStage.setScene(scnWelcome);
        });
        btnLogin.setOnAction(e -> {
            String ppsNum = userTextField.getText().trim().toUpperCase();
            if (!sm.isValidppsNum(ppsNum)) {
                Alert a = makeAlert("PPS Number must be in the form of 7 digits followed by 1 or 2 letters!",
                        "PPSN invalid", AlertType.ERROR);
                a.show();
                return;
            } else if (!sm.ownerExists(ppsNum)) {
                Alert a = makeAlert("PPS Number does not exist!\nPlease Sign up", "PPS Number Error", AlertType.ERROR);
                a.show();
                return;
            } else if (!sm.loginVerification(ppsNum, pwBox.getText())) {
                Alert a = makeAlert("Login failed!", "Login Error", AlertType.ERROR);
                a.show();
                return;
            }
            currentOwner = sm.getOwner(userTextField.getText());

            scnDash = makeDashScene(primaryStage);
            primaryStage.setScene(scnDash);
        });
        btnSignUp.setOnAction(e ->

        {
            scnSignUp = makeSignUpScene(primaryStage);
            primaryStage.setScene(scnSignUp);
        });
        btnDepLogin.setOnAction(e -> {
            scnDepLogin = makeDepLoginScene(primaryStage);
            primaryStage.setScene(scnDepLogin);
        });
        return new Scene(bp);
    }

    public Scene makeSignUpScene(Stage primaryStage) {
        Button btnBack = new Button("Cancel");
        Button btnSignUp = new Button("Sign Up");
        BorderPane bp = makeNewBorderPaneWithBtnBar("Sign up", btnBack, btnSignUp);
        GridPane grid = makeNewGridPane();
        Label lblName = new Label("Name");
        grid.add(lblName, 0, 1);
        TextField userTextField = new TextField();
        userTextField.setPromptText("e.g. John Smith");
        grid.add(userTextField, 1, 1);
        Label lblPPSN = new Label("PPS Number");
        grid.add(lblPPSN, 0, 2);
        TextField ppsBox = new TextField();
        ppsBox.setPromptText("e.g. 1234567AB");
        grid.add(ppsBox, 1, 2);
        Label lblPW = new Label("Password");
        grid.add(lblPW, 0, 3);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);
        bp.setCenter(grid);

        btnBack.setOnAction(e -> primaryStage.setScene(scnWelcome));
        btnSignUp.setOnAction(e -> {
            String name = userTextField.getText().trim();
            String pps = ppsBox.getText().trim().toUpperCase();
            String password = pwBox.getText().trim();
            if (name.length() < 3) {
                Alert a = makeAlert("Your name must be at least 3 letters!", "Invalid Name", AlertType.ERROR);
                a.show();
                return;
            } else if (!sm.isValidppsNum(pps)) {
                Alert a = makeAlert("PPS Number must be in the form of 7 digits followed by 1 or 2 letters!",
                        "PPSN invalid", AlertType.ERROR);
                a.show();
                return;
            } else if (!sm.isValidPassword(password)) {
                Alert a = makeAlert(
                        "Password Requirements. \nAt least one upper case English letter,\nAt least one lower case English letter, \nAt least one digit, \nAt least one special character, \nMinimum eight in length.",
                        "Password invalid!", AlertType.ERROR);
                a.show();
                return;
            } else if (sm.ownerExists(pps)) {
                Alert a = makeAlert("User already exists, please log in!", "User already exists!", AlertType.ERROR);
                a.show();
                return;
            } else {
                sm.registerOwner(name, pps, password);
                Alert a = makeAlert("User Registered!", "User Registered!", AlertType.INFORMATION);
                a.show();
                currentOwner = sm.getOwner(pps);
                scnDash = makeDashScene(primaryStage);
                primaryStage.setScene(scnDash);

            }

        });

        return new Scene(bp);
    }

    public Scene makeDepLoginScene(Stage primaryStage) {
        Button btnBack = new Button("Back");
        Button btnLogin = new Button("Login");
        BorderPane bp = makeNewBorderPaneWithBtnBar("Department Login", btnBack, btnLogin);
        GridPane grid = makeNewGridPane();
        Label lblWorkID = new Label("Work ID");
        grid.add(lblWorkID, 0, 1);
        TextField userTextField = new TextField();
        userTextField.setPromptText("e.g. 2134324");
        grid.add(userTextField, 1, 1);
        Label lblPW = new Label("Password");
        grid.add(lblPW, 0, 2);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        bp.setCenter(grid);

        btnBack.setOnAction(e -> primaryStage.setScene(scnWelcome));
        btnLogin.setOnAction(e -> {
            scnDepDash = makeDepDashScene(primaryStage);
            primaryStage.setScene(scnDepDash);
        });

        return new Scene(bp);
    }

    public Scene makeDashScene(Stage primaryStage) {
        Button btnBack = new Button("Logout");
        BorderPane bp = makeNewBorderPaneWithBtnBar("Owner Dashboard", btnBack);
        GridPane grid = makeNewGridPane();
        Label lblPPSN = new Label("PPSN:" + currentOwner.getPpsNum());
        Label lblName = new Label("Name:" + currentOwner.getName());
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
        buttons.addAll(
                new ArrayList<Button>(Arrays.asList(btnRegProperty, btnPayTax, btnViewProperty, btnPrevPayment)));
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
        btnRegProperty.setOnAction(e -> {
            scnRegProp = makeRegPropScene(primaryStage);
            primaryStage.setScene(scnRegProp);
        });
        btnPayTax.setOnAction(e -> {
            scnPayTax = makePayTaxScene(primaryStage);
            primaryStage.setScene(scnPayTax);
        });
        btnPrevPayment.setOnAction(e -> {
            scnPrevPayments = makePrevPayments(primaryStage);
            primaryStage.setScene(scnPrevPayments);
        });
        btnConfirmYear.setOnAction(e -> {
            if (cBoxYear.getValue() == null)
                return;
            scnYearBalance = makeYearlyBalancingStatementScene(primaryStage, cBoxYear.getValue());
            primaryStage.setScene(scnYearBalance);
        });
        btnConfirmProperty.setOnAction(e -> {
            if (cBoxEirCcde.getValue() == null)
                return;
            scnPropBalance = makePropBalancingStatementScene(primaryStage, cBoxEirCcde.getValue());
            primaryStage.setScene(scnPropBalance);
        });
        btnViewProperty.setOnAction(e -> {
            scnViewChoice = makeViewPropChoice(primaryStage);
            primaryStage.setScene(scnViewChoice);
        });
        bp.setCenter(bpCENTER);
        bp.setTop(bpTOP);

        return new Scene(bp);
    }

    public Scene makeDepDashScene(Stage primaryStage) {
        Button btnBack = new Button("Logout");
        BorderPane bp = makeNewBorderPaneWithBtnBar("Department Dashboard", btnBack);
        GridPane grid = makeNewGridPane();
        Label lblWorkID = new Label("Work ID:");
        HBox hboxTitleBar = ((HBox) bp.getTop());
        Label lblTitle = ((Label) hboxTitleBar.getChildren().get(0));
        BorderPane bpTOP = new BorderPane();
        bpTOP.setCenter(lblTitle);
        bpTOP.setRight(lblWorkID);
        BorderPane.setMargin(bpTOP, new Insets(12, 12, 12, 12));
        bp.setCenter(grid);

        BorderPane bpCENTER = new BorderPane();
        GridPane btnGrid = makeNewGridPane();
        GridPane bottomGrid = makeNewGridPane();
        bpCENTER.setCenter(btnGrid);
        bpCENTER.setBottom(bottomGrid);

        ArrayList<Button> buttons = new ArrayList<>();
        Button btnOverdueProp = new Button("View Overdue Properties");
        Button btnPropTaxStat = new Button("Property Tax Statistics by Area");
        Button btnPayData = new Button("View Property Tax Payment Data");
        buttons.addAll(new ArrayList<Button>(Arrays.asList(btnOverdueProp, btnPropTaxStat, btnPayData)));
        for (Button b : buttons) {
            b.setMinWidth(250);
        }

        btnGrid.add(btnOverdueProp, 0, 0);
        btnGrid.add(btnPropTaxStat, 0, 1);
        btnGrid.add(btnPayData, 0, 2);

        btnBack.setOnAction(e -> primaryStage.setScene(scnWelcome));
        btnOverdueProp.setOnAction(e -> {
            scnOverdueProp = makeOverdueProp(primaryStage);
            primaryStage.setScene(scnOverdueProp);
        });
        btnPropTaxStat.setOnAction(e -> {
            scnPropTaxStat = makePropTaxStat(primaryStage);
            primaryStage.setScene(scnPropTaxStat);
        });
        btnPayData.setOnAction(e -> {
            scnPayData = makePayData(primaryStage);
            primaryStage.setScene(scnPayData);
        });
        bp.setCenter(bpCENTER);
        bp.setTop(bpTOP);
        return new Scene(bp);
    }

    public Scene makeRegPropScene(Stage primaryStage) {
        Button btnBack = new Button("Cancel");
        Button btnConfirm = new Button("Confirm");

        BorderPane bp = makeNewBorderPaneWithBtnBar("Register Property", btnBack, btnConfirm);
        GridPane grid = makeNewGridPane();

        Label lblOwners = new Label("Additonal Owners:");
        grid.add(lblOwners, 0, 1);
        TextField ownersTextField = new TextField();
        ownersTextField.setPromptText("e.g. 1234567AC,1234567AD");
        grid.add(ownersTextField, 1, 1);

        Label lblAddrs = new Label("Address:");
        grid.add(lblAddrs, 0, 2);
        TextField addrsTextField = new TextField();
        addrsTextField.setPromptText("e.g. 5 Liffey Road, Ennis, County Clare");
        grid.add(addrsTextField, 1, 2);

        Label lblEircodde = new Label("Eircode:");
        grid.add(lblEircodde, 0, 3);
        TextField eircodeTextField = new TextField();
        eircodeTextField.setPromptText("e.g. V95EY01");
        grid.add(eircodeTextField, 1, 3);

        Label lblEstMarketValue = new Label("Estimated Market Value:");
        grid.add(lblEstMarketValue, 0, 4);
        TextField valTextField = new TextField();
        valTextField.setPromptText("e.g. 150000.00");
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
        btnBack.setOnAction(e -> {
            scnDash = makeDashScene(primaryStage);
            primaryStage.setScene(scnDash);
        });

        btnConfirm.setOnAction(e -> {
            // validate!
            String additionalOwners = ownersTextField.getText();
            String address = addrsTextField.getText();
            String eircode = eircodeTextField.getText();
            String estimatedMarketValue = valTextField.getText();
            String locationCategory = cBoxLocationCategory.getValue();
            boolean isPrincipalPrivateResidence = chkPrincipalResidence.isSelected();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            String[] additionalOwnersList = additionalOwners.split(",");
            for (String additionalOwner : additionalOwnersList) {
                if (!sm.isValidppsNum(additionalOwner.trim())) {
                    if (additionalOwner.trim().length() == 0)
                        continue;
                    Alert a = makeAlert(
                            "PPS Numbers must be in the form of 7 digits followed by 1 or 2 letters!\nThe PPSN "
                                    + additionalOwner.trim() + " is not in this form",
                            "PPSN invalid", AlertType.ERROR);
                    a.show();
                    return;
                } else if (!sm.ownerExists(additionalOwner.trim())) {
                    Alert a = makeAlert("Only signed up members can be registed on the app\nThe PPSN "
                            + additionalOwner.trim() + " is unregistered", "Additional owner PPSN nonexistant",
                            AlertType.ERROR);
                    a.show();
                    return;
                } else if (additionalOwner.trim().equals(currentOwner.getPpsNum())) {
                    Alert a = makeAlert("You cannot add yourself as an additional owner", "Additional owner error",
                            AlertType.ERROR);
                    a.show();
                    return;
                }

            }
            if (address.length() < 10) {
                Alert a = makeAlert("Address must be at least 10 characters", "Address Invalid", AlertType.ERROR);
                a.show();
                return;
            } else if (!sm.isDouble(estimatedMarketValue)) {
                Alert a = makeAlert("Market Value must be a number with only digit characters", "Market Value Invalid",
                        AlertType.ERROR);
                a.show();
                return;
            } else if (cBoxLocationCategory.getValue() == null) {
                Alert a = makeAlert("Location Category Must Be Selected", "Location Invalid", AlertType.ERROR);
                a.show();
                return;
            }

            sm.registerProperty(year, currentOwner.getPpsNum() + "," + additionalOwners, address, eircode,
                    estimatedMarketValue, locationCategory, isPrincipalPrivateResidence);

            scnViewProp = makeViewPropScene(primaryStage);
            primaryStage.setScene(scnViewProp);
        });

        return new Scene(bp);
    }

    public Scene makePayTaxScene(Stage primaryStage) {
        Button btnBack = new Button("Cancel");
        Button btnPay = new Button("Pay");
        btnBack.setOnAction(e -> {
            scnDash = makeDashScene(primaryStage);
            primaryStage.setScene(scnDash);
        });

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
        payTextField.setPromptText("e.g. 1000");
        grid.add(payTextField, 1, 3);

        bp.setCenter(grid);
        return new Scene(bp);
    }

    public Scene makeViewPropChoice(Stage primaryStage) {
        Button btnBack = new Button("Back");
        Button btnConfirm = new Button("Confirm");
        Button btnViewProperty = new Button("All");
        BorderPane bp = makeNewBorderPaneWithBtnBar("View Properties", btnBack);
        btnBack.setOnAction(e -> {
            scnDash = makeDashScene(primaryStage);
            primaryStage.setScene(scnDash);
        });
        btnViewProperty.setOnAction(e -> {
            scnViewProp = makeViewPropScene(primaryStage);
            primaryStage.setScene(scnViewProp);
        });

        GridPane btnGrid = makeNewGridPane();

        Label lblViewAll = new Label("View Properties:");
        Label lblViewByYear = new Label("View By Year:");
        ObservableList<String> optYears = FXCollections.observableArrayList("2020", "2019", "2018");
        final ComboBox<String> cBoxYear = new ComboBox<>(optYears);
        btnConfirm.setOnAction(e -> {
            if (cBoxYear.getValue() == null) {
                return;
            } else {
                scnPropBalance = makeViewPropScene(primaryStage, cBoxYear.getValue());
                primaryStage.setScene(scnPropBalance);
            }
            return;
        });
        btnGrid.add(lblViewAll, 0, 0);
        btnGrid.add(btnViewProperty, 1, 0);
        btnGrid.add(lblViewByYear, 0, 2);
        btnGrid.add(cBoxYear, 1, 2);
        btnGrid.add(btnConfirm, 2, 2);

        btnViewProperty.setMinWidth(80);
        cBoxYear.setMinWidth(80);

        bp.setCenter(btnGrid);
        return new Scene(bp);
    }

    public Scene makeViewPropScene(Stage primaryStage) {
        Button btnBack = new Button("Back");
        BorderPane bp = makeBorderPaneWithBtnBarAndTable("My Properties", btnBack, "Eircode", "Address", "Owners",
                "Value", "Tax Due");
        btnBack.setOnAction(e -> primaryStage.setScene(scnDash));
        ArrayList<String> eircodes = sm.getOwnerPropertiesEircodes(currentOwner.getPpsNum());
        ArrayList<Property> properties = new ArrayList<>();
        for (String eircode : eircodes) {
            properties.add(sm.getPropertyData(eircode));
        }
        TableView<Property> table = new TableView<>();

        TableColumn<Property, String> eircode = new TableColumn<>("Eircode");
        TableColumn<Property, String> address = new TableColumn<>("Address");
        TableColumn<Property, ArrayList<String>> ownersPPS = new TableColumn<>("Owners");
        TableColumn<Property, String> estimatedMarketValue = new TableColumn<>("Value");

        table.getColumns().add(eircode);
        table.getColumns().add(address);
        table.getColumns().add(ownersPPS);
        table.getColumns().add(estimatedMarketValue);
        ObservableList<Property> obslist = FXCollections.observableArrayList();
        for (Property p : properties) {
            obslist.add(p);

        }
        eircode.setCellValueFactory(new PropertyValueFactory<Property, String>("eircode"));
        address.setCellValueFactory(new PropertyValueFactory<Property, String>("address"));
        ownersPPS.setCellValueFactory(new PropertyValueFactory<Property, ArrayList<String>>("ownersPPS"));
        estimatedMarketValue.setCellValueFactory(new PropertyValueFactory<Property, String>("estimatedMarketValue"));
        table.setItems(obslist);
        bp.setCenter(table);
        return new Scene(bp);
    }

    public Scene makePrevPayments(Stage primaryStage) {
        Button btnBack = new Button("Back");
        BorderPane bp = makeBorderPaneWithBtnBarAndTable("Pevious Payments", btnBack, "Eircode", "Address", "Owners",
                "Value", "Tax Due", "Paid");
        btnBack.setOnAction(e -> {
            scnDash = makeDashScene(primaryStage);
            primaryStage.setScene(scnDash);
        });
        return new Scene(bp, 600, 400);
    }

    public Scene makeOverdueProp(Stage primaryStage) {
        Button btnBack = new Button("Back");
        Button btnConfirm = new Button("Confirm");
        BorderPane bp = makeNewBorderPaneWithBtnBar("Overdue Properties", btnBack);
        btnBack.setOnAction(e -> {
            scnDepDash = makeDepDashScene(primaryStage);
            primaryStage.setScene(scnDepDash);
        });
        // Need to go back and check if prefix is valid using system manager
        Label lblWorkID = new Label("Work ID:");
        HBox hboxTitleBar = ((HBox) bp.getTop());
        Label lblTitle = ((Label) hboxTitleBar.getChildren().get(0));
        BorderPane bpTOP = new BorderPane();
        bpTOP.setCenter(lblTitle);
        bpTOP.setRight(lblWorkID);
        BorderPane.setMargin(bpTOP, new Insets(12, 12, 12, 12));
        bp.setTop(bpTOP);

        BorderPane bpCENTER = new BorderPane();
        GridPane topGrid = makeNewGridPane();
        bpCENTER.setTop(topGrid);

        Text txtYear = new Text();
        Text txtEircodePrefix = new Text();
        txtYear.setText("Year:");
        txtEircodePrefix.setText("Eircode Prefix (Optional):");
        topGrid.add(txtYear, 0, 0);
        topGrid.add(txtEircodePrefix, 0, 1);
        TextField userTextField = new TextField();
        topGrid.add(userTextField, 1, 1);

        ObservableList<String> optYears = FXCollections.observableArrayList("2020", "2019", "2018");
        final ComboBox<String> cBoxYear = new ComboBox<>(optYears);
        topGrid.add(cBoxYear, 1, 0);
        cBoxYear.setMinWidth(200);
        btnConfirm.setOnAction(e -> {
            if (cBoxYear.getValue() == null) {
                return;
            } else if (userTextField.getText() == null || userTextField.getText().trim().isEmpty()) {
                scnPropBalance = makeOverduePropTableScene(primaryStage, cBoxYear.getValue());
                primaryStage.setScene(scnPropBalance);
            } else {
                scnPropBalance = makeOverduePropTableScene(primaryStage, cBoxYear.getValue(), userTextField.getText());
                primaryStage.setScene(scnPropBalance);
            }
            return;
        });
        topGrid.add(btnConfirm, 2, 1);
        bp.setCenter(bpCENTER);
        bp.setTop(bpTOP);

        return new Scene(bp);
    }

    public Scene makeOverduePropTableScene(Stage primaryStage, String year) {
        Button btnBack = new Button("Back");
        String titleText = "View all overdue properties in " + year;
        BorderPane bp = makeBorderPaneWithBtnBarAndTable(titleText, btnBack, "Eircode", "Address", "Owners", "Value",
                "Tax Due");
        btnBack.setOnAction(e -> {
            scnDash = makeDashScene(primaryStage);
            primaryStage.setScene(scnDepDash);
        });
        return new Scene(bp);
    }

    public Scene makeOverduePropTableScene(Stage primaryStage, String year, String eircode) {
        Button btnBack = new Button("Back");
        String eircodeLocation = "DEFAULT MUST CHANGE";
        String titleText = "View all overdue properties in " + year + " at " + eircodeLocation;
        BorderPane bp = makeBorderPaneWithBtnBarAndTable(titleText, btnBack, "Eircode", "Address", "Owners", "Value",
                "Tax Due");
        btnBack.setOnAction(e -> {
            scnDash = makeDashScene(primaryStage);
            primaryStage.setScene(scnDepDash);
        });
        return new Scene(bp);
    }

    public Scene makePropTaxStat(Stage primaryStage) {
        Button btnBack = new Button("Back");
        Button btnConfirm = new Button("Confirm");
        BorderPane bp = makeNewBorderPaneWithBtnBar("Property Tax Statistics by Area", btnBack, btnConfirm);
        /*
         * btnConfirm.setOnAction(e -> {
         * 
         * });
         */
        btnBack.setOnAction(e -> {
            scnDepDash = makeDepDashScene(primaryStage);
            primaryStage.setScene(scnDepDash);
        });
        Label lblWorkID = new Label("Work ID:");
        HBox hboxTitleBar = ((HBox) bp.getTop());
        Label lblTitle = ((Label) hboxTitleBar.getChildren().get(0));
        BorderPane bpTOP = new BorderPane();
        bpTOP.setCenter(lblTitle);
        bpTOP.setRight(lblWorkID);
        BorderPane.setMargin(bpTOP, new Insets(12, 12, 12, 12));
        bp.setTop(bpTOP);

        BorderPane bpCENTER = new BorderPane();
        GridPane topGrid = makeNewGridPane();
        bpCENTER.setTop(topGrid);

        Text txtEircodePrefix = new Text();
        txtEircodePrefix.setText("Eircode Prefix:");
        topGrid.add(txtEircodePrefix, 0, 1);
        TextField userTextField = new TextField();
        topGrid.add(userTextField, 1, 1);
        topGrid.add(btnConfirm, 2, 1);
        bp.setCenter(bpCENTER);
        bp.setTop(bpTOP);
        return new Scene(bp);
    }

    public Scene makeViewPropPaymentTableByEircodeScene(Stage primaryStage, String eircode) {
        Button btnBack = new Button("Back");
        String titleText = "View all properties payment data for " + eircode;
        BorderPane bp = makeBorderPaneWithBtnBarAndTable(titleText, btnBack, "Date", "Paid", "Owners", "Value");
        btnBack.setOnAction(e -> {
            scnPayData = makePayData(primaryStage);
            primaryStage.setScene(scnPayData);
        });
        return new Scene(bp);
    }

    public Scene makeViewPropPaymentTableByPPSNScene(Stage primaryStage, String ppsn) {
        Button btnBack = new Button("Back");
        String titleText = "View all properties payment data for " + ppsn;
        BorderPane bp = makeBorderPaneWithBtnBarAndTable(titleText, btnBack, "Date", "Ericode", "Paid", "Owners",
                "Value");
        btnBack.setOnAction(e -> {
            scnPayData = makePayData(primaryStage);
            primaryStage.setScene(scnPayData);
        });
        return new Scene(bp);
    }

    public Scene makePayData(Stage primaryStage) {
        Button btnBack = new Button("Back");
        Button btnConfirmEircode = new Button("Confirm Eircode");
        Button btnConfirmPPS = new Button("Confirm PPS");
        btnConfirmEircode.setMinWidth(150);
        btnConfirmPPS.setMinWidth(150);
        BorderPane bp = makeNewBorderPaneWithBtnBar("Get Property Tax Payment Data", btnBack, btnConfirmEircode,
                btnConfirmPPS);
        /*
         * btnConfirm.setOnAction(e -> {
         * 
         * });
         */

        btnBack.setOnAction(e -> {
            scnDepDash = makeDepDashScene(primaryStage);
            primaryStage.setScene(scnDepDash);
        });
        Label lblWorkID = new Label("Work ID:");
        HBox hboxTitleBar = ((HBox) bp.getTop());
        Label lblTitle = ((Label) hboxTitleBar.getChildren().get(0));
        BorderPane bpTOP = new BorderPane();
        bpTOP.setCenter(lblTitle);
        bpTOP.setRight(lblWorkID);
        BorderPane.setMargin(bpTOP, new Insets(12, 12, 12, 12));
        bp.setTop(bpTOP);

        BorderPane bpCENTER = new BorderPane();
        GridPane grid = makeNewGridPane();
        bpCENTER.setCenter(grid);

        Text txtEircode = new Text();
        txtEircode.setText("By Eircode:");
        grid.add(txtEircode, 0, 1);
        TextField eircodeTextField = new TextField();
        grid.add(eircodeTextField, 1, 1);
        grid.add(btnConfirmEircode, 2, 1);

        Text txtPPS = new Text();
        txtPPS.setText("By PPS:");
        grid.add(txtPPS, 0, 2);
        TextField ppsTextField = new TextField();
        grid.add(ppsTextField, 1, 2);
        grid.add(btnConfirmPPS, 2, 2);
        bp.setCenter(bpCENTER);
        bp.setTop(bpTOP);
        btnConfirmPPS.setOnAction(e -> {
            if (ppsTextField.getText().equals("")) {
                return;
            }
            scnPropPayment = makeViewPropPaymentTableByPPSNScene(primaryStage, ppsTextField.getText());
            primaryStage.setScene(scnPropPayment);
        });
        btnConfirmEircode.setOnAction(e -> {
            if (eircodeTextField.getText().equals("")) {
                return;
            }
            scnPropPayment = makeViewPropPaymentTableByEircodeScene(primaryStage, eircodeTextField.getText());
            primaryStage.setScene(scnPropPayment);
        });
        return new Scene(bp);
    }

    public Scene makeViewPropScene(Stage primaryStage, String year) {
        Button btnBack = new Button("Back");
        String titleText = "My Properties as of " + year;
        BorderPane bp = makeBorderPaneWithBtnBarAndTable(titleText, btnBack, "Eircode", "Address", "Owners", "Value",
                "Tax Due", "Tax Overdue", "Total Owed");
        btnBack.setOnAction(e -> {
            scnDash = makeDashScene(primaryStage);
            primaryStage.setScene(scnDash);
        });
        return new Scene(bp);
    }

    public Scene makeYearlyBalancingStatementScene(Stage primaryStage, String year) {
        Button btnBack = new Button("Back");
        String titleText = "Balancing Statement for " + year;
        BorderPane bp = makeBorderPaneWithBtnBarAndTable(titleText, btnBack, "Date", "Description", "Paid", "Balance");
        btnBack.setOnAction(e -> {
            scnDash = makeDashScene(primaryStage);
            primaryStage.setScene(scnDash);
        });
        return new Scene(bp);
    }

    public Scene makePropBalancingStatementScene(Stage primaryStage, String eircode) {
        Button btnBack = new Button("Back");
        String titleText = "Balancing Statement for " + eircode;
        BorderPane bp = makeBorderPaneWithBtnBarAndTable(titleText, btnBack, "Date", "Description", "Paid", "Balance");
        btnBack.setOnAction(e -> {
            scnDash = makeDashScene(primaryStage);
            primaryStage.setScene(scnDash);
        });
        return new Scene(bp);
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
            TableColumn<String, String> col = new TableColumn<>(s);
            col.setMinWidth(100);
            tableView.getColumns().add(col);
        }

        return tableView;
    }

    public Alert makeAlert(String alert, String alertTitle, AlertType atype) {
        Alert a = new Alert(AlertType.NONE);
        a.setHeaderText(alert);
        a.setTitle(alertTitle);
        a.setAlertType(atype);
        return a;
    }

}