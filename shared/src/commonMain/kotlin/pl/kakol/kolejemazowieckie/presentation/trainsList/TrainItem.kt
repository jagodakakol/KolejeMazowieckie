package pl.kakol.kolejemazowieckie.presentation.trainsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.painterResource
import pl.kakol.kolejemazowieckie.domain.model.Train
import pl.kakol.kolejemazowieckie.presentation.theme.Dimens
import pl.kakol.kolejemazowieckie.presentation.theme.KmOrange
import kolejemazowieckie.shared.generated.resources.Res
import kolejemazowieckie.shared.generated.resources.compose_multiplatform
import kolejemazowieckie.shared.generated.resources.en57akm
import kolejemazowieckie.shared.generated.resources.en71
import kolejemazowieckie.shared.generated.resources.en76_elf
import kolejemazowieckie.shared.generated.resources.er75_flirt
import kolejemazowieckie.shared.generated.resources.er160_flirt3
import kolejemazowieckie.shared.generated.resources.ezt_45we
import kolejemazowieckie.shared.generated.resources.eu47_hetman
import kolejemazowieckie.shared.generated.resources.twindexx

private val trainImages = mapOf(
    "en57akm" to Res.drawable.en57akm,
    "en71" to Res.drawable.en71,
    "en76_elf" to Res.drawable.en76_elf,
    "er75_flirt" to Res.drawable.er75_flirt,
    "er160_flirt3" to Res.drawable.er160_flirt3,
    "ezt_45we" to Res.drawable.ezt_45we,
    "eu47_hetman" to Res.drawable.eu47_hetman,
    "twindexx" to Res.drawable.twindexx
)

@Composable
fun TrainItem(
    train: Train,
    onToggleCompletionButtonClick: (Train) -> Unit
) {
    val uriHandler = LocalUriHandler.current
    val image = trainImages.getOrElse(train.id) { Res.drawable.compose_multiplatform }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(Dimens.TrainBoxWidth)
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = train.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(Dimens.TrainBoxWidth)
                .height(Dimens.TrainBoxHeight)
        )
        Column(
            modifier = Modifier
                .background(KmOrange)
                .width(Dimens.TrainBoxWidth)
                .padding(Dimens.PaddingSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = train.name,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = { uriHandler.openUri(train.infoUrl) }) {
                    Text("Info", color = Color.White)
                }
                Checkbox(
                    checked = train.completed,
                    onCheckedChange = { onToggleCompletionButtonClick(train) }
                )
            }
        }
    }
}