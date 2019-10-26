package com.home.utils.functions

infix fun Boolean.ifTrue(predicate: () -> Any) = if (this) predicate() else {}