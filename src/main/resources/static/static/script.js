function playRadio(channel){
  var player = document.getElementById("player");
  player.src = channel;
  player.play()
}




function getAPI(){
  fetch ('http://localhost:5008/radio/' + '132')
    .then((data) => {
      return data.json();
    }).then((objectData)=>{
      console.log(objectData[0].spotifyurl);
      let tableData="";
      objectData.map((values)=>{
        tableData += 
        `<tr>Title: ${values.title}</tr>
        <tr>Artist: ${values.artist}</tr>
        <tr>Find it here!: ${values.spotifyurl}</tr>
        <tr>State: ${values.previousorcurrent}</tr>`;
      });
      document.getElementById("table_body").innerHTML = tableData;
    })
  }
    





  




