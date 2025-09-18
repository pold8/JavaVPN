package com.javavpn.UI;

import com.javavpn.client.VPNClient;
import com.javavpn.config.VPNConfig;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VPNApp extends Application {

    private VPNClient client;
    private Thread clientThread;
    private Label statusLabel;
    private Button connectButton;
    private ComboBox<String> countryBox;

    @Override
    public void start(Stage primaryStage) {
        // UI elements
        statusLabel = new Label("Status: Disconnected");
        connectButton = new Button("Connect");
        countryBox = new ComboBox<>();
        countryBox.getItems().addAll("US", "Germany", "UK", "France");
        countryBox.setValue("US");

        connectButton.setOnAction(e -> {
            if (clientThread == null || !clientThread.isAlive()) {
                connectVPN();
            } else {
                disconnectVPN();
            }
        });

        VBox root = new VBox(10, countryBox, connectButton, statusLabel);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("JavaVPN");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void connectVPN() {
        String selectedCountry = countryBox.getValue();
        VPNConfig config = getConfigForCountry(selectedCountry);

        client = new VPNClient(config);
        clientThread = new Thread(client::connect);
        clientThread.start();

        statusLabel.setText("Status: Connected to " + selectedCountry);
        connectButton.setText("Disconnect");
    }

    private void disconnectVPN() {
        try {
            if (clientThread != null) {
                clientThread.interrupt();
            }
            statusLabel.setText("Status: Disconnected");
            connectButton.setText("Connect");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VPNConfig getConfigForCountry(String country) {
        VPNConfig config = new VPNConfig();
        switch (country) {
            case "US":
                config = new VPNConfig("us.server.com", 8080);
                break;
            case "Germany":
                config = new VPNConfig("de.server.com", 8080);
                break;
            case "UK":
                config = new VPNConfig("uk.server.com", 8080);
                break;
            case "France":
                config = new VPNConfig("fr.server.com", 8080);
                break;
        }
        return config;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
