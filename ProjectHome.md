# Yahoo Fantasy Football Statistics Loader #

This application allows users to download yahoo fantasy football statistics from their league for analysis.  I used to compile summary statistics for a weekly mail through an error prone exercise in cut and paste.  The stats downloader conveniently saves the stats for all the teams in your league to a file. With slight modifications, the code could also be changed to load other Yahoo fantasy football data, such as available player data.  The application comes with a simple swing Gui for basic users to  download data, but clients can use the API to add functionality to other programs as well.

## The API ##
Users supply a league key, a range of weeks, and a file location.  The data for all teams is downloaded to in CSV format to this location.  For users of the GUI, this just means filling in the fields.  To start the gui, you can use the supplied ant file and run "ant build run".  For users of the API, it is as simple as this:

```
// Get all the stats for week 1
new YahooStatsSaver().saveYahooPlayerStats(new Settings(league, cookieValue, 1, 1, "stats.csv"));
```

## Getting started ##
Simply check out the repository, run the build target of the supplied build.xml file, and then run the "run" target.  The simple swing based Gui will be started.

## What is the cookie? ##
Yahoo is password protected, so currently a authentication cookie must also be provided.  This is best found by logging into Yahoo Fantasy Football, and then viewing the subsequent request headers through a tool such as FireBug.  The conents of the "Cookie" header should be pasted into the Gui.  Adding yahoo login support is one the the first updates we should look to add.

## Dependencies ##
All java dependencies are included in the repository, but Java 1.6+ is required.