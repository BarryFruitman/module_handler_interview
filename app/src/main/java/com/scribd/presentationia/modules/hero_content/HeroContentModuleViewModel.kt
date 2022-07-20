package com.scribd.presentationia.modules.hero_content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import com.scribd.domain.entities.ModuleType
import com.scribd.domain.entities.HeroContentModuleEntity
import com.scribd.domain.entities.ModuleEntity
import com.scribd.presentationia.modules.ModuleViewModel

class HeroContentModuleViewModel(
    override val context: ModuleViewModel.ModuleContext
): ViewModel(), ModuleViewModel {

    override val moduleType = ModuleType.HERO_CONTENT

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title
    private val _subtitle = MutableLiveData<String>()
    val subtitle: LiveData<String> = _subtitle
    private val _iconId = MutableLiveData<Int>()
    val iconId: LiveData<Int> = _iconId

    override fun bindModule(module: ModuleEntity) {
        module as? HeroContentModuleEntity ?: throw IllegalArgumentException("Wrong module type")

        _title.value = module.title
        _subtitle.value = module.subtitle
        _iconId.value = when(module.contentType.name) {
            "audiobook" -> R.drawable.ic_audiobook
            else -> R.drawable.ic_podcasts_filled_16
        }
    }
}