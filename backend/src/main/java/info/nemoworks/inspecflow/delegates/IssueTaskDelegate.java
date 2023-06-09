package info.nemoworks.inspecflow.delegates;

import java.util.HashMap;
import java.util.Map;

import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import info.nemoworks.inspecflow.ApplicationContextProvider;

@Component
public class IssueTaskDelegate implements JavaDelegate{

    private Expression name;

    @Override
    public void execute(DelegateExecution execution) {
        // TODO Auto-generated method stub
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        RuntimeService runtimeService = context.getBean(RuntimeService.class);
        Map<String, Object> variables = new HashMap<>();
        variables.put("issueName", name.getValue(execution));
        runtimeService.startProcessInstanceByKey("issueflow",variables).getProcessInstanceId();
    }

    
    
}
