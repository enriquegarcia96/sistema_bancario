package com.GARCIA;

import com.GARCIA.modelos.Usuario;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        int mostrarLimite = LectorDeDatos.solicitarEntero("Ingrese un numero: ");
        ArrayList<Usuario> lista = Usuario.getUsuarios(mostrarLimite);


        for (Usuario u : lista){
            System.out.println(u.getNombreUsuario());
        }
    }
}
