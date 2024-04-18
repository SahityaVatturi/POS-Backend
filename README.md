### POS backend for a Store

The purpose of this project is to cover:

    - can do billing at Store
    - Manage orders (Sales)
    - Manage products (Inventory)

### Backend

    - MicroServices
    	- Order Management Service (REST API) - Order DB
    	- Inventory Management Service (REST API) - Inventory

    - Kafka Calling: (Restore the quantity for the cancled order)
    	- Producers
    		- OMS Service

    	- Consumer:
    		- IMS service

### Procedural Steps to Start the Application:

    - Backend
    	- Major base endpoints
    		- http://localhost:8081/order - Orders
    		- http://localhost:8082/inventory - Inventory
    		- http://localhost:8082/ - Authentication

    	- cd POS-Backend

    	- Step 1 ( create database in postgres)
    		- run ```psql postgres```
    		- create database inventory
    		- create database orders

    	- Step 2 (start services)
    		- run ```brew start kafka```
    		- run ```brew start zookeeper```
    		- run ```brew start postgres```

    	- Step 3 ( Run MicroServices in intelij)
    		- open each services in intelij
    		- then run the services

    	-Step 4 (Open swagger in browser)
    		- http://localhost:8081/swagger-ui.html (Order Management Service)
    		- http://localhost:8082/swagger-ui.html (Inventory Management Service)

    	-Step 5 (Add mock datas)
    		- mock data for inventory in /mockdata/inventory.json
    		- copy the data from json file and paste it in the swagger ui post endpoint (/inventory/add/all)

    		- add Mock user
    			- http://localhost:8082/register (Post method)
    			  - ```{"username": "admin" ,"password": "admin"}```
