package com.example.das_challenge_2.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.das_challenge_2.R
import com.example.das_challenge_2.utils.showToast
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.barcode.Barcode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_scanner.*

class ScannerActivity : AppCompatActivity() {

    private var mDisposable: Disposable? = null
    private var PERMISSION_CAMERA_VALUE = 1
    private var data = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        //Build codebar scanner
        barcodeView.setFacing(CameraSource.CAMERA_FACING_BACK)
            .setAutoFocus(true)
            .setVibration(500L)
            .setBeepSound(true)

        //Check camera permission
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), PERMISSION_CAMERA_VALUE)
        } else {
            scanCode()
        }

    }

    private fun scanCode() {
        val intent = Intent()

        mDisposable = barcodeView.getObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //Save code for result
                data = it.rawValue
                intent.data = Uri.parse(data)
                setResult(Activity.RESULT_OK, intent)
                finish()
            },
            {
                showToast(baseContext, it.message!!)
                finish()
            })
    }

    override fun onStop() {
        super.onStop()

        mDisposable?.dispose()
    }
}
