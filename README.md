# Solving the Travelling Salesman Problem - Dynamic Programming and Hill Climbing
This project was sourced from a university project completed during my enrollment. The original repository has been kept private as per the university code of conduct, so this repository has all references to the university removed.

This project achieved a final mark of **87%**

### Execution Line

The program can be executed via the following line:

```batch
java A2 N P tsp22.txt tsp23.txt tsp24.txt tsp25.txt tsp26.txt
```
The N and P values are both intended to be integers, and represent the Max Number of Iterations (N) and the Amount of Checks that can occur with no improvements to the current solution (P).

You can include additional files, or remove other files by simply adding or removing file names in the arguments. Ensure that they are all text files. The program will only execute the files supplied in order that they're given.

### Executing via file

The program can be executed by running the "<b>run.bat</b>" file in the folder. This batch file will compile and execute the program (the input files can be changed by editing the batch file itself). It will pause and display the output whilst deleting the generated <i>.class</i> files to keep the folder clean and organised.

### Executing via the command line

To execute via command line, launch the command prompt and change directory into the folder with the .java files and associated text input files. Run the command in the first section and press enter. The file should execute as expected. 

### Analysis Table
```plain
+--------------+--------------------+--------------------+
| Problem Size | Execution Time (ms)| Solution Length    |
|              +---------+----------+---------+----------+
|              | DynaTSP | ClimbTSP | DynaTSP | ClimbTSP |
+--------------+---------+----------+---------+----------+
| 22           | 2480    | 1.5      | 278     | 684      |
+--------------+---------+----------+---------+----------+
| 23           | 3940.6  | 0.3      | 470     | 1073     |
+--------------+---------+----------+---------+----------+
| 24           | 8657.2  | 0.3      | 306     | 983      |
+--------------+---------+----------+---------+----------+
| 25           | 20353.8 | 0.3      | 315     | 1078     |
+--------------+---------+----------+---------+----------+
| 26           | 42677.6 | 0.3      | 368     | 945      |
+--------------+---------+----------+---------+----------+
```

### Solution Analysis

##### Execution TIme

Execution Time between the two different solutions is perhaps the most significant difference in the comparison. The sheer scale of difference in times between the DynaTSP and ClimbTSP can make it difficult to argue in favour of DynaTSP, as it maintains a significantly larger time complexity, as evident by how largely it scales as the input goes up. Utisiling the curve estimator at [dcode.fr](https://www.dcode.fr/function-equation-finder), I was able to convert the time complexity into a function, as follows:

```t4-templating
f(x) = 3479.07x^2-157315x+178027*10^6
```

The 10<sup>6</sup> is the most significant part, demonstrating just how large the scale it, whereas with ClimbTSP, it hovers very consistently around 1ms, making ClimbTSP anywhere between 1600x and 142,000x more time efficient, depending on the input size.

##### Solution Length

DynaTSP holds the crown in terms of solution length when comparing to ClimbTSP. Since the DynaTSP algorithm is purposefully finding the most optimal solution, it's not a huge surprise that it triumphs over ClimbTSP, which is just randomly trying node swaps until a more optimal solution is found. 

DynaTSP is able to properly hold itself around the 300-size mark, making it both consistent and reliable. ClimbTSP tends to vary wildly, mostly hovering near 1000, with an exception of the 22 problem size.

ClimbTSP tends to hold itself to being between 2.4x and 3.4x less efficient than DynaTSP

### Conclusion

Both algorithms have their pros and cons, and choosing between the two will depend on the situation. Since DynaTSP has a significantly-scaling time complexity, "time is of the essence" situations that don't prioritise route efficiency may get more out of ClimbTSP, whereras situations that have room for time and need the most efficient route will tend to lend itself more to DynaTSP.

It's important to know as well that there are room for optimisations to both algorithms, and more powerful computers will struggle less with DynaTSP calculations than a consumer-grade midrange gaming PC, on which these tests were performed. As such, DynaTSP's time inefficiency won't typically be as much of a problem. 

### Discussion

##### Data Structures

As I wanted to implement the ability to take multiple input files at the same time and perform the algorithm on all of them, it made sense to write out a separate <i>Map</i> class, which will take in the coordinate list, calculate their distances and a generate a distance matrix and vertex list, both of which are retrievable via functions. A new map instance can be instantiated for each input file, making it easy to handle all of the calculations without having to worry about value accidentally bleeding over into different inputs and ultimately affecting the final outputs.

Additionally, each algorithm was put in their own class to keep the main execution file clean and separate, as expected.

##### Code Organisation

Keeping all of the major code assets in their own files and classes forces the program to treat them as separate "modules" and not a part of the main execution file, making it much easier to track variables, code and values throughout the execution period, making it significantly easier to implement new functions, troubleshoot and other necessary programming steps.
