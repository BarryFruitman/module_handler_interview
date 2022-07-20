package com.scribd.app.discover_modules.hero_content

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.scribd.app.DocUtils
import com.scribd.app.discover_modules.*
import com.scribd.presentation.AdapterWithAnalytics

class HeroContentModuleHandler(fragment: Fragment, delegate: ModuleDelegate)
    : ModuleHandler<BasicDiscoverModuleWithMetadata, HeroContentModuleHandler.HeroContentViewHolder>(fragment, delegate) {

    class HeroContentViewHolder(view: View) : ModuleViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textViewHeroContentTitle)
        val subtitle: TextView = view.findViewById(R.id.textViewHeroContentSubtitle)
        val icon: ImageView = view.findViewById(R.id.imageViewHeroContentIcon)
        val root: ConstraintLayout = view.findViewById(R.id.constraintLayoutHeroContentRoot)
    }

    override fun getLayoutId() = R.layout.module_hero_content

    override fun createViewHolder(itemView: View) = HeroContentViewHolder(itemView)

    override fun canHandle(discoverModule: DiscoverModule) = discoverModule.type == DiscoverModule.Type.hero_content.name

    override fun isDataValid(discoverModule: DiscoverModule): Boolean = !discoverModule.title.isNullOrBlank()

    override fun createDiscoverModuleWithMetadata(discoverModule: DiscoverModule, metadata: DiscoverModuleWithMetadata.ModuleMetadata?) =
        BasicDiscoverModuleWithMetadataFactory(this, discoverModule, metadata).createBasicDiscoverModuleWithMetadata()

    override fun handleView(module: BasicDiscoverModuleWithMetadata, holder: HeroContentViewHolder, position: Int, parentAdapter: AdapterWithAnalytics<*>?) {
        holder.root.contentDescription = "${module.discoverModule.title} ${module.discoverModule.subtitle}"

        holder.title.text = module.discoverModule.title
        holder.subtitle.text = module.discoverModule.subtitle
        
        if(module.discoverModule.contentTypes.isNotEmpty()){
            val contentType = module.discoverModule.contentTypes[0].name
            holder.icon.setImageResource(DocUtils.getIconResForContentType(contentType, R.drawable.ic_audiobook, false))
        }
    }

}
