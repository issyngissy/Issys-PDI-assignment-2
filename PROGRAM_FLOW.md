Main:
1.0 - Program starts in the Main class

1.1 - Set the CSV file name to "data.csv"

1.2 - Run the main() method

1.2.1 - Go into Logger.open() so output can also be written to the log file

Logger:
1.2.1.1 - Enter the Logger class

1.2.1.2 - Open "logged_data" in append mode

1.2.1.3 - Write "starting session..." to the log file

1.2.1.4 - Return to Main

Main:
1.3 - Go into GreenhouseDataStorage.loadCSV() to read "data.csv"

GreenhouseDataStorage:
1.4 - Enter the GreenhouseDataStorage class

1.5 - Begin loadCSV()

1.6 - Create an empty SensorArray to store valid readings

SensorArray:
1.6.1 - Enter the SensorArray class

1.6.2 - Set up the internal array and reading count

1.6.3 - Return the empty SensorArray to GreenhouseDataStorage

GreenhouseDataStorage:
1.7 - Create the CSV reader

1.8 - Open "data.csv"

1.9 - Skip the header row

1.10 - Track the current CSV line number

1.11 - Read the first data row

1.12 - Loop through each row

1.13 - Trim spaces from the row

1.14 - Ignore blank rows

1.15 - Go into parseLine() for the current row

GreenhouseDataStorage - parseLine:
1.15.1 - Split the CSV row into fields

1.15.2 - Check there are exactly 9 fields

1.15.3 - Trim each field

1.15.4 - Use Validator to check date and time fields are integers

1.15.5 - Convert date and time strings into numbers

1.15.6 - Use Validator to check date and time ranges

1.15.7 - Use Validator to check sensor ID, sensor type, zone, and value

SensorReading / Timestamp:
1.15.8 - Create the Timestamp

1.15.9 - Create the SensorReading

1.15.10 - Return the completed reading to GreenhouseDataStorage

GreenhouseDataStorage:
1.16 - Add the valid reading to SensorArray

1.17 - Skip invalid rows and show a warning

1.18 - Read the next CSV row

1.19 - Handle file read errors if needed

1.20 - Close the CSV reader

1.21 - Return the loaded readings to Main

Main:
1.22 - Back in Main with the loaded readings

1.23 - Create the Menu object using the loaded readings and the CSV file name

Menu:
1.23.1 - Enter the Menu class

1.23.2 - Store the readings, CSV file name, and console input reader

Main:
1.24 - Call menu.run()

Menu:
2.0 - Show the main menu and wait for the user to choose option 1 to 6

2.1 - Menu option 1: show overall statistics for all readings

2.1.1 - Show statistics submenu

2.1.2 - User chooses total, average, minimum, maximum, out-of-range count, out-of-range percent, or all

Menu:
2.1.3 - Send the selected readings straight into the Math class

Math:
2.1.4 - Calculate the statistic requested by the user

Menu:
2.2 - Menu option 2: show statistics by zone

2.2.1 - Show hardcoded choices for Zone A, Zone B, and Zone C

2.2.2 - User selects one zone

2.2.3 - Convert the menu number into ZoneA, ZoneB, or ZoneC

2.2.4 - Filter readings by zone

2.2.5 - Show statistics submenu for that zone

Menu:
2.2.6 - Send the filtered zone readings straight into the Math class

Math:
2.2.7 - Calculate the statistic requested by the user

Menu:
2.3 - Menu option 3: show statistics by sensor type

2.3.1 - Show hardcoded choices for temperature, humidity, soilMoisture, and light

2.3.2 - User selects one sensor type

2.3.3 - Convert the menu number into the matching sensor type text

2.3.4 - Filter readings by sensor type

2.3.5 - Show statistics submenu for that sensor type

Menu:
2.3.6 - Send the filtered sensor type readings straight into the Math class

Math:
2.3.7 - Calculate the statistic requested by the user

Menu:
2.4 - Menu option 4: add a reading

2.4.1 - Read the sensor ID

SensorArray:
2.4.1.1 - Check whether that sensor ID already exists

Menu:
2.4.1.2 - If the sensor ID is already used, ask for another one

2.4.1.3 - Read sensor type choice, Zone A/B/C choice, and value

2.4.2 - Read day, month, year, hour, and minute

SensorReading / Timestamp:
2.4.3 - Create a Timestamp and SensorReading from the user input

Menu:
2.4.4 - Add the new reading to SensorArray

2.4.5 - Show the added reading message

GreenhouseDataStorage:
2.4.6 - Save the updated readings back to data.csv

Menu:
2.4.7 - Return to the menu

Menu:
2.5 - Menu option 5: delete a reading

2.5.1 - Check there are readings to delete

2.5.2 - Show current readings with numbers

2.5.3 - User chooses the reading number to delete

SensorArray:
2.5.4 - Delete the selected reading from the array

Menu:
2.5.5 - Show the deleted reading message

GreenhouseDataStorage:
2.5.6 - Save the updated readings back to data.csv

Menu:
2.5.7 - Return to the menu

2.6 - Menu option 6: exit the menu loop

Main:
3.0 - Back in Main after the menu exits

3.1 - Go into Logger.close()

Logger:
3.2 - Write "ending session" to the log file

3.3 - Close the log file

Main:
3.4 - Program ends
