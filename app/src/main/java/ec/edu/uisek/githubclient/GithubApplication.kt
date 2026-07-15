package ec.edu.uisek.githubclient

import android.app.Application
import ec.edu.uisek.githubclient.services.RetrofitClient

class GithubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.init(this)
    }
}