package exceptionHandling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main(){
    val scope = CoroutineScope(Job())
    scope.launch {
        try{
            //Exception within a coroutine gets propagated directly to
            //parent coroutine. Since here, the parent coroutine also doesn't
            //have any exception handling mechanism, the error gets
            //propagated to the uncaught exception handler of the thread
            //and the code crashes.
            launch {
                throw IllegalArgumentException("Error thrown")
            }
        } catch (e: Exception){
            println("Caught exception $e")
        }
    }
    Thread.sleep(100)
}
