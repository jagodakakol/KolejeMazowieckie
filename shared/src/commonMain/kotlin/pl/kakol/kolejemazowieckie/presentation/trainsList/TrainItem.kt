package pl.kakol.kolejemazowieckie.presentation.trainsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import pl.kakol.kolejemazowieckie.domain.model.Train
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainItem(
    train: Train,
    onSeenChange: (Boolean) -> Unit,
    onRodeChange: (Boolean) -> Unit,
    onRatingChange: (Int) -> Unit
) {
    val uriHandler = LocalUriHandler.current
    val image = trainImages.getOrElse(train.id) { Res.drawable.compose_multiplatform }

    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(image),
                contentDescription = train.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(170.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = train.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))

                // Ocena w gwiazdkach
                Row {
                    for (i in 1..5) {
                        Text(
                            text = if (i <= train.rating) "★" else "☆",
                            color = Color(0xFFFFB300),
                            fontSize = 28.sp,
                            modifier = Modifier
                                .clickable { onRatingChange(i) }
                                .padding(end = 4.dp)
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))

                // Przełączniki: widziałem / jechałem
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = train.seen,
                        onClick = { onSeenChange(!train.seen) },
                        label = { Text("Widziałem") }
                    )
                    FilterChip(
                        selected = train.rode,
                        onClick = { onRodeChange(!train.rode) },
                        label = { Text("Jechałem") }
                    )
                }
                Spacer(Modifier.height(8.dp))

                TextButton(
                    onClick = { uriHandler.openUri(train.infoUrl) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Więcej informacji →")
                }
            }
        }
    }
}