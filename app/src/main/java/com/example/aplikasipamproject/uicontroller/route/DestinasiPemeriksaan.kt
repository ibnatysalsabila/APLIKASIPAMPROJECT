package com.example.aplikasipamproject.uicontroller.route

object DestinasiPemeriksaan : DestinasiNavigasi {
    override val route = "pemeriksaan"
    override val titleRes = "Riwayat Pemeriksaan"
}

object DestinasiEntryPemeriksaan : DestinasiNavigasi {
    override val route = "entry_pemeriksaan"
    override val titleRes = "Tambah Pemeriksaan"
}

object DestinasiDetailPemeriksaan : DestinasiNavigasi {
    override val route = "detail_pemeriksaan"
    const val ID_PEMERIKSAAN = "idPemeriksaan"
    val routeWithArgs = "$route/{$ID_PEMERIKSAAN}"
    override val titleRes = "Detail Pemeriksaan"
}

object DestinasiUpdatePemeriksaan : DestinasiNavigasi {
    override val route = "update_pemeriksaan"
    const val ID_PEMERIKSAAN = "idPemeriksaan"
    val routeWithArgs = "$route/{$ID_PEMERIKSAAN}"
    override val titleRes = "Update Pemeriksaan"
}