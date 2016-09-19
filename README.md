# SCDL
SoundCloud Downloader - A mock-up program written in Java, pulls audio, graphics and meta based on URL.

## Usage

### v1.3

Forget all the old instructions! The menu has been redesigned, with new commands and their respective features.

Full Name | Short Name | Arguments | Description | Usage
--- | --- | --- | --- | ---
**getTrack** | gt | `-audio`, `-art` | Downloads one track | *{url}*
**getTracks** | gts | `-audio`, `-art` | Downloads more than one track | *{url}*\**{url}*\**...*\**{url}*
**getSet** | gs | `-playlist`, `-album` | Downloads a set of tracks | *{url}*
**help** | ? | N/A | Shows command information | *{url}*
**exit** | e | N/A | Terminates the program | *{url}*

### v1.2

I'm gonna start from the group up just to recap as the program currently stands.  
1. Open a prompt and enter `cd {dir}`, replacing `{dir}` with the proper directory.  
2. Enter `java -jar SCDL.jar` and wait for a menu to appear.  
3. There are three operational options: `[s]ingle track`, `[a]rray of tracks`, and `[e]xit`. `[p]laylist of tracks` is not yet developed. Choose an option by entering either `s`, `a` or `e`.  
4. `s` will ask you to input a SoundCloud track URL. Ensure the URL is of valid format and headed by `https://`; example: `https://soundcloud.com/coast-modern/the-way-it-was-1`. If the URL is invalid, you will be redirected back to the menu.  
5. `a` acts like `s` but allows you to input many urls rather than just one at a time. This is achieved by typing in valid URLs with one asterisk (`*`) in between each URL. If a URL is interpretted to be invalid, it will be skipped and the other URLs will carry on with the process, unimpaired. The only current issue is with wildcards - only use asterisks in between URLs!  
6. Both `s` and `a` will download to the same directory as `SCDL.jar` itself, assuming proper permissions are in place. There is currently no option to change this setting - take note to put `SCDL.jar` into its own folder if you are planning on downloading many tracks otherwise it may become difficult to manage your file system.  
7. `e` is self explanatory - if you manage to make it through the program without any fatal exceptions, `e` will quit the process. You can also close the host process or prompt, although garbage collection may not perform exceptionally well that way. There don't appear to be memory leaks, but the program is anything but optimized at the moment, so don't take my word for it just yet.  
8. Enjoy your newly downloaded, high quality SoundCloud tracks with integrated album art and other metadata!  

### v1.1

The jar is still accessed in the same way as before (`java -jar ~/SCDL.jar`), most of the visible changes are in the interface.  
The user is now greeted with a running menu, with options including Single Track Mode, Array of Tracks Mode and more.   
Unless it encounters a fatal exception, the program will continue to default back to the menu until manually exited by the user via either command or process termination.  

### v1.0

First, open a prompt and type `java -jar ~/SCDL.jar`, replacing the proper directory to the jar, of course.  
Next, enter a URL to a SoundCloud track, `https://soundcloud.com/herobust/party-mcfly1` for example.  
Give the program a few moments to gather resources. The completed mp3 file should appear in the same directory as the jar.  
