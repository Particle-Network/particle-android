package network.particle.demo.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class DemoBaseActivity<DB : ViewDataBinding>(@LayoutRes var contentLayoutId: Int) :
    AppCompatActivity() {
    private var _binding: DB? = null
    protected val binding: DB get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView<DB>(this, contentLayoutId)
        initView()
        initData()
        setListeners()
        setObserver()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    open fun initView() {

    }

    open fun initData() {

    }

    open fun setListeners() {

    }

    open fun setObserver() {

    }

}