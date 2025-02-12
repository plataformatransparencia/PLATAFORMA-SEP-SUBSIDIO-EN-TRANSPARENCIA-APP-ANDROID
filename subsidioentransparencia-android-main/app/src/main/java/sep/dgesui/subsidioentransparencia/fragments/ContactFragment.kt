package sep.dgesui.subsidioentransparencia.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import retrofit2.Call
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.databinding.FragmentContactBinding
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.model.Contact


class ContactFragment : Fragment() {

    var filter = Filter.filter
    lateinit var response: Call<Contact>


    private var _binding: FragmentContactBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?

    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
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
        filter.contentContacto.observe(this) { contacto: Contact ->

            binding.textEncabezado.text = contacto.encabezado
            binding.textDepartamento.text = contacto.departamento
            binding.textDireccion.text = contacto.direccion
            binding.textTelefono.text = contacto.telefono
            binding.textTelefono.setOnClickListener {
                val callTel = Uri.parse("tel:" + contacto.telefono)
                val intentTel = Intent(Intent.ACTION_DIAL).setData(callTel)
                startActivity(intentTel)
            }
            binding.textExtensiones.text = contacto.extensiones
            binding.textCorreo.text = contacto.correo
            binding.textCorreo.setOnClickListener {
                val intenEmail = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(contacto.correo))
                }
                startActivity(intenEmail)
            }
        }
    }




}