package com.example.agecalcul

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var textView2 : TextView? = null
    private var tvAge : TextView? = null

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDOB = findViewById<Button>(R.id.btnDOB)

        val btnCancel = findViewById<Button>(R.id.btnCancel)

        val etName = findViewById<EditText>(R.id.etName)

        val textView1 = findViewById<TextView>(R.id.tv1)
        textView2 = findViewById(R.id.tv2)

        tvAge = findViewById(R.id.tvAge)

        btnDOB.setOnClickListener {

            val inputText = etName.text.toString()
            textView1.text = "Hi! $inputText"

            etName.clearFocus()

            clickDatePicker()

            if (btnCancel.visibility != View.VISIBLE) {
                btnCancel.visibility = View.VISIBLE
            }

        }

        btnCancel.setOnClickListener {

            etName.text.clear()
            textView1.text = ""
            textView2?.text = ""
            tvAge?.text = ""
            btnCancel.visibility = View.GONE

        }

    }



    @SuppressLint("SetTextI18n")
    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

                textView2?.text = "Your DateOfBirth is $selectedDate"

                val sdf = SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)

                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        val differenceInHours = differenceInMinutes / 60
                        val differenceInDays = differenceInMinutes / (60 * 24)

                        tvAge?.text = "Your Age in minutes : $differenceInMinutes\n" +
                                "Your Age in Hours : $differenceInHours\n" +
                                "Your Age in Days : $differenceInDays"

                    }

                }

            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
    }


}