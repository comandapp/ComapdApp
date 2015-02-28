/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package serviceclient;

import objetos.Bar;

/**
 *
 * @author G62
 */
public class main {
    public static void main(String args[]) {
        Bar b = ServiceClient.getBar(1);
        System.out.println(b.getCarta().getEntrada(0).getProducto().getNombre());
        System.out.println(b.getCarta().getEntrada(0).getProducto().getId());
        System.out.println(b.getCarta().getEntrada(0).getProducto().getCategoria());
//        String s = ServiceClient.encodeImage("coins.png");
//        System.out.println(s);
    }
}
