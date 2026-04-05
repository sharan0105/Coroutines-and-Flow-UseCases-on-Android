package structurredConcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

/**
 * Basic usecase of these scoping functions is to give suspend
 * functions the capability to run multiple tasks parallelly/concurrently
 */
fun main(){
    val scope = CoroutineScope(Job())
    var coroutineJob: Job? = null
    coroutineJob = scope.launch {
        coroutineScope {
           val jbz1 =  launch {
                println("Started Task 1")
                delay(100)
                println("Completed Task 1")
            }

           val jbz2 =  launch {
                println("Started Task 2")
                delay(200)
                println("Completed Task 2")
            }
            //Giving a 50ms delay so that we can register both the jobs into the coroutine
            //hierarchy
            delay(50)
            val coroutineScopeFunctionJob = this.coroutineContext.job
            println("Is jbz1 child of coroutineScope function => ${coroutineScopeFunctionJob.children.contains(jbz1)}")
            println("Is jbz2 child of coroutineScope function => ${coroutineScopeFunctionJob.children.contains(jbz2)}")
            //Let's check if coroutineScope function is related to launch coroutine's job.
            //Turns out coroutineScope's job is actually a child of coroutineJob
            println("Is coroutineScope function job child of coroutineJob => ${coroutineJob?.children?.contains(coroutineScopeFunctionJob)}")
        }

        launch {
            println("Started Task 3")
            delay(300)
            println("Completed Task 3")
        }
    }
    //Without sleeping the thread, the coroutine will immediately launch and the program will
    //finish immediately that's why, we need to add this so that the above code block
    //can complete.
    Thread.sleep(1000)
}