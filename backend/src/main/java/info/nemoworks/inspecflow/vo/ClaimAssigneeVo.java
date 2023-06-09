package info.nemoworks.inspecflow.vo;

public class ClaimAssigneeVo {
    private String taskId;
    private String assigneeId;

    public ClaimAssigneeVo(String taskId, String assigneeId) {
        this.taskId = taskId;
        this.assigneeId = assigneeId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

}
