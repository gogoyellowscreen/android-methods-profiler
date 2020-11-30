package com.github.grishberg.profiler.chart

import com.github.grishberg.profiler.common.AppLogger
import com.github.grishberg.profiler.common.SimpleMouseListener
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import javax.swing.JPanel

private const val PANEL_HEIGHT = 32

interface OnPreviewClickedAction {
    fun onPreviewClicked(offsetInPercent: Double)
}

class CallTracePreviewPanel(
    private val logger: AppLogger
) : JPanel() {
    private val bgColor = Color(0, 0, 0)
    var previewClickedAction: OnPreviewClickedAction? = null

    var image: BufferedImage? = null
        set(value) {
            field = value
            repaint()
        }

    init {
        preferredSize = Dimension(48, PANEL_HEIGHT)
        addMouseListener(object : SimpleMouseListener() {
            override fun mouseLeftClicked(e: MouseEvent) {
                previewClickedAction?.onPreviewClicked(e.x / width.toDouble())
            }
        })
    }

    override fun paintComponent(graphics: Graphics) {
        super.paintComponent(graphics)
        graphics.color = bgColor
        graphics.fillRect(0, 0, width, height)

        image?.let {
            graphics.drawImage(it, 0, 0, width, height, null)
        }
    }

    fun clear() {
        image = null
        repaint()
    }
}
