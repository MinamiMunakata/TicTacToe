package com.example.minami.tictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var locationArr = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
    private var playerArr = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
    private var cpArr = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
    private val ticTac = "×"
    private val toe = "○"
    private var isPlayersTurn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reset.setOnClickListener({v -> resetGame()})
        start()
    }

    private fun resetGame() {
        locationArr = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        playerArr = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        cpArr = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        isPlayersTurn = true
        start()
    }

    private fun closeGame() {
        for (i in 0 until panels.childCount) {
            val child = panels.getChildAt(i) as Button
            child.isClickable = false
        }
    }

    private fun showResult() {
        if (isThereWinner(playerArr)) {
            Toast.makeText(this, "YOU WIN!!", Toast.LENGTH_LONG).show()
            closeGame()
        } else if (isThereWinner(cpArr)) {
            Toast.makeText(this, "YOU LOOSE...", Toast.LENGTH_LONG).show()
            closeGame()
        } else if (isDraw()) {
            Toast.makeText(this, "DRAW", Toast.LENGTH_LONG).show()
            closeGame()
        }
    }

    private fun isDraw(): Boolean {
        for (i in 0 until locationArr.size) {
            if (locationArr[i] >= 0) {
                return false
            }
        }
        return true
    }

    private fun checkResult() {
        if (isThereWinner(playerArr)) {
            Toast.makeText(this, "YOU WIN!!", Toast.LENGTH_LONG).show()
            closeGame()
        } else if (isThereWinner(cpArr)) {
            Toast.makeText(this, "YOU LOOSE...", Toast.LENGTH_LONG).show()
            closeGame()
        } else if (isDraw()) {
            Toast.makeText(this, "DRAW", Toast.LENGTH_LONG).show()
            closeGame()
        }
    }

    private fun isThereWinner(array: Array<Int>) : Boolean {
        return (check1(array)||check2(array)||check3(array)||check4(array)||
                check5(array)||check6(array)||check7(array)||check8(array))
    }

    private fun check1(array: Array<Int>): Boolean {
        return (array[0] < 0 && array[1] < 0 && array[2] < 0)
    }

    private fun check2(array: Array<Int>): Boolean {
        return (array[3] < 0 && array[4] < 0 && array[5] < 0)
    }

    private fun check3(array: Array<Int>): Boolean {
        return (array[6] < 0 && array[7] < 0 && array[8] < 0)
    }

    private fun check4(array: Array<Int>): Boolean {
        return (array[0] < 0 && array[4] < 0 && array[8] < 0)
    }

    private fun check5(array: Array<Int>): Boolean {
        return (array[2] < 0 && array[4] < 0 && array[6] < 0)
    }

    private fun check6(array: Array<Int>): Boolean {
        return (array[1] < 0 && array[4] < 0 && array[7] < 0)
    }

    private fun check7(array: Array<Int>): Boolean {
        return (array[0] < 0 && array[3] < 0 && array[6] < 0)
    }

    private fun check8(array: Array<Int>): Boolean {
        return (array[2] < 0 && array[5] < 0 && array[8] < 0)
    }


    private fun start() {
        for (i in 0 until panels.childCount) {
            val child = panels.getChildAt(i) as Button
            child.tag = i
            child.setOnClickListener(this)
            child.text = null
        }
    }

    private fun toe(rowTag: Int) {
        if (isPlayersTurn) {
            if (locationArr[rowTag] >= 0) {
                val button: Button = panels.getChildAt(rowTag) as Button
                button.text = toe
                isPlayersTurn = false
                if (rowTag == 0) {
                    locationArr[rowTag] = -9
                    playerArr[rowTag] = -9
                } else {
                    locationArr[rowTag] = rowTag * -1
                    playerArr[rowTag] = rowTag * -1
                }
            }
        }
        showResult()
    }

    private fun ticTac() {
        val rand = Random()
        var locationForTicTac = rand.nextInt(locationArr.size)
        if (!isPlayersTurn) {
            if (panels.getChildAt(locationForTicTac).isClickable){
                if (locationArr[locationForTicTac] >= 0) {
                    val button: Button = panels.getChildAt(locationForTicTac) as Button
                    button.text = ticTac
                    if (locationForTicTac == 0) {
                        locationArr[locationForTicTac] = -9
                        cpArr[locationForTicTac] = -9
                    } else {
                        locationArr[locationForTicTac] = locationForTicTac * -1
                        cpArr[locationForTicTac] = locationForTicTac * -1
                    }
                    isPlayersTurn = true
                    showResult()
                } else {
                    ticTac()
                }
            }
        }
    }

    override fun onClick(v: View?) {
//        val button: Button = v as Button
//        var position:
        val button: Button = v as Button
        var position: Int = button.tag as Int
        if (isPlayersTurn) toe(position)
        if (!isPlayersTurn) {
            Handler().postDelayed(Runnable {
                ticTac()
            }, 500)
        }
    }
}
