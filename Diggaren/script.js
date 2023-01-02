function playRadio(channel){
  var player = document.getElementById("player");
  player.src = channel;
  player.play()
}



function getSpotify(){
  
  var id = document.getElementById("P1");
  var id2 = document.getElementById("P2");
  var id3 = document.getElementById("P3");
  var id4 = document.getElementById("P4");
  var id4B = document.getElementById("P4 Blekinge");
  var id4D = document.getElementById("P4 Dalarna");
  var id4GO = document.getElementById("P4 Gotland");
  var id4GÄ = document.getElementById("P4 Gävlinge");
  var id4GÖ = document.getElementById("P4 Göteborg");
  var id4H = document.getElementById("P4 Halland");
  var id4J = document.getElementById("P4 Jämtland");

    id.onclick = function(){
      document.getElementById("P1iframe").src = "http://localhost:5008/radio/132";
    }
    id2.onclick = function(){
      document.getElementById("P1iframe").src = "http://localhost:5008/radio/163";
    }
    id3.onclick = function(){
      document.getElementById("P1iframe").src = "http://localhost:5008/radio/164";
    }
    id4.onclick = function(){
      document.getElementById("P1iframe").src = "http://localhost:5008/radio/213";
    }
    id4B.onclick = function(){
      document.getElementById("P1iframe").src = "http://localhost:5008/radio/213";
    }
    id4D.onclick = function(){
      document.getElementById("P1iframe").src = "http://localhost:5008/radio/223";
    }
    id4GO.onclick = function(){
      document.getElementById("P1iframe").src = "http://localhost:5008/radio/205";
    }
    id4GÄ.onclick = function(){
      document.getElementById("P1iframe").src = "http://localhost:5008/radio/210";
    }
    id4GÖ.onclick = function(){
      document.getElementById("P1iframe").src = "http://localhost:5008/radio/212";
    }
    id4H.onclick = function(){
      document.getElementById("P1iframe").src = "http://localhost:5008/radio/220";
    }
    id4J.onclick = function(){
      document.getElementById("P1iframe").src = "http://localhost:5008/radio/200";
    }

}


//Fetch artist, genre, album etc

fetch('#')
  .then(res => {
    return res.json();
  })
  .then(data => {
    data.forEach(user => {
      const markup = `<li>${user.name}</li>`;

      document.querySelector('ul').insertAdjacentHTML('beforeend', markup);
    });
  })
  .catch(error => console.log(error))
