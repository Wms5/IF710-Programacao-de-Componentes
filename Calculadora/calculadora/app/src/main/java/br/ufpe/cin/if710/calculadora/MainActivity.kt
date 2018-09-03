package br.ufpe.cin.if710.calculadora

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         var result: String = ""
        btn_0.setOnClickListener {

            result = result + "0"
            text_calc.setText(result)
        }
        btn_1.setOnClickListener {

            result = result + "1"
            text_calc.setText(result)
        }
        btn_2.setOnClickListener {

            result = result + "2"
            text_calc.setText(result)

        }
        btn_Add.setOnClickListener {

            result = result + "+"
            text_calc.setText(result)
        }
        btn_3.setOnClickListener {

            result = result + "3"
            text_calc.setText(result)
        }
        btn_4.setOnClickListener {

            result = result + "4"
            text_calc.setText(result)
        }
        btn_5.setOnClickListener {

            result = result + "5"
            text_calc.setText(result)
        }
        btn_6.setOnClickListener {

            result = result + "6"
            text_calc.setText(result)
        }
        btn_7.setOnClickListener {

            result = result + "7"
            text_calc.setText(result)
        }
        btn_8.setOnClickListener {

            result = result + "8"
            text_calc.setText(result)
        }
        btn_9.setOnClickListener {

            result = result + "9"
            text_calc.setText(result)
        }
        btn_Subtract.setOnClickListener {

            result = result + "-"
            text_calc.setText(result)
        }
        btn_Divide.setOnClickListener {

            result = result + "/"
            text_calc.setText(result)
        }
        btn_Dot.setOnClickListener {

            result = result + "."
            text_calc.setText(result)
        }
        btn_LParen.setOnClickListener {

            result = result + "("
            text_calc.setText(result)
        }
        btn_RParen.setOnClickListener {

            result = result + ")"
            text_calc.setText(result)
        }
        btn_Power.setOnClickListener {

            result = result + "^"
            text_calc.setText(result)
        }
        btn_Multiply.setOnClickListener {

            result = result + "*"
            text_calc.setText(result)
        }
        btn_Clear.setOnClickListener {

            result = ""
            text_info.text = result
            text_calc.setText(result)
        }

        //Como usar a função:
        // eval("2+2") == 4.0
        // eval("2+3*4") = 14.0
        // eval("(2+3)*4") = 20.0
        //Fonte: https://stackoverflow.com/a/26227947
        fun eval(str: String): Double {
            return object : Any() {
                var pos = -1
                var ch: Char = ' '
                fun nextChar() {
                    val size = str.length
                    ch = if ((++pos < size)) str.get(pos) else (-1).toChar()
                }

                fun eat(charToEat: Char): Boolean {
                    while (ch == ' ') nextChar()
                    if (ch == charToEat) {
                        nextChar()
                        return true
                    }
                    return false
                }

                fun parse(): Double {
                    nextChar()
                    val x = parseExpression()
                    if (pos < str.length) throw RuntimeException("Caractere inesperado: " + ch)
                    return x
                }

                // Grammar:
                // expression = term | expression `+` term | expression `-` term
                // term = factor | term `*` factor | term `/` factor
                // factor = `+` factor | `-` factor | `(` expression `)`
                // | number | functionName factor | factor `^` factor
                fun parseExpression(): Double {
                    var x = parseTerm()
                    while (true) {
                        if (eat('+'))
                            x += parseTerm() // adição
                        else if (eat('-'))
                            x -= parseTerm() // subtração
                        else
                            return x
                    }
                }

                fun parseTerm(): Double {
                    var x = parseFactor()
                    while (true) {
                        if (eat('*'))
                            x *= parseFactor() // multiplicação
                        else if (eat('/'))
                            x /= parseFactor() // divisão
                        else
                            return x
                    }
                }

                fun parseFactor(): Double {
                    if (eat('+')) return parseFactor() // + unário
                    if (eat('-')) return -parseFactor() // - unário
                    var x: Double
                    val startPos = this.pos
                    if (eat('(')) { // parênteses
                        x = parseExpression()
                        eat(')')
                    } else if ((ch in '0'..'9') || ch == '.') { // números
                        while ((ch in '0'..'9') || ch == '.') nextChar()
                        x = java.lang.Double.parseDouble(str.substring(startPos, this.pos))
                    } else if (ch in 'a'..'z') { // funções
                        while (ch in 'a'..'z') nextChar()
                        val func = str.substring(startPos, this.pos)
                        x = parseFactor()
                        if (func == "sqrt")
                            x = Math.sqrt(x)
                        else if (func == "sin")
                            x = Math.sin(Math.toRadians(x))
                        else if (func == "cos")
                            x = Math.cos(Math.toRadians(x))
                        else if (func == "tan")
                            x = Math.tan(Math.toRadians(x))
                        else
                            throw RuntimeException("Função desconhecida: " + func)
                    } else {
                        throw RuntimeException("Caractere inesperado: " + ch.toChar())
                    }
                    if (eat('^')) x = Math.pow(x, parseFactor()) // potência
                    return x
                }
            }.parse()
        }
        btn_Equal.setOnClickListener {

            try {
                result = eval(result).toString()
                text_info.text = result
            }
            catch (e: RuntimeException) {
                Toast.makeText(this,"Expressão Inválida",Toast.LENGTH_SHORT).show()
            }

        }
    }
}