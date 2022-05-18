package ru.gb.may_chat.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private VBox mainPanel;

    @FXML
    private TextFlow chatArea;

    @FXML
    private ListView<String> contacts;

    @FXML
    private CheckBox messageAll;

    @FXML
    private TextField inputField;

    @FXML
    private Button btnSend;

    public void mockAction(ActionEvent actionEvent) {
        System.out.println("mock");
    }

    public void closeApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void showManual(ActionEvent event) {
        String manual = "https://docs.yandex.ru/docs/view?url=ya-disk%3A%2F%2F%2Fdisk%2FGeekBrains%20manual%20for%20chat-client.docx&name=GeekBrains%20manual%20for%20chat-client.docx&uid=26621504";
        try {
            ChatController.openWebpage(new URI(manual));
        } catch(URISyntaxException exc) {
            exc.printStackTrace();
        }
    }

    public void sendMessage(ActionEvent actionEvent) {
        String text = inputField.getText();
        if (text == null || text.isBlank()) {
            return;
        }

        boolean toAll = messageAll.isSelected();
        String focused = toAll ? "всех" : contacts.getFocusModel().getFocusedItem();

        Text contact = new Text(String.format("для %s : ", focused));
        contact.setFill(Color.BLUE);
        contact.setFont(Font.font("System", FontWeight.BOLD, 12));

        Text message = new Text(text + System.lineSeparator());
        contact.setFont(Font.font("System", FontWeight.NORMAL, 12));

        chatArea.getChildren().addAll(contact, message);

        inputField.clear();
        messageAll.setSelected(false);
    }

    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> names = List.of("Vasya", "Masha", "Petya", "Valera", "Nastya");
        contacts.setItems(FXCollections.observableList(names));
    }
}