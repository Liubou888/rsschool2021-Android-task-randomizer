package com.rsschool.android2021

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var intfListener: FragmentsInterface? = null
    private var errorMes = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        intfListener = context as FragmentsInterface
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        val etMin = view.findViewById<EditText>(R.id.min_value)
        val etMax = view.findViewById<EditText>(R.id.max_value)


        generateButton?.setOnClickListener {
            val min = checkEdit(etMin, "Min")
            val max = checkEdit(etMax, "Max")

            if (min != -1 && max != -1){
                if (min > max){
                    errorMes = "$errorMes\nMin is greater than Max"
                }else intfListener?.onGenerate(min, max)
            }
            if (errorMes.isNotBlank()){
                Toast.makeText(activity, errorMes.trim(), Toast.LENGTH_SHORT).show()
            }
            errorMes = ""
        }
    }

    private fun checkEdit(et:EditText, name:String):Int{
        var res:Int?
        if (et.text.isBlank()){
            errorMes = "$errorMes\n$name is blank"
            res = -1
        }else{
            res = et.text.toString().toIntOrNull()
            if (res == null || res < 0){
                errorMes = "$errorMes\nUnsupportable value in $name"
                res = -1
            }
        }
        return res
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}