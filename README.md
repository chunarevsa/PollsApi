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
- Документация Swagger Docs

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

## Документация Swagger ##

Документация доступна по адресу:

`http://localhost:8088/swagger-ui.html`


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


> <h3> Внутренняя валюта  </h3>

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
<summary> Получение списка всех опросов </summary>

```
curl --location --request GET 'localhost:8088/currency/all' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Разное предоставление информации в зависимости от роли (ADMIN, USER)
* `USER` - Получение списка активных валют. Выдаётся исключение, если таких нет
* `ADMIN` - Получение списка всех валют. Выдаётся исключение, если валюты отсутствуют

</details>

---

<details>
<summary> Получение информации о валюте </summary>

```
curl --location --request GET 'localhost:8088/currency/gold' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Разное предоставление информации в зависимости от роли (ADMIN, USER)
* `USER`- Получение краткой информации о валюте. Выдаётся исключение, если она не активна или отсутствует
* `ADMIN`- Получение расширенной информации о валюте. Выдаётся исключение, если такая валюта отсутствует

</details>

---

<details>
<summary> Покупка валюты </summary>

```
curl --location --request POST 'localhost:8088/currency/buy?title=gold&amount=100' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Выдаётся исключение, если валюта неактивна или отсутствует
* Выдаётся исключение, если у пользователя недостаточно средств `$` для покупки указанного количества валюты `amount`
* Валюта добавлется пользователю в `account`. Если такой валюты у пользователя ещё не было - создаётся новый счёт для валюты

</details>

---

> <h3> Товары  </h3>

<details>
<summary> Добавление нового товара </summary>

```
curl --location --request POST 'localhost:8088/item/add' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Ломик Гордона Фримена",
    "type": "Weapon",
    "description": "Cool weapon ",
    "active": "true",
        "prices": [
            {
                "cost": "5",
                "currency": "gold",
                "active": "true"
            },
            {
                "cost": "50",
                "currency": "silver",
                "active": "true"
            }
                    ]
}'
```

* Доступно только `ADMIN`
* Товар создаётся, как минимум, с одной ценой во внутренней валюте
* У товара может быть только одна цена в одной валюте 
* Выдаётся исключение, если цена указана в отсутствующей валюте 
* Выдаётся исключение, если цены и товары введены некорректно 

</details>

---


<details>
<summary> Добавление новой цены товару </summary>

```
curl --location --request POST 'localhost:8088/item/1/prices/add' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cost": "10",
    "currency": "silver",
    "active": "true"
}'
```

* Доступно только `ADMIN`
* Выдаётся исключение, если цена указана в отсутствующей валюте 
* Выдаётся исключение, если цены введены некорректно 
* Выдаётся исключение, если у товара уже есть цена в такой валюте 

</details>

---

<details>
<summary> Изменение товара </summary>

```
curl --location --request POST 'localhost:8088/item/1/edit' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Легендарный плащ",
    "type": "skin",
    "description": "обычный плащ",
    "active": "true"
}'
```

* Доступно только `ADMIN`
* Выдаётся исключение, если данные о товаре введены некорректно 

</details>

---

<details>
<summary> Изменение цены товара </summary>

```
curl --location --request POST 'localhost:8088/item/price/1/edit' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cost": "10",
    "currency": "silver",
    "active": "true"
}'
```

* Доступно только `ADMIN`
* Выдаётся исключение, если цена указана в отсутствующей валюте 
* Выдаётся исключение, если данные о цене введены некорректно 
* Выдаётся исключение, если у товара уже есть цена в такой валюте

</details>

---

<details>
<summary> Удаление товара </summary>

```
curl --location --request POST 'localhost:8088/item/1/delete' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Доступно только `ADMIN`
* Выдаётся исключение, если такой товар отсутствует

</details>

---

<details>
<summary> Получение списка всех товаров </summary>

```
curl --location --request GET 'localhost:8088/item/all' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Разное предоставление информации в зависимости от роли (ADMIN, USER)
* `USER` - Получение списка активных товаров. Выдаётся исключение, если таких нет
* `ADMIN` - Получение списка всех товаров. Выдаётся исключение, если товары отсутствуют

</details>

---

<details>
<summary> Получение информации о товаре </summary>

```
curl --location --request GET 'localhost:8088/item/1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Разное предоставление информации в зависимости от роли (ADMIN, USER)
* `USER`- Получение краткой информации о товаре. Выдаётся исключение, если он неактивен или отсутствует
* `ADMIN`- Получение расширенной информации о товаре. Выдаётся исключение, если такой товар отсутствуют

</details>

---

<details>
<summary> Получение всех цен товара </summary>

```
curl --location --request GET 'localhost:8088/item/1/prices' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Выдаётся исключение, если такой товар отсутствует
* Разное предоставление информации в зависимости от роли (ADMIN, USER)
* `USER`- Получение списка цен с краткой информацией. Выдаётся исключение, если они не активны
* `ADMIN`- Получение списка цен с расширенной информацией.

</details>

---

<details>
<summary> Покупка товара </summary>

```
curl --location --request POST 'localhost:8088/item/1/buy?currencytitle=gold&amountitem=2' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Выдаётся исключение, если товар неактивен или отсутствует
* Выдаётся исключение, если цена товара в такой валюте неактивна или отсутствует
* Выдаётся исключение, если у пользователя недостаточно валюты `gold` для покупки указанного количества товаров `amountitem`
* Валюта `gold` списывается со `account` 
* Товар добавляется пользователю в инвентарь `inventory`. Если такого товара у пользователя ещё не было - создаётся новая ячейка `inventoryUnit` 

</details>

---

> <h3> Пользователь </h3>

<details>
<summary> Свой профиль </summary>

```
curl --location --request GET 'localhost:8088/user/profile' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'
```

* Получение информации о своём профиле, балансе `$` и счетах `accounts`

</details>

---

<details>
<summary> Просмотр своего инвентаря  </summary>

```
curl --location --request GET 'localhost:8088/user/profile/inventory' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Получение информации о своём инвентаре `inventory`

</details>

---

<details>
<summary> Просмотр профиля любого пользователя  </summary>

```
curl --location --request GET 'localhost:8088/user/admin' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Выдаётся исключение, если такой пользователь отсутствует
* Получение краткой информации о другом пользователе

</details>

---

<details>
<summary> Получение списка всех пользователей  </summary>

```
curl --location --request GET 'localhost:8088/user/admin' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Доступно только `ADMIN`
* Получение списка с краткой информацией о всех пользователях

</details>

---

---
