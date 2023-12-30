package com.lideatech.imeiguard

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.lideatech.imeiguard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    object DatabaseManager {
        private var devicesDb: SQLiteDatabase? = null

        fun init(context: Context) {
            if (devicesDb == null) {
                devicesDb = context.openOrCreateDatabase("Devices", Context.MODE_PRIVATE, null)
                devicesDb?.execSQL("CREATE TABLE IF NOT EXISTS devices (id INTEGER PRIMARY KEY, manufacturer VARCHAR, model VARCHAR, sim1status INT, sim2status INT, sim1date String, sim2date String)")
            }
        }

        fun getDevicesDb(): SQLiteDatabase? {
            return devicesDb
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = Intent(this, StartedActivity::class.java)
        startActivity(intent)
        finish()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeMainFragment())
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.theme_color)))

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeMainFragment())
                R.id.counter -> replaceFragment(CounterFragment())
                R.id.settings -> replaceFragment(SettingsFragment())
                else -> {}
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frame_layout, fragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.contact -> {
                openWebsite("https://www.lideatech.com/iletisim")
                true
            }
            R.id.policy -> {
                openWebsite("https://www.lideatech.com/")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openWebsite(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}