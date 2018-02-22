Extras

* Dar alguna solución sobre qué pasa con una llamada cuando no
  hay ningún empleado libre.

* Dar alguna solución sobre qué pasa con una llamada cuando
  entran más de 10 llamadas concurrentes.

 
Solucion:   Se  utilizo un [PriorityBlockingQueue] permite manejar cualquier tipo de objetos pemitiendo asi dar  prioridad en este caso primero a los operadores
			luego supervisores y por ultimo director, por otro lado el motivo de utilizar PriorityBlockingQueue es que tiene un metodo take() el cual bloquea el flujo cuando todos los empleados se encuentran ocupados y reanuda cuando un empleado vuelva a la queue, de esta manera se garantiza cuando hay mas de 10 llamadas y todos los empleados se encuentran ocupados.
			
Tambien se utilizo la interface Callable en vez de la Runnable, por motivo que esta interface devuelve un tipo object el cual  es utilizado con la clase Future  para iniciar las llamadas que estaban en espera en la cola del PriorityBlockingQueue.
			
* https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/PriorityBlockingQueue.html
* https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/PriorityBlockingQueue.html#take
* http://www.baeldung.com/java-priority-blocking-queue
* https://dzone.com/articles/java-callable-future-understanding
* https://www.journaldev.com/1090/java-callable-future-example
* https://www.arquitecturajava.com/java-callable-interface-y-su-uso/
