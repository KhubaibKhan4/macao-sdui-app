package com.macaosoftware.sdui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.macaosoftware.component.IosComponentRender
import com.macaosoftware.component.core.Component
import com.macaosoftware.platform.IosBridge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun IosMacaoApplication(
    iosBridge: IosBridge,
    onBackPress: () -> Unit,
    rootComponentProvider: RootComponentProvider,
    splashScreenContent: @Composable () -> Unit
) {
    var rootComponent by remember(iosBridge, rootComponentProvider) {
        mutableStateOf<Component?>(null)
    }
    rootComponent.ifNotNull {
        IosComponentRender(
            rootComponent = it,
            iosBridge = iosBridge,
            onBackPress = onBackPress
        )
    }.elseIfNull {
        splashScreenContent()
        rememberCoroutineScope().launch {
            rootComponent = withContext(Dispatchers.IO) {
                rootComponentProvider.provideRootComponent()
            }
        }
    }
}
