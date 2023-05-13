document.addEventListener('DOMContentLoaded', (event) => {
    fetch('http://localhost:8080/subscriptionPlans')
    .then(response => response.json())
    .then(data => {
        let select = document.getElementById('subscription');
        data.forEach((item) => {
            let option = document.createElement('option');
            option.value = item.id;
            option.text = item.name + ' ($' + item.price + ')';
            select.appendChild(option);
        });
    });

    document.getElementById('register-form').addEventListener('submit', function(event) {
        event.preventDefault();
        let form = event.target;
        let user = {
            name: form.name.value,
            email: form.email.value,
            phone: form.phone.value,
            subscriptionPlanId: form.subscription.value,
            password: form.password.value
        };
        console.log(user);
    });
});
