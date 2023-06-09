package info.nemoworks.inspecflow.vo;

import java.util.HashMap;
import java.util.Map;

public class TaskVo {
    private String taskId;

    private Map<String, Object> nextVariables;
    
    public TaskVo(String taskId, Map<String, Object> nextVariables) {
        this.taskId = taskId;
        this.nextVariables = nextVariables;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Map<String, Object> getNextVariables() {
        return nextVariables;
    }

    public void setNextVariables(Map<String, Object> nextVariables) {
        this.nextVariables = nextVariables;
    }

    public TaskVo(String taskId) {
        this.taskId = taskId;
        this.nextVariables = new HashMap<>();
    }

    
}
