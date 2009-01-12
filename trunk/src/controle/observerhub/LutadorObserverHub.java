package controle.observerhub;

import java.util.Observer;
import util.ObserverHub;

/**
 *
 * @author renato.pinheiro@pobox.com
 */
public class LutadorObserverHub extends ObserverHub implements Observer {

    private static LutadorObserverHub singleton;

    /**
     *
     */
    private LutadorObserverHub(){
        
    }

    /**
     *
     * @return
     */
    public static ObserverHub getInstance(){

        if(singleton == null){
            singleton = new LutadorObserverHub();
        }

        return singleton;

    }
    
}
