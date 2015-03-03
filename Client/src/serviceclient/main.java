/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package serviceclient;

import java.util.ArrayList;
import java.util.HashMap;
import objetos.Bar;
import objetos.Oferta;

/**
 *
 * @author G62
 */
public class main {
    public static void main(String args[]) {
        int[] a = new int[]{1,2};
        ArrayList<Bar> bares = ServiceClient.getInfoBar(a);
                System.out.println(bares.toString());
        HashMap<Integer,Oferta> ofertas = ServiceClient.getOfertas(a);
        

        System.out.println(ofertas.toString());
//        String s = ServiceClient.encodeImage("coins.png");
//        System.out.println(s);
    }
}
