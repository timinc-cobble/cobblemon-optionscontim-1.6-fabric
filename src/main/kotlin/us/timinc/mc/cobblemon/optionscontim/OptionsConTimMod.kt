package us.timinc.mc.cobblemon.optionscontim

import com.cobblemon.mod.common.api.events.CobblemonEvents
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import us.timinc.mc.cobblemon.optionscontim.config.ConfigBuilder
import us.timinc.mc.cobblemon.optionscontim.config.OptionsConTimConfig

object OptionsConTimMod : ModInitializer {
    @Suppress("MemberVisibilityCanBePrivate")
    const val MOD_ID = "optionscontim"

    @Suppress("MemberVisibilityCanBePrivate")
    lateinit var config: OptionsConTimConfig

    init {
        CobblemonEvents.THROWN_POKEBALL_HIT.subscribe { evt ->
            val pokemon = evt.pokemon.pokemon
            if (!evt.pokemon.isBattling && !config.outOfBattleCaptures.getValue(pokemon)) evt.cancel()
        }
    }

    override fun onInitialize() {
        config = ConfigBuilder.load(OptionsConTimConfig::class.java, MOD_ID)

        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register { _, _, _ ->
            config = ConfigBuilder.load(OptionsConTimConfig::class.java, MOD_ID)
        }
    }
}