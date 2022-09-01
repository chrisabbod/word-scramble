package com.chrisabbod.wordscramble.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.chrisabbod.wordscramble.R
import com.chrisabbod.wordscramble.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateNextWordOnScreen()

        binding.apply {
            btnSubmit.setOnClickListener { onSubmitWord() }
            btnSkip.setOnClickListener { onSkipWord() }
            tvWordCount.text = getString(
                R.string.word_count, 0, MAX_NO_OF_WORDS
            )
            tvScore.text = getString(
                R.string.score, viewModel.score
            )
        }
    }

    private fun onSubmitWord() {
//        currentScrambledWord = getNextScrambledWord()
//        currentWordCount++
//        score += SCORE_INCREASE
        binding.tvScore.text = getString(R.string.score, viewModel.score)
        binding.tvWordCount.text = getString(
            R.string.word_count,
            viewModel.currentWordCount,
            MAX_NO_OF_WORDS
        )
        setErrorTextField(false)
        updateNextWordOnScreen()
    }

    private fun onSkipWord() {
//        currentScrambledWord = getNextScrambledWord()
//        currentWordCount++
        binding.tvWordCount.text = getString(
            R.string.word_count,
            viewModel.currentWordCount,
            MAX_NO_OF_WORDS
        )
        setErrorTextField(false)
        updateNextWordOnScreen()
    }

    private fun getNextScrambledWord(): String {
        val tempWord = allWordsList.random().toCharArray()
        tempWord.shuffle()

        return String(tempWord)
    }

    private fun updateNextWordOnScreen() {
        binding.tvUnscrambledWord.text = viewModel.currentScrambledWord
    }

    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.etInput.text = null
        }
    }
}