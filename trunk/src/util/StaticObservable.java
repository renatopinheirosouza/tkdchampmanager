package util;

import java.util.ArrayList;
import java.util.Observer;

/**
 *
 * @author Renato Pinheiro
 */
public abstract class StaticObservable {
    
    // colecao de observadores
    private static ArrayList<Observer> observers = new ArrayList<Observer>();

    public static void addObserver(Observer o) {
        
        if(o == null) 
            throw new IllegalArgumentException("Observador não informado");
        
        if(!observers.contains(o))
            observers.add(o);
        
    }
    
    public static void deleteObserver(Observer o) {
        
        if(observers.contains(o))
           observers.remove(o);
        
    }
    
    public static void deleteObservers() {
        
        observers.clear();
        
    }
    
    public static void notifyObservers(){
        
        Object[] arrLocal;
        
        arrLocal = observers.toArray();
        
        for(Object o: arrLocal){
            
            ((Observer) o).update( null, null);
            
        }
        
    }
    
    public static void notifyObservers(Object obj){
        
        Object[] arrLocal;
        
        arrLocal = observers.toArray();
        
        for(Object o: arrLocal){
            
            ((Observer) o).update( null, obj);
            
        }
        
    }    
    

}
