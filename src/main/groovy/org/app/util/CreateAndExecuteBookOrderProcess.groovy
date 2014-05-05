package org.app.util

import groovy.util.logging.Slf4j
import org.activiti.engine.RepositoryService
import org.activiti.engine.TaskService
import org.activiti.engine.runtime.ProcessInstance
import org.activiti.engine.task.Task
import org.app.config.ActivitiConfig
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

import static org.app.beans.BookOrderProcessService.*
import static org.app.util.LoadSampleData.*

@Slf4j
class CreateAndExecuteBookOrderProcess {
    ApplicationContext appCtx
    ActivitiBeans activiti
    DomainRepositories repositories

    public static void main(String[] args) {
        log.info "==== App Startup"


        CreateAndExecuteBookOrderProcess app = new CreateAndExecuteBookOrderProcess()

        app.initApp()

        app.deployOrderProcess()

        List<ProcessInstance> activeProcesses = app.queryActiveProcesses()
        if (activeProcesses.isEmpty())
        {
            app.executeNewBookOrderProcess()
            app.waitForAUserTask()
        }

        app.executeEveryUserTask()

        app.waitEndOfAnyProc()


        app.activiti.processEngine.close()

        log.info "==== App Over"
    }

    /**
     * Load spring context and get the processEngine
     */
    void initApp()
    {
        log.info "initApp()"

        appCtx = new AnnotationConfigApplicationContext(ActivitiConfig.class)

        activiti = ActivitiBeans.getBeans(appCtx)
        repositories = DomainRepositories.getRepositories(appCtx)


        if(repositories.bookRepo.findByTitle("Spring in Action") == null){
            log.debug "  Missing key book in BD => load sample data"
            LoadSampleData loadTool = new LoadSampleData(appCtx)
            loadTool.loadSampleData()
        }else{
            log.debug "  key book in store, no need to loadsample data"
        }
    }

    void deployOrderProcess()
    {

        log.info "deployOrderProcess()"
        RepositoryService repositoryService = activiti.processEngine.getRepositoryService()
        repositoryService.createDeployment()
                .addClasspathResource("processes/bookorder.bpmn")
                .deploy()
    }

    ProcessInstance executeNewBookOrderProcess()
    {
        log.info "executeNewBookOrderProcess()"

        def variables = [(ISBNS_PARAM_KEY): randomBookSelection(),
                         (CUSTOMER_LASTNAME_PARAM_KEY): "GANDRIAU"]
        
        ProcessInstance processInstance = activiti.runtimeService.startProcessInstanceByKey("bookorder", variables)

        log.debug"  new proc started with id: $processInstance.id - from proc def:$processInstance.processDefinitionId" 

        return processInstance
    }

    List<String> randomBookSelection(){
        def availableBooks = [SPRING_IN_ACTION_ISBN, ACTIVITI_IN_ACTION_ISBN, JAVA_PERSISTENCE_ISBN]

        List<String> selectedBooks = new  ArrayList<>()

        Random r = new Random()
        r.nextInt(5+1).times {
            selectedBooks << availableBooks[r.nextInt(availableBooks.size())]
        }

        selectedBooks

    }

    /**
     * !!!In that case, we assume that every user task is a validation by manager. <br/>
     *  So we assume that it need an "action" variable to indicate if it validation is ok or not.
     */
    void executeEveryUserTask()
    {
        log.info "executeEveryUserTask()"

        TaskService taskService = activiti.processEngine.getTaskService()
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("sales").list()

        log.debug("  found [${tasks.size()}] waiting user tasks. Complete them all:")

        for (Task task : tasks)
        {
            log.debug("    complete Task($task.name ) with id($task.id )")

            String approvalDecision = "OK"
            if(Math.random() < 0.5){
                log.debug "  too bad, validation rejected randomly"
                approvalDecision = "NO"
            }else{
                log.debug "  ok, validation approved randomly"
            }

            taskService.complete(task.id, [(ACTION_PARAM_KEY): approvalDecision])
        }
    }


    void waitEndOfAnyProc()
    {
        log.info"waitEndOfAnyProc()"
        
        List<ProcessInstance> activeProcesses = queryActiveProcesses()
        while (!activeProcesses.isEmpty())
        {
            log.debug("  found " + activeProcesses.size() + " active proc, wait...")
            try
            {
                Thread.sleep(200)
            } catch (InterruptedException e)
            {
                log.warn("Ex while sleeping to wait end of process... Just move on", e)
            }
            activeProcesses = queryActiveProcesses()
        }
    }

    List<ProcessInstance> queryActiveProcesses()
    {
        log.info"queryActiveProcesses()"
        
        List<ProcessInstance> processes = activiti.runtimeService.createProcessInstanceQuery().processDefinitionKey("bookorder").list()
        log.debug("found {} active processes", processes.size())
        return processes
    }

    /**
     * Wait for the availability of a user task
     */
    void waitForAUserTask()
    {
        log.info "waitForAUserTask()"

        boolean gotOne = false
        int repeat = 0
        int maxRepeat = 10

        while (!gotOne && repeat < maxRepeat)
        {
            TaskService taskService = activiti.processEngine.getTaskService()
            List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("sales").list()

            log.debug("  found [${tasks.size()}] waiting user tasks.")

            if (tasks.size() > 0)
            {
                gotOne = true
            } else
            {
                try
                {
                    Thread.sleep(100)
                } catch (InterruptedException e)
                {
                    log.warn("InterruptedException!!! - move on", e)
                }
                repeat++
            }
        }

        if(repeat >= maxRepeat){
            log.warn"  wait for a user task failed after $maxRepeat repeat"
        }
    }
}
