/**
 * @autor 2343025-2724 OLman Alexander Silva Zuñiga gr 81
 * @version 1.0
 */
package org.example.soleclipsado.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.soleclipsado.models.Game;

/**
 * Clase para el control del juego mediante los recursos de view y metodo.
 */
public class GameController {
    /**
     * Campo de texto que captura la palabra secreta ingresada por el usuario.
     * Este campo esta vinculado a la interfaz grafica
     *
     */
    @FXML
    private TextField txtPalabraSecreta;

    /**
     * Contenedor de letras donde se mostrarán los campos de letras adivinadas.
     */
    @FXML
    private HBox letrasContainer;

    /**
     * Botón que permite al usuario usar una pista en el juego.
     */
    @FXML
    private Button btnAyuda;

    /**
     * Label que muestra mensajes al usuario, como instrucciones o resultados.
     */
    @FXML
    private Label lblMensaje;

    /**
     * ImageView para mostrar el progreso del eclipse del sol.
     */
    @FXML
    private ImageView imgEclipseProgreso;

    /**
     * juego Instancia del juego que maneja la lógica y estado del juego.
     */
    private Game juego;

    /**
     * Arreglo de campos de texto que representan las letras de la palabra secreta.
     */
    private TextField[] camposLetras;

    /**
     * Contador de los intentos de ayuda que ha utilizado el jugador.
     */
    private int intentosAyuda = 0;

    /**
     * Imágenes del sol en diferentes estados
     */
    private Image[] imagenesEclipse;

