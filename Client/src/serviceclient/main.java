/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package serviceclient;

import java.util.ArrayList;
import java.util.HashMap;
import objetos.Bar;
import objetos.Carta;

/**
 *
 * @author G62
 */
public class main {
    public static void main(String args[]) {
        int[] a = new int[]{1};
        
        Bar bar1 = new Bar(1,"Nuevo Bar","Direccion nueva",6666666,"correo@nuevo.com",6.66,6.66,"Nueva provincia","VillaNueva",1);
        Bar noBar = new Bar(5,"No Bar","No nueva",00000,"No@No.No",0,0,"No provincia","VillaNo",1);
        
        System.out.println("--Version1--");
        System.out.println(bar1.getId());
        System.out.println(bar1.getNombre());
        System.out.println(bar1.getCorreo());
        System.out.println(bar1.getDireccion());
        System.out.println(bar1.getLatitud());
        System.out.println(bar1.getLongitud());
        System.out.println(bar1.getTelefono());
        System.out.println("vInfo: "+bar1.getVersion().getVersionInfoLocal());
        System.out.println("vCar: "+bar1.getVersion().getVersionCarta());
        System.out.println("vOfe: "+bar1.getVersion().getVersionOfertas());
        System.out.println("NumEntradasCarta: "+bar1.getCarta().numEntradas());
        System.out.println("NumOfertas: "+bar1.getOfertas().size());
        
        ArrayList<Bar> baresActualizar = new ArrayList<Bar>();
        baresActualizar.add(bar1);
        baresActualizar.add(noBar);
        
        HashMap<Integer,String> errores = ServiceClient.actualizarInfoBar(baresActualizar);
        //No actualiza carta.
        HashMap<Integer,String> errores2 = ServiceClient.actualizarCarta(baresActualizar);
        HashMap<Integer,String> errores3 = ServiceClient.actualizarOfertas(baresActualizar);

        System.out.println("--Version2--");
        System.out.println(bar1.getId());
        System.out.println(bar1.getNombre());
        System.out.println(bar1.getCorreo());
        System.out.println(bar1.getDireccion());
        System.out.println(bar1.getLatitud());
        System.out.println(bar1.getLongitud());
        System.out.println(bar1.getTelefono());
        System.out.println("vInfo: "+bar1.getVersion().getVersionInfoLocal());
        System.out.println("vCar: "+bar1.getVersion().getVersionCarta());
        System.out.println("vOfe: "+bar1.getVersion().getVersionOfertas());
        System.out.println("NumEntradasCarta: "+bar1.getCarta().numEntradas());
        System.out.println("NumOfertas: "+bar1.getOfertas().size());
        
        System.out.println("Errores 404 InfoBar: "+errores.size());
        System.out.println("Errores 404 Carta: "+errores.size());
        System.out.println("Errores 404 Ofertas: "+errores.size());
    }
}
