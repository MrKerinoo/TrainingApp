package com.example.trainingapp

import android.app.Application
import com.example.trainingapp.data.AppContainer
import com.example.trainingapp.data.AppDataContainer

/**
 * TrainingApplication is the entry point of the application.
 * It creates an instance of AppContainer to provide dependencies
 * for the entire Training app.
 */
class TrainingApplication : Application(){

    lateinit var  container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}