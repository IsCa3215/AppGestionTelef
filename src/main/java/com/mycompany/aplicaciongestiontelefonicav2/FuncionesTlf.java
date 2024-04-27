/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplicaciongestiontelefonicav2;

/**
 *
 * @author EDX
 */
public class FuncionesTlf {

    public static TipoTelefono obtenerTipoTelefono(String numero) {
        if (numero.startsWith("9") || numero.startsWith("8")) {
            return TipoTelefono.FIJO;
        } else if (numero.startsWith("6") || numero.startsWith("7")) {
            return TipoTelefono.MOVIL;
        } else if (numero.startsWith("800") || numero.startsWith("900")) {
            return TipoTelefono.GRATUITO;
        } else if (numero.startsWith("112") || numero.startsWith("0")) {
            return TipoTelefono.GRATUITO_ESPECIAL;
        } else if (numero.startsWith("803") || numero.startsWith("806") || numero.startsWith("807")
                || numero.startsWith("905") || numero.startsWith("907")) {
            return TipoTelefono.COSTE_ADICIONAL;
        } else if (numero.startsWith("901") || numero.startsWith("902")) {
            return TipoTelefono.COSTE_COMPARTIDO;
        } else {
            throw new IllegalArgumentException("No es un número de teléfono válido");
        }
    }

    private static double calcularCosteLlamada(TarifaTelefonica tarifa, TipoTelefono tipo, int duracion) {
        double coste;
        switch (tarifa) {
            case BASICA:
                coste = tipo == TipoTelefono.FIJO ? 0.13 : tipo == TipoTelefono.MOVIL ? 0.22 : 0;
                break;
            case NORMAL:
                coste = tipo == TipoTelefono.FIJO ? 0.1 : tipo == TipoTelefono.MOVIL ? 0.2 : 0.05;
                break;
            case PREMIUM:
                coste = 0;
                break;
            default:
                throw new IllegalArgumentException("Tarifa telefónica no válida.");
        }
        return coste * duracion;
    }

    private boolean verificarPasswd(String passwd) {

        return passwd.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");
    }

    private boolean verificarTitular(String titular) {

        return titular.matches("^.{15,80}$");
    }

    /**
     * @param NIF NIF NIE O CIF a verificar.
     * @return True or False si el NIF, NIE o CIF cumple con la expresión
     * regular.
     */
    private boolean verificarNif(String NIF) {
        boolean nif = NIF.matches("[0-9]{8}[A-Za-z]")
                || // NIF
                NIF.matches("[XYZ][0-9]{7}[A-Za-z]")
                || // NIE
                NIF.matches("[ABCDEFGHJPQRSUVNW][0-9]{7}[A-Za-z]"); // CIF

        return nif;
    }

    /**
     *
     * @param limite limite > 10 && limite < 5000 return true @return Tr
     * ue or False si el limite cumple los parámetros exigidos
     */
    private boolean verificarLimite(int limite) {

        boolean value = false;
        if (limite > 10 && limite < 5000) {
            value = true;
        }
        return value;
    }
    public static boolean comprobarNumeroTelefono(String numero) {
        // Validación de número de teléfono...
        return numero.matches("[689]\\d{8}");
    }
    
}
