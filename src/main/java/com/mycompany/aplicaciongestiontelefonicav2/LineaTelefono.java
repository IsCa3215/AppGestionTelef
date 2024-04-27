package com.mycompany.aplicaciongestiontelefonicav2;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class LineaTelefono {
    private String titular;
    private String nif;
    private String contraseña;
    private int limite;
    private int mesPermanencia;
    private int añoPermanencia;
    private String numeroTelefono;
    private ArrayList<Llamada> listaLlamadas;
    private TarifaTelefonica tarifa;

 

    /**
     * Constructor con parametros
     * Crea una línea de telefono nueva con varios parámetros
     * @see LineaTelefono() Constructor el cual crea una nueva línea de teléfono
     * @param titular        STRING titular de la linea(mí 15 carácteres, max 80)
     * @param nif            STRING NIF, NIE, CIF del titular de la línea
     * @param password       STRING Contraseña para acceder a los servicios de la
     *                       línea(Debe contenter al menos: 1 MAY, 1MIN, 1car especial y 1 número)
     * @param limite         INT Importe máximo que puede gastar en un mes(mínimo 10, máximo 5000)
     * @param numeroTelefono STRING Numero de teléfono asignado a la línea
     * @param tarifa         TarifaFunct Que tarifa tiene contratada la linea
     *                       telefónica (BASICA, NORMAL o PREMIUM)
     * @throws IllegalArgumentException Se lanza una excepción cuando uno de los parámetros no se han introducido correctamente
     */

    public LineaTelefono(String titular, String nif, String password, int limite, String numeroTelefono, TarifaTelefonica tarifa) {
        // COMPROBACIONES
        // FIN COMPROBACIONES
        this.titular = titular;
        this.nif = nif.toUpperCase();
        this.contraseña = contraseña;
        this.limite = limite;
        this.numeroTelefono = numeroTelefono;
        this.tarifa = tarifa;
        this.mesPermanencia = LocalDateTime.now().getMonthValue();
        this.añoPermanencia = LocalDateTime.now().getYear() + 2;
        this.listaLlamadas = new ArrayList<>();
        
    }
    /**
     * El constructor copia de LineaTelefono()
     * @see LineaTelefono(LineaTelefono lt) Constructor copia
     * @param lt (La línea a la que se le va a hacer la copia)
     */
  public LineaTelefono(LineaTelefono linea) {
        this.titular = linea.titular;
        this.nif = linea.nif;
        this.contraseña = linea.contraseña;
        this.limite = linea.limite;
        this.numeroTelefono = linea.numeroTelefono;
        this.tarifa = linea.tarifa;
        this.mesPermanencia = linea.mesPermanencia;
        this.añoPermanencia = linea.añoPermanencia;
        this.listaLlamadas = new ArrayList<>(linea.listaLlamadas);
    }

    public int numeroLlamadas() {
        // Contar el número de llamadas realizadas...
        return listaLlamadas.size();
    }

    /**
     * 
     * @param duracion duración de la llamada en formato minutos:segundos (mm:ss)
     * @param destino  Teléfono al que se realiza la llamada
     * @return true or false si se ha podido realizar la llamada
     */
      public boolean llamar(int duracion, String destino) {
        // Validaciones y realización de llamada...
        if (duracion <= 0 || !comprobarNumeroTelefono(destino)) {
            return false;
        }
        if (gastado() + calcularCosteLlamada(tarifa, obtenerTipoTelefono(destino), duracion) > limite) {
            return false;
        }
        listaLlamadas.add(new Llamada(duracion, destino));
        return true;
    }
public double gastado() {
    // Cálculo del gasto total...
    double totalGastado = 0.0;
    for (Llamada llamada : listaLlamadas) {
        totalGastado += (int) calcularCosteLlamada(tarifa, obtenerTipoTelefono(llamada.getDestino()), llamada.getDuracion());
    }
    return totalGastado;
}





    // VERIFICACIONES

    @Override
    public String toString() {
        return "LineaTelefono{" +
                "titular='" + titular + '\'' +
                ", nif='" + nif + '\'' +
                ", limite=" + limite +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                ", tarifa=" + tarifa +
                '}';
    }



    public static TipoTelefono obtenerTipoTelefono(String numero) {
        if (numero.startsWith("9") || numero.startsWith("8")) {
            return TipoTelefono.FIJO;
        } else if (numero.startsWith("6") || numero.startsWith("7")) {
            return TipoTelefono.MOVIL;
        } else if (numero.startsWith("800") || numero.startsWith("900")) {
            return TipoTelefono.GRATUITO;
        } else if (numero.startsWith("112") || numero.startsWith("0")) {
            return TipoTelefono.GRATUITO_ESPECIAL;
        } else if (numero.startsWith("803") || numero.startsWith("806") || numero.startsWith("807") ||
                numero.startsWith("905") || numero.startsWith("907")) {
            return TipoTelefono.COSTE_ADICIONAL;
        } else if (numero.startsWith("901") || numero.startsWith("902")) {
            return TipoTelefono.COSTE_COMPARTIDO;
        } else {
            throw new IllegalArgumentException("No es un número de teléfono válido");
        }
    }
    /**
     * 
     * @param tarifa Enum te TarifaTelefonica (BASICA, NORMAL...)
     * @param tipo Enum de TipoTelefono (FIJO, MOVIL...)
     * @param duracion Duración de la llamada en segundos(ss)
     * @return Coste de la llamada
     */

    private static double calcularCosteLlamada(TarifaTelefonica tarifa, TipoTelefono tipo, int duracion) {
        // Cálculo del coste de una llamada...
        double coste;
        switch (tarifa) {
            case BASICA:
                coste = tipo == TipoTelefono.FIJO ? 0.13 : tipo == TipoTelefono.MOVIL ? 0.22 : 0;
                break;
            case NORMAL:
                coste = tipo == TipoTelefono.FIJO ? 0.1 : tipo == TipoTelefono.MOVIL ? 0.2 : 0.05;
                break;
            case PREMIUM:
                coste = 0; // No hay coste para llamadas premium
                break;
            default:
                throw new IllegalArgumentException("Tarifa telefónica no válida.");
        }
        return coste * duracion;
    }
        /**
     * @see verificarPasswd() verifica que la contraseña cumpla todos los requisitos
     * @param passwd Contraseña a comprobar
     * @return true or false dependiendo si cumple o no los requisitos
     */
    private boolean verificarPasswd(String passwd) {
        
        return passwd.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");
    }
    /**
     * @see VerificarTitular() [Tiene que ser una longitud de entre 15 y 80 carácteres]
     * @param titular Titular de la línea
     * @return True or False si el titular cumple con la expresión regular
     */
    private boolean verificarTitular(String titular) {
 

        return titular.matches("^.{15,80}$");
    }
    /**
     * @param NIF NIF NIE O CIF a verificar.
     * @return True or False si el NIF, NIE o CIF cumple con la expresión regular.
     */
    private boolean verificarNif(String NIF) {
        boolean nif = NIF.matches("[0-9]{8}[A-Za-z]") || // NIF
                NIF.matches("[XYZ][0-9]{7}[A-Za-z]") || // NIE
                NIF.matches("[ABCDEFGHJPQRSUVNW][0-9]{7}[A-Za-z]"); // CIF

        return nif;
    }
    /**
     * 
     * @param limite limite > 10 && limite < 5000 return true
     * @return True or False si el limite cumple los parámetros exigidos
     */
    private boolean verificarLimite(int limite) {
        
        boolean value = false;
        if (limite > 10 && limite < 5000) {
            value = true;
        }
        return value;
    }
    /**
     * Método para consultar las llamadas de la línea
     * @return String de Llamadas
     */
    public String llamadas(int numero) {
        // Obtener información sobre las últimas llamadas...
        if (numero <= 0 || numero > listaLlamadas.size()) {
            return null;
        }
        StringBuilder llamadasInfo = new StringBuilder();
        for (int i = Math.max(0, listaLlamadas.size() - numero); i < listaLlamadas.size(); i++) {
            llamadasInfo.append(listaLlamadas.get(i).toString()).append("\n");
        }
        return llamadasInfo.toString();
    }
/**
 * Elmimina la línea de teléfno consultando el dni del titulae
 * @param lineas
 * @param nif
 */
    public static void eliminarLineaTelefono(LineaTelefono[] lineas, String nif) {
        for (int i = 0; i < lineas.length; i++) {
            if (lineas[i] != null && lineas[i].getNif().equals(nif)) {
                lineas[i] = null;
                System.out.println("Línea de teléfono eliminada correctamente.");
                return;
            }
        }
        throw new IllegalArgumentException("La línea de teléfono no existe o no se encuentra");
    }


    public static boolean comprobarNumeroTelefono(String numero) {
        // Validación de número de teléfono...
        return numero.matches("[689]\\d{8}");
    }
    // FIN VERIFICACIONES
    public ArrayList<Llamada> getArrLlamadas(){
        return listaLlamadas;
    }
    // GETTERS
    public String getTitular() {
        return titular;
    }

    public int getLimite() {
        return limite;
    }

    public String getNif() {
        return nif;
    }

    public String getContraseña() {
        return contraseña;
    }
 public int getMesPermanencia() {
        return mesPermanencia;
    }

    public int getAñoPermanencia() {
        return añoPermanencia;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }
    public TarifaTelefonica getTarifa() {
        return this.tarifa;
    }
    public TarifaTelefonica getTarifaa(){
        return this.tarifa;
    }
   
    // Fin GETTERS

    // SETTERS
  public boolean setContraseña(String contraseña) {
      boolean res = false;
        if(verificarPasswd(contraseña)){
             this.contraseña = contraseña;
             res = true;
        }
       return res;
        
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public void setTarifa(TarifaTelefonica tarifa) {
        this.tarifa = tarifa;
    }

    public void setMesPermanencia(int mesPermanencia) {
        this.mesPermanencia = mesPermanencia;
    }

    public void setAñoPermanencia(int añoPermanencia) {
        this.añoPermanencia = añoPermanencia;
    }
    
        @Override
    public boolean equals(Object o) {
        // Método equals...
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineaTelefono that = (LineaTelefono) o;
        return nif.equals(that.nif);
    }
}