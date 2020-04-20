function submitURL(){
    //Retrieves the long URL from the input, generate JSON structure and reset input
    let message = document.getElementById("shortener-text").value
    let input = '{' + '"longUrl" : "' + message + '"' + '}';
    document.getElementById('shortener-text').value='';

    //Check for HTML tag characters (< >) to prevent HTML injections
    if(message.includes("<") || message.includes(">")){
        alert("Invalid character!");
        window.location = '#new';
        return false;
    }

    //Check for empty input
    if(message == ""){
        alert("You can't shorten an empty URL!");
        window.location = '#new';
        return false;
    }

    //Send long URL to shortener API as POST
    fetch('http://localhost:8080/api/short/', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: input
    }).then((data) => {fetchURL()}).catch(console.log);

    //Automatically scroll down to URL overview
    window.location = '#recents';
}


function fetchURL(){
    fetch('http://localhost:8080/api/short/')
      .then(res => res.json())
      .then((data) => {listURL(data)}).catch(console.log);
}


function listURL(data){
    //Reset old table content
    const table = document.getElementById("tb-recents");
    table.innerHTML="";

    //Retrieve long and corresponding short URL and create row for each URL with copy and delete button
    Object.entries(data).reverse().map((url) => {
        let row = table.insertRow();
        let actionbutton = row.insertCell(0);

        actionbutton.innerHTML =
        "<button type='submit' id=actionbutton value="+url[0]+" onclick='copyURL(this.value)'>Copy</button>" +
        "<button type='submit' id=actionbutton value="+url[0]+" onclick='deleteURL(this)'>Delete</button>";

        let long = row.insertCell(0);
        long.innerHTML = "<a href="+url[1]+">"+url[1]+"</a>";

        let short = row.insertCell(0);
        short.innerHTML = "<a href="+url[0]+">"+url[0]+"</a>";
    })
}


function deleteURL(url){
    //Retrieve ID of URL to be deleted
    var id = url.value;
    let input = id.substring(32, 36);

    //Send short URL ID to API as DELETE
    fetch('http://localhost:8080/api/short/'+input, {
        method: 'DELETE',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        }
    }).then((data) => {fetchURL();}).catch(console.log);
}


function copyURL(tocopy){
    //Create temporary element and add string to copy
    var tmp = document.createElement('textarea');
    tmp.value = tocopy;

    //Set element to readonly and move out of view
    tmp.setAttribute('readonly', '');
    tmp.style = {position: 'absolute', left: '-999px'};
    document.body.appendChild(tmp);

    //Select element and copy text to clipboard
    tmp.select();
    document.execCommand('copy');

    //Delete temporary element and show message
    document.body.removeChild(tmp);
    alert("Short URL successfully to clipboard!");
}
