package info.nemoworks.inspecflow.listeners;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

public class receiveTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        // TODO Auto-generated method stub
        String name = (String) delegateTask.getVariable("assigneeName");
        delegateTask.setAssignee(name);
    }

}
