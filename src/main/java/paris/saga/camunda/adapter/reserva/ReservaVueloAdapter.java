package paris.saga.camunda.adapter.reserva;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ReservaVueloAdapter implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {

     System.out.println("ERROR Reserva Vuelo");
     
     throw new Exception("No se pudo hacer la reserva del vuelo");
     
     
//     System.out.println("RESERVA Vuelo OK");
    
  }



}
