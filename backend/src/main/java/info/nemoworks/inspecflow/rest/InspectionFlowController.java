package info.nemoworks.inspecflow.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.nemoworks.inspecflow.domain.Approval;
import info.nemoworks.inspecflow.domain.Inspection;
import info.nemoworks.inspecflow.service.InspectionFlowService;

@RestController
@RequestMapping("/api")
public class InspectionFlowController {
    
    @Autowired
    private InspectionFlowService service;
    @PostMapping("/submit")
    public void submit(@RequestBody Inspection inspection) {
        service.startProcess(inspection);
    }
    @GetMapping("/tasks")
    public List<Inspection> getTasks(@RequestParam String assignee) {
        return service.getTasks(assignee);
    }
    @PostMapping("/review")
    public void review(@RequestBody Approval approval) {
        service.submitReview(approval);
    }
}
