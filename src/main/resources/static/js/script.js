var usernameInput = document.getElementsByName("username")[0];
var passwordInput = document.getElementsByName("password")[0];
var usernameError = document.getElementById("usernameError");
var passwordError = document.getElementById("passwordError");

usernameInput.addEventListener("input", function() {
    // Clear previous error message
    usernameError.textContent = "";

    // Validate Username field
    if (usernameInput.value.length < 3 || usernameInput.value.length > 20) {
        usernameError.textContent = "Username must be between 3 and 20 characters";
    }
});

passwordInput.addEventListener("input", function() {
    // Clear previous error message
    passwordError.textContent = "";

    // Validate Password field
    if (passwordInput.value.length < 8) {
        passwordError.textContent = "Password must be at least 8 characters";
    }
});

document.getElementById("loginForm").addEventListener("submit", function(event) {
    // Check if form is valid
    if (usernameInput.value.length < 3 || usernameInput.value.length > 20 || passwordInput.value.length < 8) {
        event.preventDefault(); // Prevent form submission if validation fails
        // Display error messages
        if (usernameInput.value.length < 3 || usernameInput.value.length > 20) {
            usernameError.textContent = "Username must be between 3 and 20 characters";
        }
        if (passwordInput.value.length < 8) {
            passwordError.textContent = "Password must be at least 8 characters";
        }
    }
});