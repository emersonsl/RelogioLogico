/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.relogio.exceptions;

/**
 *Exceção para falhas ao criar o grupo multicast
 * @author emerson
 */
public class FalhaAoCriarGrupoException extends Exception{
    
    @Override
    public String getMessage(){
        return "Falha ao criar o grupo multicast";
    }
}
