package paris.saga.camunda.adapter.reserva;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ReservaTorreEiffelAdapter implements JavaDelegate {

  @Override
  public void execute(DelegateExecution ctx) throws Exception {

    System.out.println("\nRESERVA Torre Eiffel OK para el viaje: '" + ctx.getVariable("name") + "'");

  }

}
