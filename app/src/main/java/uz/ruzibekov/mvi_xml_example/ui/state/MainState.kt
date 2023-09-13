package uz.ruzibekov.mvi_xml_example.ui.state

import uz.ruzibekov.mvi_xml_example.data.model.UserModel

sealed class MainState {

    object Default : MainState()

    object Loading : MainState()

    data class Users(val user: List<UserModel>) : MainState()

    data class Error(val error: String?) : MainState()

}