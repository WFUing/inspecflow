package info.nemoworks.inspecflow.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import info.nemoworks.inspecflow.dto.ProcessInstanceDto;
import info.nemoworks.inspecflow.dto.TaskDto;
import info.nemoworks.inspecflow.service.WorkflowService;
import info.nemoworks.inspecflow.vo.ClaimAssigneeVo;
import info.nemoworks.inspecflow.vo.CreateProcessVo;
import info.nemoworks.inspecflow.vo.TaskAssigneeVo;
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
    @ResponseBody
    public ResponseEntity<String> startProcessInstance(@RequestBody CreateProcessVo createProcessVo) {
        return ResponseEntity.ok().body(workflowService.startInspectflowProcessByKey(createProcessVo.getProcessKey(),
                createProcessVo.getBusinessKey()));
    }

    /**
     * 返回当前正在运行的tasks列表
     */
    @GetMapping(value = "/tasks/current")
    @ResponseBody
    public ResponseEntity<List<TaskDto>> getCurrentTasks(@RequestBody String processInstanceId) {
        List<Task> tasks = workflowService.getTasks(processInstanceId);
        List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            dtos.add(new TaskDto(task.getId(), task.getName(), task.getAssignee(), task.getTaskLocalVariables()));
        }
        return ResponseEntity.ok().body(dtos);
    }

    /**
     * 返回指定assigneeId的tasks列表
     */
    @GetMapping(value = "/tasks/assignee")
    @ResponseBody
    public ResponseEntity<List<TaskDto>> getTasksByAId(@RequestBody TaskAssigneeVo taskAssigneeVo) {
        List<Task> tasks = workflowService.getTasksByAssigneeId(taskAssigneeVo.getProcessInstanceId(),
                taskAssigneeVo.getAssigneeId());
        List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            dtos.add(new TaskDto(task.getId(), task.getName(), task.getAssignee(), task.getTaskLocalVariables()));
        }
        return ResponseEntity.ok().body(dtos);
    }

    /**
     * 指定任务的assignee
     */
    @PostMapping(value = "/tasks/claimassignee")
    @ResponseBody
    public void claimTaskAssignee(@RequestBody ClaimAssigneeVo claimassignee) {
        workflowService.claimTaskAssignee(claimassignee.getTaskId(), claimassignee.getAssigneeId());
        return;
    }

    /**
     * 完成当前的task
     */
    @PostMapping(value = "/tasks/complete")
    @ResponseBody
    public void completeTask(@RequestBody String taskId) {
        workflowService.completeTask(taskId);
        return;
    }

    /**
     * 给出下一个任务所需要的Variables，并完成任务
     */
    @PostMapping(value = "/tasks/complete/nextwithvars")
    @ResponseBody
    public void completeTaskWithNextVariables(@RequestBody TaskVo taskVo) {
        workflowService.completeTaskWithVariables(taskVo.getTaskId(), taskVo.getNextVariables());
        return;
    }

    /**
     * 给出所有的流程实例
     */
    @GetMapping(value = "/process/instances")
    @ResponseBody
    public ResponseEntity<List<ProcessInstanceDto>> getAllProcessInstances() {
        List<ProcessInstance> processInstanceList = workflowService.getAllProcessInstances();
        List<ProcessInstanceDto> list = new ArrayList<>();
        for (ProcessInstance processInstance : processInstanceList) {
            list.add(new ProcessInstanceDto(processInstance.getId(), processInstance.getProcessDefinitionKey(),
                    processInstance.getBusinessKey()));
        }
        return ResponseEntity.ok().body(list);
    }

    /**
     * 当前task对应的url
     */
    @PostMapping(value = "/tasks/currenturl")
    @ResponseBody
    public String getUrlByTaskId(@RequestBody String taskId) {
        BpmnModel model = workflowService.getModelByTaskId(taskId);
        String taskDefinitionId = workflowService.getTaskDefinitionIdByTaskId(taskId);
        FlowElement element = model.getFlowElement(taskDefinitionId);
        Map<String, List<ExtensionElement>> extesionElementMap = element.getExtensionElements();
		if(extesionElementMap.containsKey("property")) {
			 return extesionElementMap.get("property").get(0).getElementText();
		} else {
            return "error";
        }
    }
}
