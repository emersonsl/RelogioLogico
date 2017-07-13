/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.relogio.comunication;

import br.uefs.relogio.exceptions.FalhaAoCriarGrupoException;
import br.uefs.relogio.exceptions.FalhaNoEnvioDaMensagem;
import br.uefs.relogio.exceptions.FalhaRecepcaoMensagemException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emerson
 */
public abstract class Multicast {
    private static final String endereco = "235.0.0.10";
    private static final int porta = 3050;
    
    private static MulticastSocket multicast;
    private static Thread monitor;
    
    /**
     * Entra no grupo multicast
     * @throws IOException 
     */
    public static void entrarGrupo() throws IOException{
        multicast = new MulticastSocket(porta);
        multicast.joinGroup(InetAddress.getByName(endereco));
        monitorMensagem();
    }
    
    /**
     * Sai do grupo multicast
     */
    public static void sairGrupo(){
        multicast.disconnect();
        multicast.close();
        multicast  = null;
    }
    
    /**
     * Recebe mensagem do grupo multicast
     * @return 
     */
    private static void receberMensagem() throws FalhaRecepcaoMensagemException{
        String mensagem;

        byte buff[] = new byte[1024]; //cria o buffer
        DatagramPacket pacote = new DatagramPacket(buff, buff.length); //cria o pacote 
        try {
            multicast.receive(pacote); //recebe a mensagem do grupo
            mensagem = new String(pacote.getData());
            Protocolo.receberMensagem(mensagem); //passa a mensagem para o protocolo
        } catch (IOException ex) {
            throw new FalhaRecepcaoMensagemException();
        }
    }
    
    /**
     * Monitora a recepção de mensagens do grupo
     */
    public static void monitorMensagem(){
        if (grupoAvailable()) {
            monitor = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            receberMensagem();
                        } catch (FalhaRecepcaoMensagemException ex) {
                            Logger.getLogger(Multicast.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            monitor.start();
        }
    }
    
    /**
     * Envia mensagem para o grupo multicast
     * @param mensagem
     * @throws FalhaNoEnvioDaMensagem
     * @throws FalhaAoCriarGrupoException 
     */
    public static void enviarMensagem(String mensagem) throws FalhaNoEnvioDaMensagem, FalhaAoCriarGrupoException{
        if(!grupoAvailable()){ //verifica se o grupo foi criado
            try {
                entrarGrupo();
            } catch (IOException ex) {
                throw new FalhaAoCriarGrupoException();
            }
        }
        
        
        try {
            DatagramPacket pacote = new DatagramPacket(mensagem.getBytes(), mensagem.length(),
                    InetAddress.getByName(endereco), porta); //criando pacote
            multicast.send(pacote); //enviando pacote
        } catch (UnknownHostException ex ) { //falha ao criar pacote
            throw new FalhaNoEnvioDaMensagem();
        } catch (IOException ex1) { // falha no envio
            throw new FalhaNoEnvioDaMensagem();
        }        
    }
    
    /**
     * Verifica se o grupo foi criado
     * @return 
     */
    private static  boolean grupoAvailable(){
        return multicast != null;
    }
    
}
