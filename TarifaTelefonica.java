/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplicaciongestiontelefonicav2;

/**
 *
 * @author EDX
 */
public enum TarifaTelefonica {
        BASICA(1),
        NORMAL(2),
        PREMIUM(3);

        int valor;
        TarifaTelefonica(int valor){
            this.valor = valor;
        }
    }


