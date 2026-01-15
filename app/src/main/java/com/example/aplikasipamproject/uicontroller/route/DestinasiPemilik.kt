package com.example.aplikasipamproject.uicontroller.route

object DestinasiPemilik : DestinasiNavigasi {
    override val route = "pemilik"
    override val titleRes = "Daftar Pemilik"
}

object DestinasiEntryPemilik : DestinasiNavigasi {
    override val route = "entry_pemilik"
    override val titleRes = "Tambah Pemilik"
}

object DestinasiDetailPemilik : DestinasiNavigasi {
    override val route = "detail_pemilik"
    const val ID_PEMILIK = "idPemilik"
    val routeWithArgs = "$route/{$ID_PEMILIK}"
    override val titleRes = "Detail Pemilik"
}

object DestinasiUpdatePemilik : DestinasiNavigasi {
    override val route = "update_pemilik"
    const val ID_PEMILIK = "idPemilik"
    val routeWithArgs = "$route/{$ID_PEMILIK}"
    override val titleRes = "Update Pemilik"
}