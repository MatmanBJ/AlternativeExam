# Метод резолюций

В данной инструкции вы найдёте ответы на вопросы по использованию программы.

# Как установить программу?

1. Заходим на вкладку "Releases" справа от репозитория
2. Скачиваем файл "resolution.jar"
3. В папке, где находится файл, вводим команду "java -jar resolution.jar"
4. Используем программу

# Как пользоваться программой?
Изначально программа вам выведет две строки для копирования символов:

1. `Greek alphabet to copy: ΑαΒβΓγΔδΕεΖζΗηΘθΙιΚκΛλΜμΝνΞξΟοΠπΡρΣσςΤτΥυΦφΧχΨψΩω` -- греческий алфавит для ввода констант в логике предикатов;
2. `Special symbol to copy: □` -- специальный символ "пустого" дизъюнкта (на всякий случай).

## Что можно вводить в типе логики:

Далее, сразу же, программа выведет просьбу ввести ТИП ЛОГИКИ:

`Please write type of logic (<type>):`

1. `statement` -- если хотим выбрать [привычную всем] логику высказываний;
2. `predicate` -- если хотим выбрать логику предикатов.

## Что можно вводить в типе ввода:

Потом нас попросят ввести ТИП ВВОДА:

`Please write type of input (<type>):`

1. `console` -- если хотим ввести функцию (точнее, дизъюнкты) из консоли
2. `file` -- если хотим ввести дизъюнкты из файла

## Что можно вводить в типе обработки:

