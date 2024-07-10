/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chatbotty;

import javax.swing.SwingUtilities;

/**
 *
 * @author saif
 */
public class Techy {
    private static UserAuthenticationGUI userAuthGUI;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                userAuthGUI = new UserAuthenticationGUI(); 
                userAuthGUI.setVisible(true);
            }
        });
    }    
}
