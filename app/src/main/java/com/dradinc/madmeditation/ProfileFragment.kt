package com.dradinc.madmeditation

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dradinc.madmeditation.common.Global
import com.dradinc.madmeditation.databinding.FragmentProfileBinding
import com.dradinc.madmeditation.db.DbManager
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    // Переменные фрагамента
    private lateinit var binding: FragmentProfileBinding
    private lateinit var db : DbManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = DbManager(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Добавляем биндинг фрагмента
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Заполняем фрагмент (Пользовательские данные)
        Picasso.get().load(Global.userData.avatar).into(binding.profileImage)
        binding.profileName.text = Global.userData.nickName
        // Нажатие на кнопку выхода
        binding.exitBtn.setOnClickListener {
            val dialog: AlertDialog.Builder = AlertDialog.Builder(activity)
                .setTitle("Выход")
                .setMessage("Вы действительно хотите выйти?")
                .setPositiveButton("Yes") { _,_ ->
                    db.dbOpen()
                    db.deleteUser(Global.userData.id)
                    db.dbClose()
                    val intent = Intent(activity, Onboarding::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                .setNegativeButton("No") { _,_ ->

                }
            dialog.create().show()
        }
    }
}