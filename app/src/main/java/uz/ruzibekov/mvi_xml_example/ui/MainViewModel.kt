package uz.ruzibekov.mvi_xml_example.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import uz.ruzibekov.mvi_xml_example.data.api.ApiService
import uz.ruzibekov.mvi_xml_example.ui.intent.MainIntent
import uz.ruzibekov.mvi_xml_example.ui.state.MainState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: ApiService
) : ViewModel() {

    val intent: Channel<MainIntent> = Channel(Channel.UNLIMITED)

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState.Default)
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() = viewModelScope.launch {
        intent.consumeAsFlow().collect {
            when (it) {
                is MainIntent.FetchUsers -> fetchUser()
            }
        }
    }

    private fun fetchUser() = viewModelScope.launch {
        _state.value = MainState.Loading

        delay(1000)

        _state.value = try {

            val data = service.getUsers()
            MainState.Users(data.data)

        } catch (e: Exception) {
            MainState.Error(e.message)
        }
    }
}