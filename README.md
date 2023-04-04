# MyDigitalLibrary
Spring Boot приложение для работы Электронной библиотеки.
# Функции: 
1. Регистрация читателей, новых книг 
2. Выдача читателям книг
3. Освобождение книг (после того, как читатель возвращает книгу обратно в библиотеку)
4. Удаление книг (в случае утери)
# Техническая часть:
1. Связь с БД налажена с помощью Spring Data JPA 
2. Настроена валидация данных на полях DTO, валидация полей "Дата рождения" и "Год публикации книги" осуществляется с помощью @Pattern(regexp)
3. Подключен Swagger 
4. Конфигурация приложения осуществляется с помощью файла, хранящегося локально

