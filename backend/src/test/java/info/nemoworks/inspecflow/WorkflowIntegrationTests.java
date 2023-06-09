package info.nemoworks.inspecflow;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.test.Deployment;
import org.flowable.spring.impl.test.FlowableSpringExtension;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(FlowableSpringExtension.class)
@SpringBootTest
class WorkflowIntegrationTests {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;

	@Test
	@Deployment(resources = { "processes/sequential-usertasks.bpmn20.xml" })
	void usertaskTest() {

		String did = runtimeService.startProcessInstanceByKey("sequential-usertasks").getProcessDefinitionId();
		Task task = taskService.createTaskQuery().singleResult();
		assertEquals("task1", task.getName());
		taskService.complete(task.getId());
		task = taskService.createTaskQuery().singleResult();
		assertEquals("task2", task.getName());
		// ExecutionEntity entity = (ExecutionEntity)
		// runtimeService.createExecutionQuery().processDefinitionId(did).singleResult();
		// entity.getCurrentFlowElement();
		BpmnModel model = repositoryService.getBpmnModel(did);
		String iii = task.getTaskDefinitionKey();
		FlowElement element = model.getFlowElement("sid-7154B8CF-DFE6-4614-9339-4EC087DDDBFD");
		Map<String, List<ExtensionElement>> m = element.getExtensionElements();
		System.out.println(m.keySet());
		System.out.println(m.values());
		if(m.containsKey("property")) {
			 System.out.println(m.get("property"));;
			 System.out.println(m.get("property").get(0).getElementText());;
		}
		taskService.complete(task.getId());
		assertEquals(0, runtimeService.createProcessInstanceQuery().count());
	}

	@Test
	@Deployment(resources = { "processes/exclusive-gateway.bpmn20.xml" })
	void exclusiveGatewayTest() {
		runtimeService.startProcessInstanceByKey("exclusive-gateway");
		Task task = taskService.createTaskQuery().singleResult();
		assertEquals("task-choice", task.getName());
		Map<String, Object> vars = new HashMap<>();
		vars.put("approval", false);
		taskService.complete(task.getId(), vars);
		task = taskService.createTaskQuery().singleResult();
		assertNotEquals("task-true", task.getName());
		taskService.complete(task.getId());
		assertEquals(0, runtimeService.createProcessInstanceQuery().count());
	}

}
