let previousPage = "home";


////////////////////////////////////////////
// Función para obtener una cookie
function getCookie2(name) {
  let nameEQ = name + "=";
  let ca = document.cookie.split(';');
  for (let i = 0; i < ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) == ' ') c = c.substring(1, c.length);
      if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
  }
  return null;
}

function getUserIdFromCookie2() {
  return getCookie2("userId");
}

function getBaseUrl() {
  const { protocol, host, pathname } = window.location;
  const isLocalhost = host.includes('localhost') || host.includes('127.0.0.1');

  if (isLocalhost) {
    return '';
  } else {
    const pathSegments = pathname.split('/');
    const repoName = pathSegments[1]; // Assumes the repository name is the first segment in the path
    return `${protocol}//${host}/${repoName}`;
  }
}

// Función para borrar una cookie
function deleteCookie(name) {
  document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT; path=/';
}




//////////////////////////////////////////////////////////////////



function loadPage(pageName, addToHistory = true) {


  console.log("HEYY");
  // Get the userId from the cookie
  const userId = getUserIdFromCookie2();
  console.log("USER ID: " + userId);
  // If the userId is not found, redirect to the login page
  if (!userId) {
    alert('Unauthorized access. Please login.');
    window.location.replace("../html/login.html");
    return;
  }

  console.log("IS LOGGED IN");

  const baseUrl = getBaseUrl();

  // Add new state and change the URL only if addToHistory is true
  if (addToHistory) {
    const stateObj = { page: pageName };
    history.pushState(stateObj, pageName, `${baseUrl}/html/${pageName}`);
  }

  // set the #content element to the page.html file
  $(".loading").show();
  $("#content").load("../html/" + pageName + ".html");
  document.title = pageName.charAt(0).toUpperCase() + pageName.slice(1);
  $(".loading").hide();

  const previousButtonSelected = document.getElementById(previousPage);
  previousButtonSelected.classList.remove("sidebar-button-selected");
  previousButtonSelected.classList.add("sidebar-button-unselected");

  const buttonSelected = document.getElementById(pageName);
  buttonSelected.classList.remove("sidebar-button-unselected");
  buttonSelected.classList.add("sidebar-button-selected");

  previousPage = pageName;

  changeFavicon(pageName);
}



function handleNavigationButtons() {
  window.addEventListener("popstate", function (e) {
    if (e.state && e.state.page) {
      loadPage(e.state.page, false);
    }
  });
}


function changeFavicon(iconName) {
  const link = document.querySelector("link[rel*='icon']") || document.createElement("link");
  link.type = "image/png";
  link.rel = "shortcut icon";
  link.href = `../assets/icons/${iconName}.png`;
  console.log(link.href);
  document.getElementsByTagName("head")[0].appendChild(link);
}



function sidebarListeners() {
  const home = document.getElementById("home");
  const summary = document.getElementById("summary");
  const portfolio = document.getElementById("portfolio");
  const dividends = document.getElementById("dividends");
  const social = document.getElementById("social");
  const profile = document.getElementById("profile");
  const login = document.getElementById("login");

  home.addEventListener("click", function () { loadPage("home"); });
  summary.addEventListener("click", function () { loadPage("summary"); });
  portfolio.addEventListener("click", function () { loadPage("portfolio"); });
  dividends.addEventListener("click", function () { loadPage("dividends"); });
  social.addEventListener("click", function () { loadPage("social"); });
  profile.addEventListener("click", function () { loadPage("profile"); });
  login.addEventListener("click", function () {
    window.location.replace("../html/login.html");
    deleteCookie("userId");
  });
}

function logoListener() {
  const logo = document.getElementById("logo");
  logo.addEventListener("click", function () { loadPage("home"); });
}

function searchBarListener() {
  const searchBar = document.getElementById("header-search-bar");
  // when typing in the search bar, the search results will be shown
  searchBar.addEventListener("keyup", function () {
    // show again what I did with searchResults.style.display = "none";
    const searchResults = document.getElementById("search-results");
    searchResults.style.display = "block";

    console.log("search bar value: " + searchBar.value);
    const searchedValue = searchBar.value;
    tickerSearch(searchedValue);
  });

  // when click outside the search bar and outside the search results the search results will be hidden
  document.addEventListener("click", function (e) {
    const searchResults = document.getElementById("search-results");
    if (e.target.id != "header-search-bar") {
      searchResults.style.display = "none";
    }
  });
}


function init() {
  logoListener();
  loadPage("home");
  sidebarListeners();
  searchBarListener();
  handleNavigationButtons();
}


init();
