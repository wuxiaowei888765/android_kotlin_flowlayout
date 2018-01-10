package withkotlin.android.com.kotlin_flowlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import view.FlowLayout

class MainActivity : AppCompatActivity() {

    private val array  = arrayListOf("jdaskdjadjadjakj","hda","asdadajdkadajdakjdajdadaldj","s","hasdhahda")

    private lateinit var flowLayout:FlowLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flowLayout = findViewById(R.id.flowlayout)
        initData()
    }

   private fun initData(){
        for (i in 0 until array.size){
            var btn = Button(this)

            val lp = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)

            btn.text = array[i]

            flowLayout.addView(btn,lp)
        }
    }
}
