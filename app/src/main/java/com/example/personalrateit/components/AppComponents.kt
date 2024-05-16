package com.example.quickidenti.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personalrateit.app.clickedSpecialization
import com.example.personalrateit.app.specializationInfo
import com.example.personalrateit.data.Specialization
import com.example.personalrateit.navigation.PersonalRateITAppRouter
import com.example.personalrateit.navigation.Screen
import com.example.quickidenti.ui.theme.BgColor
import com.example.quickidenti.ui.theme.Primary
import com.example.quickidenti.ui.theme.TextColor
import com.example.quickidenti.ui.theme.White
import kotlin.math.absoluteValue

@Composable
fun TextComponent(value: String,
                  fontSize: Int = 18,
                  textAlign: TextAlign = TextAlign.Center,
                  fontWeight: FontWeight = FontWeight.Normal,
                  heightIn: Int = 24,
                  color: Color = TextColor){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = heightIn.dp),
        style = TextStyle(
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontStyle = FontStyle.Normal
        ),
        color = color,
        textAlign = textAlign
    )
}


@Composable
fun TextFieldComponent(
    labelValue: String,
    mask: MaskVisualTransformation = MaskVisualTransformation(""),
    painterResource: Painter?,
    textValue: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default){
    if (painterResource != null)
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(4.dp)),
        value = textValue,
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor =  Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            backgroundColor = BgColor
        ),
        keyboardOptions = keyboardOptions,
        singleLine = true,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        }
    )
    else
        OutlinedTextField(
            modifier = Modifier
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(4.dp)),
            value = textValue,
            label = { Text(text = labelValue) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor =  Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            backgroundColor = BgColor),
            placeholder = { Text(text = "XXX-XXX-XXX XX") },
            visualTransformation = mask,
            keyboardOptions = keyboardOptions,
            singleLine = true,
            onValueChange = onValueChange
    )
}

@Composable
fun ButtonComponent(value: String,
                    modifier: Modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(48.dp),
                    action: () -> Unit){
    Button(
        modifier = modifier,
        onClick = action,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(90.dp)
        ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .heightIn(150.dp)
            .background(
                color = Primary,
                shape = RoundedCornerShape(90.dp)
            ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = White)
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun SimpleLazyColumnScreen(specialization: MutableList<Specialization>) {
    val specializations by remember { mutableStateOf(specialization) }
    Box {
        LazyColumn {
            items(specializations) { specialization ->
                PersonView(id = specialization.id,
                    title = specialization.title,
                    onItemClick = { id ->
                        clickedSpecialization.intValue = id
                        specializationInfo = mutableListOf(specializations[id].title, specializations[id].code, specializations[id].seatsAmount.toString(), specializations[id].educationPeriod)
                        PersonalRateITAppRouter.navigateTo(Screen.SpecializationInfoScreen, true)
                    })
            }
        }
    }
}
@Composable
fun PersonView(id: Int,
               title: String,
               onItemClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .clickable {
                    onItemClick(id)
                }
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

class MaskVisualTransformation(private val mask: String): VisualTransformation {
    private val specialSymbolsIndices = mask.indices.filter { mask[it] != 'X' }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0
        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }
        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object: OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            if (offsetValue == 0) return 0
            var numberOfHashtags = 0
            val masked = mask.takeWhile {
                if (it == 'X') numberOfHashtags++
                numberOfHashtags < offsetValue
            }
            return masked.length + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            return mask.take(offset.absoluteValue).count { it == 'X' }
        }
    }
}