# Complete E-Commerce Backend System

A full-featured e-commerce backend built with Spring Boot, Spring Security (JWT), Spring Data JPA, and PostgreSQL.

## Architecture & Features

This system follows a layered architecture (Controller, Service, Repository, Entity, DTO, Mapper) and implements the following components:
1. **Product Catalog**: Manage items, pricing, and description.
2. **Category Management**: Group products by categories.
3. **User Management**: Support registration, logins, profiles, and custom addresses.
4. **Authentication & Authorization**: JWT token authorization, BCrypt password hashing, and role-based access.
5. **Shopping Cart**: Manage items, quantities, and totals.
6. **Order Management**: Order placement, confirmation, and status tracking.
7. **Rate Limiting**: Custom AOP aspect intercepting endpoints to enforce request thresholds.
8. **Logging & Auditing**: Performance metrics tracking and Aspect-Oriented AOP logging.

---

## Technical Stack
- **Framework**: Spring Boot 3.5.x
- **Language**: Java 17
- **Database**: PostgreSQL
- **Security**: Spring Security & JSON Web Tokens (JJWT)
- **Object Mapping**: ModelMapper
- **API Documentation**: Springdoc OpenAPI v3 (Swagger)
- **Containerization**: Docker & Docker Compose

---

## Configuration & Environment Variables

The application is configured using properties. The following environment variables must be defined before starting the application:

| Environment Variable | Description | Default / Example                                                
|--------------------------------------------------------------------------
| `DB_URL` | PostgreSQL JDBC Connection URL  `jdbc:postgresql://localhost:5432/ecommerce`                     
| `USERNAME` | Database username  `postgres`                                                         
| `PASS` | Database password  `password`                                                         
| `DB` | Database name  `ecommerce`                                                        
| `PORT` | Embedded Tomcat Server Port  `8081`                                                             
| `TOKEN` | Base64-encoded Secret Key for JWT (min 256 bits)  `5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437` 
| `EXPIRY` | JWT Validity duration in milliseconds  `86400000` (24 Hours)                                              

---

Require : Bearer Token : eyJhbGciOiJIUzM4NCJ9.eyJyb2xlcyI6WyJBRE1JTiIsIlVTRVIiXSwiYXV0aG9yaXRpZXMiOlsiQURNSU4iLCJVU0VSIl0sInN1YiI6ImFiaGlAbWFpbC5jb20iLCJpYXQiOjE3ODA5MjgyODAsImV4cCI6MTc4MTAxNDY4MH0.gv5DrC-y6XcmDyRlpKyy7YH0KqyH58tWTYseeMzt3yrGZqnIQr_8tgxQr8DbnCK2

## API Endpoints List

### Auth APIs
- `POST /api/auth/register` - Register a new user
{
    "name":"abhi",
    "email":"xxx@mail.com",
    "password":"xxxx",
    "contactNumber":"3809352515",
    "address":{
    "city":"uzhorod",
    "state":"zakarpatiya",
    "country":"xxxx",
    "postalCode":"88000"
    },
    "roles":["ADMIN","USER"]
}
Response : {
    "id": 1,
    "name": "abhi",
    "email": "xxx@mail.com",
    "contactNumber": "3809352515",
    "address": {
        "city": "uzhorod",
        "state": "zakarpatiya",
        "country": "ukraine",
        "postalCode": "88000"
    },
    "roles": [
        "ADMIN",
        "USER"
    ]
}

- `POST /api/auth/login` - Authenticate and get JWT Access Token
{
    "email":"abhi@mail.com",
    "password": "abhi123"
}
reponse :
eyJhbGciOiJIUzM4NCJ9.eyJyb2xlcyI6WyJBRE1JTiIsIlVTRVIiXSwiYXV0aG9yaXRpZXMiOlsiQURNSU4iLCJVU0VSIl0sInN1YiI6ImFiaGlAbWFpbC5jb20iLCJpYXQiOjE3ODA5MjgyODAsImV4cCI6MTc4MTAxNDY4MH0.gv5DrC-y6XcmDyRlpKyy7YH0KqyH58tWTYseeMzt3yrGZqnIQr_8tgxQr8DbnCK2


### User APIs
- `GET /api/users/profile` - Retrieve authenticated user profile details
Response : {
    "id": 1,
    "name": "abhi",
    "email": "xxx@mail.com",
    "contactNumber": "3809352515",
    "address": {
        "city": "uzhorod",
        "state": "zakarpatiya",
        "country": "ukraine",
        "postalCode": "88000"
    },
    "roles": [
        "ADMIN",
        "USER"
    ]
}


- `PUT /api/users/update/1` - Update authenticated user profile details

### Category APIs
- `GET /api/categories` - Fetch all categories (paginated)

- `GET /api/categories/{id}` - Fetch a category by ID
Response : {
    "id": 3,
    "name": "Clothing",
    "description": "Apparel and fashion items"
}
- `POST /api/categories` - Create a new category (Admin only)
reponse {
    "id": 4,
    "name": "Books",
    "description": "Printed and digital reading materials"
}
- `PUT /api/categories/{id}` - Update a category (Admin only)
Response : {
    "id": 3,
    "name": "Wood",
    "description": "Furnitures"
}

- `DELETE /api/categories/{id}` - Delete a category (Admin only)

