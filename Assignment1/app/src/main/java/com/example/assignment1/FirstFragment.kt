package com.example.assignment1

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

/** The opening screen. Shows five buttons; */
class FirstFragment : Fragment(R.layout.fragment_first) {

    /** Every selectable button on this screen. */
    private val selectableButtonIds = listOf(
        R.id.button_one,
        R.id.button_two,
        R.id.button_three,
        R.id.button_four,
        R.id.button_five
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachClickListenerToEachButton(view)
    }

    /** Gives every button in selectableButtonIds it's behavior. */
    private fun attachClickListenerToEachButton(rootView: View) {
        for (buttonId in selectableButtonIds) {
            val button = rootView.findViewById<Button>(buttonId)
            button.setOnClickListener {
                navigateToSecondFragment(button.text.toString())
            }
        }
    }

    /**
     * Replaces this fragment with SecondFragment, passing the label of
     * the button the user chose.
     */
    private fun navigateToSecondFragment(selectedButtonLabel: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SecondFragment.newInstance(selectedButtonLabel))
            .addToBackStack(null)
            .commit()
    }
}