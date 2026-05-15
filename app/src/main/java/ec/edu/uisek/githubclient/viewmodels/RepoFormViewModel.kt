package ec.edu.uisek.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.uisek.githubclient.models.RepositoryPayload
import ec.edu.uisek.githubclient.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoFormViewModel : ViewModel() {
    private val apiService = RetrofitClient.apiService

    private val _isLoanding = MutableStateFlow(false)
    val isLoanding: StateFlow<Boolean> = _isLoanding.asStateFlow()

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg.asStateFlow()



    fun createRepository(name: String, description: String?){
        viewModelScope.launch {
            _isLoanding.value = true
            _errorMsg.value = null
            try{
                val payload = RepositoryPayload(name, description)
                apiService.createRepository(payload)
                _isSuccess.value = true
            }catch (e: Exception){
                _errorMsg.value="Error al crear el repositorio: ${e.localizedMessage}"
                e.printStackTrace()
            }finally {
                _isLoanding.value = false
            }
        }
    }

    //Agregamos las funciones de patch y delete
    fun updateRepository(owner: String, repo: String, name: String, description: String?){
        viewModelScope.launch {
            _isLoanding.value = true
            _errorMsg.value = null
            try{
                val payload = RepositoryPayload(name, description)
                apiService.updateRepository(owner, repo, payload)
                _isSuccess.value = true
            }catch (e: Exception){
                _errorMsg.value="Error al actualizar el repositorio: ${e.localizedMessage}"
                e.printStackTrace()
            }finally {
                _isLoanding.value = false
            }
        }
    }



    fun resetSuccess(){
        _isSuccess.value = false
    }

}