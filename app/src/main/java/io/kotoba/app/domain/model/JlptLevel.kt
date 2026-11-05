package io.kotoba.app.domain.model

enum class JlptLevel(
    val code: String,
    val label: String,
    val vocabTarget: Int
) {
    N5(code = "N5", label = "N5", vocabTarget = 1000),
    N4(code = "N4", label = "N4", vocabTarget = 1500),
    N3(code = "N3", label = "N3", vocabTarget = 2000),
    N2(code = "N2", label = "N2", vocabTarget = 3000),
    N1(code = "N1", label = "N1", vocabTarget = 4000)
}