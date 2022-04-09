package com.kt.notes.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kt.notes.R
import com.kt.notes.common.model.Screen
import com.kt.notes.common.presentation.components.AuthorisationTextField
import com.kt.notes.common.presentation.components.CircularProgress
import com.kt.notes.common.presentation.components.ShowSnackbar
import com.kt.notes.common.theme.NotesTheme
import com.kt.notes.login.model.LoginState

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val loginState: LoginState by loginViewModel.loginState.observeAsState(LoginState())

    LoginScreen(
        loginState = loginState,
        onUserNameChange = { loginViewModel.onUserNameTextFieldValueChanged(it) },
        onPasswordChange = { loginViewModel.onPasswordTextFieldValueChanged(it) },
        onLoginCLicked = { loginViewModel.onLoginClicked() },
        navigateToSignUpScreen = {
            navController.navigate(Screen.SignUp.route)
        },
        navigateToNotesScreen = {
            loginViewModel.onNavigated()
            navController.navigate(Screen.Notes.route) {
                popUpTo(Screen.Login.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    )
}

@Composable
private fun LoginScreen(
    loginState: LoginState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginCLicked: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
    navigateToNotesScreen: () -> Unit,
) {
    if (loginState.navigateToNotes) navigateToNotesScreen()

    val scaffoldState = rememberScaffoldState()
    NotesTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            content = {
                LoginBodyContent(
                    navigateToSignUpScreen = navigateToSignUpScreen,
                    loginState = loginState,
                    scaffoldState = scaffoldState,
                    onUserNameChange = onUserNameChange,
                    onPasswordChange = onPasswordChange,
                    onLoginCLicked = onLoginCLicked
                )
            }
        )
    }
}

@Composable
fun LoginBodyContent(
    navigateToSignUpScreen: () -> Unit,
    loginState: LoginState,
    scaffoldState: ScaffoldState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginCLicked: () -> Unit
) {
    ShowSnackbar(loginState.responseMessage, scaffoldState.snackbarHostState)

    if (loginState.isLoading) {
        CircularProgress()
    } else Column(Modifier.verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((LocalConfiguration.current.screenHeightDp / 3).dp)
                .clip(shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
                .background(color = MaterialTheme.colors.primaryVariant)
                .wrapContentSize(align = Alignment.Center)
        ) {
            Image(
                painterResource(R.drawable.ic_notes),
                contentDescription = null
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
                .padding(horizontal = 30.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Spacer(Modifier.height(40.dp))
                Text(
                    text = stringResource(R.string.login),
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(40.dp))
                AuthorisationTextField(
                    label = stringResource(R.string.username),
                    drawableId = R.drawable.ic_baseline_alternate_email_24,
                    keyBoardType = KeyboardType.Email,
                    value = loginState.userName,
                    onValueChange = onUserNameChange
                )
                Spacer(Modifier.height(40.dp))
                AuthorisationTextField(
                    label = stringResource(R.string.password),
                    drawableId = R.drawable.ic_baseline_lock_24,
                    keyBoardType = KeyboardType.Password,
                    value = loginState.password,
                    onValueChange = onPasswordChange
                )
                Spacer(Modifier.height(40.dp))
                Button(
                    onClick = onLoginCLicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = MaterialTheme.colors.primary
                    )
                ) {
                    Text(text = stringResource(R.string.login), fontSize = 20.sp)
                }
                Spacer(Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(R.string.not_a_member), color = Color.White)
                    Spacer(Modifier.width(10.dp))
                    ClickableText(
                        text = AnnotatedString(stringResource(R.string.sign_up)),
                        style = TextStyle(color = Color.White, fontSize = 16.sp),
                        onClick = { navigateToSignUpScreen() }
                    )
                }
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}
