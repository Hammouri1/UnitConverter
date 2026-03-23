package first.tutorial.unitconverter

import android.R
import android.os.Bundle
import android.provider.CalendarContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import first.tutorial.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UnitConverter(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun UnitConverter(modifier: Modifier = Modifier){

    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var iExpanded by remember { mutableStateOf(false)}
    var oExpanded by remember { mutableStateOf(false)}
    var conversionFactor by remember { mutableStateOf(1.00)}
    var oconversionFactor by remember { mutableStateOf(1.00)}

    fun convertUntis(){
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * conversionFactor * 100.0 / oconversionFactor).roundToInt() / 100.0
        outputValue = result.toString()
    }


    Column(modifier = Modifier.fillMaxSize() , verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Unit Converter",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6F00E0))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = inputValue, onValueChange = {
            inputValue = it
            convertUntis()
        }, label = {Text("Enter Value")})

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Box {
                Button(onClick = {iExpanded = true}) {
                    Text(inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "DropDown")
                }
                DropdownMenu(expanded = iExpanded, onDismissRequest = {iExpanded = false}) {
                    DropdownMenuItem(text = {Text("Centimeters")},
                        onClick = {
                            iExpanded = false
                            inputUnit = "Centimeters"
                            conversionFactor = 0.01
                            convertUntis()
                        })
                    DropdownMenuItem(text = {Text("Meters")},
                        onClick = {
                            iExpanded = false
                            inputUnit = "Meters"
                            conversionFactor = 1.00
                            convertUntis()})
                    DropdownMenuItem(text = {Text("Feet")},
                        onClick = {
                            iExpanded = false
                            inputUnit = "Feet"
                            conversionFactor = 0.3048
                            convertUntis()})
                    DropdownMenuItem(text = {Text("Milimeters")},
                        onClick = {
                            iExpanded = false
                            inputUnit = "Milimeters"
                            conversionFactor = 0.001
                            convertUntis()})
                }
            }
            Spacer(modifier = Modifier.width(16.dp))

            Box{
                Button(onClick = {
                    val tempUnit = inputUnit
                    inputUnit = outputUnit
                    outputUnit = tempUnit

                    val tempFactor = conversionFactor
                    conversionFactor = oconversionFactor
                    oconversionFactor = tempFactor

                    convertUntis()
                }) {
                    Text("⇄",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


            }

            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = {oExpanded = true}) {
                    Text(outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "DropDown")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = {oExpanded = false}) {
                    DropdownMenuItem(text = {Text("Centimeter")},
                        onClick = {
                            oExpanded = false
                            outputUnit = "Centimeters"
                            oconversionFactor = 0.01
                            convertUntis()})
                    DropdownMenuItem(text = {Text("Meter")},
                        onClick = {
                            oExpanded = false
                            outputUnit = "Meters"
                            oconversionFactor = 1.00
                            convertUntis()
                        })
                    DropdownMenuItem(text = {Text("Foot")},
                        onClick = {
                            oExpanded = false
                            outputUnit = "Feet"
                            oconversionFactor = 0.3048
                            convertUntis()})
                    DropdownMenuItem(text = {Text("Milimeter")},
                        onClick = {
                            oExpanded = false
                            outputUnit = "Milimeter"
                            oconversionFactor = 0.001
                            convertUntis()})
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("Result: $outputValue $outputUnit",
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF6F00E0)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}