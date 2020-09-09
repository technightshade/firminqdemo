package com.sample.logindemo

import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout


class MainActivity : AppCompatActivity() {

    val EMAIL="email"
    val PASSWORD="password"
    val CONFIRM_PASSWORD="confirm_pawssword"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val email=findViewById<EditText>(R.id.email)
        if(!email.text.toString().trim().isEmpty())
        outState.putString(EMAIL,email.text.toString())

        val password=findViewById<EditText>(R.id.password)
        if(!password.text.toString().trim().isEmpty())
            outState.putString(PASSWORD,password.text.toString())

        val confirmPassword=findViewById<EditText>(R.id.confirm_password)
        if(!confirmPassword.text.toString().trim().isEmpty())
            outState.putString(CONFIRM_PASSWORD,confirmPassword.text.toString())
    }




    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val email=savedInstanceState.getString(EMAIL)
        if(!email.isNullOrEmpty()){
            findViewById<EditText>(R.id.email).setText(email)
        }

        val password=savedInstanceState.getString(PASSWORD)
        if(!password.isNullOrEmpty()){
            findViewById<EditText>(R.id.password).setText(password)
        }

        val confirmPassword=savedInstanceState.getString(CONFIRM_PASSWORD)
        if(!confirmPassword.isNullOrEmpty()){
            findViewById<EditText>(R.id.confirm_password).setText(confirmPassword)
        }
    }


    private fun initViews(){
        val layoutEmail = findViewById<TextInputLayout>(R.id.email_parent)
        layoutEmail.isErrorEnabled = true
        layoutEmail.error = getString(R.string.incorrect_email)

        addTextChangeListenerToEmail(layoutEmail)

        val layoutPassword = findViewById<TextInputLayout>(R.id.password_parent)
        layoutPassword.isErrorEnabled = true
        layoutPassword.error = getString(R.string.blank_password)

        addTextChangeListenerToPassword(layoutPassword)

        val layoutConfirmPassword = findViewById<TextInputLayout>(R.id.confirm_password_parent)
        layoutConfirmPassword.isErrorEnabled = true
        layoutConfirmPassword.error = getString(R.string.blank_confirm_password)

        addTextChangeListenerToConfirmPassword(layoutConfirmPassword)

        findViewById<Button>(R.id.button_submit).setOnClickListener {

            if(layoutEmail.error==null && layoutPassword.error==null && layoutConfirmPassword.error==null){
                showAlertDialog()
            }else{
                Toast.makeText(this, "Please resolve errors", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showAlertDialog() {
        val email=findViewById<EditText>(R.id.email)
        val builder = AlertDialog.Builder(this)
        builder.setMessage("welcome "+email.text.toString())
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }


    private fun addTextChangeListenerToEmail(layoutEmail: TextInputLayout) {
        val emailEditText=findViewById<EditText>(R.id.email)
        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                validateEmail(p0, layoutEmail)
            }

        })
    }

    private fun validateEmail(editable: Editable?, layoutEmail: TextInputLayout) {
        if(editable!!.trim().isBlank()){
            layoutEmail.error=getString(R.string.blank_email)
            return
        }

        if(!isValidEmail(editable)){
            layoutEmail.error=getString(R.string.incorrect_email)
            return
        }

        layoutEmail.error=null
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }


    private fun addTextChangeListenerToPassword(layoutPassword: TextInputLayout) {
        val passwordEditText=findViewById<EditText>(R.id.password)
        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                validatePassword(p0, layoutPassword)
            }

        })
    }

    private fun validatePassword(editable: Editable?, layoutPassword: TextInputLayout) {
        if(editable!!.trim().isBlank()){
            layoutPassword.error=getString(R.string.blank_password)
            return
        }

        layoutPassword.error=null
    }




    private fun addTextChangeListenerToConfirmPassword(layoutConfirmPassword: TextInputLayout) {
        val confirmPasswordEditText=findViewById<EditText>(R.id.confirm_password)
        confirmPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                validateConfirmPassword(p0, layoutConfirmPassword)
            }

        })
    }


    private fun validateConfirmPassword(editable: Editable?, layoutConfirmPassword: TextInputLayout) {
        if(editable!!.trim().isBlank()){
            layoutConfirmPassword.error=getString(R.string.blank_confirm_password)
            return
        }

        val passwordEditText=findViewById<EditText>(R.id.password)
        if(passwordEditText.toString() != editable.toString()){
            layoutConfirmPassword.error=getString(R.string.mismatch_password)
        }

        layoutConfirmPassword.error=null
    }
}