# Architecture
The main components of Face Recognition System are client and server. The *User Application Module* (**UAM**) and *Admin Application Module* (**AAM**) makes up the client components, for server component we have the *User Repository Module* (**URM**) and *Admin Repository Module* (**ARM**) which is two separate components we will call these user server and admin server, same goes for the client components (user and admin client).

The reason we choose two different server is that admin server should be up an maintainable while the user server is down, this is also a way to make sure that the system is secure and easy to maintain.

##### Component list
- user-client
- user-server
- admin-client
- admin-server

#### Client components
While the client components differ the two are quite alike and therefor will sometimes be seen as one. The components has all the front end technology such as *HTML, CSS and JavaScript*, communicates with the different servers and this is done via http request. The client component has some functionalities that is necessary i.e.
###### client functionalities
- Login system
- Get social security number
  1. uploading picture
  2. retrieve social security number
- admin management (admin-client)

###### Based on the requirements 3.1.X
The client component is responsible for login of users, sending a picture to the URM (server), also providing the end user with a Swedish social security number.

#### Server components
The server components needs to provide good API for the client components, these has to have good cohesion and low coupling in order to make them independent. The functionalities that is required for the servers is following:
###### server functionalities
- authenticate users/admins
- upload picture and return social security number
- providing admin with RESTful web service

###### Based on the requirements 3.2.X and 3.3.X
As for the server components they should only work with authenticated users or admins based on which server. As for the user server, the requirement 3.2.4 states that from a given photo a PN (Swedish social security number) will be returned. The admin server holds responsibility of managing users i.e. add, update delete and view users.

All components have an important common requirement and that's "decrypt and encrypt all incoming and outgoing messages".

/*choose a Architecture pattern - explain*/
