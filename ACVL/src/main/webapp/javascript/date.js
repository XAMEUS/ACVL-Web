function isDate(dateString) {
    console.log(dateString);
    // Format : YYYY-MM-DD
    var datePattern = /^\d{4}-\d{2}-\d{2}$/;
    if (!dateString.match(datePattern))
        return false;
   
    var d = new Date(dateString);
    if (!d.getTime() && d.getTime() !== 0)
        return false;   
    return d.toISOString().slice(0,10) === dateString;
}

function checkBirthdate() {
    var birthdate = document.getElementsByName("birthdate")[0];
    if (isDate(birthdate.value)) {
        return true;
    } else {
        var p = document.createElement("p");
        p.setAttribute("id", "errMsg");
        var txt = document.createTextNode("Date invalide : Veuillez respecter le format YYYY-MM-DD.");
        p.appendChild(txt);
        birthdate.parentElement.appendChild(p);
        return false;
    }
}