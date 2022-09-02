package com.chrisabbod.wordscramble.ui.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.chrisabbod.wordscramble.R
import com.chrisabbod.wordscramble.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d(
            "GameFragment", "Word: ${viewModel.currentScrambledWord} " +
                    "Score: ${viewModel.score} WordCount: ${viewModel.currentWordCount}"
        )

        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Observe currentScrambledWord LiveData
        viewModel.currentScrambledWord.observe(viewLifecycleOwner) { newWord ->
            binding.tvUnscrambledWord.text = newWord
        }
        //Observe score LiveData
        viewModel.score.observe(viewLifecycleOwner) { newScore ->
            binding.tvScore.text = getString(R.string.score, newScore)
        }
        //Observe currentWordCount LiveData
        viewModel.currentWordCount.observe(viewLifecycleOwner) { newWordCount ->
            binding.tvWordCount.text = getString(
                R.string.word_count,
                newWordCount,
                MAX_NO_OF_WORDS
            )
        }

        binding.btnSubmit.setOnClickListener { onSubmitWord() }
        binding.btnSkip.setOnClickListener { onSkipWord() }
    }

    private fun onSubmitWord() {
        val playerWord = binding.etInput.text.toString()

        if (viewModel.isUserWordCorrect(playerWord)) {
            setErrorTextField(false)
            if (!viewModel.nextWord()) {
                showFinalScoreDialog()
            }
        } else {
            setErrorTextField(true)
        }
    }

    private fun onSkipWord() {
        if (viewModel.nextWord()) {
            setErrorTextField(false)
        } else {
            showFinalScoreDialog()
        }
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

    //AlertDialog is placed in the Fragment because it is UI related
    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, viewModel.score.value))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()
    }

    private fun restartGame() {
        viewModel.reinitializeData()
        setErrorTextField(false)
    }

    private fun exitGame() {
        activity?.finish()
    }
}