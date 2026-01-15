package com.example.aplikasipamproject.uicontroller.route

object DestinasiHewan : DestinasiNavigasi {
    override val route = "hewan"
    override val titleRes = "Daftar Hewan"
}

object DestinasiEntryHewan : DestinasiNavigasi {
    override val route = "entry_hewan"
    override val titleRes = "Tambah Hewan"
}

object DestinasiDetailHewan : DestinasiNavigasi {
    override val route = "detail_hewan"
    const val ID_HEWAN = "idHewan"
    val routeWithArgs = "$route/{$ID_HEWAN}"
    override val titleRes = "Detail Hewan"
}

object DestinasiUpdateHewan : DestinasiNavigasi {
    override val route = "update_hewan"
    const val ID_HEWAN = "idHewan"
    val routeWithArgs = "$route/{$ID_HEWAN}"
    override val titleRes = "Update Hewan"
}