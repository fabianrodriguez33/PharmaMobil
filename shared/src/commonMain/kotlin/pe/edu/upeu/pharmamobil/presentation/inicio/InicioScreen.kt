package pe.edu.upeu.pharmamobil.presentation.inicio
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pharmamobil.shared.generated.resources.Res
import pharmamobil.shared.generated.resources.pharma_mobil

@Composable
fun InicioScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "PharmaMobil",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Sistema de gestión farmacéutica",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
    }

    Image(
        painter = painterResource(Res.drawable.pharma_mobil),
        contentDescription = "Logo PharmaMobil",
        modifier = Modifier.size(160.dp)
    )
}