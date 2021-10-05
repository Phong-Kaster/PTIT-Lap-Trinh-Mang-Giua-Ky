/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ltm_thitracnghiem;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ngdanghau
 */
public class Utils {
    public static void enableAll(JPanel p, boolean enabled) {
        for (Component comp : p.getComponents()) {
            if(!(comp instanceof JLabel)){
                comp.setEnabled(enabled);
            } 
        }
    }
}
