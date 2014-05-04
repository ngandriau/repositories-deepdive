package org.app.util;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.app.config.DBConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpringApp
{
    final static Logger log = LoggerFactory.getLogger(SpringApp.class);

    ApplicationContext appCtx;
    ProcessEngine processEngine;
    RuntimeService runtimeService;

    public static void main(String[] args)
    {
        SpringApp app = new SpringApp();

        app.initApp();
        app.initProcessEngine();
        app.deployOrderProcess();

        List<ProcessInstance> activeProcesses = app.queryActiveProcesses();
        if (activeProcesses.isEmpty())
        {
            app.executeNewBookOrderProcess();
        }

        app.executeEveryUserTask();

        app.waitEndOfAnyProc();

        log.info("==== MainApp.Over");

        app.processEngine.close();
    }

    private void initApp()
    {
        log.info ("initApp()");
        appCtx = new AnnotationConfigApplicationContext(DBConfig.class);
    }

    void initProcessEngine()
    {
        log.info ("initProcessEngine()");


        processEngine = (ProcessEngine) appCtx.getBean("processEngine");

        runtimeService = processEngine.getRuntimeService();

    }

    void deployOrderProcess(){

        log.info("deployOrderProcess()");
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("processes/bookorder.bpmn20.xml")
                .deploy();
    }

    public ProcessInstance executeNewBookOrderProcess() {
        log.info ("executeNewBookOrderProcess()");

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("isbn", "123456");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("bookorder", variables);

        log.debug ("  new proc started with id: "+processInstance.getId()+" - from proc def: "+processInstance.getProcessDefinitionId());

        return processInstance;
    }

    public void executeEveryUserTask() {
        log.info ("executeEveryUserTask()");

        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("sales").list();

        log.debug ("  found ["+tasks.size()+"] waiting user tasks. Complete them all:");

        for (Task task : tasks) {
            log.debug ("    complete Task("+task.getName()+") with id("+task.getId()+")");
            taskService.complete(task.getId());
        }
    }

    public void waitEndOfAnyProc() {
        log.info ("waitEndOfAnyProc()");
        List<ProcessInstance> activeProcesses = queryActiveProcesses();
        while (!activeProcesses.isEmpty()) {
            log.debug ("  found "+activeProcesses.size() + " active proc, wait...");
            try
            {
                Thread.sleep(200);
            } catch (InterruptedException e)
            {
                log.warn("Ex while sleeping to wait end of process... Just move on", e);
            }
            activeProcesses = queryActiveProcesses();
        }
    }

    public List<ProcessInstance> queryActiveProcesses() {
        log.info ("queryActiveProcesses()");
        return runtimeService.createProcessInstanceQuery().processDefinitionKey("bookorder").list();
    }


}
