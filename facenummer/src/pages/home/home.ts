import { Component, Injectable } from '@angular/core';
import { NavController, AlertController } from 'ionic-angular';
import { Camera } from '@ionic-native/camera';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})

@Injectable()
export class HomePage {

  private isLoggedIn: boolean = false;
  private username: string = "";
  private password: string = "";
  private isWaitingForResponse: boolean = false;

  constructor(private http: Http, private camera: Camera, private alertCtrl: AlertController, public navCtrl: NavController) { }

  logIn(): void {
    setTimeout(() => {
      if (this.password == "password" && this.username == "user")
        this.isLoggedIn = true;
      else this.presentAlert('Log in failed!', 'Please try again.');
      this.isWaitingForResponse = false;
    }, 300);
    this.isWaitingForResponse = true;
  }

  logOut(): void {
    setTimeout(() => {
      this.username = "";
      this.password = "";
      this.isLoggedIn = false;
      this.isWaitingForResponse = false;
    }, 300);
    this.isWaitingForResponse = true;
  }

  getPN(): void {
    let alert = this.alertCtrl.create();
    alert.setTitle('How would you like to upload the photo?');
    alert.addInput({ type: 'radio', label: 'Use camera', value: 'camera', checked: true });
    alert.addInput({ type: 'radio', label: 'Use gallery', value: 'gallery' });
    alert.addButton('Cancel');
    alert.addButton({
      text: 'Okay',
      handler: data => {
        this.isWaitingForResponse = true;
        this.camera.getPicture({
          quality: 100,
          destinationType: this.camera.DestinationType.DATA_URL,
          mediaType: this.camera.MediaType.PICTURE,
          sourceType: data == "camera" ? this.camera.PictureSourceType.CAMERA : this.camera.PictureSourceType.PHOTOLIBRARY
        }).
          then(imageData => {
            this.http.post("https://lnu-face.herokuapp.com/api/v1/user/", 'file=' + encodeURIComponent(imageData), {
              headers: this.makeHeaders()
            }).
              map((response: Response) => response.json()).subscribe(
              success => {
                console.log(success);
                this.presentAlert('Received!', 'Personal number (YYYYDDMMXXX): ' + success);
                this.isWaitingForResponse = false;
              },
              err => {
                console.log(err);
                this.presentAlert('No result found!', 'Please try again.');
                this.isWaitingForResponse = false;
              });
          },
          err => {
            this.isWaitingForResponse = false;;
          });
      }
    });
    alert.present();
  }

  makeHeaders(): Headers {
    let headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('Authorization', 'Basic ' + btoa(this.username + ':' + this.password));
    return headers;
  }

  presentAlert(title: string, subTitle: string): void {
    let alert = this.alertCtrl.create({
      title: title, subTitle: subTitle, buttons: ['Ok']
    });
    alert.present();
  }
}