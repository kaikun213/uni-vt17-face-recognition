var imageCount = 0, imageData = [], imageErrors = 0;
function upload(callback){
    var images = $('#file').get(0).files;
    imageCount = images.length;
    imageData = [];
    imageErrors = 0;

    if(images.length == 0) {
        callback(null, "http://www.pacinno.eu/wp-content/uploads/2014/05/placeholder-Copy.png");
    } else {

    for(var i = 0; i < images.length; i++) {

        var reader = new FileReader();
        reader.images = [];
        reader.onload = function(e){
            uploadImage(e, function(err, link){
                if(err) {
                    imageErrors++;
                    callback(imageErrors, imageData);
                } else {
                    imageData.push(link);
                    if(imageData.length >= imageCount){//done
                        callback(null, imageData);
                    }
                }
            });
        }
        reader.readAsDataURL(images[i]);
    }}
}

function uploadImage(image, callback){
    var iurl = image.target.result.substr(image.target.result.indexOf(",") + 1, image.target.result.length);
    var clientId = '98fb764612f62b2';
    if(imageErrors == 0) {
        $.ajax({
            url: "https://api.imgur.com/3/upload",
            type: "POST",
            datatype: "json",
            data: {
                'image': iurl,
                'type': 'base64'
            },
            success: function(data){
                var link = data.data.link;
                callback(null, link);

            },//calling function which displays url
            error: function(){
                //error handleing
                if(imageErrors == 0) callback(1, null);
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", "Client-ID " + clientId);
            }
        });
    }
}