### Product APIs
- `GET /api/products` - List all products (paginated)
Response:  [
    {
        "id": 1,
        "name": "oculuse2",
        "description": "mate",
        "price": 1500.25,
        "stockQuantity": 27,
        "category": {
            "name": "Electronics",
            "description": "Gadgets and devices"
        }
    },
    {
        "id": 2,
        "name": "Samsung Galaxy S25",
        "description": "Latest flagship smartphone with advanced camera system",
        "price": 1299.99,
        "stockQuantity": 50,
        "category": {
            "name": "Electronics",
            "description": "Gadgets and devices"
        }
    },
    {
- `GET /api/products/{id}` - Retrieve details of a product
- `POST /api/products` - Create a product (Admin only)
Response : // {
//     "name": "oculuse2",
//     "description": "mate",
//     "price": 1500.25,
//     "stockQuantity": 27,
//     "category": "Electronics"
// }
- `PUT /api/products/{id}` - Update product details (Admin only)
- `DELETE /api/products/{id}` - Delete a product (Admin only)
- `POST /api/products/search` - Search and filter products dynamically

### Cart APIs
- `GET /api/cartitems/save` - ADD Products into CartItems {
    "productId": [1, 2]
}
Respones : [
    {
        "responseDtoLists": [
            {
                "id": 1,
                "productId": 1,
                "productName": "oculuse2",
                "price": 1500.25,
                "quantity": 0,
                "total": null // its null after succesfully adding into cart total will be included
            }
        ]
    },
    {
        "responseDtoLists": [
            {
                "id": 2,
                "productId": 2,
                "productName": "Samsung Galaxy S25",
                "price": 1299.99,
                "quantity": 0,
                "total": null // its null after succesfully adding into cart total will be included
            }
        ]
    }
]



- `POST /api/cart/add` - Add products to cart 
Request : {
    "userEmail":"abhi@mail.com",
    "cartRequestDtoLists":[
    {
    "cartItemId":1,
    "productId":4,
    "quantity":5
},
    {
    "cartItemId":2,
    "productId":6,
    "quantity":2
}
]
} Response : {
    "userEmail": "abhi@mail.com",
    "cartID": 1,
    "cartItems": [
        {
            "id": 1,
            "productId": 1,
            "productName": "oculuse2",
            "price": 1500.25,
            "quantity": 5,
            "total": 7501.25
        },
        {
            "id": 2,
            "productId": 2,
            "productName": "Samsung Galaxy S25",
            "price": 1299.99,
            "quantity": 2,
            "total": 2599.98
        }
    ],
    "totalQuantity": 7,
    "totalPrice": 10101.23
}
- `GET /api/cart/1` - View authenticated user's cart
{
    "userEmail": "abhi@mail.com",
    "cartID": 1,
    "cartItems": [
        {
            "id": 1,
            "productId": 1,
            "productName": "oculuse2",
            "price": 1500.25,
            "quantity": 5,
            "total": 7501.25
        },
        {
            "id": 2,
            "productId": 2,
            "productName": "Samsung Galaxy S25",
            "price": 1299.99,
            "quantity": 2,
            "total": 2599.98
        }
    ],
    "totalQuantity": 7,
    "totalPrice": 10101.23
}

- `PUT /api/cart/update` - Update item quantity in cart
- `DELETE /api/cart/remove/{id}` - Remove an item from the cart
- `DELETE /api/cart/clear` - Clear all cart items

### Order APIs
- `POST /api/orders` - Place a new order
{
    "cartId":1,
    "email":"abhi@mail.com"
} 
Response : 
{
    "status": "ORDER_PENDING",
    "useEmail": "abhi@mail.com",
    "orderCreatedAt": "2026-06-08T14:41:36.363736",
    "orderID": 1,
    "orderItemsIds": [
        1,
        2
    ],
    "productIds": [
        1,
        2
    ],
    "totalQuantity": 7,
    "userEmail": "abhi@mail.com",
    "totalPrice": 10101.23
}
- `GET /api/orders` - Retrieve list of all orders
    {
        "status": "ORDER_PENDING",
        "useEmail": "abhi@mail.com",
        "orderCreatedAt": "2026-06-08T14:41:36.363736",
        "orderID": 1,
    }
- `GET /api/orders/{id}` - View order details
- `GET /api/orders/confirm` - Confirm order processing
Request : 
{
    "email":"xxxx@mail.com"
}
Response : 
CONFIRM_ORDER


### Admin APIs
- `GET /api/admin/users` - View all registered users (Admin only)
- `GET /api/admin/orders` - View all customer orders (Admin only)
- `GET /api/admin/reports` - Generate analytics and sales reports (Admin only)

---

## Local Development Setup

### Running with Maven Wrapper
Configure the environment variables in your terminal, then run:
```bash
# Compile and package
./mvnw clean package -DskipTests

# Run the Spring Boot application
java -jar target/ecommerce-backend-0.0.1-SNAPSHOT.jar
```

---

## Docker Deployment

### Prerequisites
- Docker Installed
- Docker Compose Installed

### Commands
Build and launch all services (PostgreSQL, Application Backend, pgAdmin):
```bash
# Start all containers in background
docker-compose up -d --build

# Stop all containers
docker-compose down

# View application logs
docker-logs -f ecommerce-backend
```

The database container persists data to a Docker volume `postgres_data`.

---

## API Documentation (Swagger)
Once the server is running, you can access the interactive API Swagger docs at:
`http://localhost:8081/swagger-ui/index.html`

The docs support JWT Bearer authorization:
1. Obtain token by invoking `/api/auth/login`.
2. Click **Authorize** button in Swagger.
3. Paste token (e.g. `Bearer <eyJhbGciOiJIUzM4NCJ9.eyJyb2xlcyI6WyJBRE1JTiIsIlVTRVIiXSwiYXV0aG9yaXRpZXMiOlsiQURNSU4iLCJVU0VSIl0sInN1YiI6ImFiaGlAbWFpbC5jb20iLCJpYXQiOjE3ODA5MTc4NzAsImV4cCI6MTc4MTAwNDI3MH0.lvEbT-LajmRU_z9hIA24W88xUa6dQ-G8hmi9yKmTDCtqk1kQ793HcUoPW6BygboE>`) and authorize requests.
