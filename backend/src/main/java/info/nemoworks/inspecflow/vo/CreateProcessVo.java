package info.nemoworks.inspecflow.vo;

public class CreateProcessVo {
    private String processKey;
    private String businessKey;

    public CreateProcessVo(String processKey, String businessKey) {
        this.processKey = processKey;
        this.businessKey = businessKey;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

}
