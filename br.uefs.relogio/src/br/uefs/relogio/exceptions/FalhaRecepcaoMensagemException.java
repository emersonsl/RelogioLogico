/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.relogio.exceptions;

/**
 *Execeção para falhas no recebimento de mensagens 
 * @author emerson
 */
public class FalhaRecepcaoMensagemException extends Exception{
    
    @Override
    public String getMessage(){
        return "Falha ao receber Mensagem";
    }
}
