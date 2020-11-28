package com.reo.running.qreader

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
    const val REQUEST_CAMERA_PERMISSION : Int = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        qrChecker()
        initQRCamera()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQUEST_CAMERA_PERMISSION -> {initQRCamera()}
        }
    }

    @SuppressLint("WringConstant")
    private fun initQRCamera() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return

        val isReadPermissionGranted = (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        val isWritePermissionGranted = (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        val isCameraPermission = (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)

        if (isReadPermissionGranted && isWritePermissionGranted && isCameraPermission) {
            openQRCamera()
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA_PERMISSION)
        }
    }

    private fun openQRCamera() {
        qr_view.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                if (result != null) {
                    onPause()
                    Log.d("QRCode","$result")
                }
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) { }
        })
    }



    private fun qrChecker() {
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            qr_view.resume()
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),999)
        }
    }
}