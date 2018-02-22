package com.edison.call;

import com.edison.model.Director;
import com.edison.model.Empleado;
import com.edison.model.Operador;
import com.edison.model.Supervisor;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class InicioCallCenter {

    private static final Logger log = Logger.getLogger(InicioCallCenter.class);
    private PriorityBlockingQueue<Empleado> empleado = new PriorityBlockingQueue<>();
    
         /**
	 * crearEmpleados method.
         * Crea los empleados
	 */
     public void crearEmpleados(){
 
        //For para crear Operadores
        for(int i = 0; i < 6; i++) {
            empleado.add(new Operador("Operador N°:" + i));
        }
        
        //For para crear Supervisores
        for(int i = 0; i < 3; i++) {
            empleado.add(new Supervisor("Supervisor N°:" + i));
        }
        
        //For para crear Directores
        for(int i = 0; i < 1; i++) {
            empleado.add(new Director("Director N°:" + i));
        }
     }
     
     
         /**
	 * InicioCallCenter method.
	 * @throws ExecutionException, interrupted exception
	 */
     public void InicioCallCenter() throws ExecutionException, InterruptedException {
       
        crearEmpleados();
        Dispatcher d = new Dispatcher(empleado);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);         
        List<Callable<Empleado>> callablesList = new ArrayList<>();
        
        /**
	 * For inicia el proceso de creacion de las llamadas para el call Center
	 */
        for(int i = 0; i < 20; i++) {
            callablesList.add(new LLamadaEntranteCallable(d, i));
        }
        
        //Incia LLamadas que estan detenidas o en espera por currencia
        List<Future<Empleado>> ListEspera = threadPool.invokeAll(callablesList);
        
        threadPool.shutdown();
    }
     
         /**
	 * main method.
	 * @throws Exception
	 */
    public static void main(String[] args) throws Exception {
        new InicioCallCenter().InicioCallCenter();    
    }
    
   
}
