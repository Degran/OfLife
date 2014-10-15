OfLife
======

Java code to generate an html version of a digital journal similar to an offline OhLife

Use
------
The "OfLife_files" directory should always be in the same directory as the jar files for them to function properly.
Especially "OfLife_files/app_107.css" is necessary for the html to look right.

Once everything is initialized, add new entries by putting text files or images into the "OfLife_files" directory.
Text files should be named "yyyy-mm-dd.txt" and images "yyyy-mm-dd_img.*" with * being an image file extension.

See the "Example" directory for a working example.

Notes
------
You can use this in combination with an IFTTT script (or similar service) to remind you to write,
and maybe even correctly format the text and images.
I also place the files in dropbox as an online backup, after encrypting them with EncFS.
