package com.scribd.domain.usecase

interface CaseToViewModulesNew {
    sealed class Response {
        class Success: Response() {
            val response: Any = Unit
        }
    }
}
