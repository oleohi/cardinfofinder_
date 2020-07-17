package com.oleohialli.cardinfofinder.viewmodels

import android.app.Application
import android.content.DialogInterface
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.AndroidViewModel
import com.github.ybq.android.spinkit.SpinKitView
import com.oleohialli.cardinfofinder.MainActivity
import com.oleohialli.cardinfofinder.R
import com.oleohialli.cardinfofinder.models.ResponseDTO
import com.oleohialli.cardinfofinder.networking.generateService
import com.oleohialli.cardinfofinder.networking.service.CardInfoService
import com.oleohialli.cardinfofinder.utils.dismissDialog
import com.oleohialli.cardinfofinder.utils.errorDialog
import com.oleohialli.cardinfofinder.utils.hideLoader
import com.oleohialli.cardinfofinder.utils.showLoader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(application: Application) : AndroidViewModel(application) {

    fun getCardInfo(
        activity: MainActivity,
        cardNumber: String,
        progressLoader: SpinKitView,
        cardBrand: TextView,
        cardType: TextView,
        bank: TextView,
        country: TextView,
        detailsCardView: CardView,
        constraintLayout: ConstraintLayout) {

        val retryAction: DialogInterface.OnClickListener =
            DialogInterface.OnClickListener { _, _ ->
                getCardInfo(activity, cardNumber, progressLoader, cardBrand, cardType, bank, country, detailsCardView, constraintLayout)
            }

        detailsCardView.visibility = View.GONE
        showLoader(activity, progressLoader)

        val call = generateService(CardInfoService::class.java).getCardInfo(cardNumber)
        call.enqueue(object : Callback<ResponseDTO> {
            override fun onResponse(
                call: Call<ResponseDTO>,
                response: Response<ResponseDTO>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.code() == 200) {
                            hideLoader(activity, progressLoader)
                            detailsCardView.visibility = View.VISIBLE
                            cardBrand.text = response.body()?.brand
                            cardType.text = response.body()?.type
                            bank.text = response.body()?.bank?.name ?: ""
                            country.text = response.body()?.country?.name ?: ""

                        } else {
                            hideLoader(activity, progressLoader)
                            errorDialog(activity,
                                constraintLayout,
                                activity.getString(R.string.problem_text),
                                response.message(),
                                activity.getString(R.string.retry_text),
                                DialogInterface.OnClickListener { _, _ -> retryAction
                                })
                        }
                    } else {
                        hideLoader(activity, progressLoader)
                        errorDialog(activity,
                            constraintLayout,
                            activity.getString(R.string.problem_text),
                            "Sorry, we can't find what you're looking for",
                            activity.getString(R.string.dismiss_text),
                            DialogInterface.OnClickListener { _, _ -> dismissDialog()
                            })
                    }
                } else {
                    hideLoader(activity, progressLoader)
                    errorDialog(activity,
                        constraintLayout,
                        activity.getString(R.string.problem_text),
                        "Sorry, we can't find any information on the card provided.",
                        activity.getString(R.string.dismiss_text),
                        DialogInterface.OnClickListener { _, _ -> dismissDialog()
                        })
                }
            }

            override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                hideLoader(activity, progressLoader)
                errorDialog(activity,
                    constraintLayout,
                    activity.getString(R.string.network_error),
                    activity.getString(R.string.connection_error_text),
                    activity.getString(R.string.retry_text),
                    DialogInterface.OnClickListener { _, _ -> retryAction
                    })
            }

        })

    }

}