package com.mycompany.aplicaciongestiontelefonicav2;

import java.time.LocalDateTime;

public class Llamada {
    private int duracion;
    private String destino;
    private LocalDateTime fecha;

    public Llamada(int duracion, String destino) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración debe ser un entero positivo.");
        }
        if (!destino.matches("[689]\\d{8}")) {
            throw new IllegalArgumentException("El destino no es un número de teléfono válido.");
        }
        this.duracion = duracion;
        this.destino = destino;
        this.fecha = LocalDateTime.now();
    }

    public Llamada(Llamada ll) {
        this.duracion = ll.duracion;
        this.destino = ll.destino;
        this.fecha = ll.fecha;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setDuracion(int duracion) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración debe ser un entero positivo.");
        }
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "Llamada{" +
                "duracion=" + duracion +
                ", destino='" + destino + '\'' +
                ", fecha=" + fecha +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Llamada llamada = (Llamada) o;
        return duracion == llamada.duracion &&
                destino.equals(llamada.destino) &&
                fecha.equals(llamada.fecha);
    }
}
