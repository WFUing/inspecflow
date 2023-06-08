package info.nemoworks.inspecflow.dto;

import java.util.HashMap;
import java.util.Map;

public class TaskDto {
    private String id;
    private String name;
    private String assignee;
    private Map<String, Object> variables;

    public TaskDto(String id, String name, String assignee, Map<String, Object> variables) {
        this.id = id;
        this.name = name;
        this.assignee = assignee;
        this.variables = variables;
    }

    public TaskDto(String id, String name, String assignee) {
        this.id = id;
        this.name = name;
        this.assignee = assignee;
        this.variables = new HashMap<>();
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    private void addVariable(String str, Object object) {
        variables.put(str, object);
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
