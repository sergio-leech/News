
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> LazyGrid(
        items: List<T> = listOf(),
        column: Int = 3,
        hPadding: Int = 0,
        itemContent: @Composable LazyItemScope.(T) -> Unit
) {
    val chunkedList = items.chunked(column)
    LazyColumn(modifier = Modifier.padding(horizontal = hPadding.dp)) {
        itemsIndexed(items = chunkedList) { index, item ->
            if (index == 0) {
                Spacer(modifier = Modifier.preferredHeight(8.dp))
            }

            Row {
                item.forEachIndexed { rowIndex, item ->
                    Box(modifier = Modifier.weight(1F).align(Alignment.Top), contentAlignment = Alignment.Center) {
                        itemContent(item)
                    }
                }
                repeat(column - item.size) {
                    Box(modifier = Modifier.weight(1F).padding(2.dp)) {}
                }
            }
        }
    }
}