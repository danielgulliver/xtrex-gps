# XTrex GPS Tracking Device

The XTrex is a software simulation of a GPS tracking device that looks
suspiciously like a product previously sold by Garmin. The user is able to
enter a destination and is then provided with all of the information they
need to be able to find and reach their destination.

The software was written in Java and runs on Windows, Mac, and Linux. It
requires an internet connection in order to run. For full functionality, a
compatible GPS dongle is required. Otherwise, fake data will be used so that
you can still get a feel for how the program is supposed to operate.

## About the project

This project was completed for the module Software Engineering Team Project,
one of the modules on the BSc Computer Science course at the University of
Exeter.

We worked in a team of five using the Agile/Scrum methodology. I was the
Product Owner, although some of the responsibilities of the role had
already been taken care of by the module leader. Unlike in industry, I was also
a developer and had the same responsibilities as other developers.

We made use of the Google Maps API to provide the map images, as well as
directions. We used Microsoft Cognitive Services to provide the speech
functionality.

We delivered a working version of the project to the client every two weeks and
received feedback to help us improve during the next development cycle. Overall
we scored 72%.

## About the device

The full potential of the device is realised when a compatible GPS dongle is
plugged into the computer. Otherwise, fake data is produced in order to get a
feel for how the device would operate. You will need an internet connection in
order to use the simulation, although that would not be the case with a real
device from Garmin.

The device is controlled by pressing buttons on the outside of the case. It
does not have a touchscreen display.

![Description of buttons](/images/buttons.png)

Press the Up and Down buttons to cycle through the buttons on the screen and
press the Select button to choose that option. Press the Menu button to return
to the Main menu.

Press the Power button to turn the device off. When the device is off, press
the Power button again to turn it back on. Please note that turning the device
off causes all destinations, trip times, and other information to be reset. The
device does not have nonvolatile storage.

### Menu screen

![Menu screen](/images/menu_screen.png)

Here you can see all of the available screens to visit. To return to the Menu
screen, press the Menu button.

### Where To? screen

![Where To? screen](/images/where_to_screen.png)

On this screen, you can type in either the name of the destination or a
postcode. Cycle through the keys with the Up and Down buttons, and press the
Select button to press the highlighted key.

There are two sections of the keyboard. To see the numbers, as well as a Delete
button, select the => button to go to the right section of the keyboard. To go
back to the left section of the keyboard, select the <= button.

When you leave this screen by pressing the Menu button, the journey will begin,
with the place you entered as the destination. To edit the destination, revisit
the Where To? screen and edit the destination. Your journey will be restarted
if you set a new destination.

(Although the device is not supposed to have a touchscreen, we chose to simulate
touchscreen behaviour on this screen for ease of use. You can click the buttons
on this screen with the mouse.)

### Trip Computer screen

![Trip Computer screen](/images/trip_computer_screen.png)

The Trip Computer shows you information about the distance you have currently
travelled, the current speed of the device, and the length of time you have
been travelling for so far.

The journey time will be set to 00:00:00 until a destination has been set on
the Where To? screen.

### Map screen

![Map screen](/images/map_screen.png)

The Map screen shows the user's current position on the map. When a GPS dongle
is plugged in to the computer, the screen also displays directions, one at a
time, to the user.

If a Speech language has been selected (see Speech screen, below), the
directions will be displayed and read out in that language. Otherwise, they
will be displayed in English and will not be read out.

### Speech screen

![Speech screen](/images/speech_screen.png)

On the speech screen, the user can select a language in which the directions on
the Map screen are to be displayed and read out in. When a language is
selected, the device will announce which language was selected.

Press the Menu button to exit this screen, and the device will be set to that
language.

### Satellite screen

![Satellite screen](/images/satellite_screen.png)

The Satellite screen provides the user with information about the latitude and
longitude of the device's current location, as well as the number of GPS
satellites in view.

### About screen

![About screen](/images/about_screen.png)

The About screen provides information about the developers.

## Running the project

First, make sure you're in the right directory and compile the project with
Maven:

```bash
daniel@laptop ~ $ cd Xtrex/xtrex
daniel@laptop ~/XTrex/xtrex $ mvn compile
```

Then, in order to run the program, you'll need to run `teamk/xtrex/xtrex` from
the `Xtrex/xtrex/target/classes/` directory:

```bash
daniel@laptop ~/XTrex/xtrex cd /target/classes
daniel@laptop ~/XTrex/xtrex/target/classes java teamk.xtrex.xtrex
```

If the GPS dongle is not plugged in, you will need to pass the parameter
`false` to the program:

```bash
daniel@laptop ~/XTrex/xtrex/target/classes java teamk.xtrex.xtrex false
```

You will have to close the program from the taskbar of your desktop
environment, or via the command line.
