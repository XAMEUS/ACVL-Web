function isDate(dateString) {
    // Format : YYYY-MM-DD
    var datePattern = /^\d{4}-\d{2}-\d{2}$/;
    if (!dateString.match(datePattern))
        return false;
   
    var d = new Date(dateString);
    if (!d.getTime() && d.getTime() !== 0)
        return false;   
    //return d.toISOString().slice(0,10) === dateString;
    return false;
}