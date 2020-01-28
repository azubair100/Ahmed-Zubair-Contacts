package com.example.ahmedzubaircontacts.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.model.ContactDatabase
import com.example.ahmedzubaircontacts.model.PersonDAO
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolBar()
    }

    private fun setUpToolBar(){
        listFragmentTB.apply {
            inflateMenu(R.menu.list_menu)
            setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when(item.itemId) {
                        R.id.goToNewContactFragment ->{
                            view?.let{
                                val action =
                                    ListFragmentDirections.actionListFragmentToNewEditContactFragment()
                                action.personId = 0
                                Navigation.findNavController(it).navigate(action)
                            }
                        }
                    }
                    return false
                }
            })
        }
    }

}
