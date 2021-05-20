package com.yang.me.healthy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.yang.me.healthy.data.AppDataBase
import com.yang.me.healthy.data.bean.TypedEvent
import com.yang.me.healthy.widget.ColorfulProgressCircle
import com.yang.me.lib.extension.launchWrapped
import com.yang.me.lib.extension.toast
import kotlin.concurrent.thread

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val colorfulProgressCircle = view.findViewById<ColorfulProgressCircle>(R.id.colorful_progress)
        colorfulProgressCircle.outDestDegree = 730f
        colorfulProgressCircle.midDestDegree = 660f
        colorfulProgressCircle.innerDestDegree = 570f
        colorfulProgressCircle.animateDuration = 1500
        colorfulProgressCircle.startAnimateProgress()

//        Handler().postDelayed({
//            colorfulProgressCircle.increaseWithAnim(-50f, 50f, 50f)
//        },
//        3000)

        val progressview1 = view.findViewById<ProgressView>(R.id.progress)

        //设置颜色
        progressview1.setColor(getResources().getColor(R.color.yell));
        //设置圆角   默认无圆角
        progressview1.setRadius(6f);
        //设置进度条长度    默认px
        progressview1.setProgresss(500F);
        //设置动画时间
        progressview1.setDuration(500);
        progressview1.startAnim()


        launchWrapped(this, {
            AppDataBase.get().getTypedEventDao().insert(TypedEvent(
                eventName = "喝水",
                isPositive = true,
                targetProgress = 8,
                unit = "杯"
            ))

            AppDataBase.get().getTypedEventDao().getAllTypedEvent().size
        },  {
            context?.let { it1 -> toast(it1, "size is $it") }
        })

    }
}

