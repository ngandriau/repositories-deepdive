import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender


appender("CONSOLE", ConsoleAppender) {

    encoder(PatternLayoutEncoder) {
        //        Cannot make the mdc work :-)
        //        pattern = "%-5level %logger{15} - mdcProcDefId=%X{mdcProcessDefinitionID} - ndcExecutionId=%X{mdcExecutionId} - mdcProcessInstanceID=%X{mdcProcessInstanceID} mdcBusinessKey=%X{mdcBusinessKey}- %msg%n %xException"

        pattern = "[%-5level] %logger{15} - %msg%n %xException"
        //        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
}

root(INFO, ["CONSOLE"])


//=== Hibernate specific loggers
//logger ("org.hibernate.SQL", DEBUG) //display show jpql queries
//logger ("org.hibernate.type.descriptor.sql.BasicBinder", TRACE) //print bound parameters of the queries.
//logger ("org.hibernate.hql.internal.ast.QueryTranslatorImpl", DEBUG) //print additional info on query.
//logger ("org.hibernate", INFO)


//=== Transaction management
// when activiti is standalone
//logger ("org.apache.ibatis.transaction.jdbc.JdbcTransaction", DEBUG)
//logger ("org.activiti.engine.impl.cfg.standalone.StandaloneMybatisTransactionContext", DEBUG)

// when activiti use spring transaction manager
//logger ("org.springframework.jdbc.datasource.DataSourceTransactionManager", DEBUG)


//=== REST Call

logger ("org.apache.http.wire",DEBUG)
//logger ("org.apache.http.headers",DEBUG)

// TEST DEBUG
logger ("org.app.core.util", DEBUG)
logger ("org.app.core.service", DEBUG)
logger ("org.app.core.util.GenericProcessEventListener", INFO)