package com.macaosoftware.sdui.app.marketplace.navigationbar.topappbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.macaosoftware.component.viewmodel.StateComponent

@OptIn(ExperimentalMaterial3Api::class)
val CustomTopAppBar: @Composable StateComponent<CustomTopAppBarViewModel>.(
    modifier: Modifier,
    topAppBarViewModel: CustomTopAppBarViewModel
) -> Unit = { modifier: Modifier, topAppBarViewModel: CustomTopAppBarViewModel ->
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "TopAppBar")
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Navigation Menu"
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(it.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {
            Text("Welcome to TopAppBar Screen")
        }
    }
}