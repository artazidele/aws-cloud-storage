package com.example.aws_storage

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin

class AmplifyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            // Spraudnis autentifikācijas kategorijai
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            // Spraudnis krātuves kategorijai
            Amplify.addPlugin(AWSS3StoragePlugin())

            // Amplify inicializēšana
            Amplify.configure(applicationContext)
            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
    }
}