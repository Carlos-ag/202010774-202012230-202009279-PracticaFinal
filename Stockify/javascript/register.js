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
