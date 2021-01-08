package com.ksw.roomdatabase.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ksw.roomdatabase.R
import com.ksw.roomdatabase.model.User
import com.ksw.roomdatabase.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel : UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.et_update_firstName.setText(args.currentUser.firstName)
        view.et_update_lastName.setText(args.currentUser.lastName)
        view.et_update_age.setText(args.currentUser.age.toString())

        view.btn_update_add.setOnClickListener {
            updateItem()
        }

        // add delete menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val firstName = et_update_firstName.text.toString()
        val lastName = et_update_lastName.text.toString()
        val age = Integer.parseInt(et_update_age.text.toString())

        if (inputCheck(firstName, lastName, et_update_age.text)) {
            // create
            val updateUser = User(args.currentUser.id, firstName, lastName, age)
            // update
            mUserViewModel.updateUser(updateUser)
            Toast.makeText(requireContext(), "update success", Toast.LENGTH_SHORT).show()
            // navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "update fail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firstName: String, lastName : String, age : Editable) : Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("네") { _,_ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(), "삭제 성공, ${args.currentUser.firstName}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("아니요") {_,_ -> }
        builder.setTitle("삭제 ${args.currentUser.firstName}?")
        builder.setMessage("정말로 삭제 하겠습니까? ${args.currentUser.firstName}")
        builder.create().show()

    }
}