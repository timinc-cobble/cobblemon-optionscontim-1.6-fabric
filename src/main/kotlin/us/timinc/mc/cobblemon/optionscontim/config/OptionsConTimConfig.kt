package us.timinc.mc.cobblemon.optionscontim.config

class OptionsConTimConfig {
    val debug: Boolean = false
    val outOfBattleCaptures: OverridableOption<Boolean> = OverridableOption(true)
    val onlyDropInBattle: OverridableOption<Boolean> = OverridableOption(false)
}