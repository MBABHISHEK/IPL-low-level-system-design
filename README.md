# IPL-low-level-system-design

This repository contains the source file for the **IPL-LLD**, which was developed as part of the Commvault Hackathon 2024.

## Project Overview

It is a low-level design and data structures-based project.

## DataStructures used

1. **HashMap**
2. **List**

## Classes

1. **Player Class**
2. **Team Class**
3. **Match Class**
4. **Bowler Class**
5. **BatsMan Class**
6. **IPL Class**

## Auction Procedure

Each Team:

- Requires a total of 4 players—one of each skill. Each team has a custom budget to spend.
- Teams will register a `playerMixStrategy` consisting of 4 players and their priority like:
  - `BT: 20`, `BL: 20`, `AR: 35`, `WK: 25` (these numbers represent the percentage of the budget a team is willing to spend on each skill).
- Each team may have a different need and priority, depending on their strategy.

### Player Pick Rules:

- Each team will get a chance to fulfill one player need at a time, after which the chance goes to the next team, and so on until all needed players are picked.
- The team with the lowest initial budget goes first.
- Teams are expected to pick the closest matching priced player to the available budget for that skill.
- A team will **not overspend** on any player beyond the budget allocated for that player's skill according to the `playerMixStrategy`.

## Functionalities

### 1. Creating Pool of Players

- **Description**: Create a pool of players and store them in a list. Players are also stored in a map based on their skill. This allows for efficient retrieval during the auction process.
- **Details**: Players are sorted by skill, and the map allows constant-time retrieval for efficient player selection during the auction.

### 2. Auction

- **Description**: Conduct the player auction using a round-robin fashion to allocate players to teams based on their priorities and budgets.
- **Details**: Teams are sorted based on their budgets, and players are allocated to teams in a round-robin manner. This ensures that each team gets a fair opportunity to pick players according to their needs and budget constraints.

### 3. Conducting Match

- **Description**: Simulate matches between teams, including the handling of match events and outcomes.
- **Details**: This involves simulating the gameplay, tracking scores, and updating team and player statistics based on match results.

### 4. Updating Player Details

- **Description**: Update player details such as performance stats, career records, and other relevant information after matches.
- **Details**: Player statistics are updated to reflect their performance in matches, which helps in maintaining accurate records and improving player profiles.

### 5. Creating and Maintaining the Points Table

- **Description**: Maintain a points table to track team standings, including wins, losses, and points earned throughout the tournament.
- **Details**: The points table is updated based on match outcomes and is used to determine team rankings and playoff eligibility.

