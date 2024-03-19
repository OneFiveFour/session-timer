package net.onefivefour.sessiontimer.core.ui.components.dragger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Dragger() {

    val numberOfRows = 6

    Column(
        modifier = Modifier
            .padding(
                horizontal = 6.dp,
                vertical = 4.dp
            )
            .alpha(0.5f)
    ) {

        repeat(numberOfRows) {

            if (it != 0) {
                Spacer(modifier = Modifier.size(1.dp))
            }

            Row {
                Box(modifier = Modifier
                    .size(2.dp)
                    .background(MaterialTheme.colorScheme.onSurface)
                )

                Spacer(modifier = Modifier.size(1.dp))

                Box(modifier = Modifier
                    .size(2.dp)
                    .background(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    }

}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun DraggerPreview() {
    Dragger()
}