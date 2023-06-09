package info.nemoworks.inspecflow.dto;

public class ProcessInstanceDto {
    private String processInstanceId;

    private String instanceKey;

    private String businessKey;

    public ProcessInstanceDto(String processInstanceId, String instanceKey, String businessKey) {
        this.processInstanceId = processInstanceId;
        this.instanceKey = instanceKey;
        this.businessKey = businessKey;
    }

    public ProcessInstanceDto(String processInstanceId, String instanceKey) {
        this.processInstanceId = processInstanceId;
        this.instanceKey = instanceKey;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getInstanceKey() {
        return instanceKey;
    }

    public void setInstanceKey(String instanceKey) {
        this.instanceKey = instanceKey;
    }

    public String getProcessInstance() {
        return processInstanceId;
    }

    public void setProcessInstance(String processInstance) {
        this.processInstanceId = processInstance;
    }
}
