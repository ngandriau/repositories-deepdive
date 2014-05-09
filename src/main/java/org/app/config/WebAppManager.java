package org.app.config;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * TODO: ensure it is executed by Spring at startup, so the process are deployed...
 */
public class WebAppManager
{

    public static final String PROCESSES_BOOKORDER_BPMN = "processes/bookorder.bpmn";
    private static Logger LOG = LoggerFactory.getLogger(WebAppManager.class);

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @PostConstruct
    public void deployProcesses(){
        LOG.debug("deployProcesses");
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource(PROCESSES_BOOKORDER_BPMN)
                .deploy();

        LOG.debug("  deployed process:{}", PROCESSES_BOOKORDER_BPMN);
    }

}
