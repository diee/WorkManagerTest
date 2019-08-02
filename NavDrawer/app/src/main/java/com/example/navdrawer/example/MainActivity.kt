package com.example.navdrawer.example

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.work.WorkManager
import com.example.navdrawer.R
import com.example.navdrawer.data.Person
import com.example.navdrawer.utils.SerializerHelper
import com.example.navdrawer.workers.PERSON_OUT
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.paulinasadowska.rxworkmanagerobservers.extensions.toWorkDatasObservable
import kotlinx.android.synthetic.main.content_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

open class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { _ ->
            val personName = etName?.text.toString()
            val personId = etId?.text.toString()
            val person = Person(id = personId.toLong(), name = personName, age = 10, synced = false)
            mainViewModel.createNewPerson(person)
        }

        mainViewModel.newPersonAdded.observe(this, Observer {
            mainViewModel.getUnsycList()

            WorkManager.getInstance(this).getWorkInfosByTagLiveData(WORK_TAG)
                .toWorkDatasObservable()
                .subscribe {
                    mainViewModel.uploadNewPerson(
                        SerializerHelper.deserializeJson(
                            it.getString(PERSON_OUT)!!
                        )
                    )
                }
        })

        mainViewModel.syncedNewData.observe(this, Observer {
            if (it) mainViewModel.getSyncList()
        })

        mainViewModel.localList.observe(this, Observer {
            bindLocalList(it)
        })

        mainViewModel.syncedList.observe(this, Observer {
            bindNetworkList(it)
            mainViewModel.getUnsycList()
        })

        mainViewModel.getUnsycList()
        mainViewModel.getSyncList()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val WORK_TAG = "WORK_TAG"
    }


    private fun bindLocalList(list: List<Person>) {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        lvPersons.adapter = arrayAdapter
    }

    private fun bindNetworkList(list: List<Person>) {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        lvPersonSync.adapter = arrayAdapter
    }
}
