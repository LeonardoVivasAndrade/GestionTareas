/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ACER
 */
public class Conexion {
    private static Conexion conexion;
    private EntityManagerFactory bd; 
 
    public Conexion() {
        this.bd=Persistence.createEntityManagerFactory("TareasPU");
         
    }
        
    public static Conexion getConexion()
    {
        if(conexion==null)
        {
            conexion=new Conexion();
        } System.out.println("La conexion se ha realizado");
    return conexion;
    }
 
    public EntityManagerFactory getBd() {
        return bd;
    }
}
