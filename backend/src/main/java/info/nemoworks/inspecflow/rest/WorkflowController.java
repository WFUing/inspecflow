package info.nemoworks.inspecflow.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.nemoworks.inspecflow.dto.TaskDto;
import info.nemoworks.inspecflow.service.WorkflowService;
import info.nemoworks.inspecflow.vo.TaskVo;

@RestController
@RequestMapping("/api")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    /**
     * 部署流程
     */
    @PostMapping(value = "/process")
    public ResponseEntity<String> startProcessInstance(@RequestParam String processKey) {
        return ResponseEntity.ok().body(workflowService.startInspectflowProcessByKey(processKey));
    }

    /**
     * 返回当前正在运行的tasks列表
     */
    @GetMapping(value = "/tasks/cur")
    public ResponseEntity<List<TaskDto>> getTasks() {
        List<Task> tasks = workflowService.getTasks();
        List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            dtos.add(new TaskDto(task.getId(), task.getName(), task.getAssignee(), task.getTaskLocalVariables()));
        }
        return ResponseEntity.ok().body(dtos);
    }

    /**
     * 返回指定assigneeId的tasks列表
     */
    @GetMapping(value = "/tasks/ass")
    public ResponseEntity<List<TaskDto>> getTasksByAId(@RequestParam String assigneeId) {
        List<Task> tasks = workflowService.getTasksByAssigneeId(assigneeId);
        List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            dtos.add(new TaskDto(task.getId(), task.getName(), task.getAssignee()));
        }
        return ResponseEntity.ok().body(dtos);
    }

    /**
     * 指定任务的assignee
     */
    @PostMapping(value = "/claim/task")
    public void claimTaskAssignee(@RequestParam String taskId, String assigneeId) {
        workflowService.claimTaskAssignee(taskId, assigneeId);
        return;
    }

    /**
     * 完成当前的task
     */
    @PostMapping(value = "tasks/comp")
    public void completeTask(@RequestParam String taskId) {
        workflowService.completeTask(taskId);
        return;
    }

    /**
     * 指定下一个任务的assignee，并完成该task
     */
    @PostMapping(value = "tasks/comp/nextclaim")
    public void completeTaskWithNextAssignee(@RequestParam String taskId, @RequestParam String nextAssigneeId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("nextAssigneeId", nextAssigneeId);
        workflowService.completeTaskWithVariables(taskId, variables);
        return;
    }

    /**
     * 完成任务，并给出下一个任务所需要的Variables
     */
    @PostMapping(value = "tasks/comp/nextvars")
    public void completeTaskWithNextVariables(@RequestBody TaskVo taskVo) {
        workflowService.completeTaskWithVariables(taskVo.getTaskId(), taskVo.getNextVariables());
        return;
    }
}
