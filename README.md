# ![](ressources/logo.jpeg) Algorithme génétique
### IUT Montpellier-Sète – Département Informatique

| `WARNING: Work in progress` |
| --- |

[![Build Status](https://travis-ci.org/joan-teriihoania/remote-control.svg?branch=master)](https://travis-ci.org/joan-teriihoania/remote-control)

# Présentation
Projet créé et initié dans le cadre d'un projet tuteuré en formation de *DUT Année Spéciale en informatique* à l'*Institut Universitaire Technologie de Montpellier-Sète* pour la promotion 2019-2020 en collaboration par :
 - [TERIIHOANIA Joan](http://joan-teriihoania.fr/),
 - ODOR Thibault,
 - FAVOT Lino,
 - BES Jean-Baptiste.

Sous la supervision de **[BOUGERET Marin](http://www.lirmm.fr/~bougeret/)**, enseignant-chercheur au *LIRMM de Montpellier*.


# Fonctionnement
## Définition
L'algorithme génétique se base sur une évolution par sélection naturelle. Le principe de *[sélection naturelle](https://fr.wikipedia.org/wiki/S%C3%A9lection_naturelle)* est basé sur la théorie de *[Darwin](https://fr.wikipedia.org/wiki/Charles_Darwin)*. Ce principe repose sur la base d'une valeur sélective définie à chaque individu parmi une massée variée et difforme pour obtenir, sur une période donnée, une souche détenant les paramètres les mieux adaptés à un environnement et des critères d'évaluation déterminés. L'individu faisant parti d'une population d'une génération à un instant `t` et pouvant passer ses "gènes" à un individu fils de la génération à l'instant `t+1` en passant par une phase d'évaluation, sélection, croisement, mutation puis réinsertion.

Nous pouvons représenter ce schéma de réalisation par le suivant :

![](ressources/algo_gen_scheme.png)

Dans un environnement déterminé, des individus, chacun représentant une solution à un problème défini, sont confrontés afin de déterminer lequel, selon des critères donnés, a le plus de valeur et sera sélectionné pour l'environnement confronté à le surmonter. Cette situation, bien que relativement simple, peut ainsi être changée en divers environnements. Qu'il s'agisse d'une simulation naturelle ou d'un environnement économique où les individus sont des entreprises. L'objectif de cette méthode de sélection est de déterminer l'individu le plus efficient et efficace et obtenir une solution rapide à une situation.

> **Note :** Il est possible de coupler cet algorithme à un réseau de neurones informatique ou *Neural Network* basé sur les idées du psychologue *Franck Rosenblatt*. Notamment pour le calcul des poids de neurones.

## Objectif


## Structure du projet
Ce projet se déroulera en deux phases.  