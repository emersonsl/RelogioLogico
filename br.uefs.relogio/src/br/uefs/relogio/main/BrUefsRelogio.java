/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.relogio.main;

import br.uefs.relogio.control.Controller;
import br.uefs.relogio.view.AlterarHorario;
import javax.swing.JOptionPane;

/**
 *
 * @author emerson
 */
public class BrUefsRelogio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*String horario = JOptionPane.showInputDialog("Digite uma horario no formato 00:00:00"); //recebe a hora da janela
        String [] horarioS = horario.split(":"); //separa a hora, minutos e segundos
        
        int hora = Integer.parseInt(horarioS[0]);
        int minuto = Integer.parseInt(horarioS[1]);
        int segundo = Integer.parseInt(horarioS[2]);
        
        String id = JOptionPane.showInputDialog(null, "insira o id");
        
        int id1 = Integer.parseInt(id);
        
        Controller c = Controller.getInstance();
        c.criarRelogio(hora, minuto, segundo, 1);
        c.setId(id1);*/
        
        new AlterarHorario().show();
    }
}
