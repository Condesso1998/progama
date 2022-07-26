package pt.ipg.trabalhofinal


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import pt.ipg.trabalhofinal.databinding.FragmentEliminarCarroBinding

class EliminarCarroFragment : Fragment() {
    private var _binding: FragmentEliminarCarroBinding? = null

    private val binding get() = _binding!!
    private lateinit var carro: Carro

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarCarroBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar


     carro = EliminarCarroFragmentArgs.fromBundle(arguments!!).carro

        binding.textViewMatricula.text = carro.matricula
        binding.textViewMarca.text = carro.marca

    }
    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {

                true
            }
            R.id.action_cancelar -> {

                true
            }
            else -> false
        }
}

