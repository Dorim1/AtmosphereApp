package ru.anlyashenko.atmosphereapp.core.utils

import androidx.compose.ui.graphics.Color
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.models.PaletteModel

object MoodPalettes {

    val allPalettes = listOf(
        PaletteModel(
            id = 0,
            name = "Стандартная",
            colors = listOf(
                Color(0xFF8CB342),
                Color(0xFF00695C),
                Color(0xFFFBC02D),
                Color(0xFFEF6C00),
                Color(0xFFD50000)
            )
        ),
        PaletteModel(
            id = 1,
            name = "Палитра 1",
            colors = listOf(
                Color(0xFFF2C230),
                Color(0xFFF2921D),
                Color(0xFFF24F13),
                Color(0xFF8082A6),
                Color(0xFF46334F)
            )
        ),
        PaletteModel(
            id = 2,
            name = "Палитра 2",
            colors = listOf(
                Color(0xFF355459),
                Color(0xFF7A7848),
                Color(0xFFC68D40),
                Color(0xFFB46A3B),
                Color(0xFF963814)
            )
        ),
        PaletteModel(
            id = 3,
            name = "Палитра 3",
            colors = listOf(
                Color(0xFF5B643D),
                Color(0xFF314350),
                Color(0xFFC89839),
                Color(0xFFAB5E16),
                Color(0xFF792318)
            )
        ),
        PaletteModel(
            id = 4,
            name = "Палитра 4",
            colors = listOf(
                Color(0xFF3A3128),
                Color(0xFF615441),
                Color(0xFF89785C),
                Color(0xFF333333),
                Color(0xFF1D1D1D)
            )
        ),
        PaletteModel(
            id = 5,
            name = "Палитра 5",
            colors = listOf(
                Color(0xFF53658F),
                Color(0xFF2D4159),
                Color(0xFFE9B75A),
                Color(0xFFC79769),
                Color(0xFF5A4436)
            )
        ),
        PaletteModel(
            id = 6,
            name = "Палитра 6",
            colors = listOf(
                Color(0xFF7E3A27),
                Color(0xFFCEBF94),
                Color(0xFFAA9874),
                Color(0xFF5B3C1F),
                Color(0xFF221711)
            )
        ),
        PaletteModel(
            id = 7,
            name = "Палитра 7",
            colors = listOf(
                Color(0xFF604848),
                Color(0xFF907878),
                Color(0xFFC0A890),
                Color(0xFF787860),
                Color(0xFF484830)
            )
        ),
        PaletteModel(
            id = 8,
            name = "Палитра 8",
            colors = listOf(
                Color(0xFFC04732),
                Color(0xFFE59732),
                Color(0xFF772330),
                Color(0xFF3B2A3A),
                Color(0xFF744154)
            )
        ),
        PaletteModel(
            id = 9,
            name = "Палитра 9",
            colors = listOf(
                Color(0xFF66546C),
                Color(0xFFD2AA71),
                Color(0xFF3A1D1F),
                Color(0xFF892F31),
                Color(0xFFAF7A3D)
            )
        ),
        PaletteModel(
            id = 10,
            name = "Палитра 10",
            colors = listOf(
                Color(0xFF2C1106),
                Color(0xFF590505),
                Color(0xFF8C0106),
                Color(0xFFE2BA7C),
                Color(0xFFD1A068)
            )
        ),
        PaletteModel(
            id = 11,
            name = "Палитра 11",
            colors = listOf(
                Color(0xFF0E1618),
                Color(0xFF395563),
                Color(0xFF7DA1B1),
                Color(0xFFC47D7D),
                Color(0xFF711218)
            )
        ),
        PaletteModel(
            id = 12,
            name = "Палитра 12",
            colors = listOf(
                Color(0xFFB09F8B),
                Color(0xFF503D36),
                Color(0xFF15100D),
                Color(0xFF510A04),
                Color(0xFF860001)
            )
        ),
    )

    fun getPaletteById(id: Int) : PaletteModel {
        return allPalettes.find { it.id == id } ?: allPalettes[0]
    }


}

// Todo: Вынести
val availableMoodIcons = listOf(
    R.drawable.ic_mood_great, //
    R.drawable.ic_mood_neutral, //
    R.drawable.ic_mood_sad, //
    R.drawable.ic_mood_bad, //
    R.drawable.ic_mood_satisfied, //
    R.drawable.ic_mood_very_satisfied, //
    R.drawable.ic_mood_very_dissatisfied, //
    R.drawable.ic_mood_dissatisfied, //
    R.drawable.ic_mood_calm, //
    R.drawable.ic_mood_content, //
    R.drawable.ic_mood_excited, //
    R.drawable.ic_mood_extremely_dissatisfied, //
    R.drawable.ic_mood_frustrated, //
    R.drawable.ic_mood_sick, //
    R.drawable.ic_mood_stressed, //
    R.drawable.ic_mood_worried //
)