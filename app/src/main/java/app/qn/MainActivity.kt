package app.qn

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.lifecycle.viewmodel.compose.viewModel
import app.qn.data.NoteDatabase
import app.qn.viewmodel.NoteViewModel
import app.qn.viewmodel.NoteViewModelFactory
import app.qn.navigation.AppNavigation

class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		enableEdgeToEdge()
		super.onCreate(savedInstanceState)
        
        val database = NoteDatabase.getDatabase(applicationContext)
        
		setContent {
			val colorScheme = if(isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
			val viewModel: NoteViewModel = viewModel(
                factory = NoteViewModelFactory(database.noteDao())
            )
			MaterialTheme(colorScheme = colorScheme) {
				Surface(modifier = Modifier.fillMaxSize()) {
					AppNavigation(viewModel = viewModel)
				}
			}
		}
    }
}
