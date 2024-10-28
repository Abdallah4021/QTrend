package com.neu.trend.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext



// DEFAULT
fun DefaultCoroutineScope(
    job: Job = SupervisorJob()
): DefaultCoroutineScope = object : DefaultCoroutineScope {
    override val coroutineContext = job + Dispatchers.Default
}

fun DefaultCoroutineScope(
    context: CoroutineContext
): DefaultCoroutineScope = object : DefaultCoroutineScope {
    override val coroutineContext = context + Dispatchers.Default
}

// MAIN
fun MainCoroutineScope(
    job: Job = SupervisorJob()
): MainCoroutineScope = object : MainCoroutineScope {
    override val coroutineContext = job + Dispatchers.Main
}

fun MainCoroutineScope(
    context: CoroutineContext
): MainCoroutineScope = object : MainCoroutineScope {
    override val coroutineContext = context + Dispatchers.Main
}

// IO
fun IOCoroutineScope(
    job: Job = SupervisorJob()
): IOCoroutineScope = object : IOCoroutineScope {
    override val coroutineContext = job + Dispatchers.IO
}

fun IOCoroutineScope(
    context: CoroutineContext
): IOCoroutineScope = object : IOCoroutineScope {
    override val coroutineContext = context + Dispatchers.IO
}

// Immediate
fun MainImmediateCoroutineScope(
    job: Job = SupervisorJob()
): MainImmediateCoroutineScope = object : MainImmediateCoroutineScope {
    override val coroutineContext = job + Dispatchers.Main.immediate
}

fun MainImmediateCoroutineScope(
    context: CoroutineContext
): MainImmediateCoroutineScope = object : MainImmediateCoroutineScope {
    override val coroutineContext = context + Dispatchers.Main.immediate
}

// Immediate
fun UnconfinedCoroutineScope(
    job: Job = SupervisorJob()
): UnconfinedCoroutineScope = object : UnconfinedCoroutineScope {
    override val coroutineContext = job + Dispatchers.Unconfined
}

fun UnconfinedCoroutineScope(
    context: CoroutineContext
): UnconfinedCoroutineScope = object : UnconfinedCoroutineScope {
    override val coroutineContext = context + Dispatchers.Unconfined
}

val coroutineMain: CoroutineContext = Dispatchers.Main
val coroutineUnconfined: CoroutineContext = Dispatchers.Unconfined
val coroutineImmediate: CoroutineContext = Dispatchers.Main.immediate
val coroutineIO: CoroutineContext = Dispatchers.IO
val coroutineDefault: CoroutineContext = Dispatchers.Default