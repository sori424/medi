# medi
Android App for Medicine Search, Alarm 

Creating Medicine App with Android Studio in undergraduate project for lecture ICE3037. This app designed with android studio (language: Java) to make users easily find information of the medicine they take. Crop the image area with medicine number which is surrounded by red block. And then using Google cloud vision API to get numbers from cropped image area. In addition, our team included alarm function to alert users to take medicine at the right time. Here's the Github for the implemented code.

Creating Medicine App with Android Studio in undergraduate project for lecture ICE3037. 
This app designed with android studio (language: Java) to make users easily find information of the medicine they take. 
Crop the image area with medicine number which is surrounded by red block. And then using Google cloud vision API to get numbers from cropped image area. 
In addition, our team included alarm function to alert users to take medicine at the right time. 
Here's the <a href="https://github.com/letsgititdana/medi">Github</a> for the implemented code.


<div class="row">
    <div class="col-sm-4 mt-3 mt-md-0">
        <img class="img-fluid rounded z-depth-1" src="{{ '/prj/1.png' | relative_url }}" alt="" title="example image"/>
    </div>
    <div class="col-sm-4 mt-3 mt-md-0">
        <img class="img-fluid rounded z-depth-1" src="{{ '/prj/2.png' | relative_url }}" alt="" title="example image"/>
    </div>
    <div class="col-sm-4 mt-3 mt-md-0">
        <img class="img-fluid rounded z-depth-1" src="{{ '/prj/3.png' | relative_url }}" alt="" title="example image"/>
    </div>
</div>
<div class="caption">
   Flow of the main function of medi. 
</div>

Here's the flow of the main function. Users take picture of the medicine number from prescriptions and then automatically cropped the image area with numbers. Then users can fix the numbers manually if there's error. By the given number, users get information of the medicine which is uploaded on the users' own database automatically. 


<div class="row">
    <div class="col-sm-4 mt-3 mt-md-0">
        <img class="img-fluid rounded z-depth-1" src="{{ '/prj/4.png' | relative_url }}" alt="" title="example image"/>
    </div>
    <div class="col-sm-4 mt-3 mt-md-0">
        <img class="img-fluid rounded z-depth-1" src="{{ '/prj/5.png' | relative_url }}" alt="" title="example image"/>
    </div>
    <div class="col-sm-4 mt-3 mt-md-0">
        <img class="img-fluid rounded z-depth-1" src="{{ '/prj/6.png' | relative_url }}" alt="" title="example image"/>
    </div>
</div>
<div class="caption">
    Secondary function: Alarm setup
</div>

Here's the flow of the alarm function. Users set the time of the alarm for the time to take medicine and it's daily dose.


