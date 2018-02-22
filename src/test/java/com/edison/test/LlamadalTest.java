package com.edison.test;

import com.edison.model.Director;
import com.edison.model.Empleado;
import com.edison.model.Operador;
import com.edison.model.Supervisor;
import com.edison.call.Dispatcher;
import com.edison.call.LLamadaEntranteCallable;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class LlamadalTest {
    
    PriorityBlockingQueue<Empleado> empleado;
    ExecutorService incomingCallExecutor;
    Dispatcher dispatcher;
    
    @Test
    public void testLlamadas() throws InterruptedException, ExecutionException {
        Inciar(7,2,1,10);

        List<Callable<Empleado>> callableList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            callableList.add(new LLamadaEntranteCallable(dispatcher, i));
        }
        
        List<Future<Empleado>> futureList = incomingCallExecutor.invokeAll(callableList);
        
        int counCalls = 0;
        for(Future<Empleado> future : futureList) {
            assert future.get() != null;
            counCalls++;
        }
        
        assert counCalls == 10;
    }
    
    @Test
    /**
     * Add 7 operators, 2 supervisors and 1 director and then dispatch 7 call, all atendees that answer the call must be Operators
     */
    public void testPrioridadLLamada() throws InterruptedException, ExecutionException {
        Inciar(7,2,1,10);

        List<Callable<Empleado>> callableList = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            callableList.add(new LLamadaEntranteCallable(dispatcher, i));
        }
        
        List<Future<Empleado>> futureList = incomingCallExecutor.invokeAll(callableList);
        
        for(Future<Empleado> future : futureList) {
            assert future.get() instanceof Operador;
        }
    }
    
    @Test
    /**
     * Add 7 operators, 2 supervisors and 1 director and then dispatch 8 call, all atendees that answer the call must be Operators and the last must be Supervisor
     */
    public void testPrioridadSupervisor() throws InterruptedException, ExecutionException {
        Inciar(7,2,1,10);

        List<Callable<Empleado>> callableList = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            callableList.add(new LLamadaEntranteCallable(dispatcher, i));
        }
        
        List<Future<Empleado>> futureList = incomingCallExecutor.invokeAll(callableList);
        
        for(Future<Empleado> future : futureList) {
            Empleado e = future.get();
            assert e != null;
            assert e instanceof Operador || e instanceof Supervisor;
        }
        
        
    }

    private void Inciar(Integer operators, Integer supervisors, Integer directors, Integer callPoolSize) {
        empleado = new PriorityBlockingQueue<>();
        for(int i = 0; i < operators; i++) {
            empleado.add(new Operador("Operador N°:" + i));
        }
        
        for(int i = 0; i < supervisors; i++) {
            empleado.add(new Supervisor("Supervisor N°:" + i));
        }
        
        for(int i = 0; i < directors; i++) {
            empleado.add(new Director("Director N°:" + i));
        }
        
        incomingCallExecutor = Executors.newFixedThreadPool(callPoolSize);
        dispatcher = new Dispatcher(empleado);
    }
    
    @After
    public void Cancelar() {
        empleado = null;
        dispatcher = null;
        incomingCallExecutor.shutdown();
        if(incomingCallExecutor.isShutdown()) {
            incomingCallExecutor = null;
        }
    }
}
