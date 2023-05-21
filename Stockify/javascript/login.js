// When the login button is clicked
document.getElementById("login").addEventListener('click',async function(event){
    // Prevent the form from submitting normally
    event.preventDefault();

    // Get the user input values
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    console.log("Email: "+ email+ " Password: " + password);

    // Make an API request to authenticate the user
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
        if (response.ok) {
            return response.json();
        } else {
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

            // Define base URL for redirection based on hostname
            let baseURL = (window.location.hostname.includes('github')) ? '/home' : '/html/';
            
            // Redirect to the home page
            window.location.href = baseURL;
        } else {
            throw new Error('Response is not an object or is empty');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        const errorMessage = document.createElement('div');
        errorMessage.classList.add('alert', 'alert-danger');
        errorMessage.textContent = "Invalid email or password";
        document.querySelector('.container-form').appendChild(errorMessage);
    })
});
