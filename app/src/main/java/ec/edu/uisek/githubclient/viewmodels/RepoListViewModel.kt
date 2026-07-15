package ec.edu.uisek.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.uisek.githubclient.models.Repository
import ec.edu.uisek.githubclient.services.RetrofitClient
import ec.edu.uisek.githubclient.services.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoListViewModel: ViewModel() {
    private val _repos = MutableStateFlow<List<Repository>>(emptyList())
    val repos: StateFlow<List<Repository>> = _repos.asStateFlow()

    private val _isLoanding = MutableStateFlow(false)
    val isLoanding: StateFlow<Boolean> = _isLoanding.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg.asStateFlow()

    init {
        fetchRepos()
    }

    fun fetchRepos(){
        viewModelScope.launch {
            _isLoanding.value = true
            _errorMsg.value = null
            try {
                val response = RetrofitClient.apiService.getRepository()
                _repos.value = response
            } catch (e: Exception){
                _errorMsg.value = "Error al cargar repositorios: ${e.localizedMessage}"
                e.printStackTrace()
            }finally {
                _isLoanding.value = false
            }
        }
    }

    //se crea la funciónn Delete
    fun deleteRepository(owner: String, repo: String){
        viewModelScope.launch {
            _isLoanding.value = true
            _errorMsg.value = null
            try{
                apiService.deleteRepository(owner, repo)
                //se vuelve a cargar la lista trás cargar
                fetchRepos()

            }catch (e: Exception){
                _errorMsg.value="Error al eliminar el repositorio: ${e.localizedMessage}"
                e.printStackTrace()
            }finally {
                _isLoanding.value = false
            }
        }
    }
}