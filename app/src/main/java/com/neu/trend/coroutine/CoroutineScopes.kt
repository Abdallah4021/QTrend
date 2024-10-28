package com.neu.trend.coroutine

import kotlinx.coroutines.CoroutineScope



interface DefaultCoroutineScope : CoroutineScope
interface IOCoroutineScope : CoroutineScope
interface MainCoroutineScope : CoroutineScope
interface MainImmediateCoroutineScope : CoroutineScope
interface UnconfinedCoroutineScope : CoroutineScope