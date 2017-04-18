# Architecture Design
The main sublayers of the Face Recognition System (FRS) is server and client, server is then divided into user, admin, authentication and the face recognition. Other components that is used is a database as well as an webserver which will provide the UI for the client.

#### Component list
- face-recognition
- authentication
- admin
- user
- database
- webserver  

#### Component details
Now we will discuss why we need the different components, how they will be used and what they will require.
##### 1. face-recognition
The face-recognition is the main component of this system (FRS), this needs to be as independent as possible in order of our system to be adaptive to changes (i.e. face-recognition algorithm may be replaced in the future for a better one). The required interface that is needed is a picture and a database the provided interface is a Swedish social security number. It is the user component that will use the face-recognition component.

##### 2. authentication
The authentication component is required for security and divides the system based on whether the end-user is a normal user or a admin. The required interface for authentication is a database, the provided interface is a login functionality that returns an integer 0 - no match, 1 - user and 2 - admin.

##### 3. admin
The admin component is a core component that will hold all the admin responsibilities which is management of users. The required interface is a database and the provided interface is RESTful methods.
- GET    : get the user information by id or all.
- POST   : create a new user
- PUT    : updates an existing user by id
- DELETE : removes an existing user by id

##### 4. user
The user component is also a core component that holds responsible for providing information to the end-user. This component with the use of face-recognition component returns a Swedish social security number from an uploaded picture. The required interface is the face-recognitions and the provided interface is "*uploading a picture*"

##### 5. database
This component is a remote component that our system highly depends on, as mentioned before it will be a MYSQL-database and only use one database will be in use in this system. The provided interface is the common functionalities such as connecting and querying commands.

##### 6. webserver
The last component of the system is the actual webserver, this will not have very low coupling and great cohesion as this will be the glue code to combine all the other components. The required interfaces is authentication, user and admin. The provided interface is the *user interface* (web pages) and RESTful API calls.
- admin RESTful
- post : upload picture for user component




// add requirements that proves the different components
// mention some requirements to hold why this is like so..
