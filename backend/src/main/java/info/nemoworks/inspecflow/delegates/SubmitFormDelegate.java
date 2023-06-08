package info.nemoworks.inspecflow.delegates;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class SubmitFormDelegate implements JavaDelegate{

    @Override
    public void execute(DelegateExecution execution) {
        // TODO Auto-generated method stub
        System.out.println("提交任务");
    }
    
}
