/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.relogio.exceptions;

/**
 *Exceção para falhas no enviou de mensagens
 * @author emerson
 */
public class FalhaNoEnvioDaMensagem extends Exception{
    
    @Override
    public String getMessage(){
        return "falha no envio da mensagem";
    }
    
}
