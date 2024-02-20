package ua.kyiv.mesharea.films.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import ua.kyiv.mesharea.films.Launcher;
import ua.kyiv.mesharea.films.entity.Film;
import ua.kyiv.mesharea.films.entity.Lang;
import ua.kyiv.mesharea.films.dao.impls.HbCollectionFilms;
import ua.kyiv.mesharea.films.utils.DialogManager;
import ua.kyiv.mesharea.films.utils.LocaleManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class MainController extends Observable implements Initializable {

    private static final String FXML_EDIT = "/fxml/EditFilms.fxml";
    private static final String UA_CODE = "ua";
    private static final String EN_CODE = "en";

    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditController editController;
    private Stage editStage;
    private ResourceBundle resourceBundle;
    private HbCollectionFilms hbCollectionFilms = new HbCollectionFilms();

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSearch;
    @FXML
    private CustomTextField txtSearch;
    @FXML
    private TableView tableFilms;
    @FXML
    private TableColumn<Film, String> columnName;
    @FXML
    private TableColumn<Film, String> columnYear;
    @FXML
    private TableColumn<Film, String> columnDirector;
    @FXML
    private TableColumn<Film, String> columnActors;
    @FXML
    private TableColumn<Film, String> columnGenre;
    @FXML
    private TableColumn<Film, String> columnRating;
    @FXML
    private TableColumn<Film, String> columnCountry;
    @FXML
    private Label labelCount;
    @FXML
    private ComboBox comboLocales;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        // instead constructor
        columnName.setCellValueFactory(new PropertyValueFactory<Film, String>("name"));
        columnYear.setCellValueFactory(new PropertyValueFactory<Film, String>("year"));
        columnDirector.setCellValueFactory(new PropertyValueFactory<Film, String>("director"));
        columnActors.setCellValueFactory(new PropertyValueFactory<Film, String>("actors"));
        columnGenre.setCellValueFactory(new PropertyValueFactory<Film, String>("genre"));
        columnRating.setCellValueFactory(new PropertyValueFactory<Film, String>("rating"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<Film, String>("country"));

        setupClearButtonField(txtSearch);
        fillData();
        initListeners();
        initLoader();
    }

    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method method = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            method.setAccessible(true);
            method.invoke(null, customTextField, customTextField.rightProperty());
            customTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if (t1.trim().length() == 0) {  // если полностью очистили текст - вернуть все записи
                        hbCollectionFilms.getFilms().clear();
                        hbCollectionFilms.findAll();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillData() {
        fillTable();
        fillLangComboBox();
        updateCountLabel();
    }

    private void fillTable() {
        tableFilms.setItems(hbCollectionFilms.getFilms());
    }

    private void fillLangComboBox() {
        Lang langUA = new Lang(0, UA_CODE, resourceBundle.getString("ua"), LocaleManager.UA_LOCALE);
        Lang langEN = new Lang(1, EN_CODE, resourceBundle.getString("en"), LocaleManager.EN_LOCALE);
        comboLocales.getItems().add(langUA);
        comboLocales.getItems().add(langEN);
        if (LocaleManager.getCurrentLang() == null) {
            comboLocales.getSelectionModel().select(0);
            LocaleManager.setCurrentLang(langUA);
        } else {
            comboLocales.getSelectionModel().select(LocaleManager.getCurrentLang().getIndex());
        }
    }

    private void initListeners() {
        hbCollectionFilms.getFilms().addListener(new ListChangeListener<Film>() {
            @Override
            public void onChanged(Change<? extends Film> change) {
                updateCountLabel();
            }
        });

        tableFilms.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
//                    editController.setFilm((Film) tableFilms.getSelectionModel().getSelectedItem());
//                    showDialog();
                    btnEdit.fire();
                }
            }
        });

        // listen change lang
        comboLocales.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Lang selectedLang = (Lang) comboLocales.getSelectionModel().getSelectedItem();
                LocaleManager.setCurrentLang(selectedLang);
                // notified all listeners, about change lang
                setChanged();
                notifyObservers(selectedLang);
            }
        });
    }

    private void updateCountLabel() {
        labelCount.setText(resourceBundle.getString("count") + ": " + hbCollectionFilms.getFilms().size());
    }

    private void initLoader() {
        try {
            // загружаем 1 раз в самом начале edit и получаем контроллер
            fxmlLoader.setLocation(getClass().getResource(FXML_EDIT));
            fxmlLoader.setResources(ResourceBundle.getBundle(Launcher.BUNDLES_FOLDER, LocaleManager.getCurrentLang().getLocale()));
            fxmlEdit = fxmlLoader.load();
            editController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        Film selectedFilm = (Film) tableFilms.getSelectionModel().getSelectedItem();
        Button clickedButton = (Button) source;
        boolean research = false;

        switch (clickedButton.getId()) {
            case "btnAdd":
                editController.setFilm(new Film());
                showDialog();
                if (editController.isSaveClicked()) {
                    hbCollectionFilms.add(editController.getFilm());
                    research = true;
                }
                break;

            case "btnEdit":
                if (!filmIsSelected(selectedFilm)) {
                    return;
                }
                editController.setFilm(selectedFilm);
                showDialog();
                if (editController.isSaveClicked()) {
                    hbCollectionFilms.update(selectedFilm);
                    research = true;
                }
                break;

            case "btnDelete":
                if (!filmIsSelected(selectedFilm) || !(confirmDelete())) {
                    return;
                }
                research = true;
                hbCollectionFilms.delete(selectedFilm);
                break;
        }
        if (research) {
            actionSearch(actionEvent);
        }
    }

    private boolean confirmDelete() {
        if (DialogManager.showConfirmDialog(resourceBundle.getString("confirm"),
                resourceBundle.getString("confirm_delete")).get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    private boolean filmIsSelected(Film selectedFilm) {
        if (selectedFilm == null) {
            DialogManager.showInfoDialog(resourceBundle.getString("error"), resourceBundle.getString("selected_film"));
            return false;
        }
        return true;
    }

    private void showDialog() {
        // lazy init (editStage инициализируется только один раз. При повторном заходе в метод showDialog(), stage создаваться не будет)
        if (editStage == null) {
            editStage = new Stage();
            editStage.setTitle(resourceBundle.getString("edit"));
            editStage.setMinHeight(418);
            editStage.setMinWidth(600);
            editStage.setResizable(false);
            editStage.setScene(new Scene(fxmlEdit));
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(comboLocales.getParent().getScene().getWindow());
        }
        editStage.showAndWait();
    }

    public void actionSearch(ActionEvent actionEvent) {
        if (txtSearch.getText().trim().length() == 0) {
            hbCollectionFilms.findAll();
        } else {
            hbCollectionFilms.find(txtSearch.getText());
        }
    }
}
