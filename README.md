# Spring Boot + Kafka Streams: три сервиса

Демонстрационный проект содержит три Java-микросервиса и простой React-монитор:

1. **producer** — форма Thymeleaf принимает сообщение по `POST /messages` и отправляет JSON-событие в `demo-messages`.
2. **stream-processor** — отдельный сервис без frontend использует Kafka Streams, читает `demo-messages`, добавляет `uppercaseMessage` и `processedAt`, публикует событие в `processed-messages`.
3. **consumer** — читает `processed-messages`, печатает сообщение в stdout и сохраняет последние 100 событий для REST API `GET /api/messages`.
4. **frontend** — React + Vite 7 + TypeScript отображает обработанные события и периодически обновляет список.

## Стек

Java 17, Spring Boot 3.4.3, Gradle Kotlin DSL, Thymeleaf, Spring Kafka, Kafka Streams, Apache Kafka 3.9 в режиме KRaft, Docker Compose, React 19, Vite 7, TypeScript, JUnit 5.

## Запуск всего стека

Требуется Docker Engine с Compose plugin. Из корня проекта:

```bash
docker compose up --build
```

Compose запускает Kafka и затем все приложения. Страницы:

- форма отправки (producer): http://localhost:8080
- монитор обработанных сообщений (React): http://localhost:5173
- REST API последних событий: http://localhost:8081/api/messages

Отправьте сообщение на Thymeleaf-странице. Kafka Streams преобразует его в верхний регистр; событие появится в браузере и в логах consumer.

```bash
docker compose logs -f stream-processor consumer
```

Остановка:

```bash
docker compose down
```

Удаление также сохранённых данных Kafka:

```bash
docker compose down -v
```

## Локальный запуск Java-приложений

Нужны JDK 17+ и Gradle 8.x. Запустите брокер в Docker:

```bash
docker compose up -d kafka
```

Из корня проекта, в отдельных терминалах:

```bash
gradle :consumer:bootRun
gradle :stream-processor:bootRun
gradle :producer:bootRun
```

Для frontend локально (требуется Node.js 20.19+ или 22.12+):

```bash
cd frontend
npm install
npm run dev
```

Локальные приложения подключаются к брокеру на `localhost:29092`. React монитор обращается �� API на `http://localhost:8081`.

## Тесты

Из корня проекта:

```bash
gradle test
```

Тесты проверяют валидацию и публикацию producer, преобразование события Kafka Streams и ограничение/порядок истории consumer.
