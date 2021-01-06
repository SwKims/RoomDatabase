package com.ksw.roomdatabase.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ksw.roomdatabase.R
import com.ksw.roomdatabase.data.User
import com.ksw.roomdatabase.data.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var mUserViewModel : UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.btn_add.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val firstName = et_firstName.text.toString()
        val lastName = et_lastName.text.toString()
        val age = et_age.text

        if (inputCheck(firstName, lastName, age)) {
            // 유저 생성
            val user = User(0, firstName, lastName, Integer.parseInt(age.toString()))
            // 데이타를 데이터베이스에 추가
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Success!!!", Toast.LENGTH_SHORT).show()
            // 뒤로
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Fail!!!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(firstName: String, lastName : String, age : Editable) : Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }


}