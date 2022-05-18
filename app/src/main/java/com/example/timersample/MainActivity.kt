package com.example.timersample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.example.timersample.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    var total = 0
    var started = false
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val minute = String.format("%02d", total / 60)
            val second = String.format("%02d", total % 60)
            binding.textView.text = "$minute:$second"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonStart.setOnClickListener {
            if (!started) {
                started = true
                thread(start = true) {
                    while (started) {
                        Thread.sleep(1000)
                        if (started) {
                            total += 1
                            handler?.sendEmptyMessage(0)
                        }
                    }
                }
            }
        }

        binding.buttonStop.setOnClickListener {
            if (started) {
                started = false
                total = 0
                binding.textView.text = "00:00"
            }
        }

    }
}