package info.nemoworks.inspecflow.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.nemoworks.inspecflow.dto.TaskDto;
import info.nemoworks.inspecflow.service.WorkflowService;

@RestController
@RequestMapping("/api")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @PostMapping(value = "/process")
    public ResponseEntity<String> startProcessInstance(@RequestBody String processKeyString) {
        return ResponseEntity.ok().body(workflowService.startProcess(processKeyString));
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDto> getTasks(@RequestParam String assignee) {
        List<Task> tasks = workflowService.getTasks(assignee);
        List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            dtos.add(new TaskDto(task.getId(), task.getName(), task.getAssignee()));
        }
        return dtos;
    }

}