    /**
     * Metodo que maneja el evento de comenzar el juego.
     * Valida la palabra secreta ingresada y configura el juego.
     */
    @FXML
    public void handleComenzarJuego() {
        String palabraSecreta = txtPalabraSecreta.getText().trim().toLowerCase();
        if (palabraSecreta.length() < 6 || palabraSecreta.length() > 12 || !palabraSecreta.matches("[a-zñáéíóú]+")) {
            lblMensaje.setText("La palabra debe tener entre 6 y 12 letras y solo letras del alfabeto español.");
            return;
        }

        // Inicializar el juego con la palabra secreta
        juego = new Game(palabraSecreta);

        // Cargar las imágenes del sol en diferentes fases
        cargarImagenesEclipse();

        // Mostrar el sol completamente visible al inicio
        imgEclipseProgreso.setImage(imagenesEclipse[0]);

        // Limpiar el campo de texto de la palabra secreta y deshabilitarlo
        txtPalabraSecreta.clear();
        txtPalabraSecreta.setDisable(true);

        // Limpiar los campos anteriores
        letrasContainer.getChildren().clear();
        camposLetras = new TextField[palabraSecreta.length()];

        // Crear campos de texto dinámicos para cada letra y limitar a una sola letra
        for (int i = 0; i < palabraSecreta.length(); i++) {
            camposLetras[i] = new TextField();
            camposLetras[i].setPrefWidth(40);

            final int index = i;
            camposLetras[i].textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > 1) {
                    camposLetras[index].setText(newValue.substring(0, 1));  // Limitar a una letra
                }
            });

            letrasContainer.getChildren().add(camposLetras[i]);
        }

        lblMensaje.setText("¡El juego ha comenzado! Adivina las letras.");
        btnAyuda.setDisable(false);
        intentosAyuda = 0;
        actualizarProgresoEclipse();
    }
    /**
     * Metodo que maneja el uso de la ayuda.
     * Revela una letra de la palabra secreta si hay intentos de ayuda disponibles.
     */
    @FXML
    public void handleUsarAyuda() {
        if (intentosAyuda >= 3) {
            lblMensaje.setText("¡Ya has usado toda la ayuda disponible!");
            return;
        }

        for (int i = 0; i < camposLetras.length; i++) {
            if (camposLetras[i].getText().isEmpty()) {
                camposLetras[i].setText(Character.toString(juego.getPalabraSecreta().charAt(i)));
                lblMensaje.setText("¡Se ha revelado una letra!");
                break;
            }
        }

        intentosAyuda++;
        if (intentosAyuda >= 4) {
            btnAyuda.setDisable(true);
        }
    }

    /**
     * Metodo para validar los intentos del jugador.
     * Verifica si las letras ingresadas son correctas y actualiza el estado del juego.
     */
    @FXML
    public void handleValidarIntento() {
        boolean acierto = true;
        //validacion de cada letra ingresada
        for (int i = 0; i < camposLetras.length; i++) {
            String letraIngresada = camposLetras[i].getText().trim().toLowerCase();
            char letraCorrecta = juego.getPalabraSecreta().charAt(i);
            //verificacion si la letra ingresada es correcta
            if (!letraIngresada.isEmpty() && letraIngresada.charAt(0) == letraCorrecta) {
                camposLetras[i].setStyle("-fx-background-color: lightgreen;");
                juego.adivinarLetra(letraCorrecta);
            } else {
                acierto = false;
                camposLetras[i].setStyle("-fx-background-color: lightcoral;");
            }
        }

        // Verifica si la palabra completa ha sido adivinada
        if (juego.palabraAdivinada()) {
            mostrarMensajeGanador();  // Llamar a este metodo para mostrar el mensaje de victoria
            habilitarReinicio();
            return;  // Salir del metodo para evitar el aumento de errores innecesario
        }

        // Si hubo letras incorrectas, aumentar los errores
        if (!acierto) {
            juego.adivinarLetra(' ');  // Aquí seguimos llamando al metodo, pero con un espacio
            actualizarProgresoEclipse();
        }

        // Verificar si el juego ha terminado (por errores o adivinanza completa)
        if (juego.juegoTerminado()) {
            if (juego.palabraAdivinada()) {
                mostrarMensajeGanador();  // Llamar a este metodo para mostrar el mensaje de victoria
                habilitarReinicio();
            } else {
                lblMensaje.setText("¡Has perdido! La palabra secreta era: " + juego.getPalabraSecreta());
                habilitarReinicio();
            }
        }
    }

    /**
     * Metodo para cargar las imagenes del eclipse en diferentes fases.
     * Las imagenes se asignan a un arreglo para su uso posterior
     */
    private void cargarImagenesEclipse() {
        imagenesEclipse = new Image[6];
        imagenesEclipse[0] = new Image(getClass().getResource("/org/example/soleclipsado/images/sol_100.png").toExternalForm());
        imagenesEclipse[1] = new Image(getClass().getResource("/org/example/soleclipsado/images/sol_80.png").toExternalForm());
        imagenesEclipse[2] = new Image(getClass().getResource("/org/example/soleclipsado/images/sol_60.png").toExternalForm());
        imagenesEclipse[3] = new Image(getClass().getResource("/org/example/soleclipsado/images/sol_40.png").toExternalForm());
        imagenesEclipse[4] = new Image(getClass().getResource("/org/example/soleclipsado/images/sol_20.png").toExternalForm());
        imagenesEclipse[5] = new Image(getClass().getResource("/org/example/soleclipsado/images/sol_0.png").toExternalForm());
    }

    /**
     * Metodo para actualizar el progreso del eclipse del sol.
     * Cambia la imagen mostrada segun la cantidad de errores cometidos.
     */
    private void actualizarProgresoEclipse() {
        int errores = juego.getErrores();
        imgEclipseProgreso.setImage(imagenesEclipse[Math.min(errores, 5)]);  // Actualiza la imagen según los errores

        if (errores >= juego.getMaxErrores()) {
            lblMensaje.setText("¡El sol se ha eclipsado por completo! Has perdido.");
            habilitarReinicio();
        }
    }

    /**
     * Metodo para mostrar un mensaje indicando que el jugador ganó.
     * Muestra una alerta informando la victoria.
     */
    private void mostrarMensajeGanador() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("¡Victoria!");
        alert.setHeaderText("¡Felicidades!");
        alert.setContentText("Has adivinado la palabra correctamente. ¡Ganaste!");
        alert.showAndWait();  // Mostrar la alerta y esperar a que el jugador la cierre
    }

    /**
     * Metodo para habilitar el reinicio del juego.
     * Limpia los campos y prepara la interfaz para un nuevo juego.
     */
    private void habilitarReinicio() {
        txtPalabraSecreta.setDisable(false);
        txtPalabraSecreta.clear();
        letrasContainer.getChildren().clear();  // Limpiar los campos de letras
        btnAyuda.setDisable(true);  // Deshabilitar el botón de ayuda hasta que comience un nuevo juego
    }
}