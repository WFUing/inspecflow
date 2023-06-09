package info.nemoworks.inspecflow.service;

import java.util.List;
import java.util.Map;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkflowService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Transactional
    public String startInspectflowProcessByKey(String processKey, String businessKey) {
        return runtimeService.startProcessInstanceByKey(processKey, businessKey).getProcessInstanceId();
    }

    @Transactional
    public List<Task> getTasks(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
    }

    @Transactional
    public List<Task> getTasksByAssigneeId(String processInstanceId, String assignee) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee(assignee).list();
    }

    @Transactional
    public void claimTaskAssignee(String taskId, String assignee) {
        taskService.claim(taskId, assignee);
    }

    @Transactional
    public void unclaimTaskAssignee(String taskId) {
        taskService.unclaim(taskId);
    }

    @Transactional
    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }

    @Transactional
    public void completeTaskWithVariables(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }

    @Transactional
    public List<ProcessInstance> getAllProcessInstances() {
        return runtimeService.createProcessInstanceQuery().list();
    }

    @Transactional
    public Task getTaskByTaskId(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    @Transactional
    public BpmnModel getModelByTaskId(String taskId) {
        String processDefinitionId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessDefinitionId();
        return repositoryService.getBpmnModel(processDefinitionId);
    }

    @Transactional
    public String getTaskDefinitionIdByTaskId(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult().getTaskDefinitionKey();
    }

}
