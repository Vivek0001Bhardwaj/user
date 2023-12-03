
## Explore Rest APIs

The app defines following CRUD APIs.

### Auth

| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/auth/signup | Sign up | [JSON](#signup) |
| POST   | /api/auth/signin | Log in | [JSON](#signin) |

### Users

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/users/me | Get logged in user profile | |
| GET    | /api/users/{username}/profile | Get user profile by username | |
| GET    | /api/users/checkUsernameAvailability | Check if username is available to register | |
| GET    | /api/users/checkEmailAvailability | Check if email is available to register | |
| POST   | /api/users | Add user (Only for admins) | [JSON](#usercreate) |
| PUT    | /api/users/{username} | Update user (If profile belongs to logged in user or logged in user is admin) | [JSON](#userupdate) |
| DELETE | /api/users/{username} | Delete user (For logged in user or admin) | |
| PUT    | /api/users/{username}/giveAdmin | Give admin role to user (only for admins) | |
| PUT    | /api/users/{username}/TakeAdmin | Take admin role from user (only for admins) | |
| PUT    | /api/users/setOrUpdateInfo | Update user profile (If profile belongs to logged in user or logged in user is admin) | [JSON](#userinfoupdate) |

## Sample Valid JSON Request Bodys

##### <a id="signup">Sign Up -> /api/auth/signup</a>
```json
{
	"firstName": "Leanne",
	"lastName": "Graham",
	"username": "leanne",
	"password": "password",
	"email": "leanne.graham@gmail.com"
}
```

##### <a id="signin">Log In -> /api/auth/signin</a>
```json
{
	"usernameOrEmail": "leanne",
	"password": "password"
}
```

##### <a id="usercreate">Create User -> /api/users</a>
```json
{
  "firstName": "Ervin",
  "lastName": "Howell",
  "username": "ervin",
  "password": "password",
  "address": {
    "street": "Victor Plains",
    "suite": "Suite 879",
    "city": "Wisokyburgh",
    "zipcode": "90566-7771",
    "geo": {
      "lat": "-43.9509",
      "lng": "-34.4618"
    }
  },
  "email": "ervin.howell@gmail.com",
  "phone": "010-692-6593 x09125"
}
```

##### <a id="userupdate">Update User -> /api/users/{username}</a>
```json
{
  "firstName": "Ervin",
  "lastName": "Howell",
  "username": "ervin",
  "password": "updatedpassword",
  "email": "ervin.howell@gmail.com",
  "phone": "010-692-6593 x09125",
  "address": {
    "street": "Victor Plains",
    "suite": "Suite 879",
    "city": "Wisokyburgh",
    "zipcode": "90566-7771",
    "geo": {
      "lat": "-43.9509",
      "lng": "-34.4618"
    }
  }
}
```

##### <a id="userinfoupdate">Update User Profile -> /api/users/setOrUpdateInfo</a>
```json
{
  "street": "Douglas Extension",
  "suite": "Suite 847",
  "city": "McKenziehaven",
  "zipcode": "59590-4157",
  "phone": "1-463-123-4447",
  "lat": "-68.6102",
  "lng": "-47.0653"
}
```
