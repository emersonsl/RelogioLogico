/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.relogio.model;

/**
 *Classe que encapsula as mensagens de eleição recebidas
 * @author emerson
 */
public class MensagemEleicao implements Comparable<MensagemEleicao> {
    int id;
    Relogio relogio;
    
    /**
     * Construtor da classe
     * @param id
     * @param relogio 
     */
    public MensagemEleicao(int id, Relogio relogio) {
        this.id = id;
        this.relogio = relogio;
    }
    
    /**
     * Retorna o id
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Altera o id
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o relogio
     * @return 
     */
    public Relogio getRelogio() {
        return relogio;
    }

    /**
     * Altera o relogio
     * @param relogio 
     */
    public void setRelogio(Relogio relogio) {
        this.relogio = relogio;
    }

    /**
     * Compara qual a mensagem maior menor ou igual
     * Toma como base o relogio
     * Se o relogio for igual considera o maior id
     * @param o
     * @return 
     */
    @Override
    public int compareTo(MensagemEleicao o) {
        int i = relogio.compareTo(o.relogio);
        if(i==0){ //se a comparação do relogio for igual
            if(id<o.getId()){ //usa o id maior como criterio de decisão;
                return -1;
            }
            return 1;
        }
        return i;
    }
}
