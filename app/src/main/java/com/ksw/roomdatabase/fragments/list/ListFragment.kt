package com.ksw.roomdatabase.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksw.roomdatabase.R
import com.ksw.roomdatabase.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private lateinit var mUserViewModel : UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // add all delete menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteALlUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteALlUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("네") { _,_ ->
            mUserViewModel.deleteAllUsers()
            Toast.makeText(requireContext(), "전체 삭제 성공",  Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("아니요") {_,_ -> }
        builder.setTitle("전부 삭제 하겠습니까?")
        builder.setMessage("정말로 모든 데이터를 삭제 하겠습니까?")
        builder.create().show()
    }


}