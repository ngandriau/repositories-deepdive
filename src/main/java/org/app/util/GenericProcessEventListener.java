package org.app.util;

import org.activiti.engine.delegate.event.ActivitiActivityEvent;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ngandriau on 5/3/14.
 */
public class GenericProcessEventListener implements ActivitiEventListener
{
    final static Logger log = LoggerFactory.getLogger(GenericProcessEventListener.class);

    @Override
    public void onEvent(ActivitiEvent event) {


        switch(event.getType()){

            case ENTITY_CREATED:
            {
                ActivitiEntityEvent theEvent = (ActivitiEntityEvent) event;
                log.debug("[EVENT] entity created of type: "+theEvent.getEntity().getClass().getCanonicalName());
                break;
            }

            case ACTIVITY_STARTED:
            {
                ActivitiActivityEvent theEvent = (ActivitiActivityEvent) event;
                log.debug("[EVENT] activity("+theEvent.getActivityId()+") STARTED : ");
                break;
            }

            case ACTIVITY_COMPLETED:
            {
                ActivitiActivityEvent theEvent = (ActivitiActivityEvent) event;
                log.debug ("[EVENT] activity("+theEvent.getActivityId()+") COMPLETED : ");
                break;
            }

            default:
                log.debug ("onEvent("+event.getType()+") - not yet handled");
        }
    }

    @Override
    public boolean isFailOnException() {
        log.warn ("isFailOnException()");
        return false;
    }
}
