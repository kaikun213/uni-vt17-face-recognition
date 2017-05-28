import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

@Component({
  selector: 'page-about',
  templateUrl: 'about.html'
})
export class AboutPage {

  constructor(public navCtrl: NavController) { }

  slides = [
    {
      title: "Welcome to the Docs!",
      description: "The <b>Facenummer app documentation</b> showcases a useful information regarding the usage of app.",
    },
    {
      title: "What is Facenummer app?",
      description: "<b>Facenummer app</b> is an open source API that enables users to get their <b>Personal Number</b> by uploading their picture.",
    },
    {
      title: "What's next?",
      description: "The <b>Facenummer app</b> is currently building the <b>CRUD</b> functionality for the <b>Admin</b>. The app will be soon updated....",
    }
  ];
}