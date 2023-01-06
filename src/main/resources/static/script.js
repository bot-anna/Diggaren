function playRadio(channel){
  var player = document.getElementById("player");
  player.src = channel;
  player.play()
}

function getAPI(id){
  fetch ('http://localhost:5008/radio/' + id)
    .then((data) => {
      return data.json();
    }).then((objectData)=>{
      console.log(objectData[0].spotifyurl);
      let tableData="";
      objectData.map((values)=>{
        tableData += 
        `<tr>Title: ${values.title}</tr>
        <tr>Artist: ${values.artist}</tr>
        <iframe style="border-radius:12px" src="${"https://open.spotify.com/embed/track/" + values.spotifyurl + "?utm_source=generator"}" width="100%" height="352" frameBorder="0" allowfullscreen="" allow="autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture" loading="lazy">></iframe>
        <tr>State: ${values.previousorcurrent}</tr>`;
      });
      document.getElementById("table_body").innerHTML = tableData;
    })
  }

