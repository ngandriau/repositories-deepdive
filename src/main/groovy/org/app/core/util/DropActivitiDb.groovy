package org.app.core.util

import org.activiti.engine.impl.ProcessEngineImpl
import org.activiti.engine.impl.db.DbSqlSession
import org.activiti.engine.impl.interceptor.Command
import org.activiti.engine.impl.interceptor.CommandConfig
import org.activiti.engine.impl.interceptor.CommandContext
import org.activiti.engine.impl.interceptor.CommandExecutor
import org.app.config.ActivitiConfig
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
 * Created by ngandriau on 5/4/14.
 */
class DropActivitiDb {

    public static void main(String[] args) {

        ApplicationContext appCtx = new AnnotationConfigApplicationContext(ActivitiConfig.class)

        ProcessEngineImpl processEngine = (ProcessEngineImpl) appCtx.getBean("processEngine")
        CommandExecutor commandExecutor = processEngine.processEngineConfiguration.commandExecutor

        CommandConfig config = new CommandConfig().transactionNotSupported();
        commandExecutor.execute(config, new Command<Object>() {
            public Object execute(CommandContext commandContext) {
                commandContext
                        .getSession(DbSqlSession.class)
                        .dbSchemaDrop();
                return null;
            }
        });

        processEngine.close();
    }
}
