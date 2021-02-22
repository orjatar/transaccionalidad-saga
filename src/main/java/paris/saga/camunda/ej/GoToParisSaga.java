package paris.saga.camunda.ej;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;

import paris.saga.camunda.adapter.cancelacion.CancelacionHotelAdapter;
import paris.saga.camunda.adapter.cancelacion.CancelacionTorreEiffelAdapter;
import paris.saga.camunda.adapter.cancelacion.CancelacionVueloAdapter;
import paris.saga.camunda.adapter.reserva.ReservaHotelAdapter;
import paris.saga.camunda.adapter.reserva.ReservaTorreEiffelAdapter;
import paris.saga.camunda.adapter.reserva.ReservaVueloAdapter;


public class GoToParisSaga {

  public static void main(String[] args) {
    ProcessEngine camunda = 
        new StandaloneInMemProcessEngineConfiguration()
          .buildProcessEngine();
    
    BpmnModelInstance saga = createSaga();

    // terminamos el Saga y lo desplegamos en Camunda
    camunda.getRepositoryService().createDeployment()
        .addModelInstance("viajeVacaciones.bpmn", saga)
        .deploy();
    
    // lanzamos instancias de nuestro Saga, cuyo estado persistirá
    camunda.getRuntimeService().startProcessInstanceByKey("vacaciones", Variables.putValue("name", "Viaje Vacaciones"));
  }

  public static BpmnModelInstance createSaga() {
    // definimos el Saga como un proceso BPMN
    ProcessBuilder flow = Bpmn.createExecutableProcess("vacaciones");
    
    // Flujo de las actividades y los eventos compensatorios
    flow.startEvent()
        .serviceTask("torreEiffel").name("Reserva de la Torre Eiffel").camundaClass(ReservaTorreEiffelAdapter.class)
          .boundaryEvent().compensateEventDefinition().compensateEventDefinitionDone()
          .compensationStart().serviceTask("CancelacionTorreEiffel").camundaClass(CancelacionTorreEiffelAdapter.class).compensationDone()
          
        .serviceTask("hotel").name("Reserva del Hotel").camundaClass(ReservaHotelAdapter.class)
          .boundaryEvent().compensateEventDefinition().compensateEventDefinitionDone()
          .compensationStart().serviceTask("CancelacionHotel").camundaClass(CancelacionHotelAdapter.class).compensationDone()
          
        .serviceTask("vuelo").name("Reserva del Vuelo").camundaClass(ReservaVueloAdapter.class)
          .boundaryEvent().compensateEventDefinition().compensateEventDefinitionDone()
          .compensationStart().serviceTask("CancelacionVuelo").camundaClass(CancelacionVueloAdapter.class).compensationDone()
          
        .endEvent();
    
    // evento compensatorio en el caso de cualquier excepción no controlada
    flow.eventSubProcess()
        .startEvent().error("java.lang.Throwable")
        .intermediateThrowEvent().compensateEventDefinition().compensateEventDefinitionDone()
        .endEvent();     
    
    BpmnModelInstance saga = flow.done();
    
    return saga;
  }

}
