package de.visustruct.other

import java.awt.AWTEvent
import java.awt.Color
import java.awt.event.KeyEvent
import java.util.Locale
import javax.swing.JTextField

/** Swing-Komponente für numerische Eingaben. */
class JNumberField @JvmOverloads constructor() : JTextField() {

    init {
        enableEvents(AWTEvent.KEY_EVENT_MASK.toLong())
    }

    fun getDouble(): Double = text.toDouble()

    fun getFloat(): Float = getDouble().toFloat()

    fun getInt(): Int = getDouble().toInt()

    fun getLong(): Long = getDouble().toLong()

    fun isNumeric(): Boolean = text.trim().toDoubleOrNull() != null

    fun setDouble(d: Double) {
        text = d.toString()
    }

    fun setDouble(d: Double, n: Int) {
        text = String.format(Locale.ENGLISH, "%.${n}f", d)
    }

    fun setFloat(f: Float) {
        text = f.toString()
    }

    fun setFloat(f: Float, n: Int) {
        setDouble(f.toDouble(), n)
    }

    fun setInt(i: Int) {
        text = i.toString()
    }

    fun setLong(l: Long) {
        text = l.toString()
    }

    fun clear() {
        text = ""
    }

    override fun processKeyEvent(e: KeyEvent) {
        super.processKeyEvent(e)
        background = when {
            isNumeric() || text == "-" || text.isEmpty() || text == "." -> Color.WHITE
            else -> Color.RED
        }
    }

    companion object {
        private const val serialVersionUID = -3137694650317084473L
    }
}
