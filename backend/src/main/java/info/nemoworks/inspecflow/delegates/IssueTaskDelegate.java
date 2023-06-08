package info.nemoworks.inspecflow.delegates;

import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;


public class IssueTaskDelegate implements JavaDelegate{

    private Expression name;

    @Override
    public void execute(DelegateExecution execution) {
        // TODO Auto-generated method stub
        System.out.println(name.getValue(execution));
    }
    
}
