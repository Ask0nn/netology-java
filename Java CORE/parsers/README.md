# Parsers
Java Core 1.5  
[Main](./src/main/java/com/ask0n/)

## CSV - JSON
[Class](./csv-json/src/main/java/com/ask0n/CSVToJson.java) - класс для работы с csv  
[Data file](./src/main/resources/data.csv) - csv файл  
[Result](./data.json) - результат, json файл 

## XML - JSON
[Class](./xml-json/src/main/java/com/ask0n/XMLToJson.java) - класс для работы с xml  
[Data file](./src/main/resources/data.xml) - xml файл  
[Result](./data2.json) - результат, json файл  

## Read JSON
[Class](.csv-json/src/main/java/com/ask0n/CSVToJson.java) - класс для работы с json  
[Data file](./src/main/resources/data.csv) - json файл  
Результат:  
```
Task :Main.main()
Employee {id=1, firstName='John', lastName='Smith', country='USA', age=25}
Employee {id=2, firstName='Inav', lastName='Petrov', country='RU', age=23}
```

## Tests
[Test #1](./src/test/java/com/ask0n/CSVToJsonTest.java)  
[Test #2](./src/test/java/com/ask0n/XMLToJsonTest.java)  
[Test #3 with hamcrest](./src/test/java/com/ask0n/ReadJsonTest.java)  
