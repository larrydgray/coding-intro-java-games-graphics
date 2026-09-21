MudServer is the main class,
run this class and it will startup
two terminal emulators from the 
view. For now this class is only
an emulator. It should emulate
request/response from terminal
to server. It should load
all game world objects from xml
files. A basic game would have
a world, a realm, a subrealm 
and rooms. A basic game may
also have items. A basic game 
will have no player info to store
but will allow navigation and 
examination of the game world 
by the player only. A basic
game will not have weather or
time. 

The server should also hold the
game state in a Game object.
In that game state Player info
should be stored as well. 

This module uses classes from the javagg.gsc module. And later
will use classes from the javagg.gnl module
