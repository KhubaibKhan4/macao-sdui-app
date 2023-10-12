package com.macaosoftware.sdui.app.sdui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.navbar.BottomNavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationComponentDefaults
import com.macaosoftware.component.viewmodel.StateComponent
import com.macaosoftware.sdui.app.data.SduiConstants
import com.macaosoftware.sdui.app.view.AirportDemoComponentView
import com.macaosoftware.sdui.app.view.HotelDemoComponentView
import com.macaosoftware.sdui.app.viewmodel.AirportDemoViewModel
import com.macaosoftware.sdui.app.viewmodel.HotelDemoViewModel
import com.macaosoftware.sdui.app.viewmodel.factory.AirportDemoViewModelFactory
import com.macaosoftware.sdui.app.viewmodel.factory.AppBottomNavigationViewModelFactory
import com.macaosoftware.sdui.app.viewmodel.factory.HotelDemoViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent

class SduiComponentFactory(
    private val koinApplication: KoinApplication
) : KoinComponent {

    override fun getKoin(): Koin = koinApplication.koin

    fun getNavItemOf(
        componentJson: JsonObject
    ): NavItem {
        val childComponentType: String =
            componentJson
                .getValue(SduiConstants.JsonKeyName.componentType)
                .jsonPrimitive
                .content

        return when (childComponentType) {

            SduiConstants.ComponentType.AppBottomNavigation -> {
                NavItem(
                    label = "AppBottomNavigation",
                    component = getComponentInstanceOf(componentJson),
                    icon = Icons.Default.AccountBox
                )
            }

            SduiConstants.ComponentType.AirportDemoComponent -> {
                NavItem(
                    label = "Hotel",
                    component = getComponentInstanceOf(componentJson),
                    icon = Icons.Default.Home
                )
            }

            SduiConstants.ComponentType.HotelDemoComponent -> {
                NavItem(
                    label = "Air",
                    component = getComponentInstanceOf(componentJson),
                    icon = Icons.Default.Search
                )
            }

            else -> {
                throw Exception("Missing NavItem factory for $childComponentType")
            }
        }
    }

    fun getComponentInstanceOf(
        componentJson: JsonObject
    ): Component {

        val componentType: String =
            componentJson
                .getValue(SduiConstants.JsonKeyName.componentType)
                .jsonPrimitive
                .content

        return when (componentType) {

            SduiConstants.ComponentType.AppBottomNavigation -> {
                BottomNavigationComponent(
                    viewModelFactory = AppBottomNavigationViewModelFactory(
                        sduiHandler = AppBottomSduiHandler(componentJson, this),
                        bottomNavigationStatePresenter = BottomNavigationComponentDefaults.createBottomNavigationStatePresenter(
                            dispatcher = Dispatchers.Main
                        )
                    ),
                    content = BottomNavigationComponentDefaults.BottomNavigationComponentView
                )
            }

            SduiConstants.ComponentType.HotelDemoComponent -> {
                StateComponent<HotelDemoViewModel>(
                    viewModelFactory = HotelDemoViewModelFactory(this),
                    content = HotelDemoComponentView
                )
            }

            SduiConstants.ComponentType.AirportDemoComponent -> {
                StateComponent<AirportDemoViewModel>(
                    viewModelFactory = AirportDemoViewModelFactory(this),
                    content = AirportDemoComponentView
                )
            }

            else -> {
                throw Exception("Missing Component factory for $componentType")
            }
        }

    }

}
