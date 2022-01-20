## PollsApi ##
#### Выполнил: Чунарёв Сергей. ####

Система опросов пользователя

####	Реализовано: ####

- Авторизация пользователя 
- Разграничение функциональности по ролям (USER, ADMIN)
- Возможность создания/редактирования/удаления опросов
- Возможность создания/редактирования/удаления вопросов для опроса
- Прохождение опроса
- Выдача исключений при ошибках
- Миграция баз данных с помощью Flayway
- Запуск MYSQL сервера и проекта с помощью Docker

---

## Запуск проекта с помощью Docker ##

<h4> Что потребуется для запуска </h4>

* Docker
* Git

<h4> Загрузка  </h4>

```bash
git clone https://github.com/chunarevsa/PollsApi.git
cd PollsApi


```

<h4> Измените имя пользователя и пароль MySQL в application.properties </h4>

* `spring.datasource.username`
* `spring.datasource.password`

MYSQL сервер запускается с параметром ports:`3306:3306` 

База данных `pollsapidb` создаётся автоматически

<h4> Запуск </h4>

```bash
docker-compose -f docker-compose.dev.yml up --build

```

Проект запускается с параметром `server.port:8088`

---

## Запуск проекта без Docker ##

<h4> Что потребуется для запуска </h4>

* Java
* Maven
* MYSQL
* Git

<h4> Загрузка  </h4>

```bash
git clone https://github.com/chunarevsa/PollsApi.git
cd PollsApi


```

<h4> Подготовка </h4>

Необходимо запустить MYSQL сервер с портом `3306` 

<h4> Внесите изменения в application.properties </h4>

* `spring.datasource.username`
* `spring.datasource.password`

Переключить `spring.datasource.url` с "Для Docker" на "Без Docker"


<h4> Запуск через Bash </h4>

```bash
./mvnw spring-boot:run


```

Проект запускается с параметром `server.port:8088`

База данных `pollsapidb` создаётся автоматически

---


## API ##

Для удобства запросы из `Postman` доступны в файле 

* `Website.postman_collection.json`



> <h3> Аутентификация </h3>


---

<details>
<summary> Авторизация </summary>

```
curl --location --request POST 'localhost:8088/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "admin",
    "password": "admin"
}'
```

* Авторизация осуществляется через `username` и `password`
* Для авторизации используйте уже существующего пользователя 
`username` - `admin`
`password` - `admin`

</details>

---


> <h3> Опросы </h3>

<details>
<summary> Добавление нового опроса </summary>

```
curl --location --request POST 'localhost:8088/poll/add' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjQyNjU5NzAwLCJleHAiOjE2NDUyNTE3MDAsImF1dGhvcml0aWVzIjoiUk9MRV9BRE1JTixST0xFX1VTRVIifQ.UzIP3coQN828R0aEB-SxrwgUIbPQWSOmQwZCYN4dGNFSlhtz8f6u47GYc7xhuyIKBrym8YQeYcZM09baM-npzQ \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Poll number 1",
    "description": "Cool poll",
    "active": "true",
    "expirationDate": "2022-02-25"
}'
```

* Доступно только `ADMIN`

</details>

---

<details>
<summary> Изменение опроса </summary>

```
curl --location --request POST 'localhost:8088/poll/1/edit' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjQyNjU5NzAwLCJleHAiOjE2NDUyNTE3MDAsImF1dGhvcml0aWVzIjoiUk9MRV9BRE1JTixST0xFX1VTRVIifQ.UzIP3coQN828R0aEB-SxrwgUIbPQWSOmQwZCYN4dGNFSlhtz8f6u47GYc7xhuyIKBrym8YQeYcZM09baM-npzQ' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Poll number 1!",
    "description": "Cool poll!",
    "active": "true",
    "expirationDate": "2022-03-25"
}'
```

* Доступно только `ADMIN`

</details>

---

<details>
<summary> Удаление опроса </summary>

```
curl --location --request POST 'localhost:8088/poll/1/delete' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Доступно только `ADMIN`

</details>

---

<details>
<summary> Список всех опросов активных опросов </summary>

```
curl --location --request GET 'localhost:8088/poll/all' \

```

* Выдаёт только опросы с `active=true`
* Отсеивает "просроченные опросы"

</details>

---

<details>
<summary> Список всех опросов </summary>

```
curl --location --request GET 'localhost:8088/poll/all/admin' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'


```

</details>

---


<details>
<summary> Прохождение опроса </summary>

```
curl --location --request POST 'localhost:8088/poll/1/start' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userId": 33333
}'


```

* Введи свой уникальны индентификатор
* Один опрос можно проходить один раз
* Ответы нужно внести в терминале
* Ответ выдаётся в DTO

</details>

---

> <h3> Вопросы  </h3>

Добавлены три вида вопросов:
`TEXT_ANSWER` - можно ввести ответ в свободной форме в виде текста
`ONE_ANSWER` - выбрать один из предложенных
`MANY_ANSWER` - выбрать несколько из предложенных

<details>
<summary> Добавление вопроса в опрос </summary>

```
curl --location --request POST 'localhost:8088/poll/1/question/add' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "body": "Question 1",
    "questionType": "ONE_ANSWER",
    "active": "true",
        "answers": [
            {
                "text": "answer 1"
            },
            {
                "text": "answer 2"
            }
                    ]
}'

```

* Если типа вопроса `TEXT_ANSWER` `answers` указывать не нужно 

</details>

---

<details>
<summary> Изменение вопроса в опросе </summary>

```
curl --location --request POST 'localhost:8088/poll/1/question/1/edit' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjQyNjU5NzAwLCJleHAiOjE2NDUyNTE3MDAsImF1dGhvcml0aWVzIjoiUk9MRV9BRE1JTixST0xFX1VTRVIifQ.UzIP3coQN828R0aEB-SxrwgUIbPQWSOmQwZCYN4dGNFSlhtz8f6u47GYc7xhuyIKBrym8YQeYcZM09baM-npzQ' \
--header 'Content-Type: application/json' \
--data-raw '{
    "text": "Question number 2",
    "questionType": "TEXT_ANSWER",
    "active": "true"
}'
```

* Формат запроса `poll/{pollid}/question/{questionQueueId}/edit`


</details>

---

<details>
<summary> Удаление вопроса в опросе </summary>

```
curl --location --request POST 'localhost:8088/poll/1/question/1/delete' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Доступно только `ADMIN`
* Формат запроса `poll/{pollid}/question/{questionId}/delete`

</details>


---

---
