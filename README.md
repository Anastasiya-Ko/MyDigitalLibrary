# Цифровая библиотека
REST API на Spring Boot для работы Электронной библиотеки.

# Функции справочника книг:
1. Регистрация новых книг, с указанием Автора только из таблицы Автор
2. Работа со справочником книг: постраничных вывод всех (с установкой сортировки), поиск по id, по началу названия книги
3. Отображение всех книг, принадлежащих одному автору
4. Вывод всех книг, находящихся "на руках" у конкретного читателя
5. Отображение всех свободных/занятых книг на данный момент
6. Выдача книг читателям (статус книги меняется на
7. "Занята")
8. Освобождение книг после того, как читатель возвращает книгу обратно в библиотеку (статус меняется на - "Свободна")
9. Удаление книг (в случае утери)
# Функции справочника читателей:
1. Регистрация новых читателей, корректировка данных зарегистрированных ранее, удалении (в случае ошибки).
2. Работа со справочником читателей: постраничных вывод всех (с установкой сортировки), поиск по id, по фамилии

# Функции справочника Автор:
1. Отображение Авторов, книги которых не представлены в библиотеке

# Техническая часть:
1. Связь с БД налажена с помощью Spring Data JPA (в том числе @Query, @Formula)
2. Настроена валидация данных на полях DTO
3. Валидация полей Читателя Имя, Фамилия осуществляется с помощью @Pattern(regexp)
4. Написана аннотация для проверки написания имён и фамилий с заглавной буквы
3. Реализована пагинация с сортировкой(варианты сортировки ограничены enum-ом)
4. Реализованы кастомные методы Hibernate
5. Подключен Swagger 
6. Конфигурация приложения осуществляется с помощью файла, хранящегося локально
7. Реализован слой DTO.


