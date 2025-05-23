package com.example.gestionvuelos;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class reservationsController {

    @FXML
    private ComboBox<String> departureComboBox;
    @FXML
    private ComboBox<String> destinationComboBox;
    @FXML
    private DatePicker departureDatePicker;
    @FXML
    private DatePicker returnDatePicker;
    @FXML
    private ImageView placeImage;

    private final List<String> imageList = new ArrayList<>();
    private int currentIndex = 0;

    public void initialize() {
        // Set current date for departure and return
        departureDatePicker.setValue(LocalDate.now());
        returnDatePicker.setValue(LocalDate.now().plusDays(3)); // Default return after 3 days

        // Load available images from "resources/places"
        imageList.add("image1.jpg");
        imageList.add("image2.jpg");
        imageList.add("image3.jpg");

        // Start automatic image rotation every 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> changeImage()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void changeImage() {
        if (imageList.isEmpty()) return;

        // Correctly retrieve image resource from "places" folder
        String path = Objects.requireNonNull(getClass().getResource("/places/" + imageList.get(currentIndex))).toString();
        placeImage.setImage(new Image(path));

        // Update index for next image
        currentIndex = (currentIndex + 1) % imageList.size();
    }

    public void onSearchFlightsClick(ActionEvent actionEvent) {
    }
}
