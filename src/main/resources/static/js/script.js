console.log("hehehhehe");
let currentTheme = getTheme();


//Initially
changeTheme();

function changeTheme() {

    changePageTheme(currentTheme, currentTheme);

    const themebutton = document.querySelector("#theme-change-button");

    themebutton.addEventListener("click", (event) => {

        let oldTheme = currentTheme;

        if (currentTheme === "dark") {
            currentTheme = "light";
        } else {
            currentTheme = "dark";
        }
        // update theme in local storage
        setTheme(currentTheme);

        changePageTheme(currentTheme, oldTheme)



    })

}

//set theme to local storage
function setTheme(theme) {
    localStorage.setItem("theme", theme)
}

//get theme from local stroage
function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "dark";
}

//change current Page Theme
function changePageTheme(Theme, oldTheme) {
    //change theme in class --> this will change theme in pages
    document.querySelector("html").classList.remove(oldTheme);
    document.querySelector("html").classList.add(Theme);
    //set the text on button
    document.querySelector("#theme-change-button").querySelector("span").textContent = Theme == "light" ? "Dark" : "Light";
}

