package com.edison.call;

import com.edison.model.Empleado;
import java.util.concurrent.Callable;

public class LLamadaEntranteCallable implements Callable<Empleado>{

    private Integer call;
    private Dispatcher dispatcher;
        
    public LLamadaEntranteCallable(Dispatcher dispatcher, Integer call) {
        this.dispatcher = dispatcher;
        this.call = call;
    }

    @Override
    public Empleado call() throws Exception {
        return dispatcher.dispatchCall(call);
    }
}
