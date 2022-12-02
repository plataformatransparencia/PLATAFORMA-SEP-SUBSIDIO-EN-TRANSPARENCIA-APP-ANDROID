package sep.dgesui.subsidioentransparencia.interfaces


interface DataListMinEstExt {

    fun onClickListenerCompromiso(
        position : Int,
        monto : Double,
        fecha : String,
        Observacion : String
    )

}