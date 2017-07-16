/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.relogio.exceptions;

/**
 *Exceceção para erro de aguardar eleição
 * @author emerson
 */
public class ErroNaThreadAguardarEleicao extends Exception{

    @Override
    public String getMessage(){
        return "Erro ao aguardar eleição";
    }
}
