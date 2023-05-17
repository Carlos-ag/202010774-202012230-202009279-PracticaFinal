// Get all the input elements
const inputs = document.querySelectorAll('input, textarea, select');

// Iterate over each input element
inputs.forEach((input) => {
    // Add event listener for 'input' event
    input.addEventListener('input', (event) => {
        validateInput(event.target);
    });
});

// Add event listener for 'submit' event
document.getElementById('register-form').addEventListener('submit', (event) => {
    // Prevent default form submission
    event.preventDefault();

    // Get all inputs excluding the login button
    const inputs = document.querySelectorAll('input:not(#login-button), textarea, select');
    
    // Validate all input fields on form submission
    inputs.forEach(validateInput);
});


// Function to validate an input field
function validateInput(input) {
    if (!input.value) {
        input.setCustomValidity('This field is required.');
        input.reportValidity();
    } else {
        input.setCustomValidity('');
    }
}

let subscriptionSelect = document.getElementById('subscription');

fetch('http://localhost:8080/subscriptionPlans')
    .then(response => {
        if (!response.ok) {
            throw new Error(response.status);
        }
        return response.json();
    })
    .then(data => {
        if (data.length > 0) {
            subscriptionSelect.required = true;
            data.forEach((item) => {
                let option = document.createElement('option');
                option.value = item.id;
                option.text = item.name + ' ($' + item.price + ')';
                subscriptionSelect.appendChild(option);
            });
        } else {
            subscriptionSelect.required = false;
        }
    })
    .catch(error => {
        subscriptionSelect.required = false;
        // alert('Hubo un problema con la solicitud: ' + error.message);
    });

    function sendLoginRequest() {
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        name = document.getElementById('name').value;
        phone = document.getElementById('phone').value;

        email = document.getElementById('email').value;
        password = document.getElementById('password').value;
        
        subscriptionplan = document.getElementById('subscription').value;
      
        console.log("PLAN: " + subscriptionplan);

        var raw = JSON.stringify({
          "name": name,
          "phone": phone,
          "email": email,
          "password": password
        });
      
        var requestOptions = {
          method: 'POST',
          headers: myHeaders,
          body: raw,
          redirect: 'follow'
        };
      
        url = "http://localhost:8080/users?subscriptionPlanId=" + subscriptionplan;
        fetch(url, requestOptions)
          .then(response => response.json())
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
                if (window.name != "") {
                    window.location.href = "/html/";
                }
                // Redirect to the home page
                //window.location.href = '/html/';
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
    };
      
      // Call the function to send the login request
      //sendLoginRequest();
      
      function redirectToLogin() {
        window.location.href = "login.html";
      }