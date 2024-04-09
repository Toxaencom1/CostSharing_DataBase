Создание первого проекта - DataBase

Описание:

В этом проекте мы создаем простой микросервис, который позволяет работать с базой данных PostgreSQL. Микросервис будет предоставлять API для работы с сущностями:

    Сессии
    Чеки
    Факты оплаты
    Пользователи
    Продукты

Технологии:

    Java 17
    Spring Boot
    Spring Data JPA
    PostgreSQL
    Lombok
    DevTools

Структура проекта:

    pom.xml: Файл Maven, содержащий зависимости проекта.
    src/main/java: Папка с исходным кодом Java.
    src/main/resources: Папка с ресурсами проекта,  application.yml,  SQL скрипты.
    

Основные этапы разработки:

    Настройка проекта:
        Создаем проект Maven.
        Добавляем необходимые зависимости.
        Настраиваем порт и имя сервиса в application.yml.

    Создание сущностей:
        Создаем классы для сущностей: Session, Check, PayFact, ProductUsing, Account, TempUser.
        Определяем связи между сущностями.
        Создаем интерфейсы репозиториев для сущностей.

    Создание логики приложения:
        Создаем класс SessionService для реализации бизнес-логики.
        Реализуем CRUD-операции для сущностей.
        Добавляем дополнительные методы, необходимые для работы приложения.

    Обработка RESTful HTTP-запросов:
        Создаем класс SessionController для обработки HTTP-запросов.
        Используем аннотации @RequestMapping, @PathVariable, @RequestParam, @RequestBody для определения маршрутов и параметров запросов.

    Настройка базы данных:
        Создаем SQL-скрипт для создания таблиц.
        Настраиваем подключение к базе данных в application.yml.

    Тестирование приложения:        
        Тестируем работу API с помощью Postman.

