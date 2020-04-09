function shorten(){
 window.location = '#sec2';

      let input = '{'
          +'"longUrl" : "'+document.getElementById("shortener-txt").value+'"'
      +'}';

      fetch('http://localhost:8080/api/short/', {
            method: 'POST',
            headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
            },
            body: input
      }).then((data) => {
          fetchURL()})
          .catch(console.log)
}


function fetchURL(){
      fetch('http://localhost:8080/api/short/')
          .then(res => res.json())
          .then((data) => {
              populateTable(data)})
          .catch(console.log)
}

function populateTable(data){
console.log(data);
const table = document.getElementById("recentsbody");
table.innerHTML="";
let entryNr = 1;
Object.entries(data).map((url) => {
    let row = table.insertRow();
    let delbtn = row.insertCell(0);
    delbtn.innerHTML = "<button type='submit' id=delbtn value="+url[0]+" onclick='deleteUrl(this)'>Delete</button>";
    let copybtn = row.insertCell(0);
    copybtn.innerHTML = "<button type='submit' id=copybtn value="+url[0]+" onclick='copyButton(this.value)'>Copy</button>";
    let long = row.insertCell(0);
    long.innerHTML = "<a href="+url[1]+">"+url[1]+"</a>";
    let short = row.insertCell(0);
    short.innerHTML = "<a href="+url[0]+">"+url[0]+"</a>";
    let number = row.insertCell(0);
    number.innerHTML = entryNr;
    entryNr++;
    }
)
}




function deleteUrl(obj){
    var id = obj.value;
    let input = id.substring(32, 36);

    fetch('http://localhost:8080/api/short/'+input, {
                method: 'DELETE',
                headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json',
                }
          }).then((data) => {
              fetchURL();})
              .catch(console.log)
}

function copyButton(tocopy){
   // Temporäres Element erzeugen
      var tmp = document.createElement('textarea');
      // Den zu kopierenden String dem Element zuweisen
      tmp.value = tocopy;
      // Element nicht editierbar setzen und aus dem Fenster schieben
      tmp.setAttribute('readonly', '');
      tmp.style = {position: 'absolute', left: '-9999px'};
      document.body.appendChild(tmp);
      // Text innerhalb des Elements auswählen
      tmp.select();
      // Ausgewählten Text in die Zwischenablage kopieren
      document.execCommand('copy');
      // Temporäres Element löschen
      document.body.removeChild(tmp);
      alert("Copied to Clipboard!");

   }
