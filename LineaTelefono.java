package com.mycompany.aplicaciongestiontelefonicav2;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class LineaTelefono {
    private String titular;
    private String numeroTelefono;
    private String nif;
    private String password;
    private int limite;
    private static double coste = 0;
    private DateTimeFormatter formatear = DateTimeFormatter.ofPattern("MMM");
    private LocalDate añoPermanencia = LocalDate.now();
    private LocalDate mesPermanencia = LocalDate.now();
    
    private Llamada[] llamadas_arr = new Llamada[50];

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

    public LineaTelefono(String titular, String nif, String password, int limite, String numeroTelefono,
            TarifaTelefonica tarifa) {
        // COMPROBACIONES
        if (!verificarTitular(titular)) {
            throw new IllegalArgumentException("\u001B[31m" + "No se han cumplido los requisitos del campo titular" + "\u001B[0m");
        }
        if (!verificarNif(nif)) {
            throw new IllegalArgumentException("\u001B[31m" + "No se ha introducido un NIF válido" + "\u001B[0m");
        }
        if (!verificarPasswd(password)) {
            throw new IllegalArgumentException("\u001B[31m" + "La contraseña no cumple los requisitos del campo" + "\u001B[0m");
        }
        if (!verificarLimite(limite)) {
            throw new IllegalArgumentException("\u001B[31m" + "El limite de la tarifa no cumple." + "\u001B[0m");
        }
        if (!verificarNumero(numeroTelefono)) {
            throw new IllegalArgumentException("\u001B[31m" + "El número no es válido" + "\u001B[0m");
        }
        // FIN COMPROBACIONES
        
        this.titular = titular;
        this.nif = nif;
        this.password = password;
        this.limite = limite;
        this.numeroTelefono = numeroTelefono;
        this.tarifa = tarifa;
        this.añoPermanencia = añoPermanencia.plusYears(2);   
        
    }
    /**
     * El constructor copia de LineaTelefono()
     * @see LineaTelefono(LineaTelefono lt) Constructor copia
     * @param lt (La línea a la que se le va a hacer la copia)
     */
    public LineaTelefono(LineaTelefono lt) {
        this.titular = lt.titular;
        this.nif = lt.nif;
        this.password = lt.password;
        this.limite = lt.limite;
        this.numeroTelefono = lt.numeroTelefono;
        this.tarifa = lt.tarifa;
    }

    public int numeroLlamadas() {
        int count = 0;
        for (Llamada llamada : llamadas_arr) {
            if (llamada != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * 
     * @param duracion duración de la llamada en formato minutos:segundos (mm:ss)
     * @param destino  Teléfono al que se realiza la llamada
     * @return true or false si se ha podido realizar la llamada
     */
    public boolean Llamar(double duracion, String destino) {
        if (!verificarNumero(destino)) {
            throw new IllegalArgumentException("\u001B[31m" + "El número introducido no es válido" + "\u001B[0m");
        }
        if (duracion <= 0) {
            throw new IllegalArgumentException("\u001B[31m" + "La duración de la llamada no es válida" + "\u001B[0m");
        }
        for (int i = 0; i < llamadas_arr.length; i++) {
            if (llamadas_arr[i] == null) {
                llamadas_arr[i] = new Llamada(duracion, destino);
                break; 
            }
        }
        comprobarNumeroTelefono(destino);

        calcularCosteLlamada(tarifa, comprobarNumeroTelefono(this.numeroTelefono), 12);
        // FUNCTION calcularCosteLlamada()
        return true;
    }

    // VERIFICACIONES

    @Override
    public String toString() {
        return "___________________________________________"+"\n"
        +"Nueva línea de teléfono:" +"\n"+"titular: " + titular +"\n"+ "numeroTelefono: " + numeroTelefono +"\n"+ "NIF: " + nif +"\n"
                + "contraseña: " + password +"\n"+ "limite: " + limite + "\n"+"añoPermanencia: " + añoPermanencia +"\n"
                + "mesPermanencia: " + mesPermanencia.format(formatear) +"\n"+ "tarifa: " + tarifa + "\n"+
                "___________________________________________";
    }



    public static TipoTelefono comprobarNumeroTelefono(String numero) {
        TipoTelefono retorno = null;
        if (numero.matches("[89][1-8][0-9]{7}")) {
            retorno = TipoTelefono.FIJO;
        } else if (numero.matches("[6][0-9]\\d{7}|[7][1234]\\d{7}")) {
            retorno = TipoTelefono.MOVIL;
        } else if (numero.matches("[89][0][0][0-9]{6}")){
            retorno = TipoTelefono.GRATUITO;
        } else if (numero.matches("112|[0][0-9]{2}")){
            retorno = TipoTelefono.GRATUITO_ESPECIAL;
        } else if (numero.matches("[8][0][367][0-9]{6}|[9][0][57][0-9]{6}")){
            retorno = TipoTelefono.COSTE_ADICIONAL;
        } else if (numero.matches("[9][0][12][0-9]{6}")) {
            retorno = TipoTelefono.COSTE_COMPARTIDO;
        }
        if (retorno == null) {
            throw new IllegalArgumentException("El número no es válido");
        }
        return retorno;
    }
    /**
     * 
     * @param tarifa Enum te TarifaTelefonica (BASICA, NORMAL...)
     * @param tipo Enum de TipoTelefono (FIJO, MOVIL...)
     * @param duracion Duración de la llamada en segundos(ss)
     * @return Coste de la llamada
     */

     public static double calcularCosteLlamada(TarifaTelefonica tarifa, TipoTelefono tipo, double duracion) {
        double coste = 0;
        switch (tipo) {
            case FIJO:
                if (tarifa == TarifaTelefonica.BASICA) {
                    coste = 0.13 * duracion;
                } else if (tarifa == TarifaTelefonica.NORMAL) {
                    coste = 0.1 * duracion;
                } else {
                    coste = 0 * duracion;
                }
                break;
            case MOVIL:
                if (tarifa == TarifaTelefonica.BASICA) {
                    coste = 0.22 * duracion;
                } else if (tarifa == TarifaTelefonica.NORMAL) {
                    coste = 0.2 * duracion;
                } else {
                    coste = 0 * duracion;
                }
                break;
            case GRATUITO:
                coste = 0;
                break;
            case COSTE_ADICIONAL:
                coste = 0.30 * duracion;
                break;
            case COSTE_COMPARTIDO:
                coste = 0.15 * duracion;
                break;
            default:
                throw new IllegalArgumentException("Error en el cálculo del costo de la llamada");
        }
        return coste;
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
    public String consultarLlamadas() {
        StringBuilder llamadasInfo = new StringBuilder();
        for (Llamada llamada : llamadas_arr) {
            if (llamada != null) {
                llamadasInfo.append(llamada.toString()).append("\n");
            }
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


    private boolean verificarNumero(String numero) {
        // Añadir comprobación de número tlf
        boolean verificar = false;
        if (numero.charAt(0) == '9' || numero.charAt(0) == '8' || numero.charAt(0) == '6' || numero.charAt(0) == '7') {
            verificar = true;
        }
        return verificar;
    }
    // FIN VERIFICACIONES

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

    public String getPassword() {
        return password;
    }
    public LocalDate getAñoPermanencia() {
        return añoPermanencia;
    }
    public LocalDate getMesPermanencia() {
        return mesPermanencia;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }
    public String getTarifa() {
        return this.tarifa.toString();
    }
    public Llamada[] getLlamadas_arr() {
        return llamadas_arr;
    }
    // Fin GETTERS

    // SETTERS
    public void setPassword(String password) {
        this.password = password;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public void setTarifa(TarifaTelefonica tarifa) {
        this.tarifa = tarifa;
    }
}