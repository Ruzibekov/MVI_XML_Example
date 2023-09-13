package uz.ruzibekov.mvi_xml_example.data.model

data class DataModel(
    val data: List<UserModel>,
    val page: Int,
    val per_page: Int,
    val support: Support,
    val total: Int,
    val total_pages: Int
)