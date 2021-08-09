# medi
Android App for Medicine Search, Alarm 

Creating Medicine App with Android Studio in undergraduate project for lecture ICE3037. This app designed with android studio (language: Java) to make users easily find information of the medicine they take. Crop the image area with medicine number which is surrounded by red block. And then using Google cloud vision API to get numbers from cropped image area. In addition, our team included alarm function to alert users to take medicine at the right time. Here's the Github for the implemented code.

Creating Medicine App with Android Studio in undergraduate project for lecture ICE3037. 
This app designed with android studio (language: Java) to make users easily find information of the medicine they take. 
Crop the image area with medicine number which is surrounded by red block. And then using Google cloud vision API to get numbers from cropped image area. 
In addition, our team included alarm function to alert users to take medicine at the right time. 

![1](https://user-images.githubusercontent.com/47997074/128657253-2522c545-47a9-4ec4-af39-50973b7e42f2.png){: width="100" height="100"} | ![2](https://user-images.githubusercontent.com/47997074/128657429-f5dd9f60-27a6-4124-8545-f7aa8f2ec078.png){: width="100" height="100"} | ![3](https://user-images.githubusercontent.com/47997074/128657436-f1d88c89-7bf3-4c5a-95d6-6876aede159c.png){: width="100" height="100"}
<div class="caption">
   Flow of the main function of medi. 
</div>

Here's the flow of the main function. Users take picture of the medicine number from prescriptions and then automatically cropped the image area with numbers. Then users can fix the numbers manually if there's error. By the given number, users get information of the medicine which is uploaded on the users' own database automatically. 


![4](https://user-images.githubusercontent.com/47997074/128657465-7181ba1d-e834-47f8-89ba-e58d31a10a5c.png){: width="100" height="100"} | ![5](https://user-images.githubusercontent.com/47997074/128657474-55c79f4f-3ef2-4fda-8916-11be22815981.png){: width="100" height="100"} | ![6](https://user-images.githubusercontent.com/47997074/128657482-4c64f77c-69a8-402f-a417-b0402a97ac9e.png){: width="100" height="100"}



<div class="caption">
    Secondary function: Alarm setup
</div>

Here's the flow of the alarm function. Users set the time of the alarm for the time to take medicine and it's daily dose.


