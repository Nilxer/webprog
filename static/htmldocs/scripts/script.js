function down(){
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
              console.log(data)})
          .catch(console.log)
}