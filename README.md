# SusFarey

Una sucesión de Farey es una sucesión matemática de fracciones irreductibles entre 0 y 1 que tienen un denominador menor o igual a n en orden creciente.
Cada sucesión de Farey comienza en el 0, denotado por la fracción: 0/1 y termina en el 1
Para construir la sucesión de Farey para un número n (por ejemplo, el 3) el procedimiento sería el siguiente:

1) Se construyen unas fracciones con todas las combinaciones posibles de los números del 1 al 3:
F(3)= 1/1, 1/2, 1/3, 2/1, 2/2, 2/3, 3/1, 3/2, 3/3
2) Se eliminan aquellas fracciones superiores a 1 (o dicho de otra manera, en las que el numerador sea mayor que el denominador):

F(3)= 1/1, 1/2, 1/3, 2/2, 2/3, 3/3
3) Se simplifican todas las fracciones, descartando las repetidas:

F(3)= 1/1, 1/2, 1/3, 2/3
4) Se ordena el resultado de menor a mayor, agregando el 0 (0/1) al principio:

F(3)= 0/1, 1/3, 1/2, 2/3, 1/1
Se le pide que desarrolle una aplicación que permita dado un numero N (Muy grande), dar el resultado de la sucesión de Farey para ese valor, utilizando para ello "X" cantidad de máquinas (clientes) interconectadas (número de máquinas especificado por el usuario), entre las cuales se dividirán los cálculos para obtener el resultado final

Estudiante: Leyddi Astrid Borjas Zamora
Cedula: 21.219.922

