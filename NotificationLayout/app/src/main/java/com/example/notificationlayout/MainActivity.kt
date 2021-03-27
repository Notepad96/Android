package com.example.notificationlayout

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    val PERMISSIONCODE = 111

    val requestPermissionms = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()
    }



    private fun checkPermissions() {
        var rejectedPermissionList = ArrayList<String>()

        for(permission in requestPermissionms) {
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                rejectedPermissionList.add(permission)
            }
        }

        if(rejectedPermissionList.isNotEmpty()) {
            val arr = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this, rejectedPermissionList.toArray(arr), PERMISSIONCODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSIONCODE -> {
                if (grantResults.isNotEmpty()) {
                    for ((i, permission) in permissions.withIndex()) {
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            Log.d("Msg", "$permission Denied")
                            Toast.makeText(this, "권한을 허용 해주세요. 권한을 묻는 창이 뜨지 않을 경우 설정>애플리케이션에서 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
                            checkPermissions()
                        }
                    }
                }
            }
        }
    }

}