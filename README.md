# Uni_VT17_FaceRecognition
Software Design course. Development of an application matching photos to personal numbers

## Time management

| Date       | Name/s  | Time | Activity |
| ------------- | -------- | -------- |  :--------: |
| -17/03/17 | Jakob | 1h | Write emails/Orga |
| 20/03/17 | All | 3h | First Organisation |
| 20/03/17 | Jakob | 4h | Req. Doc - Overview |
| 20/03/17 | Sarpreet | 30m | Made use case structure |
| 23/03/17 | Sarpreet | 1h | Made 2 use cases |
| 27/03/17 | All | 1h | Meeting with Customer |
| 27/03/17 | Walid | 2h | Making some of the use cases |
| 30/03/17 | All | 3h | Finalize Requirements Doc |
| 04/04/17 | All | 4h | Group meeting |
| 04/04/17 | All | 30min | Meeting Customer |
| 04/04/17 | Jakob | 3h | Design Document Major Issues |
| 06/04/17 | Sarpreet | 2h | Write comparison of external API |
| 14/04/17 | Sarpreet | 2h | Analysis of design patterns |
| 15/04/17 | Sarpreet | 2h | Made high level class diagram of admin module|
| 15/04/17 | Walid | 2h | Domain model |
| 14/04/17 | Sarpreet | 4h | Made, update high level class diagram |
| 19/04/17 | All | 4h | Architecture and Class diagram |
| 21/04/17 | Sarpreet | 3h 30m | Made new class diagram because architecture changed |
| 25/04/17 | Sarpreet | 1h 20m | Updated class diagrams |
| 02/04/17 | Sarpreet | 45m | Updated class diagrams |
| 03/04/17 | Sarpreet | 1h 30m | Updated class diagrams |
| 04/05/17 | Walid | 2h | Designing part on the Sequence diagrams |
| 08/05/17 | Jakob | 2h | Planning & Organizing |
| 08/05/17 | All | 2h | Meeting Impl Challenges |
| 08/05/17 | Sarpreet | 2h 30m | Implementation database |
| 09/05/17 | Sarpreet | 3h | Implementation database |
| 09/05/17 | Walid | 3h | v1 of all Sequence diagrams done and added in the design document |
| 10/05/17 | Sarpreet | 3h 30m | Made class diagrams |
| 11/05/17 | Sarpreet | 2h | Implementatiom database |
| 12/05/17 | Sarpreet | 2h 30m | Implementation and testing database |
| 15/05/17 | Jakob | 2h | Implementation Admin |
| 16/05/17 | Jakob | 3h | Implementation Admin |
| 16/05/17 | Jakob | 2h | Documentation Design Issues |
| 18/05/17 | All | 2h | General meeting with the teacher |
| 18/05/17 | Jakob | 5h | Implementing Face Service |
| 18/05/17 | Sarpreet | 4h | Restructuring the implementation |
| 19/05/17 | Jakob | 6h | Storage and Integration |
| 20/05/17 | Sarpreet | 5h 30m | Restructuring and User implementation |
| 21/05/17 | Sarpreet | 5h | Bug fixes and Utils, Admin, User, FaceRecognition-api modules integration |
| 21/05/17 | Jakob | 2h | Bug fixes |
| 22/05/17 | Jakob | 3h | Deployment and Integration |
| 22/05/17 | Sarpreet | 3h 30m | Implementation and integration of face-library |
| 23/05/17 | Sarpreet | 2h 30m | Implementation and updating class diagrams |
| 24/05/17 | Sarpreet | 3h | Testing Admin and User module |
| 25/05/17 | Sarpreet | 5h | Writing design patterns, making class diagrams for each components and for whole system|
| 26/05/17 | Walid | 2h | Writing the testing on apiary.io |
| 27/05/17 | Sarpreet |3h 30m | Made class diagram for authentication, integration and system. Also read the design document. |
| 28/05/17 | Walid | 3h | Final version of the sequence diagrams (changed made according to the implementation)
# TODO
-----------------------------------

### Class Diagrams
* Classdiagrams connection to implementation instead of controller class?
* Split class diagram into smaller class diagrams (for each component)
* Include nicely in document

### Architecture
* A small text/diagram for client (simple server which serves the pages)

### Sequence Diagrams
* Sequence Diagram => Alternative flow question outside the box, Add User as actor who initializes (maybe instead of Application) , flow needs to go back from where the question went to (e.g. "Operation done" should be "Return PN" from User instead of Database)
* Include nicely in document

### General
* Encryption/HTTPS Section add (How to, Description of security, Implementation challenges (We cant get a certificate..) and limits..)
* Terminology needs to fit in the whole document and be consistent (with tought stuff in lecture!)
* 1.2 Priorities:  
Reliability - System should respond in 5sec when user requests (Requirement), write more specific and how can we tackle this? (theoretically)
Portability - Where in the requirements? Usability too.

### Major Design issues
* Reorder section (languages and frameworks may not be included
* Make sure to reference

## **=> STRONG: All sections MUST reference requirements!!**

### Done-table:

| Section | Done |
| :-------------: |:-------------:|
| **ClassDiagrams** |  |
| Connection | [Done] |
| Split | [Done] |
| Include | [Done] |
| Reference | [HOW?] |
| Authentication | [Done] |
| **Architecture** |  |
| Text for Client | [] |
| Reference | [] |
| **Sequence Diagrams** |  |
| Update | [] |
| Include | [] |
| Reference | [] |
| **General** |  |
| Encryption | [] |
| Priorities section | [] |
| Terminology | [] |
| Reference | [] |
| **Major Design Issues** | |
| Reorder | [] |
| Reference | [] |
| Make sure to follow tree order (design space - mauro lecture) | [] |
| Sequence Diagrams |
