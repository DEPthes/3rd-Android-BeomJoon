package com.example.android_basic_study_01

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_basic_study_01.databinding.ActivityMainBinding
import java.util.Stack

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var isFirstInput = true
    private val operands = mutableListOf<Double>()
    private val operators = mutableListOf<String>()
    private var tmpNum: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 숫자 버튼에 대한 클릭 리스너 설정
        val numberButtonIds = listOf(
            binding.btn0,
            binding.btn1,
            binding.btn2,
            binding.btn3,
            binding.btn4,
            binding.btn5,
            binding.btn6,
            binding.btn7,
            binding.btn8,
            binding.btn9
        )

        val operatorButtonIds = listOf(
            binding.btnAdd, binding.btnSub, binding.btnMul, binding.btnDiv
        )

        numberButtonIds.forEach { button ->
            button.setOnClickListener { onNumberButtonClick(button.text.toString()) }
        }
        operatorButtonIds.forEach { button ->
            button.setOnClickListener { onOperatorButtonClick(button.text.toString()) }
        }
        binding.btnRes.setOnClickListener {
            if (isFirstInput) {
                Toast.makeText(this, "입력된 수식이 없습니다.", Toast.LENGTH_SHORT).show()
            } else if (operators.isEmpty()) {
                Toast.makeText(this, "연산자가 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                operands.add(tmpNum.toDouble())
                tmpNum = ""
                val result = evaluateExpression()
                binding.res.text = result.toString()

//                Log.d("Result", result.toString())
//                Log.d("Operands", operands.joinToString())
//                Log.d("Operators", operators.joinToString())
                isFirstInput = true
                operands.clear()
                operators.clear()
            }
        }
        binding.btnClear.setOnClickListener {
            operands.clear()
            operators.clear()
            tmpNum = ""
            binding.res.text = "Result"
            binding.num1.text = "계산할 숫자를 입력해주세요"
            isFirstInput = true
        }

    }

    private fun onNumberButtonClick(text: String) {
        if (isFirstInput) {
            binding.num1.text = text
            isFirstInput = false
        } else {
            binding.num1.append(text)
        }
        tmpNum += text
    }

    private fun onOperatorButtonClick(text: String) {
        if (isFirstInput) {
            Toast.makeText(this, "숫자를 먼저 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            binding.num1.append(text)
            operators.add(text)
            operands.add(tmpNum.toDouble())
            tmpNum = ""
        }
    }

    private fun evaluateExpression(): Double {
        val numberStack = Stack<Double>()
        val operatorStack = Stack<String>()

        for (i in 0 until operators.size) {
            numberStack.push(operands[i])
            while (operatorStack.isNotEmpty() && precedence(operatorStack.peek()) >= precedence(
                    operators[i]
                )
            ) {
                val result =
                    applyOperation(numberStack.pop(), numberStack.pop(), operatorStack.pop())
                numberStack.push(result)
            }
            operatorStack.push(operators[i])
        }

        numberStack.push(operands.last())

        while (operatorStack.isNotEmpty()) {
            val result = applyOperation(numberStack.pop(), numberStack.pop(), operatorStack.pop())
            if (result == Double.POSITIVE_INFINITY || result == Double.NEGATIVE_INFINITY) {
                // 0으로 나누는 예외 처리
                Toast.makeText(this, "0으로 나눌 수 없습니다.", Toast.LENGTH_SHORT).show()
                operators.clear()
                operands.clear()
                return 0.0
                binding.res.text = "Result"
            } else {
                numberStack.push(result)
            }
        }

        return numberStack.pop()
    }

    private fun precedence(op: String): Int {
        return when (op) {
            "+", "-" -> 1
            "*", "/" -> 2
            else -> -1
        }
    }

    private fun applyOperation(b: Double, a: Double, op: String): Double {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            else -> throw IllegalArgumentException("Unsupported operator: $op")
        }
    }
}