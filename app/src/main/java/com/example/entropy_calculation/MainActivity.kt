package com.example.entropy_calculation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.entropy_calculation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var bilding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bilding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(bilding.root)

        supportFragmentManager.beginTransaction().
        replace(R.id.fragment,FragmentEntropy.newInstance()).commit()

        bilding.btMenu.selectedItemId = R.id.entropy
        bilding.btMenu.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.entropy->{supportFragmentManager.beginTransaction().
                replace(R.id.fragment,FragmentEntropy.newInstance()).commit()
                }
                R.id.char_probability->{supportFragmentManager.beginTransaction().
                replace(R.id.fragment,FragmentCharList.newInstance()).commit()
                }
                R.id.digram_probability->{supportFragmentManager.beginTransaction().
                replace(R.id.fragment,FragmentBigramList.newInstance()).commit()
                }
            }
            true
        }
        3
    }
}