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
    public String startInspectflowProcessByKey(String processKey) {
        return runtimeService.startProcessInstanceByKey(processKey).getId();
    }

    @Transactional
    public List<Task> getTasks() {
        return taskService.createTaskQuery().list();
    }

    @Transactional
    public List<Task> getTasksByAssigneeId(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
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
}
