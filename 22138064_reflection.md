# Reflection on Building the Smart Greenhouse Monitoring System

## Data types

The data types fall out of what each field actually is. Day, month, year, hour, and minute are whole counts of things, so they are `int`. Sensor values can be fractional (a moisture probe might read 47.3), so those are `double`. Sensor IDs, zone names, and sensor types are `String`, because they are labels and there is no arithmetic I would ever do on `"ZoneA"`. Keeping the date pieces as `int` also made the range checks cheap: `day >= 1 && day <= 31` works directly, with no parsing.

## Class structure

Composition over inheritance was the main decision. A `SensorReading` has-a `Timestamp`, and the timestamp has its own formatting logic (zero-padded `dd/mm/yyyy hh:mm`), so it earned its own class. Each value class has three constructors: a default that zero-initialises, a full one that takes every field, and a copy constructor. The copy constructor for `SensorReading` does a deep copy of the embedded `Timestamp`, which I almost forgot about. If I had passed the existing reference instead, two readings would share one timestamp object and mutating one would silently corrupt the other. Every field has a public accessor and mutator, even ones nothing currently calls, so encapsulation stays consistent across the class.

## Menu system

The menu is one `while` loop in `Menu.run()`, controlled by a `boolean menuIsRunning` flag, with an `if/else if` chain dispatching to six handler methods. I avoided `switch` so the dispatch looks the same as the other branching code in the project. For input I wrote four reusable readers (`readIntegerInRange`, `readDoubleNumber`, `readNonEmptyString`, `readValidSensorType`), each looping on its own `inputAccepted` flag until the `Validator` class approves the input. Statistics share one sub-menu method that takes whatever filtered `SensorArray` slice it is given, which stopped the three "overall, by zone, by type" handlers from duplicating seven stat options each.

## Challenges

The hardest bug was inside `SensorArray.minimum()` and `maximum()`. I had written `return min;` inside the `for` loop, so the method exited on its first iteration with the wrong answer. The same mistake also lived in `getDistinctZones`, `getDistinctTypes`, and `distinctValues`. Refactoring every method to use a single `result` variable assigned through `if/else` branches fixed the whole family of bugs and made each method easier to read. Splitting CSV lines by hand (counting commas, tracking start positions) was fiddly, but doing it manually forced me to think through what trailing commas and empty fields should produce.

## What I would improve

Date validation does not reject February 30, which it should. The four input readers belong in their own `InputReader` class instead of cluttering `Menu`. JUnit tests would beat a manual test plan. I would also add an edit option, since editing a row currently means deleting it and re-typing every field, and editing is the most common real-world action.
