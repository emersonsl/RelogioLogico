/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.relogio.comunication;

import br.uefs.relogio.control.Controller;
import br.uefs.relogio.exceptions.FalhaAoCriarGrupoException;
import br.uefs.relogio.exceptions.FalhaNoEnvioDaMensagem;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emerson
 */
public abstract class Protocolo {
    private static Controller controller = Controller.getInstance();
    
    /*codigos do protocolo*/
    private static final int enviarHorarioPorEleicao = 1;
    private static final int enviarHorarioPorCoordenacao = 2;
    private static final int solicitarEleicao = 3;
    private static final int prox = 4;
    
    
    /**
     * Recebe a mensagem do grupo multicast
     * @param mensagem 
     */
    public static void receberMensagem(String mensagem){
        System.out.println(mensagem);
        selectMensagem(mensagem);
        System.out.println(mensagem);
    }
    
    /**
     * Executa a ação com base na mensagem
     * @param mensagem 
     */
    private static void selectMensagem(String mensagem){
        String [] msg = mensagem.split(";");
        int opcao = Integer.parseInt(msg[1]);
        //int opcao = Integer.parseInt(mensagem.split(";")[1]); //verifica qual o codigo da mensagem
        
        switch(opcao){
            case enviarHorarioPorEleicao:
                receberHorarioPorEleicao(mensagem);
                break;
            case enviarHorarioPorCoordenacao:
                receberHorarioCoordenadocao(mensagem);
            case solicitarEleicao:
                receberSolicitacaoDeEleicao();
                break;
        }
    }
    
    /*************************METODOS PARA ENVIAR MENSAGENS************************************/
    
    public static void solicitarEleicao(int id) throws FalhaNoEnvioDaMensagem, FalhaAoCriarGrupoException{
        Multicast.enviarMensagem(id + ";" + solicitarEleicao + ";");
    }
    /**
     * Envia o horario para o grupo multicast durante a eleição
     * @param horario horario do meu relogio
     * @throws FalhaNoEnvioDaMensagem
     * @throws FalhaAoCriarGrupoException 
     */
    public static void enviarHorarioPorEleicao(int id, String horario) throws FalhaNoEnvioDaMensagem, FalhaAoCriarGrupoException{
        Multicast.enviarMensagem(id + ";" + enviarHorarioPorEleicao+ ";" +horario + ";");
    }
    
    /**
     * Se este relogio for o coordenador envia esse horario ao grupo.
     * @param id
     * @param horario
     * @throws FalhaNoEnvioDaMensagem
     * @throws FalhaAoCriarGrupoException 
     */
    public static void enviarHorarioPorCoordenacao(int id, String horario) throws FalhaNoEnvioDaMensagem, FalhaAoCriarGrupoException{
        Multicast.enviarMensagem(id + ";" + enviarHorarioPorCoordenacao + ";" + horario + ":" + ";");
    }
    /************************METODOS PARA RECEBER MENSAGENS************************************/
    
    /**
     * Recebe horario do grupo multicast quando acontece uma eleição
     * @param msg 
     */
    public static void receberHorarioPorEleicao(String mensagem){
        
        String [] msg = mensagem.split(";");
        
        int id = Integer.parseInt(msg[0]);
        
        String [] horario = msg[2].split(":");

        int hora = Integer.parseInt(horario[0]);
        int minuto = Integer.parseInt(horario[1]);
        int segundo = Integer.parseInt(horario[2]);

        controller.receberHorarioEleicao(id, hora, minuto, segundo);
    }
    
    /**
     * Recebe o horario do coordenador para atualização
     * @param mensagem 
     */
    public static void receberHorarioCoordenadocao(String mensagem){
 
        String [] msg = mensagem.split(";");
        
        int id = Integer.parseInt(msg[0]);
        
        if(!controller.isMyId(id)){ //verifica se não é o coordenador
            
            String [] horario = msg[2].split(":");
        
            int hora = Integer.parseInt(horario[1]);
            int minuto = Integer.parseInt(horario[2]);
            int segundo = Integer.parseInt(horario[3]);

            controller.atualizarRelogio(hora, minuto, segundo);
        }
    }
    
    /**
     * Recebe uma solicitacao de eleicao e envia o seu horario
     */
    public static void receberSolicitacaoDeEleicao(){
        controller.enviarHorarioEleicao();
    }
}
