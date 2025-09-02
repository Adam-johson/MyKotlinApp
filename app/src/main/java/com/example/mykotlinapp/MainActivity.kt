package com.example.mykotlinapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity(){
    private lateinit var fetchButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var statusTextView: TextView
    private lateinit var dataTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //初始化视图
        fetchButton = findViewById(R.id.fetchButton)
        progressBar = findViewById(R.id.progressBar)
        statusTextView = findViewById(R.id.statusTextView)
        dataTextView = findViewById(R.id.dataTextView)

        fetchButton.setOnClickListener {
            fetchData()
        }
    }

    private fun fetchData() {
        lifecycleScope.launch {
            try {
                progressBar.visibility = View.VISIBLE
                statusTextView.text ="正在获取数据"
                fetchButton.isEnabled = false

                val response: Response<List<User>> = RetrofitInstance.apiService.getUsers()

                if (response.isSuccessful){
                    val users = response.body()
                    if (users != null && users.isNotEmpty()){
                        statusTextView.text ="获取数据成功"
                        displayUsers(users)
                    }else{
                        statusTextView.text ="未获取数据"
                        dataTextView.text= ""
                    }
                }else{
                    statusTextView.text ="发生错误: ${response.code()}"
                }

            }catch (e : Exception){

            }finally {

            }
        }
    }

    private fun displayUsers(users: List<User>) {
        val stringBuilder = StringBuilder()
        for (user in users){
            stringBuilder.append("ID:${user.id}\n")
            stringBuilder.append("姓名:${user.name}\n")
            stringBuilder.append("用户名${user.username}\n")
            stringBuilder.append("邮箱:${user.email}\n")
            if (!user.phont.isNullOrEmpty()){
                stringBuilder.append("电话:${user.phont}\n")
            }

            if (!user.website.isNullOrEmpty()){
                stringBuilder.append("网站:${user.website}\n")
            }
            stringBuilder.append("\n${"-".repeat(30)}\n\n")
        }
        dataTextView.text = stringBuilder.toString()
    }
}