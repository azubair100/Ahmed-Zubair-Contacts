package com.example.ahmedzubaircontacts.util

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.model.Address
import com.example.ahmedzubaircontacts.model.Email
import com.example.ahmedzubaircontacts.model.Phone
import com.google.android.material.textfield.TextInputEditText
import com.squareup.otto.Bus

class AlertUtil {
    companion object{
        fun newPhoneAlert(context: Context, bus: Bus){
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
                bus.post(Phone(0L, type, number))
                dialog.dismiss()
            }
            dialog.show()
        }

        fun newEmailAlert(context: Context, bus: Bus){
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.new_email_alert_layout)

            val emailType = dialog.findViewById<TextInputEditText>(R.id.emailTypeTIET)
            val emailAddress = dialog.findViewById<TextInputEditText>(R.id.emailTIET)
            val saveBtn = dialog.findViewById<Button>(R.id.saveEmailBtn)
            val cancelBtn = dialog.findViewById<Button>(R.id.cancelBtn)

            emailAddress?.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    if(s.toString().trim().isNotEmpty()) emailAddress.error = null
                    else emailAddress.error = context.getString(R.string.field_required)
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    saveBtn?.isEnabled = s.toString().trim().isNotEmpty()
                }
            })

            cancelBtn?.setOnClickListener{ dialog.dismiss() }
            saveBtn?.setOnClickListener {
                val type = emailType?.text.toString()
                val email = emailAddress?.text.toString()
                bus.post(Email(0L, type, email))
                dialog.dismiss()
            }
            dialog.show()
        }

        fun newAddressAlert(context: Context, bus: Bus){
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.new_address_alert_layout)

            val saveBtn = dialog.findViewById<Button>(R.id.saveAddressBtn)
            val cancelBtn = dialog.findViewById<Button>(R.id.cancelBtn)

            val addressType = dialog.findViewById<TextInputEditText>(R.id.addressTypeTIET)
            val street = dialog.findViewById<TextInputEditText>(R.id.streetTIET)
            val city = dialog.findViewById<TextInputEditText>(R.id.cityTIET)
            val state = dialog.findViewById<TextInputEditText>(R.id.stateTIET)
            val zip = dialog.findViewById<TextInputEditText>(R.id.zipTIET)

            street?.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    if(s.toString().trim().isNotEmpty()) street.error = null
                    else street.error = context.getString(R.string.field_required)
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    saveBtn?.isEnabled = s.toString().trim().isNotEmpty()
                }
            })

            cancelBtn?.setOnClickListener{ dialog.dismiss() }
            saveBtn?.setOnClickListener {
                val type = addressType.text.toString()
                val streetName = street?.text.toString()
                val cityName = city?.text.toString()
                val stateName = state?.text.toString()
                val zipName = zip?.text.toString()
                bus.post(Address(0L, type, streetName, cityName, stateName, zipName))
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}