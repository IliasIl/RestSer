Запуск приложения: 
1. Создать БД. В проекте использовалась БД PostgreSQL со следующими параметрами: 
   url=jdbc:postgresql://localhost/restser; 
   username=postgres; 
   password=EstEnergy. 
  При использовании отличной от этих параметров ДБ необходимо: 
  * Изменить первые три параметра в ..\main\resources\application.properties;
  * Подключить зависимость в pom.xml (если будет использоваться база отличная от PostgreSQL).
2. ---Запуск из IDE. Класс входа com.RestSer.RestSerApplication. 
   ---Запуск из консоли: перейти в каталог, где лежит файл pom.xml; набрать mvn clean package; 
        перейти в каталог target(там лежит файл RestSer-0.0.1-SNAPSHOT.jar); набрать команду java -jar RestSer-0.0.1-SNAPSHOT.jar.
3. Запросы в браузере к web-сервису: перейти в инструменты разработчика => консоль: 
  для регистрации нового клиента: 
fetch(
'/restser/client', 
{
method: 'POST', 
headers: { 'Content-Type': 'application/json'}, 
body: JSON.stringify({ type: 'create', login: 'Ввести имя', password: 'Ввести пароль' })
}
).then(a => a.json().then(b=>console.log(b))) 
  для запроса клиентом своего баланса:
fetch(
'/restser/client', 
{
method: 'POST', 
headers: { 'Content-Type': 'application/json'}, 
body: JSON.stringify({ type: 'get-balance', login: 'Ввести имя', password: 'Ввести пароль' })
}
).then(a => a.json().then(b=>console.log(b))) 
