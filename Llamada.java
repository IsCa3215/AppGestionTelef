/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplicaciongestiontelefonicav2;

/**
 *
 * @author EDX
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Llamada {
    private double duracion;
    private String destino;
    private Calendar fecha = Calendar.getInstance();
    private SimpleDateFormat formater = new SimpleDateFormat("dd MMMMMMMMMMMMM yyyy HH:mm:ss");

    /**
     * Constructor de la clase Llamada.
     * 
     * @param duracion Duración de la llamada.
     * @param destino  Destino de la llamada (número de teléfono).
     * @throws IllegalArgumentException Se produce cuando la duración de la
     *                                  llamada no es válida.
     */
    public Llamada(double duracion, String destino) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración de la llamada no es válida");
        }
        
        this.duracion = duracion;
        this.destino = destino;
    }

    /**
     * Constructor de copia de la clase Llamada.
     * 
     * @param ll Objeto Llamada a copiar.
     */
    public Llamada(Llamada ll) {
        this.destino = ll.destino;
        this.duracion = ll.duracion;
        this.fecha = (Calendar) ll.fecha.clone(); // Se crea una copia de la fecha para evitar modificar el original.
    }

    /**
     * Obtiene el destino de la llamada.
     * 
     * @return El destino de la llamada.
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Obtiene la duración de la llamada.
     * 
     * @return La duración de la llamada.
     */
    public double getDuracion() {
        return duracion;
    }

    /**
     * Obtiene la fecha de la llamada.
     * 
     * @return La fecha de la llamada.
     */
    public Calendar getFecha() {
        return fecha;
    }

    /**
     * Establece la duración de la llamada.
     * 
     * @param duracion La duración de la llamada.
     */
    public void setDuracion(double duracion) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración de la llamada no es válida");
        }
        this.duracion = duracion;
    }

    /**
     * Devuelve una representación en cadena de la llamada.
     */
    @Override
    public String toString() {
        return "[Llamada realizada]" + "\n" + "duracion: " + duracion + "\n" + "destino: " + destino + "\n"
                + ", fecha: " + formater.format(fecha.getTime());
    }
}
