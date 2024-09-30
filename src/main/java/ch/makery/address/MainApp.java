package ch.makery.address;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ch.makery.address.model.Club;
import ch.makery.address.view.ClubOverviewController;
import ch.makery.address.view.ClubEditDialogController;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of Clubs.
     */
    private ObservableList<Club> ClubData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        ClubData.add(new Club("МБОУ СОШ №1", "Клуб робототехники \"ТехноЛидеры\""));
        ClubData.add(new Club("МБОУ СОШ №2", "Спортивный клуб \"Звезда\""));
        ClubData.add(new Club("МБОУ СОШ №3", "Театральный кружок \"Сцена\""));
        ClubData.add(new Club("МБОУ СОШ №4", "Литературный клуб \"Слово\""));
        ClubData.add(new Club("МБОУ СОШ №5", "Клуб программирования \"Кодеры\""));
        ClubData.add(new Club("МБОУ СОШ №1", "Художественный кружок \"Мастера кисти\""));
        ClubData.add(new Club("МБОУ СОШ №2", "Шахматный клуб \"Гроссмейстер\""));
        ClubData.add(new Club("МБОУ СОШ №3", "Клуб фотоискусства \"Взгляд\""));
        ClubData.add(new Club("МБОУ СОШ №4", "Картинговый кружок \"Красный - значит быстрый\" "));
    }

    /**
     * Returns the data as an observable list of Clubs.
     * @return
     */
    public ObservableList<Club> getClubData() {
        return ClubData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ClubApp");

        this.primaryStage.getIcons().add(new Image("2124225_card_essential_app_credit_icon.png"));

        initRootLayout();

        showClubOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/ch/makery/address/viev/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            //Scene scene = new Scene(rootLayout);
            //primaryStage.setScene(scene);
            primaryStage.setScene(new Scene(rootLayout, 1200 , 520));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the Club overview inside the root layout.
     */
    public void showClubOverview() {
        try {
            // Load Club overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/ch/makery/address/viev/ClubOverview.fxml"));
            AnchorPane ClubOverview = (AnchorPane) loader.load();

            // Set Club overview into the center of root layout.
            rootLayout.setCenter(ClubOverview);

            // Give the controller access to the main app.
            ClubOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Открывает диалоговое окно для изменения деталей указанного адресата.
     * Если пользователь кликнул OK, то изменения сохраняются в предоставленном
     * объекте адресата и возвращается значение true.
     *
     * @param person - объект адресата, который надо изменить
     * @return true, если пользователь кликнул OK, в противном случае false.
     */
    public boolean showClubEditDialog(Club club) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ch/makery/address/viev/ClubEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Club");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            ClubEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setClub(club);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}