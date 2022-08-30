# inventory

A Clojure test of web service using reitit, http-kit and an atom as database.

## Usage

Clone the project.
```
git clone https://github.com/edurevsky/clojure-inventory.git
```
Go to the project's directory
```
cd clojure-inventory
```
Run with leiningen
```
lein run
```

The server will listen at port 8888

## Routes
- If a request or response doesn't follow the expected
schema, the server will give a json response with a Clojure schema.core error
### /inventory
Get Mapping:
```json lines
// returns
{
  "inventory-items": 0,
  "inventory-value": 0
}
```
### /items
Post Mapping:
```json lines
// Receives
{
  "name": "xpto",
  "price": 2.99,
  "quantity": 2
}
// Returns 201
{
  "id": "9ad71e6b-3c85-4176-bd26-f82957473ccf",
  "name": "xpto",
  "quantity": 234,
  "price": 2.99,
  "total-price": 5.98
}
```
### /items/:id
Get Mapping:
```json lines
// /items/9ad71e6b-3c85-4176-bd26-f82957473ccf
// Returns 200 or 404 if the id is not present
// in the db
{
  "id": "9ad71e6b-3c85-4176-bd26-f82957473ccf",
  "name": "xpto",
  "quantity": 2,
  "price": 2.99,
  "total-price": 5.98
}
```
Put Mapping:
```json lines
// /items/9ad71e6b-3c85-4176-bd26-f82957473ccf
// Receives
{
  "name": "xpto2",
  "price": 3.99
}
// Returns 200 or 404 if the id is not present
// in the db
{
  "id": "9ad71e6b-3c85-4176-bd26-f82957473ccf",
  "name": "xpto2",
  "quantity": 2,
  "price": 3.99,
  "total-price": 7.98
}
```
Delete Mapping:
```json lines
// /items/9ad71e6b-3c85-4176-bd26-f82957473ccf

// Returns 204 on successful delete or 404 
// if the id is not present in the db
```
### /purchase
Put Mapping:
```json lines
// Receives
{
  "item-id": "9ad71e6b-3c85-4176-bd26-f82957473ccf",
  "amount": 2
}
// Returns 204 or 404 if the id is not present
// in the db
```
