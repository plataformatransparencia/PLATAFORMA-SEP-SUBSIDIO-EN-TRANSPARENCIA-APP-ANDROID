package sep.dgesui.subsidioentransparencia.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_contact.*
import retrofit2.Call
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.model.Contact


class ContactFragment : Fragment() {

    var filter = Filter.filter
    lateinit var response: Call<Contact>



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_fragment_container, MapsFragment()).commit()
                }
            }
        })
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filter.contact = true
        filter.getContacto()
        filter.contentContacto.observe(this ,{ contacto: Contact ->
            textEncabezado.text = contacto.encabezado
            textDepartamento.text = contacto.departamento
            textDireccion.text = contacto.direccion
            textTelefono.text = contacto.telefono
            textTelefono.setOnClickListener {
                val callTel = Uri.parse("tel:" + contacto.telefono)
                val intentTel = Intent(Intent.ACTION_DIAL).setData(callTel)
                startActivity(intentTel)
            }
            textExtensiones.text = contacto.extensiones
            textCorreo.text = contacto.correo
            textCorreo.setOnClickListener {
                val intenEmail = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(contacto.correo))
                }
                startActivity(intenEmail)
            }
        })
    }




}