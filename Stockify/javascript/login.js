// When the login button is clicked
document.getElementById("login").addEventListener('click',async function(event){
    // Prevent the form from submitting normally
    event.preventDefault();

    // Get the user input values
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    console.log("Email: "+ email+ " Password: " + password);
    // Make an API request to authenticate the user
    // CAMBIAR CARLOS TODO:!!!!!
    await fetch("http://localhost:8080/login", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
    .then(response => {
        // If the response is successful
        if (response.ok) {
            return response.json();
            
        } else {
            // Display an error message on the screen
            throw new Error("Invalid data");
        }
    })
    .then(response => {
        if (typeof response === 'object' && response !== null) {
            console.log(response);
            console.log(response.name);
    
            saveUserIdInCookie(response.id);
            const cookie = getCookie2('userID');
            userID = getUserIdFromCookie2();
    
            // Set the userID cookie
            document.cookie = `userID=${userID}; path=/`;
            window.name = response.name;
            // Redirect to the home page
            window.location.href = "index.html";
        } else {
            throw new Error('Response is not an object or is empty');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        // Display an error message on the screen
        const errorMessage = document.createElement('div');
        errorMessage.classList.add('alert', 'alert-danger');
        errorMessage.textContent = "Invalid email or password";
        document.querySelector('.container-form').appendChild(errorMessage);
    })
});