package uz.ruzibekov.mvi_xml_example.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.ruzibekov.mvi_xml_example.R
import uz.ruzibekov.mvi_xml_example.ui.adapter.UsersAdapter
import uz.ruzibekov.mvi_xml_example.ui.intent.MainIntent
import uz.ruzibekov.mvi_xml_example.ui.state.MainState

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var pbLoading: ProgressBar? = null
    private var btnFetchData: Button? = null
    private var rvUsers: RecyclerView? = null

    private val adapter = UsersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initViews()

        initEvents()

        initObservers()
    }

    private fun initViews() {
        pbLoading = findViewById(R.id.pb_loading)
        btnFetchData = findViewById(R.id.btn_fetch_data)

        rvUsers = findViewById(R.id.rv_users)
        rvUsers?.adapter = adapter
    }

    private fun initEvents() {
        btnFetchData?.setOnClickListener {
            lifecycleScope.launch {
                viewModel.intent.send(MainIntent.FetchUsers)
            }
        }
    }

    private fun initObservers() = lifecycleScope.launch {
        viewModel.state.collect {
            when (it) {

                is MainState.Default -> {
                    rvUsers?.visibility = View.GONE
                    pbLoading?.visibility = View.GONE
                    btnFetchData?.visibility = View.VISIBLE
                }

                is MainState.Loading -> {
                    btnFetchData?.visibility = View.GONE
                    rvUsers?.visibility = View.GONE
                    pbLoading?.visibility = View.VISIBLE
                }

                is MainState.Users -> {
                    pbLoading?.visibility = View.GONE
                    btnFetchData?.visibility = View.GONE
                    rvUsers?.visibility = View.VISIBLE

                    adapter.users = it.user
                }

                is MainState.Error -> {
                    rvUsers?.visibility = View.GONE
                    pbLoading?.visibility = View.GONE
                    btnFetchData?.visibility = View.VISIBLE

                    Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}