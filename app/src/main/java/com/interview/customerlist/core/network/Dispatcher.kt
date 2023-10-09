package com.interview.customerlist.core.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val customerDispatchers: CustomerDispatchers)

enum class CustomerDispatchers {
    Default,
    IO,
}