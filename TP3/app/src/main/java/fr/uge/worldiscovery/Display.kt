package fr.uge.worldiscovery

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.uge.worldiscovery.countries.Country

@Composable
fun FactBar(
    progress: Float,
    text: String,
    barColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
    borderColor: Color = MaterialTheme.colorScheme.onBackground,
    borderWidth: Dp = 1.dp,
    cornerRadius: Dp = 10.dp
) {
    val clampedProgress = progress.coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .border(borderWidth, borderColor, shape = RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor, shape = RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = clampedProgress)
                .clip(RoundedCornerShape(cornerRadius))
                .background(barColor, shape = RoundedCornerShape(cornerRadius))
                .background(color = Color(0xFF73c265))
        )
        Text(
            text = text,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.Center).padding(horizontal = 8.dp)
        )
    }
}


@Composable
fun CountryFact(
    rank: Int,
    country: Country,
    factValue: Float,
    maxFactValue: Float,
    unit: String
) {
    val fillRatio = (factValue / maxFactValue).coerceIn(0f, 1f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp).border(width = Dp.Hairline, color = Color.Black).height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.fillMaxSize().weight(1f/20f).background(Color.Gray))
        Text(
            text="#$rank",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(3f/20f)
        )
        Box(
            modifier = Modifier
                .weight(0.2f)
                //.height(24.dp)
                .aspectRatio(1.5f)
        ) {
            country.flag()
        }
        Box(modifier = Modifier.fillMaxSize().weight(1f/20f))
        Text(
            text = country.name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .weight(0.3f)
        )
        Box(modifier = Modifier.weight(0.6f)){
            FactBar(
                progress = fillRatio,
                text = "${factValue.toInt()} $unit",
            )
        }
        Box(modifier = Modifier.fillMaxSize().weight(1f/20f))
    }
}


@Composable
fun IsDesc(onValueSelected: (Boolean) -> Unit){
    var isToggled by remember { mutableStateOf(false) }
    Row{
        Box(modifier = Modifier.weight(1f).fillMaxSize()){
            Button(
                onClick = {
                    isToggled = !isToggled
                    onValueSelected(isToggled)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF73c265),
                    contentColor = Color.White
                )
            ) {
                Text(text = if (isToggled) "Dsc" else "Asc")
            }
        }
    }

}


@Composable
fun DropDownMenu(onValueSelected: (String) -> Unit) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf("filter") }

    Row{

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopStart)
        ) {
            Button(onClick = { expanded = !expanded }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF73c265), contentColor = Color.Gray) ) {
                Text(
                    text = selectedValue,
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                val options = listOf("Population", "Area", "Gdp", "Unemployment", "Import", "Export")
                for (option in options) {
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedValue = option
                            onValueSelected(option)
                            Toast.makeText(context, selectedValue, Toast.LENGTH_SHORT).show()
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


