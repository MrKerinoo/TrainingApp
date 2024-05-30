package com.example.trainingapp

import android.app.Application
import com.example.trainingapp.data.AppContainer
import com.example.trainingapp.data.AppDataContainer

class TrainingApplication : Application(){

    lateinit var  container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}