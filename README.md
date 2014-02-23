MIDI-File-Combiner
==================

A proof-of-concept project to show how to work with MIDI files in Java.

We have a task in another project that requires us to take multiple MIDI files and combine them programmatically into one. Each file is in the same key, has the same tempo, and is the same length. We have to combine them in two ways:

 - We might start with two files and combine them into a new one so that one file's contents play on one track while the other file's contents play simultaneously on a second track.
 - We might start with two files and combine them so that both file's contents are on the same track, and once the first file's contents finish playing, the second one beings playing the next measure.

This project shows how to use Java's MIDI library to combine files in these two ways. These techinques can then be used to combine multiple files in more complex ways.
