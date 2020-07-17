package com.oleohialli.cardinfofinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.oleohialli.cardinfofinder.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        findButton.setOnClickListener {
            if (cardNumberField.text.toString().trim() == "" || cardNumberField.text.toString().trim().isEmpty()) {
                cardNumberLayout.error = "Enter a credit or debit card number to find"
            } else {
                cardNumberLayout.error = ""
                val cardNumberString = cardNumberField.text.toString().trim()
                viewModel.getCardInfo(this, cardNumberString, progressLoader, cardBrandTextView, cardTypeTextView,
                bankTextView, countryTextView, detailsCardView, constraintLayout)
            }
        }
    }
}