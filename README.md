# Projet Dressage

## Introduction

**Dressage** est un projet de simulation développé en Java dans le cadre du cours GLP à l'Université de Cergy Paris. Ce projet simule le dressage d'animaux (principalement un chien, avec une implémentation partielle d’un chat) dans une maison virtuelle. L’objectif est de modéliser des comportements réalistes via un système d’apprentissage basé sur des récompenses et punitions, tout en offrant une narration immersive à travers un journal.

### Auteurs

- **Céline Arkam**
- **Corentin Desmottes**
- **Lucas Da Silva**

## Fonctionnalités

- **Simulation de comportements** : Le chien peut réaliser des actions comme manger, dormir ou jouer, définies dans `GameConfiguration`.
- **Déplacements** : L’animal se déplace entre les pièces (cuisine, jardin, etc.) en passant par des portes prédéfinies (`RoomPosition`).
- **Apprentissage** : Un système d’apprentissage (`Learning`) ajuste les probabilités des actions (+ ou - avec une degradation naturelle)
- **Gestion des états** : L’état de l’animal (santé, état mental, confiance) est mis à jour selon ses actions .
- **Journal narratif** : Les actions sont enregistrées dans un journal (`Journal`) sous forme narrative à la première personne (ex. "je suis allé dormir dans mon panier").
- **Interface graphique** : Une IHM permet de visualiser la maison, les actions de l’animal, et son journal, avec des options pour démarrer , mettre en pause ou quitter une partie.

## Architecture

Le projet est organisé en plusieurs packages :

- **engine** : Contient la logique principale.
  - `MobileElement` : Classe abstraite pour les éléments mobiles.
  - `Animal`, `Dog`, `Cat` : Classes pour les animaux et leurs comportements.
  - `Learning` : Gère l’apprentissage.
  - `Journal` : Gère le journal narratif.
  - `StateAnimal`, `Stimulus` : Gèrent l’état et les stimuli.
- **config** : Contient les données statiques.
  - `GameConfiguration` : Définit les actions et paramètres.
  - `RoomPosition` : Définit les positions des pièces et objets.
- **gui** : Gère l’interface graphique (non détaillée ici, mais inclut `MaisonGUI`, `Dashboard`, `JournalGUI`, ...).

Le moteur fonctionne en boucle (`MaisonGUI.run()`) avec des itérations toutes les 50 ms, gérant les actions, déplacements, et mises à jour de l’état.

## Installation et exécution

### Prérequis

- Java 8 ou supérieur.
- Un IDE comme Eclipse ou IntelliJ IDEA pour importer le projet.

### Instructions

1. Clonez ou téléchargez le projet depuis le dépôt.
2. Importez le projet dans votre IDE.
3. Assurez-vous que les images (dans `src/images`) sont bien présentes.
4. Exécutez la classe 'MenuGUI' pour lancer la simulation.
5. Suivez les instructions à l’écran :
   - Cliquez sur "Nouvelle Partie" pour démarrer une nouvelle simulation.
   - Utilisez les boutons "Journal Chien" ou "Journal Chat" pour voir le journal.
   - Appuyez sur "Pause" pour mettre en pause et accéder aux options.
   - Appuyez sur aide pour voir les fonctionnalités

## Contributions

Ce projet a été réalisé par Céline Arkam, Corentin Desmottes, et Lucas Da Silva. Toute contribution ou suggestion est la bienvenue ! Si vous souhaitez contribuer :

- Forkez le dépôt.
- Créez une branche pour vos modifications.
- Soumettez une pull request avec une description claire de vos changements.
