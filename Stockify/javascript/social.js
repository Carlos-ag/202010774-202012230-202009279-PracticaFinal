var data = []; // this will hold the data from API

window.onload = function() {
    fetchData();
};

function fetchData() {
    fetch('http://localhost:8080/socials')
    .then(response => response.json())
    .then(json => {
        console.log(json);
        data = json;
        displayTable(); // default display
    });
}

function displayTable() {
    let tableHTML = '<table><tr><th>Name</th><th>Email</th><th>Subscription Plan</th><th>Portfolio Movements</th></tr>';
    data.forEach((item) => {
        let color = getColor(item.portfolio_movements);
        tableHTML += `<tr>
                        <td>${item.name}</td>
                        <td>${item.email}</td>
                        <td>${item.subscription_plan}</td>
                        <td><div class="circle" style="background-color:${color}"></div>${item.portfolio_movements}</td>
                      </tr>`;
    });
    tableHTML += '</table>';
    document.getElementById('table-container').innerHTML = tableHTML;
    document.getElementById('card-container').innerHTML = '';
}

function displayCard() {
    let cardHTML = '';
    data.forEach((item) => {
        let color = getColor(item.portfolio_movements);
        cardHTML += `<div class="card" style="background-image: linear-gradient(${color}, ${color})">
                        <p>Name: ${item.name}</p>
                        <p>Email: ${item.email}</p>
                        <p>Subscription Plan: ${item.subscription_plan}</p>
                        <p>Portfolio Movements: ${item.portfolio_movements}</p>
                     </div>`;
    });
    document.getElementById('card-container').innerHTML = cardHTML;
    document.getElementById('table-container').innerHTML = '';
}

function getColor(portfolio_movements) {
    if (portfolio_movements < 10) return 'green';
    if (portfolio_movements >= 10 && portfolio_movements <= 25) return 'orange';
    return 'red';
}

function searchFunction() {
    let searchValue = document.getElementById('search').value.toLowerCase();
    let searchData = data.filter(item =>
        item.name.toLowerCase().includes(searchValue) ||
        item.email.toLowerCase().includes(searchValue) ||
        item.subscription_plan.toLowerCase().includes(searchValue) ||
        String(item.portfolio_movements).includes(searchValue));
    data = searchData;
    displayTable(); // or displayCard(), depends on the current view
}
