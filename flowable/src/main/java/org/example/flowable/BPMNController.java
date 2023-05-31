package org.example.flowable;

import org.flowable.bpmn.model.BpmnModel;

import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.FieldExtension;
import org.flowable.engine.FormService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BPMNController {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Autowired
    private FormService formService;

    @PostMapping("/deploy")
    public void deploy() {
        repositoryService.createDeployment()
                .addClasspathResource("test.bpmn20.xml")
                .deploy();
    }

    // start a process instance
    @PostMapping("/start")
    public ResponseEntity start() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("test");;
        return ResponseEntity.ok().build();
    }

    // query user tasks
    @PostMapping("/query")
    public ResponseEntity query() {
        List<Task> tasks = new ArrayList<>(taskService.createTaskQuery()
                .taskAssignee("user1")
                .list());
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : tasks) {
            BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
            // 获取userTask的extensionElements
            List<ExtensionElement> extensionElements = bpmnModel.getMainProcess().getFlowElement(task.getTaskDefinitionKey()).getExtensionElements().get("field");
            if (extensionElements == null || extensionElements.size() == 0) {
                continue;
            }
            ExtensionElement extensionElement = extensionElements.get(0);
            String formName = extensionElement.getAttributeValue(null, "stringValue");
            TaskResponse taskResponse = new TaskResponse(task.getName(), task.getId(), task.getAssignee(), formName);
            taskResponses.add(taskResponse);
        }
        return ResponseEntity.ok(taskResponses);
    }

}
