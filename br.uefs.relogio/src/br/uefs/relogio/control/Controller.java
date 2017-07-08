/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.relogio.control;

import br.uefs.relogio.comunication.Protocolo;
import br.uefs.relogio.exceptions.FalhaAoCriarGrupoException;
import br.uefs.relogio.exceptions.FalhaNoEnvioDaMensagem;
import br.uefs.relogio.model.Relogio;
import br.uefs.relogio.view.Home;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author emerson
 */
public class Controller {
    private static Controller instance;
    private static int id;
    private static int idCoordenador;
    private static Relogio ultimoHorarioEnviado;
    private Relogio relogio;
    private Thread threadRelogio;
    private Home tela;
    
    /**
     * Construtor da classe
     */
    private Controller(){
        
    }
    
    /**
     * Retorna a unica instancia da classe
     * @return 
     */
    public static Controller getInstance(){
        if(instance == null){
            instance = new Controller();
        }
        return instance;
    }
    
    /**
     * Cria o relogio principal
     * @param hora
     * @param minuto
     * @param segundo
     * @param drift 
     */
    public void criarRelogio(int hora, int minuto, int segundo, double drift){
        relogio = new Relogio(hora, minuto, segundo, drift);
        criarTela();
        iniciarContagem();
    }
    
    /**
     * Retorna o relogio principal
     * @return 
     */
    public Relogio getRelogio(){
        return relogio;
    }

    /**
     * Retorna o id desse relogio
     * @return 
     */
    public static int getId() {
        return id;
    }

    /**
     * Altera o id desse relogio
     * @param id 
     */
    public static void setId(int id) {
        Controller.id = id;
    } 
    
    /**
     * Atualiza o este relogio, a partir do horario recebido do coordenador
     */
    public void atualizarRelogio(int hora, int minuto, int segundo){
        relogio.atualizar(hora, minuto, segundo);
    }
    
    /**
     * Cria a tela principal
     */
    public void criarTela(){
        tela = new Home();
        tela.setVisible(true);
    }
    
    /**
     * retorna a tela principal
     * @return 
     */
    public Home getTela(){
        return tela;
    }
    
    /**
     * verifica se o id é igual ao desse relogio
     * @param id
     * @return 
     */
    public boolean isMyId(int id){
        return this.id == id;
    }
    
    /**
     * Recebe horario do grupo multicast
     * @param id
     * @param hora
     * @param minuto
     * @param segundo 
     */
    public void receberHorarioEleicao(int id, int hora, int minuto, int segundo){
        Relogio relogioTemp = new Relogio(hora, minuto, segundo);
        if(ultimoHorarioEnviado.isMenor(relogioTemp)){
            idCoordenador = id;
            System.out.println("Coordenador: "+id);
            atualizarRelogio(hora, minuto, segundo);
        }if(id == Controller.id && ultimoHorarioEnviado.equals(relogioTemp)){
            System.out.println("eu: "+id);
            atualizarRelogio(hora, minuto, segundo);
        }
    }
    
    /**
     * Envia o horario para eleicao
     */
    public void enviarHorarioEleicao(){
        try {
            String [] horario = relogio.toString().split(":");
            
            int hora = Integer.parseInt(horario[0]);
            int minuto = Integer.parseInt(horario[1]);
            int segundo = Integer.parseInt(horario[2]);
            
            ultimoHorarioEnviado = new Relogio(hora, minuto, segundo);
            Protocolo.enviarHorarioPorEleicao(id, ultimoHorarioEnviado.toString());
        } catch (FalhaNoEnvioDaMensagem | FalhaAoCriarGrupoException ex) {
            exibirFalha(ex);
        }
    }
    
    /**
     * Para a contagem do relogio
     */
    public void pararContagem(){
        threadRelogio.stop();
    }
    
    /**
     * Inicia a contagem do relogio
     */
    public void iniciarContagem(){
        threadRelogio = new Thread(relogio);
        threadRelogio.start();
    }
    
    /************************** EXIBIÇÃO DE POSSIVEIS FALHAS *********************************/
    
    public void exibirFalha(Exception e){
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
    
    public void solicitarEleicao(){
        try {
            Protocolo.solicitarEleicao(id);
        } catch (FalhaNoEnvioDaMensagem | FalhaAoCriarGrupoException ex) {
            exibirFalha(ex);
        }
    }
}
