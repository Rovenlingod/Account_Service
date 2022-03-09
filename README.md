# Account_Service
API for sending payrolls to the employee's account on the corporate website

## REST API

|                          | Anonymous| User| Accountant| Administrator| Auditor|
| :----------------------- | :------- | :---|:----------|:-------------|:-------|
| **POST** api/auth/signup |     +    |  +  |     +     |       +      |    +   |
| **POST** api/auth/changepass |     -    |  +  |     +     |       +      |    -   |
| **GET** api/empl/payment |     -    |  +  |     +     |       -      |    -   |
| **POST** api/acct/payments |     -    |  -  |     +     |       -      |    -   |
| **PUT** api/acct/payments |     -    |  -  |     +     |       -      |    -   |
| **GET** api/admin/user |     -    |  -  |     -     |       +      |    -   |
| **DELETE** api/admin/user |     -    |  -  |     -     |       +      |    -   |
| **PUT** api/admin/user/role |     -    |  -  |     -     |       +      |    -   |
| **PUT** api/admin/user/access |     -    |  -  |     -     |       +      |    -   |
| **GET** api/security/events |     -    |  -  |     -     |       -      |    +   |

#### Allows the user to register on the service

```http
  POST api/auth/signup
```
Required body:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | **Required**. User name |
| `lastname` | `string` | **Required**. User lastname |
| `email` | `string` | **Required**. **Must end with @acme.com** User email |
| `password` | `string` | **Required**. **MIN size 12 characters** User password |

Response on success:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | Created user name |
| `lastname` | `string` | Created user name |
| `email` | `string` | Created user email |


#### Changes a user password

```http
  POST api/auth/changepass
```

Required body:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `new_password` | `string` | **Required**. **MIN size 12 characters** New user password |

Response on success:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `email` | `string` | User name |
| `status` | `string` | Value "The password has been updated successfully" |

#### Gives access to the employee's payrolls

```http
  GET api/empl/payment
```
Possible parameters:
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `period` | `string` | **Must fit the template 'MM-yyyy'** Payment period |

Response on success with `period`:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | User name |
| `lastname` | `string` | User lastname |
| `period` | `string` | Name of month-YYYY |
| `salary` | `string` | X dollar(s) Y cent(s) |

Response on success without `period`:
List of all payments for authenticated user:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | User name |
| `lastname` | `string` | User lastname |
| `period` | `string` | Name of month-YYYY |
| `salary` | `string` | X dollar(s) Y cent(s) |

#### Uploads payrolls

```http
  POST api/acct/payments
```

Required body: JSON array of Payments:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `employee` | `string` | **Required**. User email |
| `period` | `string` | **Required**. **Must fit the template 'MM-yyyy'**. Payment period |
| `salary` | `long` | **Required**. **Must be non-negative** Salary in cents |

Additional info: Employee period pair must be unique. Operation is transactional.

Response on success:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `status` | `string` | Value "Added successfully!" |

#### Updates payment information

```http
  PUT api/acct/payments
```

Required body: List of Payments:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `employee` | `string` | **Required**. User email |
| `period` | `string` | **Required**. **Must fit the template 'MM-yyyy'**. Payment period |
| `salary` | `long` | **Required**. **Must be non-negative** Salary in cents |

Additional info: Employee period pair must be unique. Operation is transactional.

Response on success:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `status` | `string` | Value "Updated successfully!" |

#### Obtains information about all users; the information should not be sensitive.

```http
  GET api/admin/user
```

Response on success: JSON array of users
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `long` | User id |
| `name` | `string` | User name |
| `lastname` | `string` | User lastname |
| `email` | `string` | User email |
| `roles` | `array of strings` | User roles |

#### Deletes user

```http
  DELETE api/admin/user
```

Possible parameters:
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `email` | `string` | User email |


Response on success:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `user` | `string` | User email |
| `status` | `string` | Value "Deleted successfully!" |

#### Sets the roles

```http
  PUT api/admin/user/role
```

Required body:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `user` | `string` | **Required**. User email |
| `roke` | `string` | User role |
| `operation` | `string` | Possible values: [GRANT, REMOVE] |


Response on success:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `long` | User id |
| `name` | `string` | User name |
| `lastname` | `string` | User lastname |
| `email` | `string` | User email |
| `roles` | `array of strings` | User roles |

#### Locks/unlocks users

```http
  PUT api/admin/user/access
```
Required body:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `user` | `string` | **Required**. User email |
| `operation` | `string` | Possible values: [LOCK, UNLOCK] |

Response on success:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `status` | `string` | Value "User <username> <[locked, unlocked]>!" |

#### Responds with an array of objects representing the security events of the service sorted in ascending order by ID

```http
  GET api/security/events
```
Response on success:
| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `date` | `string` | Date |
| `action` | `string` | Event name |
| `subject` | `string` | The user who performed the action |
| `object` | `string` | The object on which the action was performed |
| `path` | `string` | api |

Possible information security events:
| Description | Event name |
| :-------- | :------- |
|A user has been successfully registered|	CREATE_USER |
|A user has changed the password successfully|		CHANGE_PASSWORD |
|A user is trying to access a resource without access rights|	ACCESS_DENIED |
|Failed authentication|	LOGIN_FAILED |
|A role is granted to a user|	GRANT_ROLE |
|A role has been revoked|	REMOVE_ROLE |
|The Administrator has locked the user|	LOCK_USER |
|The Administrator has unlocked a user|	UNLOCK_USER |
|The Administrator has deleted a user|DELETE_USER |
|A user has been blocked on suspicion of a brute force attack|BRUTE_FORCE|
