# Gladiator

## An email from the client

```
From: Mark Big <ceo@thepartner.com>

Subject: Ave Imperator, morituri te salutant!

Recently I've been really into Ancient Rome and Gladiator fights and stuff.
I've watched Spartacus and played Swords & Sandals for dozens of hours.
I think that we have some spare budget for a little inside-project,
so I would like to task you with creating a Gladiator Tournament simulator!
You will find the user stories and the details in the attachment below.

Mark
```

Oh, this guy again. Well, let's get to it!

In this project, your primary goal is to design and create logic behind
the Gladiator Tournament simulator. It means that the program is going
to work in the terminal at first, but later we would like to port it
to a GUI version. The main focus is on the object-oriented approach
and the separation of concerns.

## What are you going to learn?

- advanced OOP - abstraction, inheritance, interfaces, polymorphism
- practical usage of the Model-View-Controller design pattern
- practical usage of the Binary Tree data structure (with use of recursive methods)
- create a generic type
- a little bit of math ;)
- (only C#) usage of properties

## User Stories


1. Implement the `ConsoleView` class that will be used as the view module for the Tournament. It must implement the `IView`/`Viewable` interface.

    - The 'display()' method makes a string be printed out to the terminal
    - The `getNumberBetween()` method uses the standard terminal input and loops until an acceptable (i.e. within the given inclusive bounds) input is provided by the user

2. Create a base class for all gladiator types, and four subclasses: Swordsman, Archer, Assassin, and Brutal.

    - There is a `Gladiator` abstract class that provides all necessary functionalities for all its subclasses
    - Each gladiator is named upon creation (in the constructor). The name can be accessed publicly but cannot be changed
    - The `getFullName` method (or `fullName` property in C#) returns the full name of the gladiator assembled of his type and name (e.g. "Brutal Brutus", "Archer Leo")
    - The gladiators `private` fields for his base statistics (Health Points [HP], Strength Points [SP], Dexterity [DEX], Level [LVL]) are set upon creation. From this group, only Level can change later
    - There is an enum type within the `Gladiator` class representing multipliers with values `Low`, `Medium`, and `High`. It also has a method that returns the numeric value of the given multiplier (0.75, 1.0, and 1.25, respectively).
    - Within `Gladiator` class, there are three `protected` functions (properties in C#) returning multipliers for its HP, SP, and DEX statistics, respectively. The functions MUST then be implemented by every subclass of `Gladiator`.
    - Within `Gladiator` class, there are three public functions (properties in C#) returning the available values of the gladiator's statistics (HP, SP, and DEX, respectively). The available value is calculated by the following formula: `availableStatistic = baseStatistic × statisticMultiplier × level`
    - Every gladiator has a current health value. This is decreasing during a combat when receiving hits by the enemy. When this goes below zero, the gladiator dies.
    - There is a `Swordsman` subclass of `Gladiator`. Its multipliers are: HP: medium, SP: medium, DEX: medium
    - There is an `Archer` subclass of `Gladiator`. Its multipliers are: HP: medium, SP: medium, DEX: high
    - There is an `Assassin` subclass of `Gladiator`. Its multipliers are: HP: low, SP: high, DEX: high
    - There is a `Brutal` subclass of `Gladiator`. Its multipliers are: HP: high, SP: high, DEX: low

3. Implement the `GladiatorFactory` class for creating `Gladiator` instances

    - The `generateRandomGladiator` method randomly generates a new instance of one of the implemented subclasses of `Gladiator`. The `Swordsman` subclass is twice more likely to be created than any other subclass
    - HP, SP and DEX base statistics are assigned to a random value from range `[25-100]`, and LVL is assigned to a random value from range `[1-5]`
    - The static `Random` object from `RandomUtils` is used for randomization

4. Implement the `Combat` class for simulating duels between gladiators. The fighting mechanic is the same for all `Gladiator` subclasses. The combat simulation is turn based (A attacks B, then B attacks A, and so on).

    - The `simulate` method runs the simulation of the whole fight. If any of the gladiators' current health becomes negative after an attack, the combat is finished, and the attacker is returned as the winner
    - If one of the opponents is null, the winner is the one that is not null. If both of the opponents are null, the return value is null
    - The first attacker is selected randomly, then the two gladiators take turns
    - During each turn, the attacker can either hit the enemy or miss. The chance of hit is calculated by the following method. Take the dexterity difference: `attackerDex - defenderDex` which must then be clamped (forced into the range `[10-100]`). The clamped value is the percentage chance of the attacker hitting the enemy
    - If the attacker hits the enemy, the damage reduced from the enemy's current health. The damage is calculated by integer value of the following formula: `damage = attackerSp × M` where `M` is a random number from range `[0.1-0.5]`
    - If the attacker hits the enemy, the following log is displayed in the view: `"X deals D damage"`, where `X` is the attacker's name and `D` is the damage
    - If the attacker misses, the following log is displayed in the view: `"X missed"`, where `X` is the attacker's name
    - At the end of the combat, the following log is displayed in the View module: "X has died, Y wins!", where `X` and `Y` are the loser and winner gladiator's name, respectively
    - The combat's simulation does not have direct access to the View module
    - The static `Random` object from `RandomUtils` is used for randomization

5. Implement a generic `BinaryTree` data structure that will be used for arranging and invoking combats of the Tournament.

    - The tree can be constructed both with one and multiple values
    - The `add()` and `addAll()` methods are used for adding new items to the tree.
    - Adding new values keeps the tree balanced

6. Implement the `Tournament` class which serves as a main controller for the simulation. It generates participants, build a Tournament tree out of them, and executes the combats starting from the lowest level of the tree to get the final champion. During the simulation it communicates verbosely with the View.

    - The `generateGladiators()` method creates new gladiator instances using the `GladiatorFactory` provided in the constructor.
    - The `simulateCombat` method executes a combat and logs the events to the View in the following form:
      ```
      Duel Jupiter versus Nero:
       - Swordsman Jupiter (371/371 HP, 623 SP, 476 DEX, 7 LVL)
       - Brutal Nero (790/790 HP, 560 SP, 498 DEX, 8 LVL)
       - Jupiter missed, Nero deals 245 damage, Jupiter missed, Nero deals 183 damage
      Swordsman Jupiter has died, Brutal Nero wins!
      ```
    - The winner of each combat has his level increased by one (which then affects his stats), then healed-up back to the available HP.
    - The `getChampion` method takes a `BinaryTree` of `Gladiator`s, simulates a series of combats according to the tree starting from the leaves, and returns the final winner as the champion.

7. Implement a view that prints the output of the whole tournament into a `.txt` file.

    - After every Tournament a `.txt` file is created with a timestamp in the name. The file contains all the logs from the Tournament

8. [OPTIONAL] Instead of having `"X deals D damage"` and `X missed"` all the time (booooring), implement custom messages for each `Gladiator` subclass.

    - Every `Gladiator` subclass has its own set of custom messages for hitting and missing targets. The messages must contain the original references for the attacker's name and the amount of damage.

9. [OPTIONAL] Implement a "Kill or Spare" mechanic that allows loosing gladiators to survive and later fight in a second Tournament. There is no sparing in the second Tournament.

    - At the end of each fight, the crowd randomly decides whether the loosing gladiator should be spared. The chance of being spared is 25%
    - After the end of the original Tournament the spared gladiators are put into a new tournament tree and have a second Tournament and a subsequent champion
    - There is no sparing in the second Tournament

10. [OPTIONAL] Implement a weapon effect system that can cause additional damage or other advantages during combat.

    - Upon creation of a `Gladiator` there is a 10% chance that he will be granted with a special weapon effect. The effect is then randomly chosen from the ones mentioned below. The special weapon effects are displayed in the detailed view of the gladiator when announcing the combats
    - Bleeding - there is a 5% chance that upon receiving a hit, the enemy will start bleeding and will be receiving additional damage each turn (2% of his available HP per turn) until the end of the combat. There can be "multiple bleedings" at the same time with additive effects
    - Poison - there is a 20% chance that upon receiving a hit, the enemy gets poisoned and will be receiving additional damage for next 3 turns (5% of his available HP per turn). If poisoned again, the receiver immediately dies
    - Burning - there is a 15% chance that upon receiving a hit, the enemy is set on fire and will be receiving additional damage for a random amount of turns (in range `[1-5]`) (5% of his available HP per turn). If set on fire again, the duration of the burning is extended by a random amount of turns (in range `[1-5]`)
    - Paralyzing - there is a 10% chance that upon receiving a hit, the enemy is paralyzed which makes him unable to attack and to defend himself during the next 3 turns (so effectively skips 3 attacks and receives 3 hits for sure). If paralyzed again, the duration of the paralyzed state is reset to 3 turns
    - Using the weapon effect system you have implemented, create an effect of your own. Will it be freezing, magic, or something else? Be creative!


## General requirements


None

## Hints

- Maybe the hardest part of this project is to build up the tournament tree
  (a binary tree with gladiators as "leaves"), and also the "execution"
  of combats on the tree. The tree itself is a recursive structure, and
  both processes require a recursive approach.
    - When adding a new value, "roll it down" the tree until it reaches
      an empty slot without further branches. If there is already a value
      there, open new branches.
    - Ensure that the tree is building up in a balanced way: the tree
      must be [balanced](https://en.wikipedia.org/wiki/Binary_tree#Types_of_binary_trees)
      which means you don't open a new "level" until there is an empty
      slot on the deepest one.
- The `Tournament` class is provided with a View and a `GladiatorFactory`
  instance. It does not have to worry about either the details or the
  instantiation of these objects. This pattern is called _dependency
  injection_, the best way to decouple parts of our code. You'll hear
  a lot about this later on.
- As you may have noticed, there are a lot of numbers in this project.
  Don't just put them into your code without explanation. Remember, using
  "magic numbers" is bad practice!
- Manage all randomization through the pre-created static `Random` object
  in `Utils`. In this case, if a "random seed" is provided to the `Random`
  constructor, the tournament will be _exactly_ reproducible. Check it out!

## Starting repository

Follow [this link](https://journey.code.cool/v2/project/solo/blueprint/gladiator-java/java) to create your project repository.

## Background materials

- :exclamation: [Binary Tree](https://www.w3schools.in/data-structures-tutorial/binary-trees/)
- [Model-view-controller](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)
- [How to design classes](https://learn.code.cool/full-stack/#/../pages/java/how-to-design-classes)
- [Polymorphism](https://learn.code.cool/full-stack/#/../pages/java/polymorphism)
- [Enums](https://learn.code.cool/full-stack/#/../pages/java/enums)
- [Generics](https://learn.code.cool/full-stack/#/../pages/java/generics-and-the-diamond-operator)
- [Polymorphism](https://www.tutorialspoint.com/java/java_polymorphism.htm)
- [Generic Types](https://www.tutorialspoint.com/java/java_generics.htm)
- [Random seed](https://en.wikipedia.org/wiki/Random_seed)

## Acceptance review

You will need this only at review time, **after** completing the project.
[Use this form](https://forms.gle/8zJutgYdY1vDCJ9Y9) to record the review you provide for your peer.

For now, you can check your (and everyone else's) results in the [summary spreadsheet](https://docs.google.com/spreadsheets/d/1otJkV-zl-Sfg3BWX1ZbFJ_e-GpLf5Jr6oSysZTfpfD4/edit#gid=31463349).
