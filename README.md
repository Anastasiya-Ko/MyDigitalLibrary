# Цифровая библиотека
REST API на Spring Boot для работы Электронной библиотеки.

# Функции справочника читателей:
1. Регистрация новых читателей, корректировка данных зарегистрированных ранее, удалении (в случае ошибки).
2. Работа со справочником читателей: постраничных вывод всех (с установкой сортировки), поиск по id, по фамилии

# Функции справочника книг:
1. Регистрация новых книг, с указанием Автора только из таблицы Автор  
2. Работа со справочником книг: постраничных вывод всех (с установкой сортировки), поиск по id, по началу названия книги
3. Отображение всех книг, принадлежащих одному автору
4. Вывод всех книг, находящихся "на руках" у конкретного читателя
5. Отображение всех свободных/занятых книг на данный момент
6. Выдача книг читателям (статус книги становится - "Занята")
7. Освобождение книг после того, как читатель возвращает книгу обратно в библиотеку (статус меняется на - "Свободна")
8. Удаление книг (в случае утери)

# Техническая часть:
1. Связь с БД налажена с помощью Spring Data JPA 
2. Настроена валидация данных на полях DTO, валидация полей Имя, Фамилия осуществляется с помощью @Pattern(regexp)
3. Возраст людей высчитывается, с помощью @Formula
4. Реализована пагинация с сортировкой
5. Реализованы кастомные методы Hibernate
6. Подключен Swagger 
7. Конфигурация приложения осуществляется с помощью файла, хранящегося локально


