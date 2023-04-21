package network.particle.demo.ui.adapter

import android.widget.ImageView
import coil.load
import com.minijoy.demo.R
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class BannerAdapter : BaseBannerAdapter<Int>() {
    override fun bindData(
        holder: BaseViewHolder<Int>, data: Int, position: Int,
        pageSize: Int
    ) {
        val imageView = holder.findViewById<ImageView>(R.id.banner_image)
        imageView.load(data)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.banner_item
    }


}