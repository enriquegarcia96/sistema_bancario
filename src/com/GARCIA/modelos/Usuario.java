package com.GARCIA.modelos;
import com.GARCIA.libs.Conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Usuario {

    private int codigo;
    private String identidad;
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private String correoElectronico;
    private String telefono;
    private Date creacion;

    //constructor
    public Usuario(String identidad, String nombre, String apellido, String correoElectronico) {
        this.setIdentidad(identidad);
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setCorreoElectronico(correoElectronico);

    }


    public int getCodigo() {
        return codigo;
    }

    public String getIdentidad() {
        return identidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }


    //se va a enccargar si el nombre esta nulo
    public String getNombreUsuario() {
        if (this.nombreUsuario == null) {
            this.nombreUsuario = this.generarNombreUsuario(0);

        }
        return this.nombreUsuario;
    }

    //metodo privado, para uso privado de la clase
    //
    private String generarNombreUsuario(int contador) {
        String temNombreUsuario =
                this.nombre.toLowerCase() + "." + this.apellido.toLowerCase();
        // ^para convertir la cadena de caracter en minuscula
        if (contador > 0) {
            temNombreUsuario += "." + String.valueOf(contador);
        }

        PreparedStatement sentencia = null;
        try {
            sentencia = Conexion.abrirConexion().prepareStatement(
                    "select nombre_usuario,creacion from usuarios where nombre_usuario = ?"
            );
            sentencia.setString(1, temNombreUsuario);
            ResultSet resultado = sentencia.executeQuery();

            boolean encontrado = false;
            while (resultado.next()) {
                encontrado = true;
            }
            if (encontrado) {
                generarNombreUsuario(contador + 1);
            }
        } catch (SQLException e) {
            System.err.println("Error al generar el nombre de usuario " + e.getMessage());
        }
        return temNombreUsuario;
    }


    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public Date getCreacion() {

        return creacion;
    }


    public void setIdentidad(String identidad) {
        this.identidad = identidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    //metodo.
    public boolean guardar() {
        try {
            PreparedStatement sentancia = Conexion.abrirConexion().prepareStatement(
                    "insert into usuario (identidad, nombre_usuario, primer_nombre, apellido, correo_electronico, telefono)" +
                            " values (?,?,?,?,?,?)"

            );
            sentancia.setString(1, this.getIdentidad());
            sentancia.setString(2, this.getNombreUsuario());
            sentancia.setString(3, this.getNombre());
            sentancia.setString(4, this.getApellido());
            sentancia.setString(5, this.getCorreoElectronico());
            sentancia.setString(6, this.getTelefono());
            return sentancia.execute();
            //boolean res = sentancia.execute();
            //return res;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }


    public static ArrayList<Usuario> getUsuarios(int limiteDeRegistro) {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        PreparedStatement sentencia = null;
        try {
            sentencia = Conexion.abrirConexion().prepareStatement(
                    "select * from usuario limit "+limiteDeRegistro+" "
            );
            ResultSet resultado = sentencia.executeQuery();
            //sentencia.setMaxRows(limiteDeRegistro);

            while (resultado.next()) {
                Usuario usuario = new Usuario(
                        resultado.getString("identidad"),
                        resultado.getString("nombre_usuario"),
                        resultado.getString("apellido"),
                        resultado.getString("correo_electronico"));
                usuario.codigo = resultado.getInt("codigo");
                usuario.nombreUsuario = resultado.getString("nombre_usuario");
                usuario.creacion = resultado.getDate("creacion");
                listaUsuarios.add(usuario);

            }
        } catch (SQLException e) {
            System.err.println("algo salio mal " + e.getMessage());
        }
        return listaUsuarios;
    }


    public static ArrayList<Usuario> getUsuario(){
        return getUsuario();

    }
}

