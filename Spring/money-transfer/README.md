# Money transfer

### Запуск  
Для запуска нужно собрать проект с помощью Gradle в bootJar

1) без Docker
```
java -jar {bootJar}
```
2) c Docker
```
docker-compose up
```
Порт 5500
### Форма для тестов  
[GITHUB](https://github.com/serp-ya/card-transfer)  
[FRONT](https://serp-ya.github.io/card-transfer/)

### Примеры запросов

1) Transfer
```
localhost:5500/transfer
request body:
{
  "cardFromNumber": "string",
  "cardFromValidTill": "string",
  "cardFromCVV": "string",
  "cardToNumber": "string",
  "amount": {
    "value": 0,
    "currency": "string"
  }
}

response body:
{
  "operationId": "string"
}
```
2) ConfirmOperation
```
localhost:5500/confirmOperation
request body:
{
  "operationId": "string",
  "code": "string"
}

response body:
{
  "operationId": "string"
}
```

При возникновении ошибок отправляется id и сообщение ошибки
```
{
  "message": "string",
  "id": 0
}
```
