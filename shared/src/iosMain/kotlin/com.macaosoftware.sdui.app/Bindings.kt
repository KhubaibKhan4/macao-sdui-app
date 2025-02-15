package com.macaosoftware.sdui.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.macaosoftware.component.IosComponentRender
import com.macaosoftware.component.core.Component
import com.macaosoftware.platform.DefaultAppLifecycleDispatcher
import com.macaosoftware.platform.IosBridge
import platform.UIKit.UIViewController

fun buildDemoViewController(
    rootComponent: Component,
    iosBridge: IosBridge,
    onBackPress: () -> Unit = {}
): UIViewController = ComposeUIViewController {
    IosComponentRender(rootComponent, iosBridge, onBackPress)
}

fun buildDemoMacaoApplication(
    iosBridge: IosBridge,
    onBackPress: () -> Unit = {}
): UIViewController = ComposeUIViewController {

    IosMacaoApplication(
        iosBridge = iosBridge,
        onBackPress = onBackPress,
        rootComponentProvider = IosRootComponentProvider(iosBridge)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Example of iOS Splash Screen"
            )
        }
    }
}

// Todo: Replace with swift implementation
fun createPlatformBridge(): IosBridge {
    return IosBridge(
        appLifecycleDispatcher = DefaultAppLifecycleDispatcher()
    )
}
