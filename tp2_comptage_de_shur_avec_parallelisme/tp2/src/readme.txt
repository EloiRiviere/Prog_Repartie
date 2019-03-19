salome.pereira@etu.uca.fr
eloi.riviere@etu.uca.fr

Pour  10000.schur:
Commande: time java tp2.MainTp2   10000.schur  1 | Temps: real: 0m  0,119s / user: 0m  0,152s | Valeur:   249332
Commande: time java tp2.MainTp2   10000.schur  2 | Temps: real: 0m  0,100s / user: 0m  0,160s | Valeur:   249332
Commande: time java tp2.MainTp2   10000.schur  4 | Temps: real: 0m  0,085s / user: 0m  0,144s | Valeur:   249332
Commande: time java tp2.MainTp2   10000.schur  8 | Temps: real: 0m  0,092s / user: 0m  0,160s | Valeur:   249332
Commande: time java tp2.MainTp2   10000.schur 16 | Temps: real: 0m  0,089s / user: 0m  0,156s | Valeur:   249332

Pour 100000.schur
Commande: time java tp2.MainTp2  100000.schur  1 | Temps: real: 0m  3,474s / user: 0m  3,532s | Valeur:  6251210
Commande: time java tp2.MainTp2  100000.schur  2 | Temps: real: 0m  1,900s / user: 0m  3,788s | Valeur:  6251210
Commande: time java tp2.MainTp2  100000.schur  4 | Temps: real: 0m  1,057s / user: 0m  3,996s | Valeur:  6251210
Commande: time java tp2.MainTp2  100000.schur  8 | Temps: real: 0m  0,938s / user: 0m  3,344s | Valeur:  6251210
Commande: time java tp2.MainTp2  100000.schur 16 | Temps: real: 0m  0,841s / user: 0m  3,120s | Valeur:  6251210

Pour 300000.schur
Commande: time java tp2.MainTp2  300000.schur  1 | Temps: real: 0m 26,308s / user: 0m 26,376s | Valeur: 14066974
Commande: time java tp2.MainTp2  300000.schur  2 | Temps: real: 0m 13,925s / user: 0m 27,812s | Valeur: 14066974
Commande: time java tp2.MainTp2  300000.schur  4 | Temps: real: 0m  7,809s / user: 0m 29,420s | Valeur: 14066974
Commande: time java tp2.MainTp2  300000.schur  8 | Temps: real: 0m  6,974s / user: 0m 26,724s | Valeur: 14066974
Commande: time java tp2.MainTp2  300000.schur 16 | Temps: real: 0m  5,976s / user: 0m 23,376s | Valeur: 14066974

Pour 1000000.schur
Commande: time java tp2.MainTp2 1000000.schur  1 | Temps: real: 4m 56,021s / user: 4m 56,156s | Valeur: 39072907
Commande: time java tp2.MainTp2 1000000.schur  2 | Temps: real: 2m 32,815s / user: 5m  5,532s | Valeur: 39072907
Commande: time java tp2.MainTp2 1000000.schur  4 | Temps: real: 1m 19,738s / user: 5m  4,708s | Valeur: 39072907
Commande: time java tp2.MainTp2 1000000.schur  8 | Temps: real: 1m 19,533s / user: 5m  7,500s | Valeur: 39072907
Commande: time java tp2.MainTp2 1000000.schur 16 | Temps: real: 1m  8,142s / user: 4m 27,596s | Valeur: 39072907

-> On observe un ralentissement de la proportionnalité [augmentation du nombre de threads / baisse du temps réel d'exécution] lorsque l'on dépasse 4 threads
-> Référence: pour 1000000: De 2 à 4 threads on passe de 2m 32,815s à 1m 19,738s de temps réel d'exécution, et de 4 à 8 threads on passe de 1m 19,738s à 1m 19,533s de temps réel d'exécution
-> Il y a donc 4 cœurs dans ma machine [Asus ROG GL-502-VT]
