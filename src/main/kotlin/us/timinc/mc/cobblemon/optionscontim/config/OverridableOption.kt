package us.timinc.mc.cobblemon.optionscontim.config

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.pokemon.Pokemon

data class OverridableOption<T>(
    val value: T,
    val overrides: Map<String, T> = emptyMap(),
) {
    fun getValue(pokemon: Pokemon): T =
        overrides.entries.find { (k) -> PokemonProperties.parse(k).matches(pokemon) }?.value ?: value
}
