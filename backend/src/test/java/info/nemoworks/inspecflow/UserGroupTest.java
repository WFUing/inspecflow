// package info.nemoworks.inspecflow;

// import java.util.List;

// import org.flowable.engine.IdentityService;
// import org.flowable.engine.ProcessEngine;
// import org.flowable.engine.ProcessEngineConfiguration;
// import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
// import org.flowable.idm.api.Group;
// import org.flowable.idm.api.User;
// import org.junit.Test;

// // import liquibase.repackaged.org.apache.commons.lang3.ObjectUtils.Null;

// public class UserGroupTest {

//     public ProcessEngineConfiguration getTaskflowConfiguration() {
//         // 获取 ProcessEngineConfiguration 对象
//         ProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
//         // 配置 相关的数据库的连接信息
//         configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
//         configuration.setJdbcUsername("root");
//         configuration.setJdbcPassword("12345678");
//         configuration
//                 .setJdbcUrl("jdbc:mysql://localhost:3306/taskflow?serverTimezone=UTC&nullCatalogMeansCurrent=true");
//         // 如果数据库中的表结构不存在就新建
//         configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//         return configuration;
//     }

//     /**
//      * 维护用户
//      */
//     @Test
//     public void createSZYUser() {
//         ProcessEngineConfiguration configuration = getTaskflowConfiguration();
//         ProcessEngine processEngine = configuration.buildProcessEngine();
//         // 通过 IdentityService 完成相关的用户和组的管理
//         IdentityService identityService = processEngine.getIdentityService();
//         User user = identityService.newUser("zhangsan");
//         user.setFirstName("zhang");
//         user.setLastName("san");
//         user.setEmail("zhangsan@qq.com");
//         identityService.saveUser(user);

//         user = identityService.newUser("lisi");
//         user.setFirstName("li");
//         user.setLastName("si");
//         user.setEmail("lisi@qq.com");
//         identityService.saveUser(user);

//         user = identityService.newUser("wangwu");
//         user.setFirstName("wang");
//         user.setLastName("wu");
//         user.setEmail("wangwu@qq.com");
//         identityService.saveUser(user);
//     }

//     /**
//      * 创建用户组
//      */
//     @Test
//     public void createSZYGroup() {
//         ProcessEngineConfiguration configuration = getTaskflowConfiguration();
//         ProcessEngine processEngine = configuration.buildProcessEngine();
//         IdentityService identityService = processEngine.getIdentityService();
//         // 创建Group对象并指定相关的信息
//         Group group = identityService.newGroup("group1");
//         group.setName("省专员");
//         group.setType("type1");
//         // 创建Group对应的表结构数据
//         identityService.saveGroup(group);

//     }

//     /**
//      * 将用户分配给对应的Group
//      */
//     @Test
//     public void userSZYGroup() {
//         ProcessEngineConfiguration configuration = getTaskflowConfiguration();
//         ProcessEngine processEngine = configuration.buildProcessEngine();
//         IdentityService identityService = processEngine.getIdentityService();
//         // 根据组的编号找到对应的Group对象
//         Group group = identityService.createGroupQuery().groupId("group1").singleResult();
//         List<User> list = identityService.createUserQuery().list();
//         for (User user : list) {
//             // 将用户分配给对应的组
//             identityService.createMembership(user.getId(), group.getId());
//         }
//     }

//     /**
//      * 维护用户
//      */
//     @Test
//     public void createDZZUser() {
//         ProcessEngineConfiguration configuration = getTaskflowConfiguration();
//         ProcessEngine processEngine = configuration.buildProcessEngine();
//         // 通过 IdentityService 完成相关的用户和组的管理
//         IdentityService identityService = processEngine.getIdentityService();
//         User user = identityService.newUser("oneone");
//         user.setFirstName("one");
//         user.setLastName("one");
//         user.setEmail("oneone@qq.com");
//         identityService.saveUser(user);

//         user = identityService.newUser("twotwo");
//         user.setFirstName("two");
//         user.setLastName("two");
//         user.setEmail("twotwo@qq.com");
//         identityService.saveUser(user);

//         user = identityService.newUser("threethree");
//         user.setFirstName("three");
//         user.setLastName("three");
//         user.setEmail("threethree@qq.com");
//         identityService.saveUser(user);
//     }

//     /**
//      * 创建用户组
//      */
//     @Test
//     public void createDZZGroup() {
//         ProcessEngineConfiguration configuration = getTaskflowConfiguration();
//         ProcessEngine processEngine = configuration.buildProcessEngine();
//         IdentityService identityService = processEngine.getIdentityService();
//         // 创建Group对象并指定相关的信息
//         Group group = identityService.newGroup("group2");
//         group.setName("督查组");
//         group.setType("type2");
//         // 创建Group对应的表结构数据
//         identityService.saveGroup(group);

//     }

//     /**
//      * 将用户分配给对应的Group
//      */
//     @Test
//     public void userDZZGroup() {
//         ProcessEngineConfiguration configuration = getTaskflowConfiguration();
//         ProcessEngine processEngine = configuration.buildProcessEngine();
//         IdentityService identityService = processEngine.getIdentityService();
//         // 根据组的编号找到对应的Group对象
//         Group group = identityService.createGroupQuery().groupId("group2").singleResult();
//         List<User> list = identityService.createUserQuery().list();
//         for (User user : list) {
//             // 将用户分配给对应的组
//             if (identityService.getUserInfoKeys(user.getId()) == null) {
//                 identityService.createMembership(user.getId(), group.getId());
//             }
//         }
//     }
// }
