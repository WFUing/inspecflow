package info.nemoworks.inspecflow.dto;

public class TaskUrlDto {
    String taskId;
    String url;

    public TaskUrlDto(String taskId, String url) {
        this.taskId = taskId;
        this.url = url;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
