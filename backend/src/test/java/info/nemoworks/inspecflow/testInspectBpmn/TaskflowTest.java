package info.nemoworks.inspecflow.testInspectBpmn;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.IdentityService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.junit.Before;
import org.junit.Test;

public class TaskflowTest {

    private ProcessEngineConfiguration configuration;

    private ProcessEngine processEngine;

    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private IdentityService identityService;

    @Before
    public void before() {
        // 获取 ProcessEngineConfiguration 对象
        configuration = new StandaloneProcessEngineConfiguration();
        // 配置 相关的数据库的连接信息
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("12345678");
        configuration
                .setJdbcUrl("jdbc:mysql://localhost:3306/taskflow?serverTimezone=UTC&nullCatalogMeansCurrent=true");
        // 如果数据库中的表结构不存在就新建
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        // 通过 ProcessEngineConfiguration 构建 processEngine 对象
        processEngine = configuration.buildProcessEngine();
        // 获取Service
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        identityService = processEngine.getIdentityService();
    }

    /**
     * 部署流程定义
     */
    @Test
    public void testProcessEngine() {
        // 完成流程的部署操作
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("processes/taskflow.bpmn20.xml")// 关联要部署的流程文件
                .name("督查计划派发")
                .deploy();// 部署流程
        // 得到deploymentId
        // 1
        assertEquals(deploy.getId(), "1");
    }

    /**
     * 启动流程实例，创建用户，实现“创建任务”
     */
    @Test
    public void runProcess() {
        // 得到processDefinitionId
        String processId = repositoryService.createProcessDefinitionQuery().deploymentId("1").singleResult().getId();
        // 数据库ACT_RE_PROCDEF中查看processId为process:1:4，并进行验证
        assertEquals(processId, "process:1:4");
        // 创建用户
        createUsersAndGroup();
        // 设置 assignee 和 assigneelist
        Map<String, Object> variables = new HashMap<>();
        variables.put("com", "pcom");
        variables.put("g2", "group2");
        // 启动流程实例
        runtimeService.startProcessInstanceById(processId, variables);
    }

    public void createUsersAndGroup() {
        // 创建督查组成员
        User user = identityService.newUser("zhangsan");
        user.setFirstName("zhang");
        user.setLastName("san");
        user.setEmail("zhangsan@qq.com");
        identityService.saveUser(user);

        user = identityService.newUser("lisi");
        user.setFirstName("li");
        user.setLastName("si");
        user.setEmail("lisi@qq.com");
        identityService.saveUser(user);

        user = identityService.newUser("wangwu");
        user.setFirstName("wang");
        user.setLastName("wu");
        user.setEmail("wangwu@qq.com");
        identityService.saveUser(user);

        // 创建督查组
        Group group = identityService.newGroup("group2");
        group.setName("督查组");
        group.setType("type2");
        identityService.saveGroup(group);

        // 将现有用户绑定督查组
        List<User> userList = identityService.createUserQuery().list();
        for (User temp : userList) {
            // 将用户分配给对应的组
            identityService.createMembership(temp.getId(), group.getId());
        }

        user = identityService.newUser("pcom");
        user.setFirstName("p");
        user.setLastName("com");
        user.setEmail("pcom@qq.com");
        identityService.saveUser(user);
    }

    /**
     * 实现“指定督导组成员（含组长）派发任务”
     * 使用taskListener的方式实现下一个task的assignee指定
     */
    @Test
    public void runTask2() {
        Task task = taskService.createTaskQuery()
                .processDefinitionId("process:1:4")
                .singleResult();
        if (task != null) {
            Map<String, Object> variables = new HashMap<>();
            // 设置assignee的取值
            variables.put("nextAssigneeId", "zhangsan");
            // 完成任务
            taskService.complete(task.getId(), variables);
            // System.out.println("完成Task");
        }
    }

    /**
     * 实现“接受任务”
     */
    @Test
    public void runTask3() {
        Task task = taskService.createTaskQuery()
                .processDefinitionId("process:1:4")
                .singleResult();
        if (task != null) {
            // 数据库ACT_RU_TASK中查看taskId为5004，并进行验证
            assertEquals(task.getId(), "5004");
            Map<String, Object> variables = new HashMap<>();
            // 设置isclosed的取值
            variables.put("isclosed", true);
            // 完成任务
            taskService.complete(task.getId(), variables);
            // System.out.println("完成Task");
        }
    }

    /**
     * 实现“审核”
     */
    @Test
    public void runTask4() {
        Task task = taskService.createTaskQuery()
                .processDefinitionId("process:1:4")
                .singleResult();
        if (task != null) {
            // 数据库ACT_RU_TASK中查看taskId为12506，并进行验证
            assertEquals(task.getId(), "12506");
            Map<String, Object> variables = new HashMap<>();
            // 设置ispassed的取值
            variables.put("ispassed", false);
            // 完成任务
            taskService.complete(task.getId(), variables);
        }
    }

    /**
     * 实现“编辑问题库”
     */
    @Test
    public void runTask5() {
        Task task = taskService.createTaskQuery()
                .processDefinitionId("process:1:4")
                .singleResult();
        if (task != null) {
            // 数据库ACT_RU_TASK中查看taskId为15006，并进行验证
            assertEquals(task.getId(), "15006");
            // 指定多个assignee
            taskService.claim(task.getId(), "lisi,zhangsan");
            // 完成任务
            taskService.complete(task.getId());
        }
    }

    /**
     * 实现“审核”
     */
    @Test
    public void runTask6() {
        Task task = taskService.createTaskQuery()
                .processDefinitionId("process:1:4")
                .singleResult();
        if (task != null) {
            // 数据库ACT_RU_TASK中查看taskId为17508，并进行验证
            // assertEquals(task.getId(), "17508");
            Map<String, Object> variables = new HashMap<>();
            // 设置ispassed的取值
            variables.put("ispassed", true);
            // 设置issues的取值
            ArrayList<String> issues= new ArrayList<>() ;
            issues.add("issue1");
            issues.add("issue2");
            issues.add("issue3");
            variables.put("issues", issues);
            // 完成任务
            taskService.complete(task.getId(), variables);
        }
    }

}
