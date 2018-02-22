package com.edison.call;

import com.edison.model.Empleado;
import org.apache.log4j.Logger;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

public class Dispatcher {
    
    private PriorityBlockingQueue<Empleado> empleado;
    
    private static final Integer MAX_TIEMPO = 10000;
    private static final Integer MIN_TIEMPO = 5000;
    
    private static final Logger log = Logger.getLogger(Dispatcher.class);

    /**
     * Constructor
     * @param empleado
     */
    public Dispatcher(PriorityBlockingQueue<Empleado> empleado) {
        this.empleado = empleado;
    }

    /**
     * dispatchCall Method
     * Asigna la llamada por prioridad los empleados.. si todos estan ocupados el metodo take() los asigna a cola de espera
     * @param call
     * @return
     */
    public Empleado dispatchCall(Integer call) {
        Empleado asigando = null;
        try {
            asigando = empleado.take();
            log.info("LLamada entrante  N°:" + call + " Asignado a: " + asigando);
            RandomTiempo(call, asigando);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return asigando;
    }

    /**
     * RandomTiempo Method 
     * Realiza tiempo aleatorio de la llamada y libera el empleado para recibir nuevas llamadasS
     * @param call
     * @param asignado
     */
    private void RandomTiempo(Integer call, Empleado asignado) {
        try {
            Integer duration = new Random().nextInt((MAX_TIEMPO - MIN_TIEMPO) + 1) + MIN_TIEMPO;
            Thread.sleep(duration);
            log.info("llamada terminada  N°:" + call + " para: " + asignado + " duracion: " + duration);
            empleado.add(asignado);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
