package info.nemoworks.inspecflow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.nemoworks.inspecflow.domain.Approval;
import info.nemoworks.inspecflow.domain.Inspection;

@Service
public class InspectionFlowService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Transactional
    public void startProcess(Inspection inspection) {
        Map<String, Object> variables = new HashMap<String, Object>();
        runtimeService.startProcessInstanceByKey("articleReview", variables);
    }

    @Transactional
    public List<Inspection> getTasks(String assignee) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskCandidateGroup(assignee)
                .list();

        List<Inspection> inspections = tasks.stream()
                .map(task -> {
                    Map<String, Object> variables = taskService.getVariables(task.getId());
                    return new Inspection(
                            task.getId());
                })
                .collect(Collectors.toList());
        return inspections;
    }

    @Transactional
    public void submitReview(Approval approval) {
        Map<String, Object> variables = new HashMap<String, Object>();
        taskService.complete(approval.getId(), variables);
    }

}
