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

function checkDate(name) {
    var element = document.getElementsByName(name)[0];
    if (isDate(element.value)) {
        return true;
    } else {
        var p = document.createElement("p");
        p.setAttribute("class", "error-message");
        var txt = document.createTextNode("Date invalide : Veuillez respecter le format YYYY-MM-DD.");
        p.appendChild(txt);
        element.parentElement.appendChild(p);
        return false;
    }
}

function checkPeriod() {
    var err = document.getElementsByClassName("error-message");
    while(err[0]) {
        err[0].parentNode.removeChild(err[0]);
    }
    
    if (checkDate("limitDate") && checkDate("startDate") && checkDate("endDate")) {
         var startDate = document.getElementsByName("startDate")[0];
         var endDate = document.getElementsByName("endDate")[0];
         if (startDate.value < endDate.value) {
             return true;
         } else {
            var p = document.createElement("p");
            p.setAttribute("class", "error-message");
            var txt = document.createTextNode("Date invalide : la date de début doit être avoir lieu avant la date de fin.");
            p.appendChild(txt);
            startDate.parentElement.appendChild(p);
            endDate.parentElement.appendChild(p.cloneNode(true));
            return false;
         }
    } else {
        return false;
    }
}