Думаю, это было очевидно.
Теперь перейдём к более сложному.
На этом этапе нам предстоит выбрать тип обработки дизъюнктов, который будет применяться к только что введённым вами дихъюнктам.
Их, по сравнеию с предыдущими пунктами бóльшее количество и предлагают они весьма разнообразную и неординарную реализацию метода резолюции.
Кстати, если вы ещё не ознакомились с нашей [теорией](https://docs.google.com/document/d/1r_mcCiTdolnnHsmiRI_9j5ZyZ3upzpUTGpa-gEHu6A0/edit?usp=sharing) и различными [методами и стратегиями резолюции](https://docs.google.com/document/d/1CQF3K2Z_RgfvqFVIwOvWBBYwGXIVb4-NkI3j6AR70_U/edit?usp=sharing), то настоятельно рекомендуем с ними ознакомиться перед запуском программы для понимания механизма работы метода резолюции. Вернёмся, далее программа попросит ввести ТИП ОБРАБОТКИ:

`Please write type of treatment (<type>):`

**ВНИМАНИЕ! СПИСОК ВОЗМОЖНЫХ КОМАНД НА ДАННОМ ЭТАПЕ ОТЛИЧАЕТСЯ ОТ ТОГО, ЧТО ВЫ ВЫБРАЛИ НА ПЕРВОМ ЭТАПЕ: ЛОГИКУ ВЫСКАЗЫАНИЙ (`statement`) ИЛИ ЛОГИКУ ПРЕДИКАТОВ (`predicate`)**

### Если вы выбрали логику высказываний:

1. `all` -- если хотите увидеть полный перебор всех возможных дизъюнктов (**ВНИМАНИЕ! ЭТОТ МЕТОД МОЖЕТ НЕ ЗАКОНЧИТЬ СВОЮ РАБОТУ И СОЗДАН ДЛЯ ПРОВЕРКИ**);
2. `all number` -- если хотите увидеть полный перебор всех возможных дизъюнктов, но только на заданном количестве шагов (итераций), в данном случае, программа после ввода всех дизъюнктов предложит ввести количество шагов, которые вы хотите увидеть;
3. `all find` -- если хотите увидеть полный перебор всех возможных дизъюнктов ровно до того момента, как найдётся первый пустой дизъюнкт (**ВНИМАНИЕ! ЭТОТ МЕТОД МОЖЕТ НЕ ЗАКОНЧИТЬ СВОЮ РАБОТУ И СОЗДАН ДЛЯ ПРОВЕРКИ**);
4. `idz` -- если хотите увидеть решение четвёртого задания второго ИДЗ по математической логике и теории алгоритмов (там требуется построить только первый уровень новых дизъюнктов);
5. `all unique` -- если хотите увидеть построение всех уникальных дизъюнктов, то есть, если создаётся новый дизъюнкт, все такие же вновь созданные будут удалены, причём не обязательно пустой дизъюнкт будет последним;
6. `saturation` -- если хотите увидеть стратегию насыщения уровней (подробнее читайте [здесь](https://docs.google.com/document/d/1CQF3K2Z_RgfvqFVIwOvWBBYwGXIVb4-NkI3j6AR70_U/edit?usp=sharing));
7. `preference` -- если хотите увидеть стратегию предпочтения (подробнее читайте [здесь](https://docs.google.com/document/d/1CQF3K2Z_RgfvqFVIwOvWBBYwGXIVb4-NkI3j6AR70_U/edit?usp=sharing));
9. `strikeout` -- если хотите видеть стратегию вычёркивания (подробнее читайте [здесь](https://docs.google.com/document/d/1CQF3K2Z_RgfvqFVIwOvWBBYwGXIVb4-NkI3j6AR70_U/edit?usp=sharing));
10. `strategies demonstration` -- если хотите увидеть демонстрацию нескольких стратегий резолюции;
11. `semantic` -- если хотите увидеть метод семантической резолюции (подробнее читайте [здесь](https://docs.google.com/document/d/1CQF3K2Z_RgfvqFVIwOvWBBYwGXIVb4-NkI3j6AR70_U/edit?usp=sharing)).

### Если вы выбрали логику предикатов:

Пока что здесь только один метод, но в будущем их количество увеличится

1. `all unique` -- если хотите увидеть построение всех уникальных дизъюнктов.

## Что можно вводить в типе вывода:

Наконец, программа нам предложит ввести ТИП ВЫВОДА:

`Please write type of output (<type 1> or <type 1> <type 2> or all):`

1. `console` -- если хотим вывести результат обработки только в консоль;
2. `file` -- если хотим вывести результат обработки только в файл;
3. `console file` или `file console` -- если хотим вывести результат обработки и в консоль, и в файл;
4. `all` -- если хотим вывести результат обработки и в консоль, и во все типы файлов (об этом позже);

## Пример выполнения может выглядеть так:

```
Please write type of logic (<type>):
statement
Please write type of input (<type>):
console
Please write type of treatment (<type>):
saturation
Please write type of output (<type 1> or <type 1> <type 2> or all):
file console
```

## Как ввести дизъюнкты из консоли или из файла:

### Из консоли:

```
Repeated and same disjuncts will be deleted!
How many disjuncts do you want?
```

**ЗДЕСЬ ВВОДИМ КОЛИЧЕСТВО ДИЗЪЮНКТОВ**

`Input them:`

**ЗДЕСЬ ВВОДИМ САМИ ДИЗЪЮНКТЫ**

#### Пример может выглядеть так:

```
---------- CONSOLE INPUT ----------
Repeated and same disjuncts will be deleted!
How many disjuncts do you want?
3
Input them:
!A + B
Input 1: !A + B
A + B
Input 2: A + B
!B
Input 3: !B
Current console input:
№1 (id 1): (0, 0, 0) !A + B
№2 (id 2): (0, 0, 0) A + B
№3 (id 3): (0, 0, 0) !B
```

#### Правила ввода переменных:

1. Букв в переменной может быть несколько, например `a`, `a1`, `ab`, `A`, `AA`;
2. Отрицание обозначается `!` буз пробелов у переменной, например, `!a`, `!a1`, `!ab`;
3. Несколько переменных разделяются знаком `+` обязательно через пробел, например, `a + b`, `!a1 + !a2 + a3 + c1`;

### Из файла:

```
---------- FILE INPUT ----------
Please write file name(<file name>):
```

**ЗДЕСЬ ВВОДИМ АБСОЛЮТНЫЙ ПУТЬ К ФАЙЛУ ИЛИ ПУТЬ ОТНОСИТЕЛЬНО ПАПКИ ПРОГРАММЫ ИЛИ ПРОЕКТА IDE**

## Ссылки на работы:

1. [Документ с теорией](https://docs.google.com/document/d/1r_mcCiTdolnnHsmiRI_9j5ZyZ3upzpUTGpa-gEHu6A0/edit?usp=sharing);
2. [Документ с методами](https://docs.google.com/document/d/1CQF3K2Z_RgfvqFVIwOvWBBYwGXIVb4-NkI3j6AR70_U/edit?usp=sharing);
3. [Презентация](https://docs.google.com/presentation/d/1FEjB-ixD_4w8hBM2LGQj1OG16Mslhync4m7j_d_NMFs/edit?usp=sharing);
