package exceptionHandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main(){
    val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        println("Caught exception $throwable")
    }
    val scope = CoroutineScope(Job())
    //Always ensure to pass exception handler to top level coroutine
    //(i.e. a coroutine launched directly from the scope or
    //a coroutine that is the direct child of a supervisor job.)
    scope.launch(exceptionHandler) {
        //NOTE: Imp, If I pass the exceptionHandler to the child coroutine and
        //the exceptionHandler is not present in the parent coroutine, then again,
        //the code will crash.
        launch {
            throwError()
        }
    }
    Thread.sleep(100)
}

fun throwError() {
    throw IllegalStateException("Encountered error.")
}