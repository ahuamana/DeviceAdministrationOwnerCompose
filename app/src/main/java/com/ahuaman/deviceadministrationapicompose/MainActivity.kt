package com.ahuaman.deviceadministrationapicompose

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahuaman.deviceadministrationapicompose.components.DeviceAdminSample
import com.ahuaman.deviceadministrationapicompose.ui.theme.DeviceAdministrationApiComposeTheme

class MainActivity : ComponentActivity() {

    private lateinit var dpm: DevicePolicyManager
    private lateinit var deviceAdminSample: ComponentName

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Device Admin is active
        } else {
            // Device Admin is not active
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            dpm = getSystemService(DevicePolicyManager::class.java)
            deviceAdminSample = ComponentName(this, DeviceAdminSample::class.java)

            DeviceAdministrationApiComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        onClickAction = {
                        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
                        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdminSample)
                        launcher.launch(intent)
                        },
                        onClickAction2 = {
                            val active = dpm.isAdminActive(deviceAdminSample)
                            if (active) {
                                Toast.makeText(this, "Device Admin is active", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Device Admin is not active", Toast.LENGTH_SHORT).show()
                            }
                        },
                        onClickAction3 = {
                            dpm.removeActiveAdmin(deviceAdminSample)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit,
    onClickAction2: () -> Unit,
    onClickAction3: () -> Unit
) {

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Device Administration API", modifier = Modifier.padding(16.dp))

        Button(onClick = {
            onClickAction()
        }) {
            Text(text = "Enable Device Admin")
        }

        Button(onClick = { onClickAction2() }) {
            Text(text = "Is Device Admin Active")
        }

        Button(onClick = { onClickAction3() }) {
            Text(text = "Disable Device Admin")
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DeviceAdministrationApiComposeTheme {
        HomeScreen(onClickAction = {}, onClickAction2 = {}, onClickAction3 = {})
    }
}