# News App
## Description
The application is a simple CRUD API app that is the backbone of the news feed app.
## Installation
1. Set up the MySQL instance on local machine. Version 8.0 is fine.
2. Install JDK. (version 17 in my case)
3. Set the next environment variables before the start (use `source` command or tools like https://direnv.net/):
   * NEWS_DB_URL
   * NEWS_DB_SCHEMA
   * NEWS_DB_USER
   * NEWS_DB_PASSWORD
4. Use `./mvnw spring-boot:run` from the root of the project.)
5. Flyway going to create the schema automatically.
6. Use.
### Postman Collection
Please, use `News_API.postman_collection.json` in the root of the project to import the POSTMAN collection.
This will help you to understand the API and will help to make HTTP calls faster.

### Points could be considered for improvement (Optional)
1. Make the app Docker friendly
2. Security integration
3. Add KDoc documentation. However, in my point of view good code should 
self-explanatory ;)
4. Logging tool integration. (logging of some income parameters, etc)
5. Mock data generation???