package paris.saga.camunda.adapter.cancelacion;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CancelacionHotelAdapter implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {

    System.out.println("Se CANCELA el hotel");

  }

}
