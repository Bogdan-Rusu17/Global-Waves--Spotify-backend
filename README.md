*COPYRIGHT RUSU BOGDAN*
# Project GlobalWaves  - Stage 1

The purpose of the `first stage` of the project is to implement a `backend` simulation of a `Spotify-like` application in `Java` that supports `user input`.
* the goals of the project include:
  * utilizing basic OOP concepts to conceive a `larger-scale` Java project;
  * usage of `inheritance` and `overriding/overloading` to achieve reusable code;
  * debugging in a different `programming language`.

* user input comes in the form of a `JSON` file that is parsed using the `ObjectMapper` class and its functionalities.
* after determining the `type` of the command, a `copy constructor` is called to perform said command and formats the output back into a `JSON file`.

* the `conceptual approach` for the task was as follows in the picture bellow:
<div style="border: 2px solid black; padding: 10px; margin: 10px; width: 80vw; height: 50vh; position:relative">
  <input placeholder="What do you want to listen to? "style= "border-radius:20px 20px 20px 20px; position: relative; margin-left:24%; margin-right:20%; background-color: rgb(49, 55, 51); border:2px solid rgb(0, 0, 0); text-align:center; padding: 5px; width: 40vw; height:6vh">  </input>
  <div style="position: absolute; left:2vw; top:2vh">
    <table style="border:none">
      <tr>
        <th style="text-align:center; border:none">Playlists</th> 
      </tr> 
      <tr>
        <td style="text-align:center; font-size:.7rem; border:none">Best pop songs</td>
      </tr>
      <tr>
        <td style="text-align:center; font-size:.7rem; border:none">90's rock</td>
      </tr>
    </table>
  </div>
  
  <div style="position: absolute; top: 12vh; bottom: 10.1vh; left: 18vw; right: -0.3vw; border: 2px solid black">
    <div style="position: absolute; top: 2vh; left:2vw"> Ed Sheeran
    </div>
    <span style="position: absolute; left: 26vw; top:8vh; bottom: 2.1vh; right: 2vw; border: 2px solid black; border-radius: 20px">
      <span>
        <table style="position: absolute; border:none; font-size: .8rem">
          <tr> 
            <th style="border: none; position: absolute;left:5vw">
            Merch
            </th>
          </tr>
          <tr>
            <td style="border: none; position: absolute; top: 5vh">
            Signed_Shirt
            </td>
          </tr>
          <tr>
            <td style="border: none; position: absolute; top: 10vh">
            Signed_Guitar_Pick
            </td>
          </tr>
        </table>
      </span>
      <span style="position:absolute; left:17vw">
        <table style="position: absolute; border: none; font-size: .8rem">
          <tr> 
            <th style="border: none; position: absolute;left:5vw">
            Event
            </th>
          </tr>
          <tr>
            <td style="border: none; position: absolute; top: 5vh">
            Concert_in_London
            </td>
          </tr>
          <tr>
            <td style="border: none; position: absolute; top: 10vh">
            Fan_meet_in_Munich
            </td>
          </tr>
        </table>
      </span>
    </span>
    <span style = "position: absolute; top: 8vh; left:2vw; border: 2px solid black; bottom: 2.1vh; right: 40vw; border-radius: 20px">
      <table style="position: absolute; border: none">
        <tr> 
          <th style="border: none; position: absolute;left:5vw">
          Albums
          </th>
        </tr>
        <tr>
          <td style="border: none; position: absolute; top: 5vh">
          Divide
          </td>
        </tr>
        <tr>
          <td style="border: none; position: absolute; top: 10vh">
          Plus
          </td>
        </tr>
        <tr>
          <td style="border: none; position: absolute; top: 15vh">
          Subtract
          </td>
        </tr>
      </table>
    </span>
  </div>
  <footer style ="position:absolute; bottom:0; left:0; padding:6px; border-top:2px solid black; width:81.5vw">
  <div>
    <span style="height:1vh">Shape of you</span>
    <span style="text-align:center; margin-left:25vw">
      <button style="
      background-color: #1DB954;
      border: none;
      border-radius: 50%;
      color: white;
      font-size: 24px;
      cursor: pointer;
      justify-content: center;
      align-items: center;">&#9654;
      </button>
   </span>
  </div>
  <div style="font-size:0.7rem; color: rgb(112, 120, 114)"> Ed Sheeran</div>
  <footer>
</div>

* each user has a `UserInterface` object predefined to them and each `UI` has a `SearchBar`, a `Player` and a `Playlist` ArrayList as its main objects.
* the most `interesting` part, though, is how the the `state` of the loaded `Audio File` is kept:
  * whenever the user loads a new Audio File, a new `state` object is created, which is `updated` each time the user inputs a new command;
  * for the `Podcast` audio file, a `Resumer` object is also kept, implemented with a `HashMap`, so as to keep track of the `progress` within a `Podcast`, whenever the user loads it a second time.
* a `Like` and a `Playlist` database are kept as `Global Objects` so as to facilitate fast access to `statistics` or `search`.
* the `Next`, `Prev`, `Backward` and `Forward` commands employ the change of state of the current loaded source
  * e.g. for `Next`, there is a change of state of the audio file as though its `remaining time duration` has suddenly been reduced to 0'
  * for `Backward`, there is a change of state of the audio file as though its `remaining time duration` has been increased by 90 seconds

  # Project GlobalWaves  - Stage 2
  The `second part` of the project supports the addition of entities such as: `privileged users` (artists and hosts), audio files belonging to them (`albums` and `podcasts` respectively) and certain types of `items` (events, annoucements or merch).
  
  Simultaneously, this stage also presents a `page system`, that follows the principles of `Spotify` and is envisioned in the `HTML/CSS` visual preview of the app itself.
    * the `page system` is implemented with the help of the `Visitor Design pattern` where the visitors itselves are the `builder` and the `printer` ones
    * whenever a normal user searches for an `artist` or `host` and selects a result item, their `current page` is changed to that of the `special` user selected
    * for the `artists` and `hosts` their pages store their `posted information` and `audio collections` and are `statically built`, meaning whenever there appear `changes` to their pages, all users receive the changes as the `current page` of the normal users stores the `instance of the page` of the special user
    * the `Home and LikedContent pages` of the normal users are built `dinamically`, meaning at each `print page command` issued by them, the `database` is queried and the `sorted information` is displayed

  The `newly added` Album entity respects the same rules as the `Playlist` entities and the way the commands on them behave are `generally the same` as fo the playlists.

  This stage of the application `also supports` the status of the `normal users`, meaning their connection can be `interrupted` and their actions be ceased. `Technically`, the `offline status` is a `pause command`, but it implies displaying `error messages`, whenever they attempt to issue a new command. Because of that, the way this command is treated is whenever the `connection` is set to be `offline` (a field in the `user interface` of every user is set on `false`, initially all users are `online`, the variable `connectionStat` being set on `true`), the currently `loaded` audio file/collection keeps its state before the `loss of connection`.