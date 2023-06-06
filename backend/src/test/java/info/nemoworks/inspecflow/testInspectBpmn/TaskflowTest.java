package info.nemoworks.inspecflow.testInspectBpmn;

import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.IdentityService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.idm.api.Group;
import org.flowable.task.api.Task;
import org.junit.Test;

public class TaskflowTest {

    public ProcessEngineConfiguration getTaskflowConfiguration() {
        // 获取 ProcessEngineConfiguration 对象
        ProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        // 配置 相关的数据库的连接信息
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("12345678");
        configuration
                .setJdbcUrl("jdbc:mysql://localhost:3306/taskflow?serverTimezone=UTC&nullCatalogMeansCurrent=true");
        // 如果数据库中的表结构不存在就新建
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        return configuration;
    }

    @Test
    public void testProcessEngine() {
        ProcessEngineConfiguration configuration = getTaskflowConfiguration();

        // 通过 ProcessEngineConfiguration 构建我们需要的 processEngine 对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        // 获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 完成流程的部署操作
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("processes/taskflow.bpmn20.xml")// 关联要部署的流程文件
                .name("督查计划派发")
                .deploy();// 部署流程
        // 简单验证
        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println(deploy.getName());
    }

    /**
     * 启动流程实例
     * 在启动之前先运行UserGroupTest中代码
     */
    @Test
    public void runProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 给流程定义中的UEL表达式赋值
        Map<String, Object> variables = new HashMap<>();
        variables.put("g1", "group1");
        variables.put("g2", "group2");
        runtimeService.startProcessInstanceById("process:1:4", variables);
    }

    /**
     * 拾取任务 —— "创建任务"
     */
    @Test
    public void claimTask1Candidate() {
        String userId = "zhangsan";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 根据当前登录的用户找到对应的组
        IdentityService identityService = processEngine.getIdentityService();
        // 当前用户所在的组
        Group group = identityService.createGroupQuery().groupMember(userId).singleResult();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                // .processInstanceId("2501")
                .processDefinitionId("process:1:4")
                .taskCandidateGroup(group.getId())
                .singleResult();
        if (task != null) {
            // 任务拾取
            taskService.claim(task.getId(), userId);
            // System.out.println("任务拾取成功");
        } else {
            // web 端需要报错
            System.out.println("任务拾取失败！！");
        }
    }

    /**
     * 完成任务 —— "创建任务"
     */
    @Test
    public void completeTask1() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                // .processInstanceId("2501")
                .processDefinitionId("process:1:4")
                .taskAssignee("zhangsan")
                .singleResult();
        if (task != null) {
            // 完成任务
            taskService.complete(task.getId());
            // System.out.println("完成Task");
        } else {
            // web 端需要报错
            System.out.println("完成任务失败！！");
        }
    }

    /**
     * 拾取任务 —— "指定督导组成员（含组长）派发任务"
     */
    @Test
    public void claimTask2Candidate() {
        // String userId = "lisi";
        String userId = "lisi"; //
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 根据当前登录的用户找到对应的组
        IdentityService identityService = processEngine.getIdentityService();
        // 当前用户所在的组
        Group group = identityService.createGroupQuery().groupMember(userId).singleResult();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                // .processInstanceId("2501")
                .processDefinitionId("process:1:4")
                .taskCandidateGroup(group.getId())
                .singleResult();
        System.out.println(task);
        if (task != null) {
            // 任务拾取
            taskService.claim(task.getId(), userId);
            // System.out.println("任务拾取成功");
        } else {
            // web 端需要报错
            System.out.println("任务拾取失败！！");
        }
    }

    /**
     * 完成任务 —— "指定督导组成员（含组长）派发任务"
     */
    @Test
    public void completeTask2() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                // .processInstanceId("2501")
                .processDefinitionId("process:1:4")
                .taskAssignee("lisi")
                .singleResult();
        if (task != null) {
            // 完成任务
            taskService.complete(task.getId());
            // System.out.println("完成Task");
        } else {
            // web 端需要报错
            System.out.println("完成任务失败！！");
        }
    }

    /**
     * 拾取任务 —— "接受任务"
     */
    @Test
    public void claimTask3Candidate() {
        // String userId = "lisi";
        String userId = "oneone"; //
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 根据当前登录的用户找到对应的组
        IdentityService identityService = processEngine.getIdentityService();
        // 当前用户所在的组
        Group group = identityService.createGroupQuery().groupMember(userId).singleResult();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                // .processInstanceId("2501")
                .processDefinitionId("process:1:4")
                .taskCandidateGroup(group.getId())
                .singleResult();
        System.out.println(task);
        if (task != null) {
            // 任务拾取
            taskService.claim(task.getId(), userId);
            // System.out.println("任务拾取成功");
        } else {
            // web 端需要报错
            System.out.println("任务拾取失败！！");
        }
    }

    /**
     * 完成任务 —— "接受任务"
     */
    @Test
    public void completeTask3() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                // .processInstanceId("2501")
                .processDefinitionId("process:1:4")
                .taskAssignee("oneone")
                .singleResult();
        if (task != null) {
            // 添加流程变量 isclosed
            Map<String, Object> map = task.getProcessVariables();
            map.put("isclosed", false);
            // 完成任务
            taskService.complete(task.getId(), map);
            // System.out.println("完成Task");
        } else {
            // web 端需要报错
            System.out.println("完成任务失败！！");
        }
    }
}
