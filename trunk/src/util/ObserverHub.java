package util;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author renato.pinheiro@pobox.com
 */
public abstract class ObserverHub implements Observer {

    // Colecao de observadores registrados
    private ArrayList<Observer> observers;

    /**
     *
     */
    public ObserverHub(){
        this.observers = new ArrayList<Observer>();
    }

    /**
     *
     * @param o
     */
    public void addObservable(Observable o){
        o.addObserver(this);
    }

    /**
     *
     * @param o
     */
    public void delObservable(Observable o){
        this.delObserver(this);
    }

    /**
     *
     * @param o
     */
    public void addObserver(Observer o){
        this.observers.add(o);
    }

    /**
     *
     * @param o
     */
    public void delObserver(Observer o){
        this.observers.remove(o);
    }

    /**
     * 
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg){

        for(Observer observador: observers){
            observador.update(o, arg);
        }
        
    }
    
}
