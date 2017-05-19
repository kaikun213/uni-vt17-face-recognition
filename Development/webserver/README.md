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
