## About the App
wishlist app is a program to store product that we want to buy. The product is retrieved from another [rest service](https://dummyjson.com/docs/products).

## How to Run
1. run docker to run database
2. run app via maven ```mvn spring-boot:run```

## Endpoints
### Get Categories  
- Retrieve all categories data. The data can be used to retrieve the product by category
- endpoint : GET ```localhost:8080/categories```

### Get Products
- Retrieve product data. We can filter by category or by name of the product
- endpoint : GET ```localhost:8080/products?category=CATEGORY_NAME``` or ```localhost:8080/products?name=PRODUCT_NAME``` 


### Add Wishlist
- Add product to wishlist
- endpoint : POST ```localhost:8080/wishlist/add```
- example body message
```json
{
    "productId": 99
}
```

### Done Wishlist
- Set status wishlist into `Done`
- endpoint : POST ```localhost:8080/wishlist/done```
- example body message
```json
{
    "wishlistId": 99
}
```

### Delete Wishlist
- Set status wishlist into `Deleted`
- endpoint : POST ```localhost:8080/wishlist/delete```
- example body message
```json
{
    "wishlistId": 99
}
```

### Get Wishlist
- Retrieve wishlist data
- endpoint : 
  - POST ```localhost:8080/wishlist?category=CATEGORY_NAME```
  - POST ```localhost:8080/wishlist?name=PRODUCT_NAME``` 
  - POST ```localhost:8080/wishlist?status=STATUS_WISHLIST```
  - we can limit the query by adding param `limit` and `page`

### Get Top Nth product price per category
- Retrieve product data which was the most expensive one
- endpoint : POST ```localhost:8080/top-nth-product-per-category?n=5```
