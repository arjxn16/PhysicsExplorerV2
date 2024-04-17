package com.base.game.physicsexplorer.MainMenu;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class SoundController {
    private Scene previousScene;
    private Stage stage;
    private Config config;

    @FXML
    private Slider volumeSlider;
    @FXML
    private TextField volumeTextField;
    @FXML
    private CheckBox muteCheckBox; // Changed to CheckBox

    public SoundController() {
        this.config = new Config("config.txt");
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        loadConfig();
        setupListeners();
    }

    private void setupListeners() {
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> syncSliderWithTextField());
        volumeTextField.textProperty().bindBidirectional(volumeSlider.valueProperty(), new NumberStringConverter());
        volumeTextField.setOnKeyReleased(this::onVolumeTextFieldChanged);
    }

    private void syncSliderWithTextField() {
        volumeTextField.setText(String.valueOf((int) volumeSlider.getValue()));
        updateMuteCheckBoxState();
    }

    public void onVolumeTextFieldChanged(KeyEvent event) {
        if (event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
          try {
              int value = Integer.parseInt(volumeTextField.getText()); //convert text field to integer
              if (value < 0){
                  volumeSlider.setValue(0);  // set slider to minimum value if below range
              } else if (value > 100) {
                  volumeSlider.setValue(100); // set slider to maximum if value is above range
              } else {
                  volumeSlider.setValue(value); // set slider to entered value if value within range
              }
          } catch (NumberFormatException e) {
              volumeTextField.setText(String.valueOf((int) volumeSlider.getValue())); // reset to sliders value on invalid input
          }
        }
    }

    @FXML
    public void onMuteBoxClick() {
        if (muteCheckBox.isSelected()) {
            volumeSlider.setValue(0);
        } else {
            volumeSlider.setValue(config.loadVolumeSetting());
        }
    }


    private void saveConfig() {
        int currentVolume = (int) volumeSlider.getValue();
        new Thread(() -> {
            config.saveVolumeSetting(currentVolume);
            System.out.println("Volume saved: " + currentVolume);
        }).start();
    }

    private void loadConfig() {
        int volume = config.loadVolumeSetting();
        volumeSlider.setValue(volume);
        volumeTextField.setText(String.valueOf(volume));
        updateMuteCheckBoxState();
    }

    private void updateMuteCheckBoxState() {
        muteCheckBox.setSelected(volumeSlider.getValue() == 0);
    }

    @FXML
    public void onReturnButtonClick() {
        saveConfig();

        if (previousScene != null && stage != null) {
            stage.setScene(previousScene);
            stage.setTitle("Main Menu");
        }

    }

}
