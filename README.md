Domain model:

    ● Product

    ● Category

    ● Price


Relations:

    ● Product can have one or many Categories.

    ● Categories can have 0 or many Products.

    ● Product must have one or many Prices.

    ● Category can have sub category or super category.


Implement a backoffice application which support:

    ● Pagination

    ● Search:

        o Product

            ▪ By code (unique)

            ▪ By name

            ▪ By price

            ▪ By category code

        o Category

            ▪ By code (unique)

            ▪ By name

        o Price

            ▪ By currency

            ▪ By product

            ▪ By price range (from 100$ to 200$)

    ● CRUD operations for all entities (Create, read, update, delete)

    ● Basic auth for REST endpoints


Application should expose REST API to perform operations above (JSON format). All endpoints should be secured. Provide tests for verifying functionality for all application layers from DAO to controllers.


Technologies to use:

    ● Spring Boot

    ● Spring Data + Hibernate

    ● Spring Security

    ● Spring MVC

    ● Spring Test

    ● PostgreSQL
