package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
//손전등 기능 객체
class Torch(context: Context) {
    private var cameraId:String? = null
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    //클래스를 초기화
    init {
        cameraId = getCameraId()
    }
    //카메라 플래쉬를 켜는 메서드
    fun flashOn() {
        cameraId?.let { cameraManager.setTorchMode(it, true) }
    }
    //카메라 플래쉬를 끄는 메서드
    fun flashOff() {
        cameraId?.let { cameraManager.setTorchMode(it, false) }
    }
    //카메라 ID를 얻는 메서드(기기에 내장된 카메라는 고유한 ID부여)
    private fun getCameraId():String? {
        val cameraIds = cameraManager.cameraIdList //카메라에 대한 정보 목록
        for(id in cameraIds) {
            val info = cameraManager.getCameraCharacteristics(id) //각 ID별로 세부 정보
            val flashAvailable = info.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) //가능 여부
            val lensFacing = info.get(CameraCharacteristics.LENS_FACING) //렌즈 방향
            if(flashAvailable != null
                && flashAvailable
                && lensFacing != null
                && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                return id
            }
        }
        return null
    }
}