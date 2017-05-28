import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

@Component({
  selector: 'page-contact',
  templateUrl: 'contact.html'
})
export class ContactPage {

  constructor(public navCtrl: NavController) { }

  members = [
    {
      name: "Jakob Heyder",
      email: "jh223gj@student.lnu.se",
      src: "assets/img/jakob.jpg",
    },
    {
      name: "Oscar Maris",
      email: "om222df@student.lnu.se",
      src: "assets/img/oscar.jpg",
    },
    {
      name: "Henry Pap",
      email: "hp222fq@student.lnu.se",
      src: "assets/img/henry.jpg",
    },
    {
      name: "Walid Balegh",
      email: "wb222au@student.lnu.se",
      src: "assets/img/walid.jpg",
    },
    {
      name: "Sarpreet Singh",
      email: "sb223ce@student.lnu.se",
      src: "assets/img/singh.jpg",
    }
  ];
}