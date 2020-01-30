package com.example.ahmedzubaircontacts.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.Editable
import android.view.LayoutInflater
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.AlertLayoutCallTextBinding
import com.example.ahmedzubaircontacts.databinding.AlertLayoutNewAddressBinding
import com.example.ahmedzubaircontacts.databinding.AlertLayoutNewEmailBinding
import com.example.ahmedzubaircontacts.databinding.AlertLayoutNewPhoneNumberBinding
import com.example.ahmedzubaircontacts.model.Address
import com.example.ahmedzubaircontacts.model.Email
import com.example.ahmedzubaircontacts.model.Phone
import com.squareup.otto.Bus


class Util {
    companion object {

        fun newPhoneAlert(context: Context, bus: Bus) {
            context.let {
                val binding = DataBindingUtil.inflate<AlertLayoutNewPhoneNumberBinding>(
                    LayoutInflater.from(it),
                    R.layout.alert_layout_new_phone_number, null, false
                )

                val alert = AlertDialog.Builder(it).setView(binding.root).create()
                alert.show()

                binding.cancelBtn?.setOnClickListener { alert.dismiss() }
                binding.savePhoneBtn?.setOnClickListener {
                    val type = binding.phoneTypeTIET?.text.toString()
                    val number = binding.numberTIET?.text.toString()
                    bus.post(Phone(0L, type, number))
                    alert.dismiss()
                }

                binding.numberTIET?.addTextChangedListener(
                    afterTextChanged = fun(it: Editable?) {
                        if (it.toString().trim().isNotEmpty()) binding.numberTIET.error = null
                        else binding.numberTIET.error = context.getString(R.string.field_required)
                    },
                    onTextChanged = fun(s: CharSequence?, _: Int, _: Int, _: Int) {
                        binding.savePhoneBtn.isEnabled = s.toString().trim().isNotEmpty()
                    }
                )
            }
        }

        fun newEmailAlert(context: Context, bus: Bus) {
            context.let {
                val binding = DataBindingUtil.inflate<AlertLayoutNewEmailBinding>(
                    LayoutInflater.from(it),
                    R.layout.alert_layout_new_email, null, false
                )

                val alert = AlertDialog.Builder(it).setView(binding.root).create()
                alert.show()

                binding.cancelBtn?.setOnClickListener { alert.dismiss() }
                binding.saveEmailBtn?.setOnClickListener {
                    val type = binding.emailTypeTIET?.text.toString()
                    val email = binding.emailTIET?.text.toString()
                    bus.post(Email(0L, type, email))
                    alert.dismiss()
                }

                binding.emailTIET?.addTextChangedListener(
                    afterTextChanged = fun(it: Editable?) {
                        if (it.toString().trim().isNotEmpty()) binding.emailTIET.error = null
                        else binding.emailTIET.error = context.getString(R.string.field_required)
                    },
                    onTextChanged = fun(s: CharSequence?, _: Int, _: Int, _: Int) {
                        binding.saveEmailBtn.isEnabled = s.toString().trim().isNotEmpty()
                    }
                )
            }
        }

        fun newAddressAlert(context: Context, bus: Bus) {
            context.let {
                val binding = DataBindingUtil.inflate<AlertLayoutNewAddressBinding>(
                    LayoutInflater.from(it),
                    R.layout.alert_layout_new_address, null, false
                )

                val alert = AlertDialog.Builder(it).setView(binding.root).create()
                alert.show()

                binding.cancelBtn?.setOnClickListener { alert.dismiss() }
                binding.saveAddressBtn?.setOnClickListener {
                    val type = binding.addressTypeTIET?.text.toString()
                    val streetName = binding.streetTIET?.text.toString()
                    val cityName = binding.cityTIET?.text.toString()
                    val stateName = binding.stateTIET?.text.toString()
                    val zipName = binding.zipTIET?.text.toString()
                    bus.post(Address(0L, type, streetName, cityName, stateName, zipName))
                    alert.dismiss()
                }

                binding.streetTIET?.addTextChangedListener(
                    afterTextChanged = fun(it: Editable?) {
                        if (it.toString().trim().isNotEmpty()) binding.streetTIET.error = null
                        else binding.streetTIET.error = context.getString(R.string.field_required)
                    },
                    onTextChanged = fun(s: CharSequence?, _: Int, _: Int, _: Int) {
                        binding.saveAddressBtn.isEnabled = s.toString().trim().isNotEmpty()
                    }
                )
            }
        }

        fun callOrTextAlert(context: Context, number: String) {

            context.let {
                val binding = DataBindingUtil.inflate<AlertLayoutCallTextBinding>(
                    LayoutInflater.from(it),
                    R.layout.alert_layout_call_text, null, false
                )
                val alert = AlertDialog.Builder(it).setView(binding.root).create()
                alert.show()
                binding.canelBtn.setOnClickListener { alert.dismiss() }
                binding.textBtn?.setOnClickListener {
                    sendText(context, number)
                    alert.dismiss()
                }
                binding.callBtn?.setOnClickListener {
                    callPhone(context, number)
                    alert.dismiss()
                }
            }
        }

        private fun sendText(context: Context, number: String) {
            context.startActivity(
                Intent(
                    Intent.ACTION_SENDTO,
                    Uri.parse("smsto:$number")
                ).putExtra("sms_body", "Here goes your message...")
            )
        }

        private fun callPhone(context: Context, number: String) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CALL_PHONE), 10
                )
                return
            } else context.startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$number")))
        }

        fun goToGoogleMaps(context: Context, street: String, city: String) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=$street+$city")
                )
            )
        }

        fun sendEmail(context: Context, number: String) {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", number, null
                )
            )
            context.startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }
    }

}
