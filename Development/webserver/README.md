# WebServer

### How to run it?
1. Have nodeJS installed
 - Install if not
2. Goto webserver location with command line
3. run command: 'npm install'
4. run command: 'node server'
5. enjoy

That is all that is needed to run the webserver, if error occurs make sure that nothing is running on port 80.

## Now for the server part!
This is what the API has to look like!!

| meaning | url | type | body | response code + {response},(meaning)   |
| :-- | :---- | :----- | :----- | :-------- |
| Authorise clients | /login | POST | <ul><li>company</li><li>password</li></ul> | <ul><li>404 : (no auth)</li><li>203 : (user auth)</li><li>200 : (admin auth)</li></ul> |
| Get PN | /user | POST | image | <ul><li>200 : {link}</li><li>>200: {errorMSG}</li></ul> |
| Get Users | /admin | GET | <ul><li>index</li><li>limit</li></ul> | <ul><li>200 : {users}</li><li>404: (no users)</li><li>403 : unauthorized</li></ul> |
| Update user | /admin | PUT | <ul><li>id</li><li>pn</li><li>image</li></ul> | <ul><li>200 : (user updated)</li><li>400 : (error)</li><li>403 : unauthorized</li></ul> |
| Create user | /admin | POST | <ul><li>id</li><li>pn</li></ul> | <ul><li>203 : (created)</li></li><li>400 : (error)</li><li>403 : unauthorized</li></ul> |
| Remove user | /admin | DELETE | id | <ul><li>200 : (user removed)</li><li>400 : (error)</li><li>403 : unauthorized</li></ul> |

Actual API at the moment: (Error codes are only some shown)

| meaning | url | type | Request param (url) | response code + {response},(meaning)   |
| :-- | :---- | :----- | :----- | :-------- |
| Get PN | /user | POST | file={ImageInBase64} | <ul><li>200 : {String (link)}</li><li>>200: {errorMSG}</li></ul> |
| Get Users | /admin | GET | <ul><li>page=1</li><li>size=20</li></ul> | <ul><li>200 : {List of UserEntity}</li><li>404: (no users)</li><li>403 : unauthorized</li></ul> |
| Get User | /admin/{id} | GET |  | <ul><li>200 : {UserEntity}</li><li>404: (no such user)</li><li>403 : unauthorized</li></ul> |
| Update user | /admin{id} | PUT | <ul><li>personalNumbe=199828115973r</li><li>file={ImageInBase64}</li></ul> | <ul><li>200 : {UserEntity}</li><li>400 : (error)</li><li>403 : unauthorized</li></ul> |
| Create user | /admin | POST | <ul><li>personalNumber</li></ul> | <ul><li>203 : {UserEntity}</li></li><li>400 : (error)</li><li>403 : unauthorized</li></ul> |
| Remove user | /admin{id} | DELETE | | <ul><li>200 : (user removed)</li><li>400 : (error)</li><li>403 : unauthorized</li></ul> |

UserEntity = {
 id : int
 photoLink : String
 personalNumber : String
}