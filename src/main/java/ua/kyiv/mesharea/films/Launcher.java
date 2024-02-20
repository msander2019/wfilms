package ua.kyiv.mesharea.films;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ua.kyiv.mesharea.films.controller.MainController;
import ua.kyiv.mesharea.films.entity.Lang;
import ua.kyiv.mesharea.films.utils.LocaleManager;
import ua.kyiv.mesharea.films.utils.TestPreloader;

import java.io.IOException;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class Launcher extends Application implements Observer {

    private static final String FXML_MAIN = "/fxml/MainFilms.fxml";
    public static final String BUNDLES_FOLDER = "bundles.Locale";
    private Stage primaryStage;
    private Parent parent;
    private MainController mainController;
    private FXMLLoader fxmlLoader;
    private VBox currentRoot;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/imdb.png")));
        } catch (Exception ex) {
            System.out.println("Failed to load icon");
        }
        createGUI(LocaleManager.UA_LOCALE);
    }

//    @Override
//    public void init() throws Exception {
//        for (int i = 0; i < 50; i++) {
//            Thread.sleep(15);
//            notifyPreloader(new Preloader.ProgressNotification(i));
//        }
//    }

    public static void main(String[] args) {
        launch(args);
//        LauncherImpl.launchApplication(Launcher.class, TestPreloader.class, args);
    }

    @Override
    public void update(Observable o, Object arg) {
        Lang lang = (Lang) arg;
        VBox vBox = loadFXML(lang.getLocale());  // получить новое дерево компонентов с нужной локалью
        currentRoot.getChildren().setAll(vBox.getChildren()); // заменить старый дочерний компонент на новый - с другой локалью
    }

    // загружает дерево компонентов и возвращает в виде VBox (корневой элемент в FXML)
    private VBox loadFXML(Locale locale) {
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXML_MAIN));
        fxmlLoader.setResources(ResourceBundle.getBundle(BUNDLES_FOLDER, locale));
//        fxmlLoader.setResources(ResourceBundle.getBundle("bundles.Locale", locale));
        VBox node = null;
        try {
            node = (VBox) fxmlLoader.load();
            mainController = fxmlLoader.getController();
            mainController.addObserver(this);
            primaryStage.setTitle(fxmlLoader.getResources().getString("films"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    private void createGUI(Locale locale) {
        currentRoot = loadFXML(locale);
        Scene scene = new Scene(currentRoot, 1300, 820);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(820);
        primaryStage.setMinWidth(1300);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }


}
