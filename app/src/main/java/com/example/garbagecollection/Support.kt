package com.example.garbagecollection

import android.animation.LayoutTransition
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Support.newInstance] factory method to
 * create an instance of this fragment.
 */
class Support : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_support, container, false)
        val q1=root.findViewById<CardView>(R.id.Q1)
        val l1=root.findViewById<LinearLayout>(R.id.L1)
        val que=root.findViewById<TextView>(R.id.que)
        val ans=root.findViewById<TextView>(R.id.ans)
        l1.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
       q1.setOnClickListener{
           val v=if(ans.visibility ==View.GONE)View.VISIBLE else View.GONE
           ans.visibility=v
       }
        //q2
        val q2=root.findViewById<CardView>(R.id.Q2)
        val l2=root.findViewById<LinearLayout>(R.id.L2)
        val que2=root.findViewById<TextView>(R.id.que2)
        val ans2=root.findViewById<TextView>(R.id.ans2)
        l2.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        q2.setOnClickListener{
            val v=if(ans2.visibility ==View.GONE)View.VISIBLE else View.GONE
            ans2.visibility=v
        }
        //q3
        val q3=root.findViewById<CardView>(R.id.Q3)
        val l3=root.findViewById<LinearLayout>(R.id.L3)
        val que3=root.findViewById<TextView>(R.id.que3)
        val ans3=root.findViewById<TextView>(R.id.ans3)
        l3.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        q3.setOnClickListener{
            val v=if(ans3.visibility ==View.GONE)View.VISIBLE else View.GONE
            ans3.visibility=v
        }
        return root
    }


}