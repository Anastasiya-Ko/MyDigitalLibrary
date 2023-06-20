# Цифровая библиотека
REST API на Spring Boot для работы Электронной библиотеки.

# Функции справочника Книг:
1. Постраничный вывод всех книг(с установкой возможной сортировки);
2. Чтение книги по id, по началу названия книги;
3. Чтение книги по началу названия книги;
4. Вывод всех книг, находящихся "на руках" у запрашиваемого читателя;
5. Отображение всех свободных/занятых книг на данный момент;
6. Чтение книг, написанных группой авторов;
7. Регистрация новых книг, с указанием Автора только из таблицы Автор;
8. Обновление информации о книге;
9. Назначение книги читателю, только находящемуся в таблице Читатель, с автоматической установкой статуса - "книга занята";
10. Освобождение книги, при возвращении читателем в библиотеку(статус назначается - "книга свободна");
11. Удаление книги;
12. Запись в файл Excel отчёта по всем книгам.

# Функции справочника Читателей:
1. Постраничный вывод всех читателей(с установкой возможной сортировки);
2. Поиск читателей по id, по фамилии;
3. Регистрация новых читателей;
4. Корректировка данных зарегистрированных ранее читателей;
5. Удаление учётной записи;
6. Запись в файл Excel отчёта по возрастному составу читателей.

# Функции справочника Авторов:
1. Постраничный вывод всех авторов в библиотеке;
2. Поиск автора по id;
3. Отображение Авторов, книги которых не представлены в библиотеке;
4. Регистрация новых авторов;
5. Корректировка данных учётных записей;
6. Удаление автора.

# Техническая часть:
1. Связь с БД налажена с помощью Spring Data JPA (в том числе @Query, @Formula),а также используется JDBCTemplate;
2. Реализованы кастомные методы Hibernate;
3. Создание отчётов осуществляется с помощью библиотеки Apache poi;
4. Настроена валидация данных на полях DTO и данных, приходящих через URL;
5. Валидация полей Читателя Имя, Фамилия осуществляется с помощью @Pattern(regexp);
6. Написана и применена аннотация для проверки: 
   1)соответствия половой принадлежности заданному перечислению,
   2)написания имён и фамилий с заглавной буквы;
7. Реализована пагинация с сортировкой(варианты сортировки ограничены enum-ом);
8. Подключено документирование Swagger;
9. Ведётся логирование, с записью в файл;
10. Конфигурация приложения осуществляется с помощью файла, хранящегося локально;
11. Проект подключен к системе контроля версий GitHub.

Примеры отчётов, формирующихся в приложении:
Отчёт по возрастным группам читателей: [readersGroupAge.xlsx](https://github.com/Anastasiya-Ko/MyDigitalLibrary/files/11798900/readersGroupAge.xlsx)
Отчёт по всем книгам библиотеки: [allBooks.xlsx](https://github.com/Anastasiya-Ko/MyDigitalLibrary/files/11799057/allBooks.xlsx)



