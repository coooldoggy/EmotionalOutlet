import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coooldoggy.emotionaloutlet.Friend
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

val chatList = listOf<Friend>(
    Friend(name = "F친구", profile = "smile.png"), Friend(name = "대나무 숲", profile = "bamboo.png"),
    Friend(name = "쓰레기통", profile = "delete.png")
)

@Composable
fun ChatSelection() {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        LazyColumn(modifier = Modifier.fillMaxSize().align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            items(chatList.size) { index ->
                FriendItem(chatList[index])
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FriendItem(friend: Friend) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column {
            Image(
                painter = painterResource(friend.profile),
                contentDescription = friend.name,
                modifier = Modifier.clip(
                    CircleShape
                ).size(50.dp)
            )
            Text(
                text = friend.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}
