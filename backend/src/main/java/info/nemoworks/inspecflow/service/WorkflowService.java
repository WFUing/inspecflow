package info.nemoworks.inspecflow.service;

import java.util.List;
import java.util.Map;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
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

    @Transactional
    public String startProcess(String processKeyString) {
        return runtimeService.startProcessInstanceByKey(processKeyString).getId();
    }

    @Transactional
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    @Transactional
    public void setTaskAssignee(String taskKey, String assignee){
        taskService.createTaskQuery().taskDefinitionKey(taskKey).taskAssignee(assignee);
    }

    @Transactional
    public void completeTask(String taskId){
        taskService.complete(taskId);
    }

    @Transactional
    public void completeTaskWithVariables(String taskId, Map<String, Object> variables){
        taskService.complete(taskId, variables);
    }
}
