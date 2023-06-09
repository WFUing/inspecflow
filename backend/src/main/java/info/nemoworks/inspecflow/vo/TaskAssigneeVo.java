package info.nemoworks.inspecflow.vo;

public class TaskAssigneeVo {
    private String processInstanceId;
    private String assigneeId;

    public TaskAssigneeVo(String processInstanceId, String assigneeId) {
        this.processInstanceId = processInstanceId;
        this.assigneeId = assigneeId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

}
