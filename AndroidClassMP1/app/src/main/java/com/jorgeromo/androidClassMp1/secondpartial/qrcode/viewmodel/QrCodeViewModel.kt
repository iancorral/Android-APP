package com.jorgeromo.androidClassMp1.secondpartial.qrcode.viewmodel

import android.app.Application
import android.graphics.ImageFormat
import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QrCodeViewModel(app: Application) : AndroidViewModel(app) {

    private val _qrResult = MutableStateFlow<String?>(null)
    val qrResult: StateFlow<String?> = _qrResult

    val analyzer = ImageAnalysis.Analyzer { imageProxy ->
        processImageProxy(imageProxy)
    }

    @OptIn(androidx.camera.core.ExperimentalGetImage::class)
    private fun processImageProxy(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null && imageProxy.format == android.graphics.ImageFormat.YUV_420_888) {
            val image = com.google.mlkit.vision.common.InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            val scanner = com.google.mlkit.vision.barcode.BarcodeScanning.getClient()

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        if (barcode.format == com.google.mlkit.vision.barcode.common.Barcode.FORMAT_QR_CODE) {
                            _qrResult.value = barcode.rawValue
                            break
                        }
                    }
                }
                .addOnFailureListener {
                    android.util.Log.e("QrCodeViewModel", "Scan failed", it)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }

    fun clearResult() {
        _qrResult.value = null
    }
}