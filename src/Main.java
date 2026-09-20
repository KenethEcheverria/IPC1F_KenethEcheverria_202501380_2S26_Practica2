// Clase principal que arranca el juego

import vista.MenuPrincipal;
import javax.swing.*;

public class Main {
    public static void main(String [] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MenuPrincipal menu=new MenuPrincipal();
                menu.setVisible(true);
            }
        });
    }
}