/*
 * Copyright © 2017-2025 WireGuard LLC. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */
package com.wireguard.android.fragment

import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wireguard.android.R
import com.wireguard.android.util.QrCodeGenerator

class QrCodeDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val configText = requireArguments().getString(KEY_CONFIG_TEXT)!!
        val tunnelName = requireArguments().getString(KEY_TUNNEL_NAME)!!

        val view = layoutInflater.inflate(R.layout.qr_code_dialog_fragment, null)
        val imageView = view.findViewById<ImageView>(R.id.qr_code_image)

        val size = resources.displayMetrics.widthPixels
        val qrBitmap: Bitmap = QrCodeGenerator.generateQrCode(configText, size)
        imageView.setImageBitmap(qrBitmap)

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.qr_code_title, tunnelName))
            .setView(view)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }

    companion object {
        private const val KEY_CONFIG_TEXT = "config_text"
        private const val KEY_TUNNEL_NAME = "tunnel_name"

        fun newInstance(tunnelName: String, configText: String): QrCodeDialogFragment {
            return QrCodeDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_CONFIG_TEXT, configText)
                    putString(KEY_TUNNEL_NAME, tunnelName)
                }
            }
        }
    }
}
