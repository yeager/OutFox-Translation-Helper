Manual
======

File requirements
-----------------

Make sure you have a set of translation files, for this TranslationHelper to process.  
The files should be in the following folder structure:
```
/  (some root folder) 
    /default/  (some sub folder)
                     en.ini  (source language file)
                     nl.ini  (target language file)
    /_fallback/  (some other sub folder)
                     en.ini
                     nl.ini            
```
The program will ask for the full path to the root folder, and ask for the sub-folder separately.    
The program will remember the root folder path, so you only have to paste it in once.  
You can change which sub folder to work on, by changing that in the settings.  
The program only supports ini-files, with translations for Project OutFox.

What the program can do
-----------------------

- The program can check for missing keys
- The program can check for non-translated keys

About checking for missing keys
-------------------------------

You have to provide a source language file, which contains all the required translation keys.
Usually this will be the 'en.ini', the English translations. As these are usually the most up to date.
But you can provide another language if you'd like.
The source language file must exist in the subfolder, and must be an ini-file.

You must also provide a target language file. This is the language you want to translate Project OutFox to. 
The target language file must be an ini-file. (It can be empty)

*This file will be compared to the source language file. Any keys that are missing in the target language file will be listed.*


So this can help you find any missing keys in the target language file.
The TranslationHelper will not add the missing keys to the target language file. 
You have to do this manually. You can run the command as many times as needed.

You'll probably just copy-paste the keys from the source to the target file.

If you want to work on a different sub folder, change the sub folder in the settings.

About checking for non-translated keys
--------------------------------------

You have to provide a source language file, which contains all the required translation keys.
Usually this will be the 'en.ini', the English translations. As these are usually the most up to date.
But you can provide another language if you'd like.
The source language file must exist in the subfolder, and must be an ini-file.

You must also provide a target language file. This is the language you want to translate Project OutFox to.
The target language file must be an ini-file. (It can be empty)

*This file will be compared to the source language file. Any keys that have the same value (translation) in the target language file will be listed.*  
Values that consists only out of digits (numbers) will be ignored.

So this can help you find non-translated key in the target language file. 
Keep in mind that this program will point out keys with the *same values*. 
This doesn't have to necessarily mean that a key isn't translated. 
It's of course possible that values happen to be the same in both languages.

The progam will also print the number of same values that have been found.
Run this command as often as needed. 
After working some more on your translation, you can see the number come down, and keep track of your progress.

How to run the program
----------------------

*If your computer can run Minecraft Java Edition, you're probably fine* 

Make sure you have a Java Virtual Machine Runtime Environment (JRE) installed. This is required to run the program.
You can download a JRE from https://www.java.com/en/download/  
JRE's are available for Windows/Mac/Linux.
- Windows, just download the JRE from the URL above.
- Mac OS may have already a JRE build-in/pre-installed.
- Linux may have an alternative way to install the JRE, check your distribution's package manager.

The `java`-command should be available from you Terminal (Mac/Linux) or Commandline (Windows).  
If not, check how to add the `java` command to your operating system's `PATH`-variable.  
(I will not explain that here, just search online how to edit the `PATH`-variable on your OS)

You can download the `.jar` file from the Releases section in the GitHub repository. 

Open a terminal, and run the following command, to start the application:  
```
java -jar translation-helper.jar
```

Using the program
-----------------

The program will start with the following screen (main menu):
```
Translation Helper for Project OutFox; by FrankkieNL
Menu:
0) Exit
1) Check for missing keys
2) Check for non-translated keys
3) Check app settings
4) Edit app settings
```

Fill in the number of the option you want to select, and press `Enter`.  
- 0 will exit the program
- 1 will start the checking for missing keys command.  
The program will ask you for the root and sub folder if these aren't known by the program yet. 
- 2 will start the checking for same-keys command.  
The program will ask you for the root and sub folder if these aren't known by the program yet.
- 3 View the current settings; The current root and sub folder will be shown here; And the source and target language.
- 4 Edit the current settings; Use this if you want to change a folder or language.

