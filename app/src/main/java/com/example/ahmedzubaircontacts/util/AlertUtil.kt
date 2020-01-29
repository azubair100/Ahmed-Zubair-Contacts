package com.example.ahmedzubaircontacts.util

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.example.ahmedzubaircontacts.R
import com.google.android.material.textfield.TextInputEditText

class AlertUtil {
    companion object{
        fun newPhoneAlert(context: Context){
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.new_phone_number_alert_layout)

            val phoneType = dialog.findViewById<TextInputEditText>(R.id.phoneTypeTIET)
            val phoneNumber = dialog.findViewById<TextInputEditText>(R.id.numberTIET)
            val saveBtn = dialog.findViewById<Button>(R.id.savePhoneBtn)
            val cancelBtn = dialog.findViewById<Button>(R.id.cancelBtn)

            phoneNumber?.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(s.toString().trim().isNotEmpty()) phoneNumber.error = null
                    else phoneNumber.error = context.getString(R.string.field_required)
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    saveBtn?.isEnabled = s.toString().trim().isNotEmpty()
                }
            })

            cancelBtn?.setOnClickListener{ dialog.dismiss() }
            saveBtn?.setOnClickListener {
                val type = phoneType?.text.toString()
                val number = phoneNumber?.text.toString()
                /*todo otto eventbus
                phoneTextDisplay.add("$type: $number")
                newContactAdapterPhone.updateContactDetailsList(phoneTextDisplay)*/
                dialog.dismiss()

            }
            dialog.show()
        }

        fun newEmailAlert(){

        }

        fun newAddressAlert(){

        }
    }
}