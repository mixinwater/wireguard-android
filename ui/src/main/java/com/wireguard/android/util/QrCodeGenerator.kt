/*
 * Copyright © 2017-2025 WireGuard LLC. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.wireguard.android.util

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

object QrCodeGenerator {
    /**
     * Generates a QR code [Bitmap] from the given [configText].
     *
     * @param configText The WireGuard configuration text (from Config.toWgQuickString()).
     * @param size The desired width and height of the QR code bitmap in pixels.
     * @param foregroundColor The color for the QR code modules (dark pixels).
     * @param backgroundColor The color for the QR code background (light pixels).
     * @return A [Bitmap] containing the QR code.
     */
    fun generateQrCode(
        configText: String,
        size: Int,
        foregroundColor: Int = Color.BLACK,
        backgroundColor: Int = Color.WHITE
    ): Bitmap {
        val hints = mapOf(
            EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.L,
            EncodeHintType.MARGIN to 1
        )
        val bitMatrix = QRCodeWriter().encode(configText, BarcodeFormat.QR_CODE, size, size, hints)
        val pixels = IntArray(size * size)
        for (y in 0 until size) {
            for (x in 0 until size) {
                pixels[y * size + x] = if (bitMatrix.get(x, y)) foregroundColor else backgroundColor
            }
        }
        return Bitmap.createBitmap(pixels, size, size, Bitmap.Config.ARGB_8888)
    }
}
