document.getElementById("loginForm").addEventListener("submit", function(event) {
    var usernameInput = document.getElementsByName("username")[0];
    var passwordInput = document.getElementsByName("password")[0];
    var usernameError = document.getElementById("usernameError");
    var passwordError = document.getElementById("passwordError");
    var isValid = true;

    // Clear previous error messages
    usernameError.textContent = "";
    passwordError.textContent = "";

    // Validate Username field
    if (usernameInput.value.length < 3 || usernameInput.value.length > 20) {
        usernameError.textContent = "Username must be between 3 and 20 characters";
        isValid = false;
    }

    // Validate Password field
    if (passwordInput.value.length < 8) {
        passwordError.textContent = "Password must be at least 8 characters";
        isValid = false;
    }

    if (!isValid) {
        event.preventDefault(); // Prevent form submission if validation fails
    }
});