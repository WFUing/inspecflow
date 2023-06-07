package info.nemoworks.inspecflow;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

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

	@Test
	@Deployment(resources = { "processes/sequential-usertasks.bpmn20.xml" })
	void usertaskTest() {
		runtimeService.startProcessInstanceByKey("sequential-usertasks");
		Task task = taskService.createTaskQuery().singleResult();
		assertEquals("task1", task.getName());
		taskService.complete(task.getId());
		task = taskService.createTaskQuery().singleResult();
		assertEquals("task2", task.getName());
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
		taskService.complete(task.getId(),vars);
		task = taskService.createTaskQuery().singleResult();
		assertEquals("task-true", task.getName());
		taskService.complete(task.getId());
		assertEquals(0, runtimeService.createProcessInstanceQuery().count());
	}

}
