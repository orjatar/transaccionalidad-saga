package paris.saga.camunda.adapter.reserva;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ReservaHotelAdapter implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {

    System.out.println("RESERVA Hotel OK");

  }

}
