package io.kotoba.app.domain.usecase

import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.StudyStats
import io.kotoba.app.domain.repository.GrammarRepository
import io.kotoba.app.domain.repository.KanjiRepository
import io.kotoba.app.domain.repository.StoryRepository
import io.kotoba.app.domain.repository.VocabRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

class DashboardUseCases(
    private val vocabRepository: VocabRepository,
    private val kanjiRepository: KanjiRepository,
    private val grammarRepository: GrammarRepository,
    private val storyRepository: StoryRepository
) {

    fun statsForLevel(level: JlptLevel): Flow<StudyStats> {
        val vocab = combine(
            vocabRepository.countByLevel(level),
            vocabRepository.countLearnedByLevel(level)
        ) { tv, lv -> tv to lv }
        val kanji = combine(
            kanjiRepository.countByLevel(level),
            kanjiRepository.countLearnedByLevel(level)
        ) { tk, lk -> tk to lk }
        val grammar = combine(
            grammarRepository.countByLevel(level),
            grammarRepository.countLearnedByLevel(level)
        ) { tg, lg -> tg to lg }
        return combine(vocab, kanji, grammar, storyRepository.countByLevel(level)) { (tv, lv), (tk, lk), (tg, lg), ts ->
            StudyStats(
                totalVocab = tv,
                learnedVocab = lv,
                totalKanji = tk,
                learnedKanji = lk,
                totalGrammar = tg,
                learnedGrammar = lg,
                dueCards = 0,
                streakDays = 0
            )
        }.distinctUntilChanged()
    }
}