# SCDL
SoundCloud Downloader - A mock-up program written in Java, pulls audio, graphics and meta based on URL.

## Usage

### v1.1

The jar is still accessed in the same way as before (`java -jar ~/SCDL.jar`), most of the visible changes are in the interface.
The user is now greeted with a running menu, with options including Single Track Mode, Array of Tracks Mode and more. 
Unless it encounters a fatal exception, the program will continue to default back to the menu until manually exited by the user via either command or process termination.

### v1.0

First, open a prompt and type `java -jar ~/SCDL.jar`, replacing the proper directory to the jar, of course.
Next, enter a URL to a SoundCloud track, `https://soundcloud.com/herobust/party-mcfly1` for example.
Give the program a few moments to gather resources. The completed mp3 file should appear in the same directory as the jar.
