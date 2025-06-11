package us.timinc.mc.cobblemon.optionscontim

import com.cobblemon.mod.common.api.Priority
import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import us.timinc.mc.cobblemon.optionscontim.config.ConfigBuilder
import us.timinc.mc.cobblemon.optionscontim.config.OptionsConTimConfig

object OptionsConTimMod : ModInitializer {
    @Suppress("MemberVisibilityCanBePrivate")
    const val MOD_ID = "optionscontim"

    @Suppress("MemberVisibilityCanBePrivate")
    lateinit var config: OptionsConTimConfig

    private val logger: Logger = LogManager.getLogger(MOD_ID)

    init {
        CobblemonEvents.THROWN_POKEBALL_HIT.subscribe { evt ->
            val pokemon = evt.pokemon.pokemon
            if (!config.outOfBattleCaptures.getValue(pokemon)) {
                if (!evt.pokemon.isBattling) {
                    debug("[outOfBattleCaptures][${pokemon.uuid}](${pokemon.species.name}) Blocked from capture.")
                    evt.cancel()
                }
            }
        }
        CobblemonEvents.LOOT_DROPPED.subscribe(Priority.HIGHEST) { evt ->
            val pokemonEntity = (evt.entity as? PokemonEntity ?: return@subscribe)
            val pokemon = pokemonEntity.pokemon
            if (config.onlyDropInBattle.getValue(pokemon)) {
                if (!pokemonEntity.isBattling) {
                    debug("[onlyDropInBattle][${pokemon.uuid}](${pokemon.species.name}) Blocked from dropping.")
                    evt.cancel()
                }
            }
        }
    }

    override fun onInitialize() {
        config = ConfigBuilder.load(OptionsConTimConfig::class.java, MOD_ID)

        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register { _, _, _ ->
            config = ConfigBuilder.load(OptionsConTimConfig::class.java, MOD_ID)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun debug(msg: String, bypassConfig: Boolean = false) {
        if (!config.debug && !bypassConfig) return
        logger.info(msg)
    }
}