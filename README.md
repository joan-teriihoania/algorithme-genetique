# ![](.ressources/logo.jpeg) Algorithme génétique
### IUT Montpellier-Sète – Département Informatique

| `WARNING: Work in progress` |
| --- |

[![Build Status](https://travis-ci.org/joan-teriihoania/remote-control.svg?branch=master)](https://travis-ci.org/joan-teriihoania/remote-control)

# Le projet
Projet créé et initié dans le cadre d'un projet tuteuré par des étudiants en formation de *DUT Année Spéciale en informatique* à l'*Institut Universitaire Technologie de Montpellier-Sète* pour la promotion 2019-2020 :
 - [TERIIHOANIA Joan](http://joan-teriihoania.fr/),
 - ODOR Thibault,
 - FAVOT Lino,
 - BES Jean-Baptiste.

Sous la supervision de **[BOUGERET Marin](http://www.lirmm.fr/~bougeret/)**, enseignant-chercheur au *LIRMM de Montpellier*.


# Préambule
## Définition
L'algorithme génétique se base sur une évolution par sélection naturelle. Le principe de *[sélection naturelle](https://fr.wikipedia.org/wiki/S%C3%A9lection_naturelle)* est basé sur la théorie de *[Darwin](https://fr.wikipedia.org/wiki/Charles_Darwin)*. Ce principe repose sur la base d'une valeur sélective définie à chaque individu parmi une masse plus ou moins difforme pour obtenir, sur une période donnée, une souche détenant les paramètres les mieux adaptés à un environnement sélectionné sur des critères d'évaluation déterminés. L'individu `a` faisant parti d'une population d'une génération à un instant `t` et pouvant passer ses "gènes" à un individu `b` fils de la génération à l'instant `t+1` en passant par une phase d'évaluation, sélection, croisement, mutation puis réinsertion.

> **Important :** Afin de préserver un potentiel d'évolutivité, l'algorithme ne doit ni être eugéniste (*[Eugénisme](https://fr.wikipedia.org/wiki/Eug%C3%A9nisme)*) ni élitiste. Les individus de la population, qu'ils soient faiblement ou moyennement évalués ne doivent pas être supprimés pour conserver une certaine diversité au sein de la population et éviter toute stagnation évolutive.

Nous pouvons représenter ce schéma de réalisation par le suivant :

![](.ressources/algo_gen_scheme.png)

Dans un environnement déterminé ci-nommé `problème`, des individus ci-nommés `solutions` sont confrontés afin de déterminer lequel, selon des critères donnés, a le plus de valeur et sera sélectionné pour l'environnement confronté à le surmonter.

**Autrement dit,** pour un problème établi, nous émettons des `hypothèses` de solution qui, au fur et à mesure de la simulation, établieront selon leur évaluation leur efficacité à résoudre le problème édicté.


Cette situation, bien que relativement simple, peut avoir des applications variées. Qu'il s'agisse d'une simulation naturelle ou d'un environnement économique où les individus sont des entreprises.

## Objectif

L'objectif du principe de sélection naturelle est donc de déterminer l'individu le plus efficient et efficace et obtenir une solution rapide à une situation.

> **Note :** Il est possible de coupler cet algorithme à un réseau de neurones artificiels ou [*Neural Network*](https://fr.wikipedia.org/wiki/R%C3%A9seau_de_neurones_artificiels) basé sur les idées du psychologue [*Franck Rosenblatt*](https://fr.wikipedia.org/wiki/Frank_Rosenblatt). Notamment pour le calcul des poids de neurones. *Quelques lectures peuvent être trouvées à ce sujet dont (en anglais) :*
> - [*"Deep Learning with Darwin: Evolutionary Synthesis of Deep Neural Networks"*](https://arxiv.org/pdf/1606.04393.pdf) de M. J. Shafiee, A. Mishra et A. Wong;
> - [*"Evolutionary Robot Behaviors Based on Natural Selection and Neural Network"*](https://link.springer.com/chapter/10.1007/1-4020-8151-0_6) dans *Artificial Intelligence Applications and Innovations* de M. Bramer et V. Devedzic;
> - [*"Evolving Neural Networks"*](https://home.fnal.gov/~souvik/Brain/index.html) et [*"A biology-inspired neural network evolving through natural
selection"*](https://home.fnal.gov/~souvik/Brain/BrainInWorld.pdf) de Souvik Das ;
>
> Ou encore, un jeu de simulation [*"The Bibites"*](https://leocaussan.itch.io/the-bibites) développé par Léo Caussan dont le développement peut être suivi depuis sa chaîne [YouTube](https://www.youtube.com/channel/UCjJEUMnBFHOP2zpBc7vCnsA).

L'objectif de ce projet est :
 - D'une première part, **produire** un algorithme et une structure algorithmique permettant de calculer et **simuler** l'évolution d'une population d'individus dans un environnement;
 - D'une seconde part, **observer** leur comportement afin d'en extrapoler des résultats que nous tenterons d'expliquer et d'évaluer.

# Déroulement

Ce projet se déroulera en deux phases distincts.