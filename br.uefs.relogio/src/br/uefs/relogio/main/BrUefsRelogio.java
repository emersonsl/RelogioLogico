/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.relogio.main;

import br.uefs.relogio.view.AlterarHorario;

/**
 *Classe responsavel por executar a aplicação
 * @author emerson
 */
public class BrUefsRelogio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         
        new AlterarHorario().show(); //inicia a interface a aplicação
    }
}
