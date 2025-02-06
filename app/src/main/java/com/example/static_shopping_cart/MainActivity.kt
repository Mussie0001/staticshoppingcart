package com.example.static_shopping_cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.static_shopping_cart.ui.theme.StaticshoppingcartTheme
import kotlinx.coroutines.launch

// class for cart items
class Items(
    val name: String,
    val price: Double,
    val quantity: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StaticshoppingcartTheme {
                ShoppingCartScreen()
            }
        }
    }
}

@Composable
fun ShoppingCartScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // list of shopping cart items
    val cartItems = listOf(
        Items(name = "T-Shirt", price = 10.00, quantity = 2),
        Items(name = "Sweatpants", price = 14.99, quantity = 3),
        Items(name = "Jacket", price = 50.00, quantity = 1)
    )
    // cart total price calc
    val totalCost = cartItems.sumOf { it.price * it.quantity }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // screen title
            Text(
                text = "Shopping Cart",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            // display cart items using for loop
            for (item in cartItems) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // item
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    // Cart price and qty details
                    Text(
                        text = "Price: \$${item.price}  Qty: ${item.quantity}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // cart summary + checkout button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total: \$${"%.2f".format(totalCost)}",
                    style = MaterialTheme.typography.headlineSmall
                )
                Button(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Ordered")
                        }
                    }
                ) {
                    Text(text = "Checkout")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StaticshoppingcartTheme {
        ShoppingCartScreen()
    }
}