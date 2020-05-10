<h2>Android Music Player</h2>
<hr>
<p>Simple media player that get the list of directories and files related to .Mp3 files</p>
<p>Acquires permission from the user for acessing internal device storage</p>
<li>Process the file in a temp memory</li>
<li>2 Activities are used MainActivity and PlayerActivity</li>
<li>MainActivity uses Listview to display the records from the dataAdapter</li>
<li>OnSelectItemListener starts next activity with list of data to mediaplayer</li>
<li>PlayerActivity houses Seekbar, Next, Pervious, Play, Pause actions and name of the song in Textview</li>
<li>Seekbar is controlled by seperate thread that monitors the current and total duration of the song from the Mediaplayer</li>
<li>After a particular song is finished next song will be followed by the Mediaplayer</li>
<hr>

<b>Copyright:</b> Should not be used for profitable purpose except study purpose.


