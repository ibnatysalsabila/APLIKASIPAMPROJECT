package com.example.aplikasipamproject.uicontroller.route

object DestinasiLayanan : DestinasiNavigasi {
    override val route = "layanan"
    override val titleRes = "Daftar Layanan Medis"
}

object DestinasiEntryLayanan : DestinasiNavigasi {
    override val route = "entry_layanan"
    override val titleRes = "Tambah Layanan"
}

object DestinasiDetailLayanan : DestinasiNavigasi {
    override val route = "detail_layanan"
    const val ID_LAYANAN = "idLayanan"
    val routeWithArgs = "$route/{$ID_LAYANAN}"
    override val titleRes = "Detail Layanan"
}

object DestinasiUpdateLayanan : DestinasiNavigasi {
    override val route = "update_layanan"
    const val ID_LAYANAN = "idLayanan"
    val routeWithArgs = "$route/{$ID_LAYANAN}"
    override val titleRes = "Update Layanan"
}