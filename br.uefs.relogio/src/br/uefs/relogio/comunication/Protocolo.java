/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.relogio.comunication;

import br.uefs.relogio.control.Controller;
import br.uefs.relogio.exceptions.FalhaAoCriarGrupoException;
import br.uefs.relogio.exceptions.FalhaNoEnvioDaMensagem;

/**
 *Classe responsavel pela codificação e decodificação das mensagens
 * @author emerson
 */
public abstract class Protocolo {
    private static final Controller CONTROLLER = Controller.getInstance();
    
    /*codigos do protocolo*/
    private static final int ENVIAR_HORARIO_POR_ELEICAO = 1;
    private static final int ENVIAR_HORARIO_POR_COORDENACAO = 2;
    private static final int SOLICITAR_ELEICAO = 3;
    
    
    /**
     * Recebe a mensagem do grupo multicast
     * @param mensagem 
     */
    public static void receberMensagem(String mensagem){
        
        /*Tratamento para recebimento de mensagens duplicadas*/
        if(!CONTROLLER.getUltimaMensagemRecebida().equals(mensagem)){
            selectMensagem(mensagem);
            CONTROLLER.setUltimaMensagemRecebida(mensagem);
            System.out.println("Mensagem Recebida: "+mensagem);
        }
        
    }
    
    /**
     * Executa a ação com base na mensagem
     * @param mensagem 
     */
    private static void selectMensagem(String mensagem){
        String [] msg = mensagem.split(";");
        int opcao = Integer.parseInt(msg[1]); //verifica qual o codigo da mensagem 
        
        switch(opcao){
            case ENVIAR_HORARIO_POR_ELEICAO:
                receberHorarioPorEleicao(mensagem);
                break;
            case ENVIAR_HORARIO_POR_COORDENACAO:
                receberHorarioCoordenadocao(mensagem);
                break;
            case SOLICITAR_ELEICAO:
                receberSolicitacaoDeEleicao();
                System.err.println("id: "+msg[0]+"Solicitou eleição");
                break;
        }
    }
    
    /*************************METODOS PARA ENVIAR MENSAGENS************************************/
    
    /**
     * Envia a solicitação de eleição para o grupo multicast
     * @param id
     * @throws FalhaNoEnvioDaMensagem
     * @throws FalhaAoCriarGrupoException 
     */
    public static void solicitarEleicao(int id) throws FalhaNoEnvioDaMensagem, FalhaAoCriarGrupoException{
        System.err.println("Solicitei eleição id: "+id);
        Multicast.enviarMensagem(id + ";" + SOLICITAR_ELEICAO + ";");
    }
    
    /**
     * Envia o horario para o grupo multicast durante a eleição
     * @param horario horario do meu relogio
     * @throws FalhaNoEnvioDaMensagem
     * @throws FalhaAoCriarGrupoException 
     */
    public static void enviarHorarioPorEleicao(int id, String horario) throws FalhaNoEnvioDaMensagem, FalhaAoCriarGrupoException{
        Multicast.enviarMensagem(id + ";" + ENVIAR_HORARIO_POR_ELEICAO+ ";" +horario + ";");
    }
    
    /**
     * Se este relogio for o coordenador envia esse horario ao grupo.
     * @param id
     * @param horario
     * @throws FalhaNoEnvioDaMensagem
     * @throws FalhaAoCriarGrupoException 
     */
    public static void enviarHorarioPorCoordenacao(int id, String horario) throws FalhaNoEnvioDaMensagem, FalhaAoCriarGrupoException{
        Multicast.enviarMensagem(id + ";" + ENVIAR_HORARIO_POR_COORDENACAO+ ";" +horario + ";");
    }
    /************************METODOS PARA RECEBER MENSAGENS************************************/
    
    /**
     * Recebe horario do grupo multicast quando acontece uma eleição 
     * @param mensagem
     */
    public static void receberHorarioPorEleicao(String mensagem){
        System.out.println("Recebeu horario eleição");
        
        String [] msg = mensagem.split(";");
        
        int id = Integer.parseInt(msg[0]);
        
        String [] horario = msg[2].split(":");

        int hora = Integer.parseInt(horario[0]);
        int minuto = Integer.parseInt(horario[1]);
        int segundo = Integer.parseInt(horario[2]);

        CONTROLLER.receberHorarioEleicao(id, hora, minuto, segundo);
        System.out.println("Recebeu horario Eleicao id: "+id+"horario"+msg[2]);
    }
    
    /**
     * Recebe o horario do coordenador para atualização
     * @param mensagem 
     */
    public static void receberHorarioCoordenadocao(String mensagem){
        
        String [] msg = mensagem.split(";");
        
        int id = Integer.parseInt(msg[0]);
        
        /*tratamento de erro para reconecção*/ 
        if(id!=Controller.getIdCoordenador()) 
            CONTROLLER.solicitarEleicao(); 
        
        if(!CONTROLLER.isMyId(id)){ //verifica se não é o coordenador
            
            String [] horario = msg[2].split(":");
        
            int hora = Integer.parseInt(horario[0]);
            int minuto = Integer.parseInt(horario[1]);
            int segundo = Integer.parseInt(horario[2]);

            CONTROLLER.receberHorarioCoordenacao(hora, minuto, segundo);
        }
        
        System.out.println("Recebeu horario coordenação id: "+id+"horario"+msg[2]);
    }
    
    /**
     * Recebe uma solicitacao de eleicao e envia o seu horario
     */
    public static void receberSolicitacaoDeEleicao() {
        System.out.println("Recebeu solicitação de eleição");
        
        CONTROLLER.receberSolicitacaoEleicao();
    }
}
