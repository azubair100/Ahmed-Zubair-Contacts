package com.example.ahmedzubaircontacts.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.FragmentListBinding
import com.example.ahmedzubaircontacts.view.adapters.PersonListAdapter
import com.example.ahmedzubaircontacts.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private lateinit var personAdapter: PersonListAdapter
    private lateinit var dataBinding: FragmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        listViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        personAdapter = PersonListAdapter(arrayListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeViewModel()
        listViewModel.getAllPersons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataBinding.unbind()
    }

    private fun setUpViews(){
        dataBinding.apply {
            setUpRecyclerView()
            setUpToolBar()
            setUpSearchView()
        }
    }

    private fun setUpRecyclerView(){
        listFragmentRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = personAdapter
        }
    }

    private fun setUpSearchView(){
        listFragmentSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                listViewModel.getPersonsByQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (listFragmentSV.query.isEmpty()) {
                    listViewModel.getAllPersons()
                }
                return false
            }
        })
    }

    private fun observeViewModel(){
        listViewModel.personList.observe(viewLifecycleOwner, Observer { persons ->
            persons?.let {
                listFragmentRV.visibility = View.VISIBLE
                personAdapter.updatePersonList(persons)
            }
        })

        listViewModel.listLLoadError.observe(viewLifecycleOwner, Observer {isError ->
            isError?.let { listFragmentErrTV.visibility = if(it) View.VISIBLE else View.GONE }
        })

        listViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                listFragmentPB.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    listFragmentRV.visibility = View.GONE
                    listFragmentErrTV.visibility = View.GONE
                }
            }
        })
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
