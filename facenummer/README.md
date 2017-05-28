# How to run

1. Vist [node.js](https://nodejs.org/en/) and install the latest version.

2. Open terminal.

3. Run 
        
        npm install -g cordova ionic 

for installing *ionic* and *cordova*. (linux and mac users need to add **sudo**).

4. Connect your device to computer with USB.

5. Use terminal and go to project directory *facenummer*.

6. Run

        npm install

7. Run the following command according to platform:
            
        ionic cordova run ios --device

        ionic cordova build andriod
        ionic cordova run andriod --device

        ionic cordova build browser
        ionic cordova run browser

8. App credentials     
                
                username = user
                password = password

## Note
For ios platform, you need to install the app after every 6 days because of some certificates. 