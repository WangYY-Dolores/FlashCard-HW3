package com.example.flashcard_hw3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var num1Text : TextView
    private lateinit var num2Text : TextView
    private lateinit var operationText : TextView
    private lateinit var welcome : TextView
    private lateinit var answerEditText : EditText
    private lateinit var generateBtn : Button
//    private lateinit var radioGroup: RadioGroup
    private lateinit var submit : Button

    private var curQuestion = 0
    private var score = 0
    private var curAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val username = intent.getStringExtra("admin") ?: "Guest"
        Toast.makeText(this, "Welcome, $username!", Toast.LENGTH_SHORT).show()
        welcome = findViewById(R.id.welcome_msg)
        val welcomeMsg = getString(R.string.welcome_msg, username)
        welcome.text = welcomeMsg


        num1Text = findViewById(R.id.num1)
        num2Text = findViewById(R.id.num2)
        operationText = findViewById(R.id.operation)
        answerEditText = findViewById(R.id.answerText)
        generateBtn = findViewById(R.id.generate)
//        radioGroup = findViewById(R.id.radioGroup)
        submit = findViewById(R.id.submit)

        savedInstanceState?.let {
            score = it.getInt("score")
            curQuestion = it.getInt("curQuestion")
            num1Text.text = it.getString("num1Text")
            num2Text.text = it.getString("num2Text")
            operationText.text = it.getString("operationText")
            generateBtn.isEnabled = it.getBoolean("generateBtnEnabled")
            submit.isEnabled = it.getBoolean("submitEnabled")
            curAnswer = it.getInt("curAnswer")
            welcome.text = it.getString("welcomeMsg")
        }


        generateBtn.setOnClickListener {
            generateQuestions()
            generateBtn.isEnabled = false
            submit.isEnabled = true
        }

        submit.setOnClickListener {
            checkAnswer()

        }


//        findViewById<RadioButton>(R.id.addition).setOnClickListener {
//            updateOperation(true)
//        }
//
//        findViewById<RadioButton>(R.id.subtraction).setOnClickListener {
//            updateOperation(false)
//        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("score", score)
        outState.putInt("curQuestion", curQuestion)
        outState.putString("num1Text", num1Text.text.toString())
        outState.putString("num2Text", num2Text.text.toString())
        outState.putString("operationText", operationText.text.toString())
        outState.putBoolean("generateBtnEnabled", generateBtn.isEnabled)
        outState.putBoolean("submitEnabled", submit.isEnabled)
        outState.putInt("curAnswer", curAnswer)
        outState.putString("welcomeMsg", welcome.text.toString())
    }

    private fun generateQuestions() {
        curQuestion = 1
        generateRandomQuestion()
    }

    private fun generateRandomQuestion() {
        val num1 = (1..99).random()
        val num2 = (1..20).random()

        num1Text.text = num1.toString()
        num2Text.text = num2.toString()

        val isAddition = Random.nextBoolean()
        updateOperation(isAddition)
    }

    private fun updateOperation(isAddition: Boolean) {
        operationText.text = if (isAddition) "+" else "-"
        val num1 = num1Text.text.toString().toInt()
        val num2 = num2Text.text.toString().toInt()
        curAnswer = if (isAddition) num1 + num2 else num1 - num2
    }

    private fun checkAnswer() {
        val userAnswer = answerEditText.text.toString().toIntOrNull()
        if (userAnswer == curAnswer) {
            score ++
        }
        if (curQuestion < 10) {
            curQuestion ++
            generateRandomQuestion()
        } else {
            Toast.makeText(this, "$score out of 10", Toast.LENGTH_SHORT).show()
            resetGame()
        }
    }

    private fun resetGame() {
        score = 0;
        generateBtn.isEnabled = true
        submit.isEnabled = false
    }
}



