/**
 * MINIPROYECTO 1 : EL SOL ECLIPSADO
 * @autor Alexander Silva
 * @version 1.0
 */
package org.example.soleclipsado;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Clase Main para la ejecución de la aplicación.
 */

public class Main extends Application {
    /**
     * Metodo para inicializar y mostrar el stage.
     * @param stage El escenario principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Game-View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 600);
        stage.setTitle("El Sol Eclipsado");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo principal que inicia la aplicacion
     */
    public static void main(String[] args) {
        launch();
    }
}

