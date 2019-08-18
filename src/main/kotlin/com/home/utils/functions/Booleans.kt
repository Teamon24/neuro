package com.home.utils.functions

infix fun Boolean.then(predicate: () -> Any) = if (this) predicate() else {}