# Spring Data Repositories - A Deep Dive

*   fix the rest controller to return json
    *   https://spring.io/blog/2013/05/11/content-negotiation-using-spring-mvc


I should change this project name.

*   I started it from  the [repositories-deepdive from Oliver Gierke](https://github.com/olivergierke/repositories-deepdive)
*   But it has not a lot in common now

This project is my sandbox to play with the following stack of techno:

*   Java & groovy
*   Spring
    *   java spring configuration (no spring boot, not yet ;-) )
    *   spring data repository
*   hibernate via JPA
*   activiti bpm
*   spock
*   gradle

## quick howto

*   only dependency: java 7 or 8 (maybe 6, not tested)
*   clone the repo in direcotry <repo_home>
*   `#cd <repo_home>`
*   `./gradlew clean executeBookOrderProcess --daemon`
*   You should see some trace similar to this, but may vary as:
    *   list of books in order is randomly picked
    *   approval, if necessary, is random

```JAVA
[DEBUG] o.a.b.BookOrderProcessService -   Missing key book in BD => load sample data
[INFO ] o.a.b.BookOrderProcessService - deployOrderProcess()
[INFO ] o.a.e.i.b.d.BpmnDeployer - Processing resource processes/bookorder.bpmn
[INFO ] o.a.b.BookOrderProcessService - queryActiveProcesses()
[DEBUG] o.a.b.BookOrderProcessService - found 0 active processes
[INFO ] o.a.b.BookOrderProcessService - executeNewBookOrderProcess()
[DEBUG] o.a.b.BookOrderProcessService -   new proc started with id: 5 - from proc def:bookorder:1:4
[INFO ] o.a.b.BookOrderProcessService - waitForAUserTask()
[INFO ] o.a.b.BookOrderProcessService - newOrder()
[DEBUG] o.a.b.BookOrderProcessService -   process param[isbns]=[9781617290121, 1932394354, 1932394354, 1-932394-88-5 ]
[DEBUG] o.a.b.BookOrderProcessService -   process param[customerLastname]=GANDRIAU
[DEBUG] o.a.b.BookOrderProcessService -   found [0] waiting user tasks.
[DEBUG] o.a.b.BookOrderProcessService -   found a customer with id:1 and matching lastname:GANDRIAU. Use it
[DEBUG] o.a.b.BookOrderProcessService -   add book(9781617290121) in order
[DEBUG] o.a.b.BookOrderProcessService -   add book(1932394354) in order
[DEBUG] o.a.b.BookOrderProcessService -   add book(1932394354) in order
[DEBUG] o.a.b.BookOrderProcessService -   add book(1-932394-88-5 ) in order
[INFO ] o.a.b.BookOrderProcessService - checkForApproval() - max amount for auto approval = 75
[DEBUG] o.a.b.BookOrderProcessService -   found the order with id(2)
[DEBUG] o.a.b.BookOrderProcessService -   order amount = 102.70
[DEBUG] o.a.b.BookOrderProcessService -     order need approval!
[DEBUG] o.a.b.BookOrderProcessService -   found [1] waiting user tasks.
[INFO ] o.a.b.BookOrderProcessService - executeEveryUserTask()
[DEBUG] o.a.b.BookOrderProcessService -   found [1] waiting user tasks. Complete them all:
[DEBUG] o.a.b.BookOrderProcessService -     complete Task(Validate By Manager ) with id(20 )
[DEBUG] o.a.b.BookOrderProcessService -   ok, validation approved randomly
[INFO ] o.a.b.BookOrderProcessService - waitEndOfAnyProc()
[INFO ] o.a.b.BookOrderProcessService - queryActiveProcesses()
[DEBUG] o.a.b.BookOrderProcessService - found 1 active processes
[DEBUG] o.a.b.BookOrderProcessService -   found 1 active proc, wait...
Hourra Order Submited
[INFO ] o.a.b.BookOrderProcessService - queryActiveProcesses()
[DEBUG] o.a.b.BookOrderProcessService - found 0 active processes
```

# Content of the project

*   a few entity reprensting book, customer and bookorder
*   a few repositories to have CRUD and a few more search on them
*   a BookOrder Process in BPMN V2.0
*   a few utils to test it:
    *   loadSampleData
    *   execute a bookOrderProcess
*   some spock specifications, but not enough

# Basic Idea

*   Production code is in java
    *   entity, service, repositories
    *   I will try a full groovy version soon
*   Utils and Tests are in groovy
    *   Both cohabite nicelly with gradle and intellij IDEA



