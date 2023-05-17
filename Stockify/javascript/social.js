var data = []; // this will hold the data from API
var view = "table"; // default view is "table
document.getElementById('table-button').checked = true;

document.getElementById('table-button').classList.add('active');

function fetchData() {
    fetch('http://localhost:8080/socials')
    .then(response => response.json())
    .then(json => {
        console.log(json);
        data = json;
        displayViewSelected();
    });
}


function displayTable() {
    let tableHTML = '<table><tr><th>Name</th><th>Email</th><th>Subscription Plan</th><th>Portfolio Movements</th></tr>';
    data.forEach((item) => {
        tableHTML += `<tr>
                        <td>${item.name}</td>
                        <td>${item.email}</td>
                        <td>${item.subscription_plan}</td>
                        <td>${item.portfolio_movements}</td> 
                      </tr>`;
    });
    tableHTML += '</table>';
    document.getElementById('table-container').innerHTML = tableHTML;
    document.getElementById('card-container').innerHTML = '';
}

function displayCard() {
    let cardHTML = '';
    data.forEach((item) => {
        let color = getCardColor(item.portfolio_movements); // Use different method for card color
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

function getCardColor(portfolio_movements) { // New method for card color
    if (portfolio_movements < 10) return '#FFA500'; // Different shades of orange
    if (portfolio_movements >= 10 && portfolio_movements <= 25) return '#FF8C00';
    return '#FF4500';
}

function searchFunction() {
    let searchValue = document.getElementById('search').value;
    fetch(`http://localhost:8080/socials/search?name=${searchValue}`)
    .then(response => response.json())
    .then(json => {
        console.log(json);
        data = json;
        displayViewSelected();

    });
}

function toggleVariable(viewSelected) {
    view = viewSelected;

    if(view === 'table') {
        document.getElementById('table-button').checked = true;
        document.getElementById('card-button').checked = false;
    } else if(view === 'card') {
        document.getElementById('card-button').checked = true;
        document.getElementById('table-button').checked = false;
    }
    displayViewSelected();
}




function displayViewSelected() {
    if (data.length === 0) {
        
        document.getElementById('error-results').style.display = 'block';

        document.getElementById('table-container').style.display = 'none';
        document.getElementById('card-container').style.display = 'none';

    }
    else {
        document.getElementById('error-results').style.display = 'none';


        document.getElementById('table-container').style.display = 'block';
        document.getElementById('card-container').style.display = 'block';

        if (view === 'table') 
        {displayTable();}
        else if (view === 'card')
        {displayCard();}
    }
    
}

fetchData();