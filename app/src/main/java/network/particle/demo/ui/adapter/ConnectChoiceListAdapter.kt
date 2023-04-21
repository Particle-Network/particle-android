package network.particle.demo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.connect.common.IConnectAdapter
import com.minijoy.demo.R
import com.particle.base.ChainInfo


class ConnectChoiceListAdapter(private val context: Context, private val list: List<IConnectAdapter>) : BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): IConnectAdapter {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chain_choice, parent, false)
        }
        val item = getItem(position)
        val titleTextView = view!!.findViewById<TextView>(com.particle.gui.R.id.tvChainName)
        titleTextView.text = item.name
        return view
    }
}