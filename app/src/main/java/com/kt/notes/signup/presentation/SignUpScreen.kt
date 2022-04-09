package com.kt.notes.signup.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.kt.notes.signup.model.SignUpState

@Composable
fun SignUpScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel
) {
    val signUpState: SignUpState by signUpViewModel.signUpState.observeAsState(SignUpState())

    SignUpScreen(
        signUpState = signUpState,
        onUserNameChange = { signUpViewModel.onUserNameTextFieldValueChanged(it) },
        onPasswordChange = { signUpViewModel.onPasswordTextFieldValueChanged(it) },
        onRepeatedPasswordChange = { signUpViewModel.onRepeatedPasswordTextFieldValueChanged(it) },
        onSignUpCLicked = { signUpViewModel.onSignUpClicked() },
        popBackStack = { navController.popBackStack() },
        navigateToNotesScreen = {
            signUpViewModel.onNavigated()
            navController.navigate(Screen.Notes.route) {
                popUpTo(Screen.SignUp.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        },
    )
}

@Composable
fun SignUpScreen(
    signUpState: SignUpState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatedPasswordChange: (String) -> Unit,
    onSignUpCLicked: () -> Unit,
    popBackStack: () -> Unit,
    navigateToNotesScreen: () -> Unit
) {
    if (signUpState.navigateToNotes) navigateToNotesScreen()

    val scaffoldState = rememberScaffoldState()
    NotesTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            content = {
                SignUpBodyContent(
                    signUpState = signUpState,
                    scaffoldState = scaffoldState,
                    onUserNameChange = onUserNameChange,
                    onPasswordChange = onPasswordChange,
                    onRepeatedPasswordChange = onRepeatedPasswordChange,
                    onSignUpCLicked = onSignUpCLicked,
                    popBackStack = popBackStack
                )
            })
    }

}

@Composable
fun SignUpBodyContent(
    signUpState: SignUpState,
    scaffoldState: ScaffoldState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatedPasswordChange: (String) -> Unit,
    onSignUpCLicked: () -> Unit,
    popBackStack: () -> Unit,
) {
    ShowSnackbar(signUpState.responseMessage, scaffoldState.snackbarHostState)

    if (signUpState.isLoading) {
        CircularProgress()
    } else Column(Modifier.verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((LocalConfiguration.current.screenHeightDp / 3).dp)
                .clip(shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
                .background(color = MaterialTheme.colors.primaryVariant)
        ) {
            IconButton(
                onClick = popBackStack,
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
            Image(
                painterResource(R.drawable.ic_notes),
                contentDescription = null,
                modifier = Modifier.align(alignment = Alignment.Center)
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
                    text = stringResource(R.string.sign_up),
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(40.dp))
                AuthorisationTextField(
                    label = stringResource(R.string.username),
                    drawableId = R.drawable.ic_baseline_alternate_email_24,
                    keyBoardType = KeyboardType.Email,
                    value = signUpState.userName,
                    onValueChange = onUserNameChange
                )
                Spacer(Modifier.height(40.dp))
                AuthorisationTextField(
                    label = stringResource(R.string.password),
                    drawableId = R.drawable.ic_baseline_lock_24,
                    keyBoardType = KeyboardType.Password,
                    value = signUpState.password,
                    onValueChange = onPasswordChange
                )
                Spacer(Modifier.height(40.dp))
                AuthorisationTextField(
                    label = stringResource(R.string.repeated_password),
                    drawableId = R.drawable.ic_baseline_lock_24,
                    keyBoardType = KeyboardType.Password,
                    value = signUpState.repeatedPassword,
                    onValueChange = onRepeatedPasswordChange
                )
                Spacer(Modifier.height(40.dp))
                Button(
                    onClick = onSignUpCLicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = MaterialTheme.colors.primary
                    )
                ) {
                    Text(text = stringResource(R.string.sign_up), fontSize = 17.sp)
                }
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}
