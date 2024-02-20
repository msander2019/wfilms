package ua.kyiv.mesharea.films.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.kyiv.mesharea.films.entity.Film;
import ua.kyiv.mesharea.films.utils.DialogManager;

import java.net.URL;
import java.util.ResourceBundle;

public class EditController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtYear;
    @FXML
    private TextField txtDirector;
    @FXML
    private TextField txtActors;
    @FXML
    private TextField txtGenre;
    @FXML
    private TextField txtRating;
    @FXML
    private TextField txtCountry;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    private Film film;
    private ResourceBundle resourceBundle;
    private boolean saveClicked = false; // для определения нажатой кнопки

    public void setFilm(Film film) {
        if (film == null) {
            return;
        }
        saveClicked = false;
        this.film = film;
        txtName.setText(film.getName());
        txtYear.setText(film.getYear());
        txtDirector.setText(film.getDirector());
        txtActors.setText(film.getActors());
        txtGenre.setText(film.getGenre());
        txtRating.setText(film.getRating());
        txtCountry.setText(film.getCountry());
    }

    public Film getFilm() {
        return film;
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
//        stage.close();
        stage.hide();
    }

    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues()) {
            return;
        }
        film.setName(txtName.getText());
        film.setYear(txtYear.getText());
        film.setDirector(txtDirector.getText());
        film.setActors(txtActors.getText());
        film.setGenre(txtGenre.getText());
        film.setRating(txtRating.getText());
        film.setCountry(txtCountry.getText());
        saveClicked = true;
        actionClose(actionEvent);
    }

    private boolean checkValues() {
        if (txtName.getText().trim().length() == 0 ||
                txtYear.getText().trim().length() == 0 ||
                txtDirector.getText().trim().length() == 0 ||
                txtActors.getText().trim().length() == 0 ||
                txtGenre.getText().trim().length() == 0 ||
                txtRating.getText().trim().length() == 0 ||
                txtCountry.getText().trim().length() == 0) {
            DialogManager.showInfoDialog(resourceBundle.getString("error"), resourceBundle.getString("fill_field"));
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }
}
