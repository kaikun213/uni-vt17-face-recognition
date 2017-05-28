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
    this.isWaitingForResponse = true;
    setTimeout(() => {
      if (this.username != "user" || this.password != "password") {
        this.presentAlert('Log in failed!', 'Please try again.');
        this.isWaitingForResponse = false;
        return;
      }
      this.isLoggedIn = true;
      this.isWaitingForResponse = false;
    }, 500);
  }

  logOut(): void {
    this.isWaitingForResponse = true;
    setTimeout(() => {
      this.username = "";
      this.password = "";
      this.isLoggedIn = false;
      this.isWaitingForResponse = false;
    }, 500);
  }

  showCheckBox(): void {
    let alert = this.alertCtrl.create();
    alert.setTitle('How would you like to upload the photo?');

    alert.addInput({
      type: 'radio',
      label: 'Use camera',
      value: 'camera',
      checked: true
    });

    alert.addInput({
      type: 'radio',
      label: 'Use gallery',
      value: 'gallery'
    });

    alert.addButton('Cancel');
    alert.addButton({
      text: 'Okay',
      handler: data => {
        if (new String(data) == "camera")
          this.openCamera();
        else
          this.openGallery();
      }
    });
    alert.present();
  }

  postData(image: String): any {
    let headers: Headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('Authorization', 'Basic ' + btoa(this.username + ':' + this.password));
    return this.http.post("https://lnu-face.herokuapp.com/user/", 'file=' + image, { headers: headers }).map((response: Response) => response.json());
  }

  openGallery(): void {
    this.camera.getPicture({
      destinationType: this.camera.DestinationType.DATA_URL,
      quality: 100,
      encodingType: this.camera.EncodingType.JPEG,
      sourceType: this.camera.PictureSourceType.PHOTOLIBRARY
    }).then(imageData => setTimeout(this.displayResult(imageData), 5000));
  }

  openCamera(): void {
    this.camera.getPicture({
      quality: 100,
      destinationType: this.camera.DestinationType.DATA_URL,
      encodingType: this.camera.EncodingType.JPEG,
      mediaType: this.camera.MediaType.PICTURE
    }).then(imageData => setTimeout(this.displayResult(imageData), 5000));
  }

  displayResult(imageData) {
    this.isWaitingForResponse = true;
    this.postData(imageData).subscribe(
      result => {
        this.isWaitingForResponse = false;
        this.presentAlert('Success', 'PN received!');
        //Todo: display the PN
      },
      err => {
        this.isWaitingForResponse = false
        this.presentAlert('No result found!', 'Please contact the admin.');
      });
  }

  presentAlert(title: string, subTitle: string): void {
    let alert = this.alertCtrl.create({
      title: title,
      subTitle: subTitle,
      buttons: ['Ok']
    });
    alert.present();
  }
}