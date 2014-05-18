package org.app.rest.util

import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

/**
 * Created by ngandriau on 5/5/14.
 */
@Slf4j
class QuickRestCalls {

    static def restEndPoint = "http://localhost:8080"
    static def BASE_URI  = "/mycomp"

    def restClient = new RESTClient(restEndPoint)


    public static void main(String[] args) {
        QuickRestCalls tool = new QuickRestCalls()

        try {
            tool.getEveryOrders()
        } catch (e) {
            log.error("ex:", e)
        }

        try {
            tool.getOrder(123)
        } catch (e) {
            log.error("ex:", e)
        }

        try {
            tool.createAnOrder()
        } catch (e) {
            log.error("ex:", e)
        }
    }

    void getEveryOrders(){
        def response = restClient.get(
                path: "$BASE_URI/orders",
                contentType: ContentType.JSON,
                requestContentType: ContentType.JSON
        )
    }

    void getOrder(Long id){
        def response = restClient.get(
                path: "$BASE_URI/orders/$id",
                contentType: ContentType.JSON,
                requestContentType: ContentType.JSON
        )
    }

    void createAnOrder() {
        log.debug "createAnOrder()"

        def orderJson = """{'dateTimeOfSubmission':'1985-04-12T23:20:50.52Z', 'id':'123'}"""

        def response = restClient.post(
                path: "$BASE_URI/orders",
                body: [id:'123'],
//                body: [dateTimeOfSubmission:'1985-04-12T23:20:50.52Z', id:'123'],
                contentType: ContentType.JSON,
                requestContentType: ContentType.JSON
        )
    }
}
