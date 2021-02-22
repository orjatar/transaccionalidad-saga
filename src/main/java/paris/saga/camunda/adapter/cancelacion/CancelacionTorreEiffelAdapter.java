package paris.saga.camunda.adapter.cancelacion;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CancelacionTorreEiffelAdapter implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {

     System.out.println("Se CANCELA la Torre Eiffel");
    
  }

}
