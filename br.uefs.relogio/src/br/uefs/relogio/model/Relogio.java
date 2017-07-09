/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.relogio.model;

import br.uefs.relogio.control.Controller;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emerson
 */
public class Relogio implements Runnable, Comparable<Relogio>{
    private int hora;
    private int minuto;
    private int segundo;
    private double drift;
    
    
    /**
     * Contrutor da classe
     */
    public Relogio(){
        hora=0;
        minuto=0;
        segundo=0;
        drift = 0;
    }
    
    /**
     * Construtor da classe
     * @param hora
     * @param minuto
     * @param segundo 
     */
    public Relogio(int hora, int minuto, int segundo) {
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
    }
    
    /**
     * Construtor da classe
     * @param hora
     * @param minuto
     * @param segundo
     * @param drift 
     */
    public Relogio(int hora, int minuto, int segundo, double drift) {
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
        this.drift = drift;
    }

    /**
     * Retorna a hora
     * @return 
     */
    public int getHora() {
        return hora;
    }

    /**
     * Retorna os minutos
     * @return 
     */
    public int getMinuto() {
        return minuto;
    }
    
    /**
     * retorna os segundos
     * @return 
     */
    public int getSegundo() {
        return segundo;
    }
   
    /**
     * Mundança do drift
     * @param drift 
     */
    public void setDrift(double drift){
        this.drift = drift;
    }
    
    /**
     * Atualiza o tempo desse relogio
     * @param hora
     * @param minuto
     * @param segundo 
     */
    public void atualizar(int hora, int minuto, int segundo) {
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
    }
    
    /**
     * Verifica se este relogio é menor que o recebido
     * @param r
     * @return 
     */
    private boolean isMenor(Relogio r){
        int horaT = r.hora;
        int minutoT = r.minuto;
        int segundoT = r.segundo;
        
        if(horaT>this.hora){ //verifica se a hora é maior
            return true;
        }else if(horaT == this.hora){ //verifica se a hora é igual
            if(minutoT>this.minuto){ //verifica se o minuto é maior
                return true;
            }else if(minutoT == minuto){ //verifica se o minuto é igual
                if(segundoT>this.segundo){ //verifica se o segundo é maior
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Verifica se o relogio é igual
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o){
        Relogio r = (Relogio) o;
        return r.hora == hora && r.minuto == minuto && r.segundo == segundo;
    }
    
    /**
     * Metodo de impressão do horario
     * @return 
     */
    @Override
    public String toString(){
        return hora + ":" + minuto + ":" + segundo;
    }

    /**
     * metodo reponsavel pela contagem do relogio
     */
    @Override
    public void run() {
        try {
            while(true){
                segundo++; 
                if(segundo == 60){ //fim dos segundos
                    segundo = 0;
                    minuto++;
                    if(minuto == 60){ //fim dos minutos
                        minuto =0;
                        hora++;
                        if(hora == 24){ //fim das horas
                            hora = 0;
                        }
                    }
                }
                Thread.sleep((long) (drift*1000)); //atraso
                Controller.getInstance().getTela().atualizarHora();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Relogio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Compara o relogio recebido com o relogio da classe
     * @param o
     * @return 
     */
    @Override
    public int compareTo(Relogio o) {
        if(isMenor(o)){
            return -1;
        }else if(equals(o)){
            return 0;
        }
        return 1;
    }
}
