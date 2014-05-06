package org.app.core.util

import org.activiti.engine.ProcessEngine
import org.activiti.engine.RuntimeService
import org.springframework.context.ApplicationContext

/**
 * Created by ngandriau on 5/4/14.
 */
class ActivitiBeans {

    ProcessEngine processEngine;
    RuntimeService runtimeService;

    static ActivitiBeans getBeans(ApplicationContext ctx){

        def beans = new ActivitiBeans(processEngine: ctx.getBean("processEngine"),
                runtimeService: ctx.getBean("processEngine").getRuntimeService())

        return beans
    }

}
