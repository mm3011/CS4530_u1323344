package com.example.assignment1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

/** Displays the label of whichever button was tapped on, with a back button*/
class SecondFragment : Fragment(R.layout.fragment_second) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displaySelectedButtonLabel(view)
        setUpBackButton(view)
    }

    /** Pulls the label of button the user clicked*/
    private fun displaySelectedButtonLabel(rootView: View) {
        val selectedButtonLabel = arguments?.getString(ARG_SELECTED_BUTTON_LABEL).orEmpty()
        val selectionTextView = rootView.findViewById<TextView>(R.id.text_selected_button)
        selectionTextView.text = getString(R.string.selection_message, selectedButtonLabel)
    }

    /** Reveals the first fragment again. */
    private fun setUpBackButton(rootView: View) {
        val backButton = rootView.findViewById<Button>(R.id.button_go_back)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        /** Key used to store and retrieve the chosen button label */
        private const val ARG_SELECTED_BUTTON_LABEL = "selected_button_label"

        /** Creates a SecondFragment that already knows which label to display. */
        fun newInstance(selectedButtonLabel: String): SecondFragment {
            val fragment = SecondFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_SELECTED_BUTTON_LABEL, selectedButtonLabel)
            }
            return fragment
        }
    }
}