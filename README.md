# 🌦 RestAppWeather

REST API-приложение для сбора и хранения метеорологических данных с пользовательских сенсоров.

## 📌 Описание

RestAppWeather — это серверное приложение на **Spring Boot**, которое позволяет:
- Регистрировать новые сенсоры.
- Добавлять измерения температуры, информацию о наличии дождя.
- Получать все измерения в виде JSON.
- Вычислять количество дождливых дней.

Данные сохраняются в базе **PostgreSQL**. Валидация выполняется на уровне DTO и с помощью кастомных валидаторов.

---

## ⚙ Технологии

- **Java 17**
- **Spring Boot** (Web, Validation, Data JPA)
- **PostgreSQL**
- **ModelMapper**
- **Hibernate**
- **Jakarta Validation**

---

## 📡 REST API эндпоинты

### 📍 Сенсоры
| Метод | URL | Описание | Тело запроса |
|-------|-----|----------|--------------|
| `POST` | `/sensors/registration` | Регистрация нового сенсора | `{ "name": "SensorName" }` |

---

### 📍 Измерения
| Метод | URL | Описание | Тело запроса |
|-------|-----|----------|--------------|
| `GET` | `/measurements` | Получить список всех измерений | — |
| `POST` | `/measurements/add` | Добавить измерение | `{ "value": 23.5, "raining": true, "sensor": { "name": "SensorName" } }` |
| `GET` | `/measurements/rainyDaysCount` | Получить количество дождливых дней | — |

---

## 🗄 Структура БД

**Таблица Sensor**
- `id` — PK
- `name` — уникальное имя сенсора

**Таблица Measurement**
- `id` — PK
- `value` — температура (-100…100)
- `raining` — флаг наличия дождя
- `created_at` — дата добавления измерения в БД
- `sensor_id` — FK на Sensor

---

## 🚀 Клиентское Java-приложение для взаимодействия [RestConsumerAppWeather](https://github.com/Volodyanoy/RestConsumerAppWeather)


