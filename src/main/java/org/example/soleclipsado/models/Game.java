/**
 * Modelo
 * @autor 2343025-2724 Olman Alexander Silva Zuñiga gr 81
 * @version 1.0
 */
package org.example.soleclipsado.models;

/**
 * Clase que representa la logica del juego.
 *
 */
public class Game {
    /**
     * palabraSecreta Representa la palabra que debe advinar el jugador.
     */
    private String palabraSecreta;
    /**
     * letrasReveladas Representa las letras que se van encontrando..
     */
    private boolean[] letrasReveladas;
    /**
     * errores Representa el numero de errores que se van generando.
     */
    private int errores;
    /**
     * maxErrores Representa la cantidad maxima de errores permitidos.
     */
    private final int maxErrores = 5;

    /**
     * Metodo para la asignacion inicial de la palabra secreta.
     * @param palabraSecreta Representa la paalabra secreta asignada.
     */
    public Game(String palabraSecreta) {
        this.palabraSecreta = palabraSecreta.toLowerCase();
        this.letrasReveladas = new boolean[palabraSecreta.length()];
        this.errores = 0;
    }

    /**
     * Metodo para verificar si una letra adivinada es correcta y actualizar el estado.
     * @param letra Variable tipo char para hacer la validacion por letra
     * @return acierto como un boolean que indica si se acertó o no se acertó la letra.
     */

    public boolean adivinarLetra(char letra) {
        boolean acierto = false;
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraSecreta.charAt(i) == letra) {
                letrasReveladas[i] = true;  // Marcar esta letra como revelada
                acierto = true;
            }
        }
        if (!acierto) {
            errores++;  // Aumentar los errores si no hay aciertos
        }

        return acierto;  // Devolver si se acertó o no la letra
    }

    /**
     * Metodo que verifica si la palabra completa ha sido adivinada.
     * @return Retorna true cuando todas las letras han sido adivinadas.
     */
    public boolean palabraAdivinada() {
        for (boolean revelada : letrasReveladas) {
            if (!revelada) {
                return false;  // Si hay alguna letra que no ha sido revelada, la palabra no ha sido adivinada.
            }
        }
        return true;  // Todas las letras han sido adivinadas
    }

    /**
     * Metodo para obtener los errores
     * @return numero de errores
     */
    public int getErrores() {
        return errores;
    }

    /**
     * Metodo para obtener la palabra secreta
     * @return palabra secreta
     */
    public String getPalabraSecreta() {
        return palabraSecreta;
    }

    /**
     * Metodo para obtener el maximo de errores.
     * @return maximo de errores
     */
    public int getMaxErrores() {
        return maxErrores;
    }

    /**
     * Metodo para determinar el fin del juego
     * @return true or false
     */
    public boolean juegoTerminado() {
        return errores >= maxErrores || palabraAdivinada();
    }

    
}